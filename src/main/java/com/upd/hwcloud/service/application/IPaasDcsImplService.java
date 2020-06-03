package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.application.paas.dcs.PaasDcsImpl;

/**
 * <p>
 * 基于虚拟机的DCS分布式缓存实施表 服务类
 * </p>
 *
 * @author yyc
 * @since 2020-05-07
 */
public interface IPaasDcsImplService extends IService<PaasDcsImpl>,IImplHandler<PaasDcsImpl> {

}
