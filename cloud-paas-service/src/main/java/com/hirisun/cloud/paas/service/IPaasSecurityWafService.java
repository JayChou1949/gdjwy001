package com.hirisun.cloud.paas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.paas.bean.PaasSecurityWaf;

/**
 * 大数据安全体系-WAF 服务类
 */
public interface IPaasSecurityWafService extends IService<PaasSecurityWaf>,
	IApplicationHandler<PaasSecurityWaf> {

}
