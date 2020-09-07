package com.hirisun.cloud.daas.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.daas.bean.Bigdata;
import com.hirisun.cloud.model.daas.vo.ServiceIssueVo;
import com.hirisun.cloud.model.daas.vo.ServiceQualityVo;
import com.hirisun.cloud.model.daas.vo.ServiceRequestVo;
import com.hirisun.cloud.model.daas.vo.ServiceSubscribeVo;
import com.hirisun.cloud.model.param.PageParam;
import com.hirisun.cloud.model.param.ServiceIssueParam;
import com.hirisun.cloud.model.param.ServiceSubscribeParam;

public interface IBigdataService extends IService<Bigdata>{

	/**************************DaaS服务统计******************************/
    /**
     * 按发布统计
     * @return
     */
    Page<ServiceIssueVo> serviceStatisticsByIssue(ServiceIssueParam param);
    /**
     * 按订阅统计
     * @return
     */
    Page<ServiceSubscribeVo> serviceStatisticsBySubscribe(ServiceSubscribeParam param);
    /**
     * 按调用统计
     * @return
     */
    Page<ServiceRequestVo> serviceStatisticsByRequest(ServiceSubscribeParam param);
    /**
     * 按质量统计
     * @return
     */
    Page<ServiceQualityVo> serviceStatisticsByQuality(PageParam param);
    
    
	Page<Bigdata> getPage(Page<Bigdata> page, String name, String dataFrom, String collectionUnit, String category);
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
