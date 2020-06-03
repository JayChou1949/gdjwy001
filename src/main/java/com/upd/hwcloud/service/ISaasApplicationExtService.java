package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.entity.SaasApplicationExt;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * SaaS申请原始信息扩展表 服务类
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
 */
public interface ISaasApplicationExtService extends IService<SaasApplicationExt> {

    /**
     * 通过主表id查询申请服务列表
     */
    List<SaasApplicationExt> getListByMasterId(String id);

}
