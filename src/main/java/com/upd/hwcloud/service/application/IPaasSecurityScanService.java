package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.application.PaasSecurityScan;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 大数据安全体系-漏洞扫描 服务类
 * </p>
 *
 * @author wuc
 * @since 2020-04-07
 */
public interface IPaasSecurityScanService extends IService<PaasSecurityScan>,IApplicationHandler<PaasSecurityScan> {

}
