package com.hirisun.cloud.order.service.daas;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.daas.DaasApplication;
import com.hirisun.cloud.order.continer.IApplicationHandler;
import com.hirisun.cloud.order.continer.IImplHandler;

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

    /**
     * 老数据迁移特殊处理
     * @param info
     */
    void specialOldDataDeal(ApplicationInfo<DaasApplication,Object> info);


}
