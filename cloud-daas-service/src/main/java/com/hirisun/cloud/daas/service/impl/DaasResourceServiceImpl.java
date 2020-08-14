package com.hirisun.cloud.daas.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.redis.RedisApi;
import com.hirisun.cloud.common.consts.RedisKey;
import com.hirisun.cloud.common.util.AreaPoliceCategoryUtils;
import com.hirisun.cloud.daas.bean.DaasResource;
import com.hirisun.cloud.daas.mapper.DaasResourceMapper;
import com.hirisun.cloud.daas.service.DaasResourceService;
import com.hirisun.cloud.model.daas.vo.ServiceIssueVo;
import com.hirisun.cloud.model.daas.vo.ServiceQualityVo;
import com.hirisun.cloud.model.daas.vo.ServiceRequestVo;
import com.hirisun.cloud.model.daas.vo.ServiceSubscribeVo;
import com.hirisun.cloud.model.daas.vo.YqServiceDetailVo;
import com.hirisun.cloud.model.daas.vo.YqStatisticsVo;
import com.hirisun.cloud.model.param.PageParam;
import com.hirisun.cloud.model.param.ServiceIssueParam;
import com.hirisun.cloud.model.param.ServiceSubscribeParam;
import com.hirisun.cloud.model.service.alter.vo.ServiceAlterVo;
import com.hirisun.cloud.model.service.publish.vo.ServicePublishVo;

@Service
public class DaasResourceServiceImpl implements DaasResourceService {
	
    @Autowired
    private DaasResourceMapper daasResourceMapper;
    @Autowired
    private RedisApi redisApi;

    @Override
    public Page<DaasResource> getPage(Page<DaasResource> page, String name, String dataFrom, 
    		String collectionUnit, String category) {
        return daasResourceMapper.getPage(page,name,dataFrom,collectionUnit,category);
    }

    @Override
    public List<DaasResource> getList(String name, String dataFrom, 
    		String collectionUnit, String category) {
        return daasResourceMapper.getPage(name,dataFrom,collectionUnit,category);
    }

    @Override
    public List getColumnConfig() {
    	
    	String configJson = null;
    	
    	Object object = redisApi.get(RedisKey.KEY_DAAS_COLUMN_CONFIG);
    	if(object != null) {
    		return (List) object;
    	}
    	
        if (StringUtils.isEmpty(configJson)) {
            List<String> m = new ArrayList<>();
            m.add("name");
            m.add("version");
            m.add("provider");
            m.add("category");
            m.add("createTime");
            configJson = JSONArray.toJSONString(m);
            redisApi.setForPerpetual(RedisKey.KEY_DAAS_COLUMN_CONFIG, configJson);
        }
        return JSONArray.parseObject(configJson, LinkedList.class);
    }

    @Override
    public void saveColumnConfig(List config) {
        String configJson = JSONArray.toJSONString(config);
        redisApi.setForPerpetual(RedisKey.KEY_DAAS_COLUMN_CONFIG, configJson);
    }

    @Override
    public void resetColumnConfig() {
        redisApi.delete(RedisKey.KEY_DAAS_COLUMN_CONFIG);
    }

    @Override
    public Page<ServiceIssueVo> serviceStatisticsByIssue(ServiceIssueParam param) {
        //设置分页参数
        Page<ServiceIssueVo> page = new Page<>();
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        //组装查询条件
        QueryWrapper<DaasResource> wrapper = new QueryWrapper<>();
        String area = param.getArea();
        String policeCategory = param.getPoliceCategory();
        String startDate = param.getStartDate();
        String endDate = param.getEndDate();
        if (!StringUtils.isEmpty(area)){
            wrapper.like("AREA_NAME",area);
        }
        if (!StringUtils.isEmpty(policeCategory)){
            wrapper.eq("POLICE_CATEGORY",policeCategory);
        }
        if (!StringUtils.isEmpty(startDate)){
            wrapper.ge("TO_CHAR(CREATE_TIME, 'yyyy-MM-dd')",startDate);
        }
        if (!StringUtils.isEmpty(endDate)){
            wrapper.le("TO_CHAR(CREATE_TIME, 'yyyy-MM-dd')",endDate);
        }
        Integer catalog = param.getCatalog();
        if (catalog != null){
            if (catalog == 1){//DASS
                wrapper.eq("CATA_LOG", 7);
            }else if (catalog == 2){//PASS
                wrapper.in("CATA_LOG", 8,9,10);
            }
        }
        wrapper.isNotNull("NAME");
        wrapper.orderByDesc("CREATE_TIME");
        List<ServiceIssueVo> serviceIssueVos = daasResourceMapper.serviceStatisticsByIssue(page, wrapper);
        if (catalog != null && catalog == 2){//PASS
            for (ServiceIssueVo serviceIssueVo : serviceIssueVos) {
                serviceIssueVo.setAreaName(AreaPoliceCategoryUtils.getAreaName(serviceIssueVo.getName()));
                serviceIssueVo.setPoliceCategory(AreaPoliceCategoryUtils.getPoliceCategory(serviceIssueVo.getName()));
            }
        }
        page.setRecords(serviceIssueVos);
        return page;
    }

    @Override
    public Page<ServiceSubscribeVo> serviceStatisticsBySubscribe(ServiceSubscribeParam param) {
        //设置分页参数
        Page<ServiceSubscribeVo> page = new Page<>();
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        //组装查询条件
        QueryWrapper<DaasResource> wrapper = new QueryWrapper<>();
        String area = param.getArea();
        String policeCategory = param.getPoliceCategory();
        String startDate = param.getStartDate();
        String endDate = param.getEndDate();
        String name = param.getName();
        Integer type = param.getType();
        if (type == 1){//服务
            if (!StringUtils.isEmpty(area)){
                wrapper.like("B.AREA_NAME",area);
            }
            if (!StringUtils.isEmpty(policeCategory)){
                wrapper.eq("B.POLICE_CATEGORY",policeCategory);
            }
            if (!StringUtils.isEmpty(name)){
                wrapper.like("B.NAME",name);
            }
            if (!StringUtils.isEmpty(startDate)){
                wrapper.ge("TO_CHAR(B.CREATE_TIME, 'yyyy-MM-dd')",startDate);
            }
            if (!StringUtils.isEmpty(endDate)){
                wrapper.le("TO_CHAR(B.CREATE_TIME, 'yyyy-MM-dd')",endDate);
            }
        }else if (type == 2){//应用
            if (!StringUtils.isEmpty(area)){
                wrapper.like("I.AREA_NAME",area);
            }
            if (!StringUtils.isEmpty(policeCategory)){
                wrapper.eq("I.POLICE_CATEGORY",policeCategory);
            }
            if (!StringUtils.isEmpty(name)){
                wrapper.like("SUBSTR(I.ALIAS, 0, INSTR(I.ALIAS,'（')-1)",name);
            }
            if (!StringUtils.isEmpty(startDate)){
                wrapper.ge("TO_CHAR(I.CREATED_AT, 'yyyy-MM-dd')",startDate);
            }
            if (!StringUtils.isEmpty(endDate)){
                wrapper.le("TO_CHAR(I.CREATED_AT, 'yyyy-MM-dd')",endDate);
            }
        }
        Integer catalog = param.getCatalog();
        if (catalog != null){
            if (catalog == 1){//DASS
                wrapper.eq("I.CATA_LOG", 7);
            }else if (catalog == 2){//PASS
                wrapper.in("I.CATA_LOG", 8,9,10);
            }
        }
        wrapper.isNotNull("B.NAME");
        wrapper.isNotNull("SUBSTR(I.ALIAS, 0, INSTR(I.ALIAS,'（')-1)");
        wrapper.orderByDesc("I.CREATED_AT");
        List<ServiceSubscribeVo> serviceSubscribeVos = daasResourceMapper.serviceStatisticsBySubscribe(page, wrapper);
        page.setRecords(serviceSubscribeVos);
        return page;
    }

    @Override
    public Page<ServiceRequestVo> serviceStatisticsByRequest(ServiceSubscribeParam param) {
        List<ServiceRequestVo> serviceRequestVos = new ArrayList<>();
        //设置分页参数
        Page<ServiceRequestVo> page = new Page<>();
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        //组装查询条件
        QueryWrapper<DaasResource> wrapper = new QueryWrapper<>();
        String area = param.getArea();
        String policeCategory = param.getPoliceCategory();
        String startDate = param.getStartDate();
        String endDate = param.getEndDate();
        String name = param.getName();
        Integer type = param.getType();
        Integer catalog = param.getCatalog();
        if (catalog != null){
            if (catalog == 1){//DASS
                wrapper.eq("A.CATA_LOG", 7);
            }else if (catalog == 2){//PASS
                wrapper.in("A.CATA_LOG", 8,9,10);
            }
        }
        if (type == 1){//服务
            if (!StringUtils.isEmpty(area)){
                wrapper.like("B.AREA_NAME",area);
            }
            if (!StringUtils.isEmpty(policeCategory)){
                wrapper.eq("B.POLICE_CATEGORY",policeCategory);
            }
            if (!StringUtils.isEmpty(name)){
                wrapper.like("B.NAME",name);
            }
            if (!StringUtils.isEmpty(startDate)){
                wrapper.ge("A.CURRENT_TIME",startDate);
            }
            if (!StringUtils.isEmpty(endDate)){
                wrapper.le("A.CURRENT_TIME",endDate);
            }
            wrapper.groupBy("A.REQ_COUNT,A.CURRENT_TIME,B.NAME,B.AREA_NAME,B.POLICE_CATEGORY");
            wrapper.orderByDesc("A.CURRENT_TIME");
            wrapper.isNotNull("B.NAME");
            wrapper.isNotNull("SUBSTR(I.ALIAS, 0, INSTR(I.ALIAS,'（')-1)");
            serviceRequestVos = daasResourceMapper.serviceStatisticsByRequest(page, wrapper);
        }else if (type == 2){//应用
            if (!StringUtils.isEmpty(area)){
                wrapper.like("I.AREA_NAME",area);
            }
            if (!StringUtils.isEmpty(policeCategory)){
                wrapper.eq("I.POLICE_CATEGORY",policeCategory);
            }
            if (!StringUtils.isEmpty(name)){
                wrapper.like("SUBSTR(I.ALIAS, 0, INSTR(I.ALIAS,'（')-1)",name);
            }
            if (!StringUtils.isEmpty(startDate)){
                wrapper.ge("A.CURRENT_TIME",startDate);
            }
            if (!StringUtils.isEmpty(endDate)){
                wrapper.le("A.CURRENT_TIME",endDate);
            }
            wrapper.groupBy("SUBSTR(I.ALIAS, 0, INSTR(I.ALIAS,'（')-1),A.REQ_COUNT,A.CURRENT_TIME,B.NAME,B.AREA_NAME,B.POLICE_CATEGORY,I.AREA_NAME,I.POLICE_CATEGORY");
            wrapper.orderByDesc("A.CURRENT_TIME");
            wrapper.isNotNull("B.NAME");
            wrapper.isNotNull("SUBSTR(I.ALIAS, 0, INSTR(I.ALIAS,'（')-1)");
            serviceRequestVos = daasResourceMapper.appStatisticsByRequest(page, wrapper);
        }
        page.setRecords(serviceRequestVos);
        return page;
    }

    @Override
    public Page<ServiceQualityVo> serviceStatisticsByQuality(PageParam param) {
        //设置分页参数
        Page<ServiceQualityVo> page = new Page<>();
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        //组装查询条件
        QueryWrapper<DaasResource> wrapper = new QueryWrapper<>();
        String name = param.getName();
        String startDate = param.getStartDate();
        String endDate = param.getEndDate();
        Integer catalog = param.getCatalog();
        if (catalog != null){
            if (catalog == 1){//DASS
                wrapper.eq("A.CATA_LOG", 7);
            }else if (catalog == 2){//PASS
                wrapper.in("A.CATA_LOG", 8,9,10);
            }
        }
        if (!StringUtils.isEmpty(name)){
            wrapper.like("B.NAME",name);
        }
        if (!StringUtils.isEmpty(startDate)){
            wrapper.ge("A.CURRENT_TIME",startDate);
        }
        if (!StringUtils.isEmpty(endDate)){
            wrapper.le("A.CURRENT_TIME",endDate);
        }
        wrapper.isNotNull("B.NAME");
        wrapper.groupBy("B.NAME,B.AREA_NAME,B.POLICE_CATEGORY,B.DATA_RESOURCE");
        wrapper.orderByAsc("B.NAME");
        List<ServiceQualityVo> serviceQualityVos = daasResourceMapper.serviceStatisticsByQuality(page, wrapper);
        for (ServiceQualityVo serviceQualityVo : serviceQualityVos) {
            serviceQualityVo.setServiceAreaName(AreaPoliceCategoryUtils.getAreaName(serviceQualityVo.getServiceName()));
            serviceQualityVo.setServicePoliceCategory(AreaPoliceCategoryUtils.getPoliceCategory(serviceQualityVo.getServiceName()));
        }
        page.setRecords(serviceQualityVos);
        return page;
    }

    @Override
    public boolean servicePublish2DaaS(ServicePublishVo servicePublish,String serviceId) {
        System.out.println("服务应用申请发布信息--------------"+servicePublish.toString());
        if(servicePublish == null){
            return false;
        }
        DaasResource daas = null;
        try {
            daas = new DaasResource();
            daas.setId(null);
            //服务发布信息
            daas.setName(servicePublish.getServiceName());
            daas.setApigGuid(serviceId);
            daas.setProvider(servicePublish.getVendor());
            daas.setCreateTime(servicePublish.getCreateTime());
            daas.setAreaName(servicePublish.getAreaName());
            daas.setPoliceCategory(servicePublish.getPoliceCategory());
            daas.setCategory(servicePublish.getCategory());
            daas.setExplanation(servicePublish.getRemark());
            daas.setUpdateCycle(servicePublish.getUpdateCycle());
            daas.setDataResource(servicePublish.getDataResource());
            daas.setDataFrom(servicePublish.getDataFrom());
            daas.setFromNet(servicePublish.getFromNet());
            daas.setFromSystem(servicePublish.getFromSystem());
            daas.setCollectionUnit(servicePublish.getCollectionUnit());
            daas.setLabel(servicePublish.getTag());
            daas.setCataLog("7");
            daas.setCreator(servicePublish.getCreator());
            daas.setStatus(2L);
            System.out.println("生成的daas信息--------------"+daas);
            daasResourceMapper.insert(daas);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<YqServiceDetailVo> yqServiceDetail(String label,String serviceName,String appName,Integer page,Integer size) {
        //  模糊查询
        StringBuffer labelBuffer=new StringBuffer("%").append(label).append("%");
        StringBuffer serviceNameBuffer=new StringBuffer("%").append(serviceName).append("%");
        StringBuffer appNameBuffer=new StringBuffer("%").append(appName).append("%");
        Integer before=(page-1)*size;
        Integer after=page*size;
        //  查询服务订购详情
        List<YqServiceDetailVo> yqServiceDetail=daasResourceMapper.yqServiceDetail(labelBuffer.toString(),serviceNameBuffer.toString(),appNameBuffer.toString(),before,after);
        for (YqServiceDetailVo serviceDetail : yqServiceDetail) {
            //  统计调用次数以及平均时延
        	YqServiceDetailVo countAndAvg = daasResourceMapper.countAndAvg(serviceDetail.getApiId(), serviceDetail.getActualId());
            if (countAndAvg!=null){
                serviceDetail.setReqCount(countAndAvg.getReqCount());
                serviceDetail.setAvgLatency(countAndAvg.getAvgLatency());
            }else {
                serviceDetail.setReqCount(0);
                serviceDetail.setAvgLatency(0d);
            }
        }
        return yqServiceDetail;
    }

    @Override
    public Integer yqdy(String label, String serviceName, String appName){
        //  模糊查询
        StringBuffer stringBuffer=new StringBuffer("%").append(label).append("%");
        StringBuffer serviceNameBuffer=new StringBuffer("%").append(serviceName).append("%");
        StringBuffer appNameBuffer=new StringBuffer("%").append(appName).append("%");
        return daasResourceMapper.yqdy(stringBuffer.toString(),serviceNameBuffer.toString(),appNameBuffer.toString());
    }

    @Override
    public DaasResource getServiceByServiceId(String serviceId) {
    	DaasResource daasResource = daasResourceMapper.selectOne(new QueryWrapper<DaasResource>().lambda()
    			.eq(DaasResource::getApigGuid, serviceId));
        return daasResource;
    }

    @Override
    public List<YqStatisticsVo> areaOrder(String label) {
        //  模糊查询
        StringBuffer stringBuffer=new StringBuffer("%").append(label).append("%");
        //  查询服务订购详情
        List<YqStatisticsVo> YqStatistics=daasResourceMapper.areaOrder(stringBuffer.toString());
        return YqStatistics;
    }

    @Override
    public List<YqStatisticsVo> policeOrder(String label) {
        //  模糊查询
        StringBuffer stringBuffer=new StringBuffer("%").append(label).append("%");
        //  查询服务订购详情
        List<YqStatisticsVo> YqStatistics=daasResourceMapper.policeOrder(stringBuffer.toString());
        return YqStatistics;
    }

    @Override
    public List<YqStatisticsVo> appOrder(String label) {
        //  模糊查询
        StringBuffer stringBuffer=new StringBuffer("%").append(label).append("%");
        //  查询服务订购详情
        List<YqStatisticsVo> YqStatistics=daasResourceMapper.appOrder(stringBuffer.toString());
        return YqStatistics;
    }

    @Override
    public List<YqStatisticsVo> serviceByOrder(String label) {
        //  模糊查询
        StringBuffer stringBuffer=new StringBuffer("%").append(label).append("%");
        //  查询服务订购详情
        List<YqStatisticsVo> YqStatistics=daasResourceMapper.serviceByOrder(stringBuffer.toString());
        return YqStatistics;
    }

	@Override
	public boolean serviceAlter2DaaS(ServiceAlterVo serviceAlter, String serviceId) {
        if(serviceAlter == null){
            return false;
        }
        System.out.println("服务应用申请变更信息--------------"+serviceAlter.toString());
        DaasResource daas = null;
        try {
            daas = new DaasResource();
            daas.setId(null);
            //服务发布信息
            daas.setName(serviceAlter.getServiceName());
            daas.setApigGuid(serviceId);
            daas.setProvider(serviceAlter.getVendor());
            daas.setCreateTime(serviceAlter.getCreateTime());
            daas.setAreaName(serviceAlter.getAreaName());
            daas.setPoliceCategory(serviceAlter.getPoliceCategory());
            daas.setCategory(serviceAlter.getCategory());
            daas.setExplanation(serviceAlter.getRemark());
            daas.setUpdateCycle(serviceAlter.getUpdateCycle());
            daas.setDataResource(serviceAlter.getDataResource());
            daas.setDataFrom(serviceAlter.getDataFrom());
            daas.setFromNet(serviceAlter.getFromNet());
            daas.setFromSystem(serviceAlter.getFromSystem());
            daas.setCollectionUnit(serviceAlter.getCollectionUnit());
            daas.setLabel(serviceAlter.getTag());
            daas.setCataLog("7");
            daas.setCreator(serviceAlter.getCreator());
            daas.setStatus(2L);
            System.out.println("生成的daas信息--------------"+daas);
            daasResourceMapper.insert(daas);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

	@Override
	public DaasResource getDaasResourceById(String id) {
		DaasResource daasResource = daasResourceMapper.selectById(id);
		return daasResource;
	}

	@Transactional(rollbackFor = Exception.class)
	public void edit(DaasResource daasResource) {
		LambdaUpdateWrapper<DaasResource> uw = new UpdateWrapper<DaasResource>().lambda()
                .eq(DaasResource::getId, daasResource.getId())
                .set(DaasResource::getUpdateCycle, daasResource.getUpdateCycle())
                .set(DaasResource::getDataResource, daasResource.getDataResource())
                .set(DaasResource::getDataFrom, daasResource.getDataFrom())
                .set(DaasResource::getFromSystem, daasResource.getFromSystem())
                .set(DaasResource::getFromNet, daasResource.getFromNet())
                .set(DaasResource::getCollectionUnit, daasResource.getCollectionUnit())
                .set(DaasResource::getExplanation, daasResource.getExplanation())
                .set(DaasResource::getCategory, daasResource.getCategory())
                .set(DaasResource::getAreaName, daasResource.getAreaName())
                .set(DaasResource::getPoliceCategory, daasResource.getPoliceCategory());
		daasResourceMapper.update(new DaasResource(), uw);
		
	}



}
