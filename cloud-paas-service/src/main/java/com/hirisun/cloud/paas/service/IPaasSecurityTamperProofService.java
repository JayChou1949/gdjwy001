package com.hirisun.cloud.paas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.paas.bean.PaasSecurityTamperProof;

/**
 * 大数据安全体系-网页防篡改 服务类
 */
public interface IPaasSecurityTamperProofService extends IService<PaasSecurityTamperProof>,
	IApplicationHandler<PaasSecurityTamperProof> {

}
