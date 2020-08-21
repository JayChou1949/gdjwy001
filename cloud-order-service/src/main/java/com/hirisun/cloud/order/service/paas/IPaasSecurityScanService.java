package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasSecurityScan;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * 大数据安全体系-漏洞扫描 服务类
 */
public interface IPaasSecurityScanService extends IService<PaasSecurityScan>,
	IApplicationHandler<PaasSecurityScan> {

}
