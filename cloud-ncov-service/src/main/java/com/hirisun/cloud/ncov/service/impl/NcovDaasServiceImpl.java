package com.hirisun.cloud.ncov.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.hirisun.cloud.api.redis.RedisApi;
import com.hirisun.cloud.common.util.FastJsonUtil;
import com.hirisun.cloud.model.ncov.contains.NcovFileupload;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.vo.daas.CallAndNameVo;
import com.hirisun.cloud.model.ncov.vo.daas.CallAndTimeVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataAccessVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceLevel2Vo;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataModelingVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataServiceVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataSharingVo;
import com.hirisun.cloud.model.ncov.vo.daas.HomePageDataVo;
import com.hirisun.cloud.model.ncov.vo.daas.NcovDataOverviewVo;
import com.hirisun.cloud.ncov.bean.NcovHomePageData;
import com.hirisun.cloud.ncov.mapper.NcovDataAreaMapper;
import com.hirisun.cloud.ncov.service.NcovDaasService;
import com.hirisun.cloud.ncov.service.NcovHomePageDataService;
import com.hirisun.cloud.ncov.util.NcovEcsImportUtil;

@Service
@RefreshScope
public class NcovDaasServiceImpl implements NcovDaasService {

    @Autowired
    private NcovDataAreaMapper ncovDataAreaMapper;
    @Autowired
    private RedisApi redisApi;
    @Autowired
    private NcovHomePageDataService ncovHomePageDataService;
    
    
	/**
	 * 首页daas模块疫情数据,先取缓存
	 */
	public HomePageDataVo getHomePageBigData() throws Exception {
		
		HomePageDataVo homePage = null;
		String  res = redisApi.getStrValue(NcovKey.HOME_PAGE);
        if(StringUtils.isNotBlank(res) && !"null".equals(res)){
        	homePage = JSON.parseObject(res,HomePageDataVo.class);
            if(homePage != null)return  homePage;
        }
        homePage = homePage();
        redisApi.setForTerminable(NcovKey.HOME_PAGE, JSON.toJSONString(homePage),30, TimeUnit.MINUTES);
		return homePage;
	}

	/**
	 * 首页daas模块疫情数据,读取excel和数据库统计
	 */
	public HomePageDataVo homePage() throws Exception {
		
		HomePageDataVo homePageData = new HomePageDataVo();
		
        //数据共享
		DataSharingVo dataSharingVo = getDataSharding(NcovFileupload.DAAS_DATA_SHARING);
		if(dataSharingVo != null) homePageData.setDataSharingOverview(dataSharingVo.getDataSharingOverview());
        
        //数据建模
        DataModelingVo dataModelingVo = dataModeling(NcovFileupload.DAAS_DATA_MODELING);
        if(dataModelingVo != null) homePageData.setDataModelingOverview(dataModelingVo.getDataModelingOverview());
        
        //数据服务
        Integer serviceCount = ncovDataAreaMapper.serviceCount();
        Integer policeCount = ncovDataAreaMapper.policeCount();
        Integer areaCount = ncovDataAreaMapper.areaCount();
        Long ncovServiceCall = ncovDataAreaMapper.ncovServiceCall();
        Map<String, Map<String, Long>> map = getDataServiceCount(NcovFileupload.DAAS_DATA_SERVICE);
        
        Integer dataServiceCount = extracted(map);
        
        homePageData.setServiceCount(serviceCount);
		homePageData.setUnitCount(policeCount+areaCount+dataServiceCount);
        homePageData.setAllCall(ncovServiceCall);
        
        //String time = getTime(31);
        Long yesterdayCall = ncovDataAreaMapper.yesterdayCall(getTime(1));
        homePageData.setYesterdayCall(yesterdayCall);
        
        //数据接入
        DataAccessVo dataAccess = getDataAccess(NcovFileupload.DAAS_DATA_ACCESS);
        if(dataAccess != null) {
        	homePageData.setTotalCount(dataAccess.getTotalCount());
            homePageData.setResourceCount(dataAccess.getResourceCount());
            homePageData.setYesterdayCount(dataAccess.getTotalCount());
        }
        
        //数据治理
        DataGovernanceVo dataGovernanceVo = getDataGovernance(NcovFileupload.DAAS_DATA_GOVERNANCE);
        if(dataGovernanceVo != null) {
        	
        	List<DataGovernanceLevel2Vo> updateTypeVos = dataGovernanceVo.getUpdateType();
        	List<DataGovernanceLevel2Vo> updateCycleVos = dataGovernanceVo.getUpdateCycle();
        	
            homePageData.setUpdateType(updateTypeVos);
            homePageData.setUpdateCycle(updateCycleVos);
        }
        
        return homePageData;
    }

	private Integer extracted(Map<String, Map<String, Long>> map) {
		Integer dataServiceCount = 0;
        
        if(MapUtils.isNotEmpty(map)) {
        	Map<String, Long> areaMap = map.get("areaMap");
        	Map<String, Long> govMap = map.get("govMap");
        	Set<String> keySet = new HashSet<String>();
        	keySet.addAll(areaMap.keySet());
        	keySet.addAll(govMap.keySet());
        	dataServiceCount = keySet.size();
        }
		return dataServiceCount;
	}

	
	/**
	 * 获取 数据治理 先从缓存读取，没有则读取文件服务器下载
	 * @param fileId
	 * @return
	 */
	private DataGovernanceVo getDataGovernance(String dataType) {
		DataGovernanceVo dataGovernanceVo = null;
        String dataGovernanceStr = redisApi.getStrValue(NcovKey.HOME_PAGE_DAAS_DATA_GOVERNANCE);
		if(StringUtils.isNotBlank(dataGovernanceStr) && !"null".equals(dataGovernanceStr)) {
        	
			dataGovernanceVo =JSON.parseObject(dataGovernanceStr,DataGovernanceVo.class);
        	
        }else {
        	NcovHomePageData ncovHomePageData = ncovHomePageDataService.getNcovHomePageDataByType(dataType);
			String json = ncovHomePageData.getData();
			if(StringUtils.isNotBlank(json)) {
				dataGovernanceVo = JSON.parseObject(json,DataGovernanceVo.class);
				redisApi.setForPerpetual(NcovKey.HOME_PAGE_DAAS_DATA_GOVERNANCE, json);
			}
        }
		return dataGovernanceVo;
	}

	/**
	 * 获取 数据接入 先从缓存读取，没有则读取文件服务器下载
	 * @param fileId
	 * @return
	 */
	private DataAccessVo getDataAccess(String dataType) {
		DataAccessVo dataAccess = null;
        String dataAccessStr = redisApi.getStrValue(NcovKey.HOME_PAGE_DAAS_DATA_ACCESS);
        if(StringUtils.isNotBlank(dataAccessStr)  && !"null".equals(dataAccessStr)) {
        	dataAccess = JSON.parseObject(dataAccessStr,DataAccessVo.class);
        }else {
        	
        	NcovHomePageData ncovHomePageData = ncovHomePageDataService.getNcovHomePageDataByType(dataType);
			String json = ncovHomePageData.getData();
			if(StringUtils.isNotBlank(json)) {
				dataAccess = JSON.parseObject(json,DataAccessVo.class);
				redisApi.setForPerpetual(NcovKey.HOME_PAGE_DAAS_DATA_ACCESS, json);
				
			}
        		
        }
		return dataAccess;
	}

	/**
	 * 获取 数据服务数据 先从缓存读取，没有则读取文件服务器下载
	 * @param fileId
	 * @return
	 */
	private Map<String, Map<String, Long>> getDataServiceCount(String dataType) {
		Map<String, Map<String, Long>> dataGovernanceMap = null;
        String dataServiceCountStr = redisApi.getStrValue(NcovKey.HOME_PAGE_DAAS_DATA_SERVICE_COUNT);
        
        if(StringUtils.isNotBlank(dataServiceCountStr) && !"null".equals(dataServiceCountStr)) {
        	dataGovernanceMap =JSON.parseObject(dataServiceCountStr,new TypeReference<Map<String, Map<String, Long>>>(){});
        	
        }else {
        	
        	NcovHomePageData ncovHomePageData = ncovHomePageDataService.getNcovHomePageDataByType(dataType);
			String json = ncovHomePageData.getData();
			if(StringUtils.isNotBlank(json)) {
				dataGovernanceMap =JSON.parseObject(dataServiceCountStr, new TypeReference<Map<String, Map<String, Long>>>(){});
				redisApi.setForPerpetual(NcovKey.HOME_PAGE_DAAS_DATA_SERVICE_COUNT, json.toString());
			}
        }
		return dataGovernanceMap;
	}

	/**
	 * 获取 数据建模数据 先从缓存读取，没有则读取文件服务器下载
	 * @param fileId
	 * @return
	 */
	private DataModelingVo dataModeling(String dataType) {
		DataModelingVo dataModelingVo = null;
        String dataModelingStr = redisApi.getStrValue(NcovKey.HOME_PAGE_DAAS_DATA_MODELING);
        if(StringUtils.isNotBlank(dataModelingStr) && !"null".equals(dataModelingStr)) {
        	dataModelingVo = JSON.parseObject(dataModelingStr,DataModelingVo.class);
		}else {
			
			NcovHomePageData ncovHomePageData = ncovHomePageDataService.getNcovHomePageDataByType(dataType);
			String json = ncovHomePageData.getData();
			if(StringUtils.isNotBlank(json)) {
				dataModelingVo = JSON.parseObject(json,DataModelingVo.class);
				redisApi.setForPerpetual(NcovKey.HOME_PAGE_DAAS_DATA_MODELING, json);
			}
			
				
		}
		return dataModelingVo;
	}

	/**
	 * 获取 数据共享先从缓存读取，没有则读取文件服务器下载
	 * @param fileId
	 * @return
	 */
	private DataSharingVo getDataSharding(String dataType) {
		DataSharingVo dataSharingVo = null;
		
		String dataSharingStr = redisApi.getStrValue(NcovKey.HOME_PAGE_DAAS_DATA_SHARING);
		if(StringUtils.isNotBlank(dataSharingStr) && !"null".equals(dataSharingStr)) {
			dataSharingVo = JSON.parseObject(dataSharingStr,DataSharingVo.class);
		}else {
			NcovHomePageData ncovHomePageData = ncovHomePageDataService.getNcovHomePageDataByType(dataType);
			String json = ncovHomePageData.getData();
			if(StringUtils.isNotBlank(json)) {
				dataSharingVo = JSON.parseObject(json,DataSharingVo.class);
				redisApi.setForPerpetual(NcovKey.HOME_PAGE_DAAS_DATA_SHARING, json);
			}
		}
		return dataSharingVo;
	}

	
	private String getTime(Integer day){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //  当前时间毫秒数
        long timeMillis = System.currentTimeMillis();
        //  七天前毫秒数
        long sevenDaysMillis = timeMillis - day*24L*60*60*1000;
        Date date =new Date(sevenDaysMillis);
        return simpleDateFormat.format(date);
    }

	@Override
	public DataAccessVo getDataAccessVo() throws Exception {
		DataAccessVo dataAccessVo = getDataAccess(NcovFileupload.DAAS_DATA_ACCESS);
		return dataAccessVo;
	}

	@Override
	public DataGovernanceVo getDataGovernance() {
		DataGovernanceVo dataGovernance = getDataGovernance(NcovFileupload.DAAS_DATA_GOVERNANCE);
		return dataGovernance;
	}

	@Override
	public DataServiceVo getDataService() {
		
        DataServiceVo dataService = new DataServiceVo();
        
        Integer serviceCount = ncovDataAreaMapper.serviceCount();
        dataService.setServiceCount(serviceCount);
        Integer policeCount = ncovDataAreaMapper.policeCount();
        Integer areaCount = ncovDataAreaMapper.areaCount();
        dataService.setPoliceCount(policeCount+areaCount);
        Long ncovPlatform = ncovDataAreaMapper.ncovPlatform();
        dataService.setGovCall(ncovPlatform);
        Long ncovServiceCall = ncovDataAreaMapper.ncovServiceCall();
        dataService.setPoliceCall(ncovServiceCall-ncovPlatform);
        String time = getTime(1);
        // String time = getTime(31);
        Long yesterdayCall = ncovDataAreaMapper.yesterdayCall(time);
        dataService.setYesterdayCall(yesterdayCall);
        List<CallAndNameVo> callByPolice = ncovDataAreaMapper.callByPolice();
        for (CallAndNameVo callAndNameDto : callByPolice) {
            String name = callAndNameDto.getName();
            if ("科信".equals(name)){
                callAndNameDto.setName("大数据办");
            }
        }
        List<CallAndNameVo> callByArea = ncovDataAreaMapper.callByArea();
        for (CallAndNameVo callAndNameDto : callByArea) {
            callAndNameDto.setName(callAndNameDto.getName()+"科信");
        }
        callByPolice.addAll(callByArea);
        Collections.sort(callByPolice);
        dataService.setPolice(callByPolice.subList(0,10));
        String last7Time = getTime(6);
        //String last7Time = getTime(36);
        List<CallAndTimeVo> callAll = ncovDataAreaMapper.callAll(last7Time);
        List<CallAndTimeVo> callByApp = ncovDataAreaMapper.callByApp(last7Time);
        dataService.setGovLately(callByApp);
        for (CallAndTimeVo callAndTimeDto : callAll) {
            Map<String, Long> collect = callByApp.stream().collect(Collectors.toMap(CallAndTimeVo::getTime, CallAndTimeVo::getCount));
            Long aLong = collect.get(callAndTimeDto.getTime());
            callAndTimeDto.setCount(callAndTimeDto.getCount()-aLong);
        }
        dataService.setPoliceLately(callAll);
        
        Map<String, Map<String, Long>> map = getDataServiceCount(NcovFileupload.DAAS_DATA_SERVICE);
        Integer dataServiceCount = extracted(map);
        
        Map<String, Long> areaMap = map.get("areaMap");
    	Map<String, Long> govMap = map.get("govMap");
        
        dataService.setCity(NcovEcsImportUtil.mapSort(areaMap));
        dataService.setGovDirect(NcovEcsImportUtil.mapSort(govMap));
        dataService.setGovCount(dataServiceCount);
        return dataService;
    
		
	}

	@Override
	public DataModelingVo getDataModeling() {
		DataModelingVo dataModeling = dataModeling(NcovFileupload.DAAS_DATA_MODELING);
		return dataModeling;
	}

	@Override
	public DataSharingVo getDataSharing() {
		DataSharingVo dataSharingVo = getDataSharding(NcovFileupload.DAAS_DATA_SHARING);
		return dataSharingVo;
	}

}
