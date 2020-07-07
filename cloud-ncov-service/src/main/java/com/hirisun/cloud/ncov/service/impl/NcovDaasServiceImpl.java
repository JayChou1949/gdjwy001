package com.hirisun.cloud.ncov.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.hirisun.cloud.common.util.FastJsonUtil;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceLevel2Vo;
import com.hirisun.cloud.model.ncov.vo.daas.HomePageDataVo;
import com.hirisun.cloud.model.ncov.vo.daas.NcovDataOverviewVo;
import com.hirisun.cloud.ncov.mapper.NcovDataAreaMapper;
import com.hirisun.cloud.ncov.service.NcovDaasService;
import com.hirisun.cloud.ncov.util.NcovEcsImportUtil;

@Service
public class NcovDaasServiceImpl implements NcovDaasService {

	@Value("${ncov.datasharing.name}")
    private String dataSharingName;
    @Value("${ncov.datamodeling.name}")
    private String dataModelingName;
    @Value("${ncov.datagovernance.name}")
    private String dataGovernanceName;
    @Value("${ncov.dataaccess.name}")
    private String dataAccessName;
    @Value("${ncov.dataservice.name}")
    private String dataServiceName;
	
    @Autowired
    private NcovDataAreaMapper ncovDataAreaMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
	@Override
	public HomePageDataVo getHomePageBigData() throws Exception {
		
		HomePageDataVo homePage = null;
		String  res = stringRedisTemplate.opsForValue().get(NcovKey.HOME_PAGE);
        if(StringUtils.isNotBlank(res)){
        	homePage = JSON.parseObject(res,HomePageDataVo.class);
            if(homePage != null)return  homePage;
        }
        homePage = homePage();
        stringRedisTemplate.opsForValue().set(NcovKey.HOME_PAGE, JSON.toJSONString(homePage),30, TimeUnit.MINUTES);
		
		return homePage;
	}

	private boolean setNx(String key) {
		
		Boolean isOk = stringRedisTemplate.opsForValue().setIfAbsent(key, key, 1, TimeUnit.MINUTES);
		return isOk;
	}
	
	private String get(String key) {
		String value = stringRedisTemplate.opsForValue().get(key);
		return value;
	}
	
	private void set(String key,String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}
	private void set(String key,String value,long timeout, TimeUnit unit) {
		stringRedisTemplate.opsForValue().set(key, value,timeout,unit);
	}
	
	
	//读取 excel 数据共享数据
	private void getExcelData(List<NcovDataOverviewVo> dataSharingDtos,String fileName,
			 Integer num, Integer sheetNum) {
		
		List<List<Object>> dataSharing = NcovEcsImportUtil.ncovDataList(fileName,num,sheetNum);
		for (List<Object> objects : dataSharing) {
			NcovDataOverviewVo ncovDataOverviewVo = new NcovDataOverviewVo();
			ncovDataOverviewVo.setName((String) objects.get(0));
			ncovDataOverviewVo.setCount((String) objects.get(1));
			ncovDataOverviewVo.setUnit((String) objects.get(2));
		    dataSharingDtos.add(ncovDataOverviewVo);
		}
	}
	
	public HomePageDataVo homePage() throws Exception {
		
		HomePageDataVo homePageData = new HomePageDataVo();
		
        //  数据共享
		List<NcovDataOverviewVo> dataSharingVos = getDataSharding();
        homePageData.setDataSharingOverview(dataSharingVos);
        
        //  数据建模
        List<NcovDataOverviewVo> dataModelingVos = dataModeling();
        homePageData.setDataModelingOverview(dataModelingVos);
        
        //  数据服务
        Integer serviceCount = ncovDataAreaMapper.serviceCount();
        Integer policeCount = ncovDataAreaMapper.policeCount();
        Integer areaCount = ncovDataAreaMapper.areaCount();
        Long ncovServiceCall = ncovDataAreaMapper.ncovServiceCall();
        Integer dataServiceCount = getDataServiceCount();
        
        homePageData.setServiceCount(serviceCount);
        homePageData.setUnitCount(policeCount+areaCount+dataServiceCount);
        homePageData.setAllCall(ncovServiceCall);
        
        //String time = getTime(31);
        Long yesterdayCall = ncovDataAreaMapper.yesterdayCall(getTime(1));
        homePageData.setYesterdayCall(yesterdayCall);
        
    //  数据接入
        Long total = 0L;
        Long yesterday = 0L;
        List<List<Object>> overview = getDataAccess();
        
        for (List<Object> list : overview) {
            total += Long.valueOf((String)list.get(5));
            yesterday += Long.valueOf((String)list.get(6));
        }
        homePageData.setTotalCount(total);
        homePageData.setResourceCount(overview.size());
        homePageData.setYesterdayCount(yesterday);
        
        
        //  数据治理
        Map<String, List<DataGovernanceLevel2Vo>> dataGovernanceMap = getDataGovernance();
        List<DataGovernanceLevel2Vo> updateTypeVos = dataGovernanceMap.get("updateTypeVos");
        List<DataGovernanceLevel2Vo> updateCycleVos = dataGovernanceMap.get("updateCycleVos");
        
        homePageData.setUpdateType(updateTypeVos);
        homePageData.setUpdateCycle(updateCycleVos);
        return homePageData;
    }

	@SuppressWarnings("unchecked")
	private Map<String, List<DataGovernanceLevel2Vo>> getDataGovernance() {
		Map<String, List<DataGovernanceLevel2Vo>> dataGovernanceMap = null;
        String dataGovernanceStr = get(NcovKey.HOMG_PAGE_DATA_GOVERNANCE);
        if(StringUtils.isNotBlank(dataGovernanceStr)) {
        	dataGovernanceMap = (Map<String, List<DataGovernanceLevel2Vo>>) FastJsonUtil.json2Bean(dataGovernanceStr, 
        			new TypeReference<List<Map<String, List<DataGovernanceLevel2Vo>>>>(){});
        }else {
        	dataGovernanceMap = NcovEcsImportUtil.getDataGovernanceMap(dataGovernanceName);
        	if(MapUtils.isEmpty(dataGovernanceMap))
        		set(NcovKey.HOMG_PAGE_DATA_GOVERNANCE, FastJsonUtil.bean2Json(dataGovernanceMap));
        }
		return dataGovernanceMap;
	}

	private List<List<Object>> getDataAccess() {
		List<List<Object>> overview = null;
        String dataAccessStr = get(NcovKey.HOMG_PAGE_DATA_ACCESS);
        if(StringUtils.isNotBlank(dataAccessStr)) {
			overview = FastJsonUtil.json2ListBean2(dataAccessStr, new TypeReference<List<List<Object>>>(){});
        }else {
        	overview = NcovEcsImportUtil.ncovDataList(dataAccessName,1,0);
            set(NcovKey.HOMG_PAGE_DATA_ACCESS, FastJsonUtil.bean2Json(overview));
        }
		return overview;
	}

	private Integer getDataServiceCount() {
		Integer dataServiceCount = 0;
        String dataServiceCountStr = get(NcovKey.HOMG_PAGE_DATA_SERVICE_COUNT);
        
        if(StringUtils.isNotBlank(dataServiceCountStr)) {
        	dataServiceCount = Integer.valueOf(dataServiceCountStr);
        }else {
        	
        	dataServiceCount = NcovEcsImportUtil.getNcovDataServiceCount(dataServiceName);
            set(NcovKey.HOMG_PAGE_DATA_SERVICE_COUNT, dataServiceCount.toString());
        	
        }
		return dataServiceCount;
	}

	private List<NcovDataOverviewVo> dataModeling() {
		List<NcovDataOverviewVo> dataModelingVos = Lists.newArrayList();
        String dataModelingStr = get(NcovKey.HOMG_PAGE_DATA_MODELING);
        if(StringUtils.isNotBlank(dataModelingStr)) {
        	dataModelingVos = FastJsonUtil.json2ListBean(dataModelingStr, NcovDataOverviewVo.class);
		}else {
			getExcelData(dataModelingVos,dataModelingName,1,0);
	        //缓存存储
	        set(NcovKey.HOMG_PAGE_DATA_MODELING, FastJsonUtil.bean2Json(dataModelingVos));
		}
		return dataModelingVos;
	}

	private List<NcovDataOverviewVo> getDataSharding() {
		List<NcovDataOverviewVo> dataSharingVos = Lists.newArrayList();
		
		String dataSharingStr = get(NcovKey.HOMG_PAGE_DATA_SHARING);
		if(StringUtils.isNotBlank(dataSharingStr)) {
			dataSharingVos = FastJsonUtil.json2ListBean(dataSharingStr, NcovDataOverviewVo.class);
		}else {
			//获取共享数据
			getExcelData(dataSharingVos,dataSharingName,1,0);
	        //缓存存储
	        set(NcovKey.HOMG_PAGE_DATA_SHARING, FastJsonUtil.bean2Json(dataSharingVos));;
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
