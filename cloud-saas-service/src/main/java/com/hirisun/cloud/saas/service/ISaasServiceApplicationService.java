package com.hirisun.cloud.saas.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.saas.bean.SaasServiceApplication;

/**
 *  saas 服务类
 */
public interface ISaasServiceApplicationService extends IService<SaasServiceApplication>,
	IApplicationHandler<SaasServiceApplication>,IImplHandler<SaasServiceApplication>  {

    List<SaasServiceApplication> getUnsucessByAppInfoId(String appInfoId);

    /**
     * 合并提交DaaS服务
     * @param appInfoId
     * @param shoppingCartIdList
     */
    void submitMerge(String appInfoId,List<String> shoppingCartIdList);


}
