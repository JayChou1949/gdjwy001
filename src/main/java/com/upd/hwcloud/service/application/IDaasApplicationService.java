package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.DaasApplication;

import java.util.List;

/**
 * <p>
 * DaaS服务申请信息 服务类
 * </p>
 *
 * @author huru
 * @since 2019-03-10
 */
public interface IDaasApplicationService extends IService<DaasApplication>,IApplicationHandler<DaasApplication>,IImplHandler<DaasApplication> {

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
