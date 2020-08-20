package com.hirisun.cloud.order.service.saas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.daas.SaasServiceApplication;
import com.hirisun.cloud.order.continer.IApplicationHandler;
import com.hirisun.cloud.order.continer.IImplHandler;

import java.util.List;

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

    /**
     * 老数据迁移特殊处理
     * @param info
     */
    void specialOldDataDeal(ApplicationInfo<SaasServiceApplication,Object> info);

}
