package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.DaasApplication;
import com.upd.hwcloud.bean.entity.application.SaasServiceApplication;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuc
 * @since 2019-12-24
 */
public interface ISaasServiceApplicationService extends IService<SaasServiceApplication>,IApplicationHandler<SaasServiceApplication>,IImplHandler<SaasServiceApplication>  {

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
