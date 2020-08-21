package com.hirisun.cloud.order.service.iaas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.iaas.IaasYzm;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * IaaS 云桌面申请信息 服务类
 */
public interface IIaasYzmService extends IService<IaasYzm>, IApplicationHandler<IaasYzm> {

}
