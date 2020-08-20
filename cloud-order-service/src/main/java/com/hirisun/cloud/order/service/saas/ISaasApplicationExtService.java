package com.hirisun.cloud.order.service.saas;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.saas.SaasApplicationExt;

/**
 * SaaS申请原始信息扩展表 服务类
 */
public interface ISaasApplicationExtService extends IService<SaasApplicationExt> {

    /**
     * 通过主表id查询申请服务列表
     */
    List<SaasApplicationExt> getListByMasterId(String id);

}
