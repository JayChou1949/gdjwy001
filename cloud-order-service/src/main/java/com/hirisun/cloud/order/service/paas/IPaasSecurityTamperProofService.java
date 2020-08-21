package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasSecurityTamperProof;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * 大数据安全体系-网页防篡改 服务类
 */
public interface IPaasSecurityTamperProofService extends IService<PaasSecurityTamperProof>,
	IApplicationHandler<PaasSecurityTamperProof> {

}
