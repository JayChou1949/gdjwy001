package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.application.paas.PaasDms;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 基于虚拟机的DCS分布式缓存 服务类
 * </p>
 *
 * @author yyc
 * @since 2020-05-07
 */
public interface IPaasDmsService extends IService<PaasDms>,IApplicationHandler<PaasDms> {

}
