package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasDcsImpl;
import com.hirisun.cloud.order.continer.IImplHandler;

/**
 * 基于虚拟机的DCS分布式缓存实施表 服务类
 */
public interface IPaasDcsImplService extends IService<PaasDcsImpl>,IImplHandler<PaasDcsImpl> {

}
