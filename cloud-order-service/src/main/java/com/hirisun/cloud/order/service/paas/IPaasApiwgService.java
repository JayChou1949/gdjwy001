package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasApiwg;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * PaaS API 网关申请信息 服务类
 */
public interface IPaasApiwgService extends IService<PaasApiwg>, IApplicationHandler<PaasApiwg> {

}
