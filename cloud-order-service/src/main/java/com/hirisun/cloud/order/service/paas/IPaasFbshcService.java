package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasFbshc;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * PaaS 分布式缓存申请信息 服务类
 */
public interface IPaasFbshcService extends IService<PaasFbshc>, IApplicationHandler<PaasFbshc> {

}
