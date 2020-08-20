package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasRqzy;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * PAAS容器资源申请信息 服务类
 */
public interface IPaasRqzyService extends IService<PaasRqzy>,IApplicationHandler<PaasRqzy> {

}
