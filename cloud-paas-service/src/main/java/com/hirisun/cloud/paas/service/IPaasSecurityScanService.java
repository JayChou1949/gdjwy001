package com.hirisun.cloud.paas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.paas.bean.PaasSecurityScan;

/**
 * 大数据安全体系-漏洞扫描 服务类
 */
public interface IPaasSecurityScanService extends IService<PaasSecurityScan>,
	IApplicationHandler<PaasSecurityScan> {

}
