package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasDatabaseImpl;
import com.hirisun.cloud.order.continer.IImplHandler;

/**
 * PaaS 数据库实施信息 服务类
 */
public interface IPaasDatabaseImplService extends IService<PaasDatabaseImpl>,IImplHandler<PaasDatabaseImpl> {

}
