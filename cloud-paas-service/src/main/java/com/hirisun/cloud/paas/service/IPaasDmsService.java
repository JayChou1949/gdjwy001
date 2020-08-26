package com.hirisun.cloud.paas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.paas.bean.PaasDms;

/**
 * 基于虚拟机的DCS分布式缓存 服务类
 */
public interface IPaasDmsService extends IService<PaasDms>,IApplicationHandler<PaasDms> {

}
