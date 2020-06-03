package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.entity.IaasOverview;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangwy
 * @since 2019-09-09
 */
public interface IIaasOverviewService extends IService<IaasOverview> {

    IaasOverview getAll(String area);
}
