package com.hirisun.cloud.saas.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.saas.bean.SaasAppExtResource;

/**
 * SaaS申请原始信息扩展表——可视化建模平台资源信息 服务类
 */
public interface ISaasAppExtResourceService extends IService<SaasAppExtResource> {

    List<SaasAppExtResource> getListByMasterId(String id);

}
