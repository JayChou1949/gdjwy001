package com.hirisun.cloud.daas.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.daas.bean.DaasResource;
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

public interface DaasResourceService {
	
	public void edit(DaasResource daasResource);
	
	public DaasResource getDaasResourceById(String id);

    Page<DaasResource> getPage(Page<DaasResource> page, String name, String dataFrom, String collectionUnit, String category);

    List<DaasResource> getList(String name, String dataFrom, String collectionUnit, String category);

    List getColumnConfig();

    void saveColumnConfig(List config);

    void resetColumnConfig();

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

    boolean servicePublish2DaaS(ServicePublishVo servicePublish,String serviceId);

    List<YqServiceDetailVo> yqServiceDetail(String label,String serviceName,String appName,Integer page,Integer size);

    List<YqStatisticsVo> areaOrder(String label);

    List<YqStatisticsVo> policeOrder(String label);

    List<YqStatisticsVo> appOrder(String label);

    List<YqStatisticsVo> serviceByOrder(String label);

    Integer yqdy(String label, String serviceName, String appName);

    DaasResource getServiceByServiceId(String serviceId);

	boolean serviceAlter2DaaS(ServiceAlterVo serviceAlter,String serviceId);
	
}
