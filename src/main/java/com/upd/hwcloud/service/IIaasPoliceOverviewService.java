package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.IaasPoliceOverview;

/**
 * <p>
 * 警种IAAS资源总览 服务类
 * </p>
 *
 * @author xqp
 * @since 2020-06-04
 */
public interface IIaasPoliceOverviewService extends IService<IaasPoliceOverview> {

    IaasPoliceOverview getAllByPolice(String police);

}
