package com.hirisun.cloud.ncov.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hirisun.cloud.api.redis.RedisApi;
import com.hirisun.cloud.common.util.FastJsonUtil;
import com.hirisun.cloud.model.ncov.contains.NcovFileupload;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceLevel2Vo;
import com.hirisun.cloud.model.ncov.vo.daas.HomePageDataVo;
import com.hirisun.cloud.model.ncov.vo.daas.NcovDataOverviewVo;
import com.hirisun.cloud.ncov.bean.NcovHomePageData;
import com.hirisun.cloud.ncov.mapper.NcovDataAreaMapper;
import com.hirisun.cloud.ncov.service.NcovDaasService;
import com.hirisun.cloud.ncov.service.NcovHomePageDataService;

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
        if(StringUtils.isNotBlank(res)){
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
		List<NcovDataOverviewVo> dataSharingVos = getDataSharding(NcovFileupload.DAAS_DATA_SHARING);
        homePageData.setDataSharingOverview(dataSharingVos);
        
        //数据建模
        List<NcovDataOverviewVo> dataModelingVos = dataModeling(NcovFileupload.DAAS_DATA_MODELING);
        homePageData.setDataModelingOverview(dataModelingVos);
        
        //数据服务
        Integer serviceCount = ncovDataAreaMapper.serviceCount();
        Integer policeCount = ncovDataAreaMapper.policeCount();
        Integer areaCount = ncovDataAreaMapper.areaCount();
        Long ncovServiceCall = ncovDataAreaMapper.ncovServiceCall();
        Integer dataServiceCount = getDataServiceCount(NcovFileupload.DAAS_DATA_SERVICE);
        
        homePageData.setServiceCount(serviceCount);
        homePageData.setUnitCount(policeCount+areaCount+dataServiceCount);
        homePageData.setAllCall(ncovServiceCall);
        
        //String time = getTime(31);
        Long yesterdayCall = ncovDataAreaMapper.yesterdayCall(getTime(1));
        homePageData.setYesterdayCall(yesterdayCall);
        
        //数据接入
        Map<String, Object> dataAccess = getDataAccess(NcovFileupload.DAAS_DATA_ACCESS);
        homePageData.setTotalCount(Long.valueOf(dataAccess.get("total").toString()));
        homePageData.setResourceCount(Integer.valueOf(dataAccess.get("size").toString()));
        homePageData.setYesterdayCount(Long.valueOf(dataAccess.get("yesterday").toString()));
        
        
        //数据治理
        Map<String, List<DataGovernanceLevel2Vo>> dataGovernanceMap = getDataGovernance(NcovFileupload.DAAS_DATA_GOVERNANCE);
        List<DataGovernanceLevel2Vo> updateTypeVos = dataGovernanceMap.get("updateTypeVos");
        List<DataGovernanceLevel2Vo> updateCycleVos = dataGovernanceMap.get("updateCycleVos");
        
        homePageData.setUpdateType(updateTypeVos);
        homePageData.setUpdateCycle(updateCycleVos);
        return homePageData;
    }

	/**
	 * 获取 数据治理 先从缓存读取，没有则读取文件服务器下载
	 * @param fileId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, List<DataGovernanceLevel2Vo>> getDataGovernance(String dataType) {
		Map<String, List<DataGovernanceLevel2Vo>> dataGovernanceMap = null;
        String dataGovernanceStr = redisApi.getStrValue(NcovKey.HOME_PAGE_DAAS_DATA_GOVERNANCE);
		try {
			if(StringUtils.isNotBlank(dataGovernanceStr)) {
	        	
	        	dataGovernanceMap =JSON.parseObject(dataGovernanceStr,Map.class);
	        	
	        }else {
	        	NcovHomePageData ncovHomePageData = ncovHomePageDataService.getNcovHomePageDataByType(dataType);
				String json = ncovHomePageData.getData();
				if(StringUtils.isNotBlank(json)) {
					dataGovernanceMap = JSON.parseObject(json,Map.class);
					redisApi.setForPerpetual(NcovKey.HOME_PAGE_DAAS_DATA_GOVERNANCE, json);
				}
	        	
	        		
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
        
		return dataGovernanceMap;
	}

	/**
	 * 获取 数据接入 先从缓存读取，没有则读取文件服务器下载
	 * @param fileId
	 * @return
	 */
	private Map<String, Object> getDataAccess(String dataType) {
		Map<String, Object> dataAccess = null;
        String dataAccessStr = redisApi.getStrValue(NcovKey.HOME_PAGE_DAAS_DATA_ACCESS);
        if(StringUtils.isNotBlank(dataAccessStr)) {
        	
        	try {
        		dataAccess = JSON.parseObject(dataAccessStr,Map.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        }else {
        	
        	NcovHomePageData ncovHomePageData = ncovHomePageDataService.getNcovHomePageDataByType(dataType);
			String json = ncovHomePageData.getData();
			if(StringUtils.isNotBlank(json)) {
				dataAccess = JSON.parseObject(json,Map.class);
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
	private Integer getDataServiceCount(String dataType) {
		Integer dataServiceCount = 0;
        String dataServiceCountStr = redisApi.getStrValue(NcovKey.HOME_PAGE_DAAS_DATA_SERVICE_COUNT);
        
        if(StringUtils.isNotBlank(dataServiceCountStr)) {
        	dataServiceCount = Integer.valueOf(dataServiceCountStr);
        }else {
        	
        	NcovHomePageData ncovHomePageData = ncovHomePageDataService.getNcovHomePageDataByType(dataType);
			String json = ncovHomePageData.getData();
			if(StringUtils.isNotBlank(json)) {
				dataServiceCount = Integer.valueOf(json);
				redisApi.setForPerpetual(NcovKey.HOME_PAGE_DAAS_DATA_SERVICE_COUNT, json.toString());
			}
        	
        	
        	
        }
		return dataServiceCount;
	}

	/**
	 * 获取 数据建模数据 先从缓存读取，没有则读取文件服务器下载
	 * @param fileId
	 * @return
	 */
	private List<NcovDataOverviewVo> dataModeling(String dataType) {
		List<NcovDataOverviewVo> dataModelingVos = Lists.newArrayList();
        String dataModelingStr = redisApi.getStrValue(NcovKey.HOME_PAGE_DAAS_DATA_MODELING);
        if(StringUtils.isNotBlank(dataModelingStr)) {
        	dataModelingVos = FastJsonUtil.json2ListBean(dataModelingStr, NcovDataOverviewVo.class);
		}else {
			
			NcovHomePageData ncovHomePageData = ncovHomePageDataService.getNcovHomePageDataByType(dataType);
			String json = ncovHomePageData.getData();
			if(StringUtils.isNotBlank(json)) {
				dataModelingVos = FastJsonUtil.json2ListBean(json, NcovDataOverviewVo.class);
				redisApi.setForPerpetual(NcovKey.HOME_PAGE_DAAS_DATA_MODELING, json);
			}
			
				
		}
		return dataModelingVos;
	}

	/**
	 * 获取 数据共享先从缓存读取，没有则读取文件服务器下载
	 * @param fileId
	 * @return
	 */
	private List<NcovDataOverviewVo> getDataSharding(String dataType) {
		List<NcovDataOverviewVo> dataSharingVos = Lists.newArrayList();
		
		String dataSharingStr = redisApi.getStrValue(NcovKey.HOME_PAGE_DAAS_DATA_SHARING);
		if(StringUtils.isNotBlank(dataSharingStr)) {
			dataSharingVos = FastJsonUtil.json2ListBean(dataSharingStr, NcovDataOverviewVo.class);
		}else {
			NcovHomePageData ncovHomePageData = ncovHomePageDataService.getNcovHomePageDataByType(dataType);
			String json = ncovHomePageData.getData();
			if(StringUtils.isNotBlank(json)) {
				dataSharingVos = FastJsonUtil.json2ListBean(json, NcovDataOverviewVo.class);
				redisApi.setForPerpetual(NcovKey.HOME_PAGE_DAAS_DATA_SHARING, json);
			}
		}
		return dataSharingVos;
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

}
