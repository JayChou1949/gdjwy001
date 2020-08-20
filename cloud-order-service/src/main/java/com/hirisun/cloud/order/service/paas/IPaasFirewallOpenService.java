package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasFirewallOpen;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * 防火墙开通基本信息 服务类
 */
public interface IPaasFirewallOpenService extends IService<PaasFirewallOpen> ,IApplicationHandler<PaasFirewallOpen>{

}
