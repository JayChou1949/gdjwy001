package com.hirisun.cloud.order.service.saas;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.saas.SaasAppExtResource;

/**
 * <p>
 * SaaS申请原始信息扩展表——可视化建模平台资源信息 服务类
 * </p>
 *
 * @author xqp
 * @since 2020-07-21
 */
public interface ISaasAppExtResourceService extends IService<SaasAppExtResource> {

    List<SaasAppExtResource> getListByMasterId(String id);

}
