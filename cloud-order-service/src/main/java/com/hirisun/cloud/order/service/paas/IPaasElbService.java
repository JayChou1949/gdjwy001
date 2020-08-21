package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasElb;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * ELB弹性负载均衡 服务类
 */
public interface IPaasElbService extends IService<PaasElb>,IApplicationHandler<PaasElb>{

}
