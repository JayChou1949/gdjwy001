package com.hirisun.cloud.ncov.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.dto.daas.NcovDataOverviewDTO;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceLevel2Vo;
import com.hirisun.cloud.model.ncov.vo.daas.HomePageDataVo;
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
        stringRedisTemplate.opsForValue().set(NcovKey.HOME_PAGE, JSON.toJSONString(homePage),5, TimeUnit.HOURS);
		
		return homePage;
	}

	
	
	public HomePageDataVo homePage() throws Exception {
		HomePageDataVo homePageData = new HomePageDataVo();
        //  数据共享
        List<List<Object>> dataSharing = NcovEcsImportUtil.ncovDataList(dataSharingName,1,0);
        List<NcovDataOverviewDTO> dataSharingDtos = Lists.newArrayList();
        for (List<Object> objects : dataSharing) {
            NcovDataOverviewDTO ncovDataOverviewDto = new NcovDataOverviewDTO();
            ncovDataOverviewDto.setName((String) objects.get(0));
            ncovDataOverviewDto.setCount((String) objects.get(1));
            ncovDataOverviewDto.setUnit((String) objects.get(2));
            dataSharingDtos.add(ncovDataOverviewDto);
        }
        homePageData.setDataSharingOverview(dataSharingDtos);
        //  数据建模
        List<List<Object>> dataModeling = NcovEcsImportUtil.ncovDataList(dataModelingName,1,0);
        List<NcovDataOverviewDTO> dataModelingDtos = Lists.newArrayList();
        for (List<Object> objects : dataModeling) {
        	NcovDataOverviewDTO ncovDataOverviewDto = new NcovDataOverviewDTO();
            ncovDataOverviewDto.setName((String) objects.get(0));
            ncovDataOverviewDto.setCount((String) objects.get(1));
            ncovDataOverviewDto.setUnit((String) objects.get(2));
            dataModelingDtos.add(ncovDataOverviewDto);
        }
        homePageData.setDataModelingOverview(dataModelingDtos);
        //  数据服务
        Integer serviceCount = ncovDataAreaMapper.serviceCount();
        homePageData.setServiceCount(serviceCount);
        Integer policeCount = ncovDataAreaMapper.policeCount();
        Integer areaCount = ncovDataAreaMapper.areaCount();
        List<List<Object>> dataServiceArea = NcovEcsImportUtil.ncovDataList(dataServiceName,3,0);
        List<List<Object>> dataServiceGov = NcovEcsImportUtil.ncovDataList(dataServiceName,3,1);
        Set<String> set = new HashSet<>();
        for (List<Object> objects : dataServiceArea) {
            String name = (String) objects.get(3);
            set.add(name);
        }
        for (List<Object> objects : dataServiceGov) {
            String name = (String) objects.get(3);
            set.add(name);
        }
        homePageData.setUnitCount(policeCount+areaCount+set.size());
        Long ncovServiceCall = ncovDataAreaMapper.ncovServiceCall();
        homePageData.setAllCall(ncovServiceCall);
        String time = getTime(1);
        //String time = getTime(31);
        Long yesterdayCall = ncovDataAreaMapper.yesterdayCall(time);
        homePageData.setYesterdayCall(yesterdayCall);
        //  数据接入
        List<List<Object>> overview = NcovEcsImportUtil.ncovDataList(dataAccessName,1,0);
        Long total = 0L;
        Long yesterday = 0L;
        for (List<Object> list : overview) {
            total += Long.valueOf((String)list.get(5));
            yesterday += Long.valueOf((String)list.get(6));
        }
        homePageData.setTotalCount(total);
        homePageData.setResourceCount(overview.size());
        homePageData.setYesterdayCount(yesterday);
        //  数据治理
        List<DataGovernanceLevel2Vo> updateTypeDtos = Lists.newArrayList();
        List<List<Object>> updateType = NcovEcsImportUtil.ncovDataList(dataGovernanceName,1,2);
        for (List<Object> objects : updateType) {
        	DataGovernanceLevel2Vo dataGovernanceLevel2 = new DataGovernanceLevel2Vo();
            dataGovernanceLevel2.setType((String) objects.get(1));
            dataGovernanceLevel2.setNum((String) objects.get(2));
            dataGovernanceLevel2.setSum((String) objects.get(3));
            dataGovernanceLevel2.setPercentage((String) objects.get(4));
            updateTypeDtos.add(dataGovernanceLevel2);
        }
        homePageData.setUpdateType(updateTypeDtos);
        List<List<Object>> updateCycle = NcovEcsImportUtil.ncovDataList(dataGovernanceName,1,3);
        List<DataGovernanceLevel2Vo> updateCycleDtos = Lists.newArrayList();
        for (List<Object> objects : updateCycle) {
        	DataGovernanceLevel2Vo dataGovernanceLevel2 = new DataGovernanceLevel2Vo();
            dataGovernanceLevel2.setType((String) objects.get(1));
            dataGovernanceLevel2.setNum((String) objects.get(2));
            dataGovernanceLevel2.setSum((String) objects.get(3));
            dataGovernanceLevel2.setPercentage((String) objects.get(4));
            updateCycleDtos.add(dataGovernanceLevel2);
        }
        homePageData.setUpdateCycle(updateCycleDtos);
        return homePageData;
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
