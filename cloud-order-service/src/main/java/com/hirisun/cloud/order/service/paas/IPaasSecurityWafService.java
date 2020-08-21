package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasSecurityWaf;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * 大数据安全体系-WAF 服务类
 */
public interface IPaasSecurityWafService extends IService<PaasSecurityWaf>,
	IApplicationHandler<PaasSecurityWaf> {

}
