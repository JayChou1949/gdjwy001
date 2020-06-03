package com.upd.hwcloud.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.RedisKey;
import com.upd.hwcloud.bean.dto.YqExcelDto;
import com.upd.hwcloud.bean.dto.YqServiceDetail;
import com.upd.hwcloud.bean.dto.YqStatistics;
import com.upd.hwcloud.bean.dto.apig.ServiceReturnBean;
import com.upd.hwcloud.bean.entity.Bigdata;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.param.PageParam;
import com.upd.hwcloud.bean.param.ServiceIssueParam;
import com.upd.hwcloud.bean.param.ServiceSubscribeParam;
import com.upd.hwcloud.bean.vo.daasService.ServiceIssueVo;
import com.upd.hwcloud.bean.vo.daasService.ServiceQualityVo;
import com.upd.hwcloud.bean.vo.daasService.ServiceRequestVo;
import com.upd.hwcloud.bean.vo.daasService.ServiceSubscribeVo;
import com.upd.hwcloud.common.utils.AreaPoliceCategoryUtils;
import com.upd.hwcloud.dao.BigdataMapper;
import com.upd.hwcloud.service.IBigdataService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 大数据库服务目录 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-12-26
 */
@Service
public class BigdataServiceImpl extends ServiceImpl<BigdataMapper, Bigdata> implements IBigdataService {

    @Autowired
    private BigdataMapper bigdataMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Page<Bigdata> getPage(Page<Bigdata> page, String name, String dataFrom, String collectionUnit, String category) {
        return bigdataMapper.getPage(page,name,dataFrom,collectionUnit,category);
    }

    @Override
    public List<Bigdata> getList(String name, String dataFrom, String collectionUnit, String category) {
        return bigdataMapper.getPage(name,dataFrom,collectionUnit,category);
    }

    @Override
    public List getColumnConfig() {
        String configJson = stringRedisTemplate.opsForValue().get(RedisKey.KEY_DAAS_COLUMN_CONFIG);
        if (StringUtils.isEmpty(configJson)) {
            List<String> m = new ArrayList<>();
            m.add("name");
            m.add("version");
            m.add("provider");
            m.add("category");
            m.add("createTime");
            configJson = JSONArray.toJSONString(m);
            stringRedisTemplate.opsForValue().set(RedisKey.KEY_DAAS_COLUMN_CONFIG, configJson);
        }
        return JSONArray.parseObject(configJson, LinkedList.class);
    }

    @Override
    public void saveColumnConfig(List config) {
        String configJson = JSONArray.toJSONString(config);
        stringRedisTemplate.opsForValue().set(RedisKey.KEY_DAAS_COLUMN_CONFIG, configJson);
    }

    @Override
    public void resetColumnConfig() {
        stringRedisTemplate.delete(RedisKey.KEY_DAAS_COLUMN_CONFIG);
    }

    @Override
    public Page<ServiceIssueVo> serviceStatisticsByIssue(ServiceIssueParam param) {
        //设置分页参数
        Page<ServiceIssueVo> page = new Page<>();
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        //组装查询条件
        QueryWrapper<Bigdata> wrapper = new QueryWrapper<>();
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
        List<ServiceIssueVo> serviceIssueVos = bigdataMapper.serviceStatisticsByIssue(page, wrapper);
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
        QueryWrapper<Bigdata> wrapper = new QueryWrapper<>();
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
        List<ServiceSubscribeVo> serviceSubscribeVos = bigdataMapper.serviceStatisticsBySubscribe(page, wrapper);
        page.setRecords(serviceSubscribeVos);
        return page;
    }

    @Override
    public Page<ServiceRequestVo> serviceStatisticsByRequest(ServiceSubscribeParam param) {
        //设置分页参数
        Page<ServiceRequestVo> page = new Page<>();
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        //组装查询条件
        QueryWrapper<Bigdata> wrapper = new QueryWrapper<>();
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
            wrapper.groupBy("B.NAME,SUBSTR(I.ALIAS, 0, INSTR(I.ALIAS,'（')-1),B.AREA_NAME, B.POLICE_CATEGORY,I.AREA_NAME,I.POLICE_CATEGORY");
            wrapper.orderByAsc("B.NAME");
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
            wrapper.groupBy("SUBSTR(I.ALIAS, 0, INSTR(I.ALIAS,'（')-1),B.NAME,B.AREA_NAME,B.POLICE_CATEGORY,I.AREA_NAME,I.POLICE_CATEGORY");
            wrapper.orderByAsc("SUBSTR(I.ALIAS, 0, INSTR(I.ALIAS,'（')-1)");
        }
        wrapper.isNotNull("B.NAME");
        wrapper.isNotNull("SUBSTR(I.ALIAS, 0, INSTR(I.ALIAS,'（')-1)");
        List<ServiceRequestVo> serviceRequestVos = bigdataMapper.serviceStatisticsByRequest(page, wrapper);
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
        QueryWrapper<Bigdata> wrapper = new QueryWrapper<>();
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
        List<ServiceQualityVo> serviceQualityVos = bigdataMapper.serviceStatisticsByQuality(page, wrapper);
        for (ServiceQualityVo serviceQualityVo : serviceQualityVos) {
            serviceQualityVo.setServiceAreaName(AreaPoliceCategoryUtils.getAreaName(serviceQualityVo.getServiceName()));
            serviceQualityVo.setServicePoliceCategory(AreaPoliceCategoryUtils.getPoliceCategory(serviceQualityVo.getServiceName()));
        }
        page.setRecords(serviceQualityVos);
        return page;
    }

    @Override
    public boolean servicePublish2DaaS(ServicePublish servicePublish,ServiceReturnBean returnBean) {
        System.out.println("服务应用申请发布信息--------------"+servicePublish.toString());
        if(servicePublish == null){
            return false;
        }
        Bigdata daas = null;
        try {
            daas = new Bigdata();
            daas.setId(null);
            //服务发布信息
            daas.setName(servicePublish.getServiceName());
            daas.setApigGuid(returnBean.getServiceId());
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
            this.save(daas);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<YqServiceDetail> yqServiceDetail(String label,String serviceName,String appName,Integer page,Integer size) {
        //  模糊查询
        StringBuffer labelBuffer=new StringBuffer("%").append(label).append("%");
        StringBuffer serviceNameBuffer=new StringBuffer("%").append(serviceName).append("%");
        StringBuffer appNameBuffer=new StringBuffer("%").append(appName).append("%");
        Integer before=(page-1)*size;
        Integer after=page*size;
        //  查询服务订购详情
        List<YqServiceDetail> yqServiceDetail=bigdataMapper.yqServiceDetail(labelBuffer.toString(),serviceNameBuffer.toString(),appNameBuffer.toString(),before,after);
        for (YqServiceDetail serviceDetail : yqServiceDetail) {
            //  统计调用次数以及平均时延
            YqServiceDetail countAndAvg = bigdataMapper.countAndAvg(serviceDetail.getApiId(), serviceDetail.getActualId());
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
        return bigdataMapper.yqdy(stringBuffer.toString(),serviceNameBuffer.toString(),appNameBuffer.toString());
    }

    @Override
    public List<YqStatistics> areaOrder(String label) {
        //  模糊查询
        StringBuffer stringBuffer=new StringBuffer("%").append(label).append("%");
        //  查询服务订购详情
        List<YqStatistics> YqStatistics=bigdataMapper.areaOrder(stringBuffer.toString());
        return YqStatistics;
    }

    @Override
    public List<YqStatistics> policeOrder(String label) {
        //  模糊查询
        StringBuffer stringBuffer=new StringBuffer("%").append(label).append("%");
        //  查询服务订购详情
        List<YqStatistics> YqStatistics=bigdataMapper.policeOrder(stringBuffer.toString());
        return YqStatistics;
    }

    @Override
    public List<YqStatistics> appOrder(String label) {
        //  模糊查询
        StringBuffer stringBuffer=new StringBuffer("%").append(label).append("%");
        //  查询服务订购详情
        List<YqStatistics> YqStatistics=bigdataMapper.appOrder(stringBuffer.toString());
        return YqStatistics;
    }

    @Override
    public List<YqStatistics> serviceByOrder(String label) {
        //  模糊查询
        StringBuffer stringBuffer=new StringBuffer("%").append(label).append("%");
        //  查询服务订购详情
        List<YqStatistics> YqStatistics=bigdataMapper.serviceByOrder(stringBuffer.toString());
        return YqStatistics;
    }

}
