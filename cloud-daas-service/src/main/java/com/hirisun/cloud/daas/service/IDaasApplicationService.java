package com.hirisun.cloud.daas.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.daas.bean.DaasApplication;

/**
 * DaaS服务申请信息 服务类
 */
public interface IDaasApplicationService extends IService<DaasApplication>,
	IApplicationHandler<DaasApplication>,IImplHandler<DaasApplication> {

    /**
     * 获取订购失败的服务
     */
    List<DaasApplication> getUnsucessByAppInfoId(String appInfoId);


    /**
     * 合并提交DaaS服务
     * @param appId
     * @param shoppingCartId
     */
    void submitMerge(String appId,List<String> shoppingCartId);

}
