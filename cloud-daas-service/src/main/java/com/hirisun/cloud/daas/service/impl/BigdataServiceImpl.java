package com.hirisun.cloud.daas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.util.AreaPoliceCategoryUtils;
import com.hirisun.cloud.daas.bean.Bigdata;
import com.hirisun.cloud.daas.mapper.BigdataMapper;
import com.hirisun.cloud.daas.service.IBigdataService;
import com.hirisun.cloud.model.daas.vo.ServiceIssueVo;
import com.hirisun.cloud.model.daas.vo.ServiceQualityVo;
import com.hirisun.cloud.model.daas.vo.ServiceRequestVo;
import com.hirisun.cloud.model.daas.vo.ServiceSubscribeVo;
import com.hirisun.cloud.model.param.PageParam;
import com.hirisun.cloud.model.param.ServiceIssueParam;
import com.hirisun.cloud.model.param.ServiceSubscribeParam;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 大数据库服务目录 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-02
 */
@Service
public class BigdataServiceImpl extends ServiceImpl<BigdataMapper, Bigdata> implements IBigdataService {

	 @Autowired
	    private BigdataMapper bigdataMapper;
	    @Autowired
	    private StringRedisTemplate stringRedisTemplate;
	    
	    
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
			List<ServiceRequestVo> serviceRequestVos = new ArrayList<>();
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
	            wrapper.groupBy("A.REQ_COUNT,A.CURRENT_TIME,B.NAME,B.AREA_NAME,B.POLICE_CATEGORY");
	            wrapper.orderByDesc("A.CURRENT_TIME");
	            wrapper.isNotNull("B.NAME");
	            wrapper.isNotNull("SUBSTR(I.ALIAS, 0, INSTR(I.ALIAS,'（')-1)");
	            serviceRequestVos = bigdataMapper.serviceStatisticsByRequest(page, wrapper);
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
	            serviceRequestVos = bigdataMapper.appStatisticsByRequest(page, wrapper);
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
    public Page<Bigdata> getPage(Page<Bigdata> page, String name, String dataFrom, String collectionUnit, String category) {
        return bigdataMapper.getPage(page,name,dataFrom,collectionUnit,category);
    }
    
    
}
