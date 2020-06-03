package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.application.paas.PaasElb;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * ELB弹性负载均衡 服务类
 * </p>
 *
 * @author yyc
 * @since 2020-05-07
 */
public interface IPaasElbService extends IService<PaasElb>,IApplicationHandler<PaasElb>{

}
