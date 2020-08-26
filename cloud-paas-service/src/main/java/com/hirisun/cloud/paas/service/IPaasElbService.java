package com.hirisun.cloud.paas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.paas.bean.PaasElb;

/**
 * ELB弹性负载均衡 服务类
 */
public interface IPaasElbService extends IService<PaasElb>,IApplicationHandler<PaasElb>{

}
