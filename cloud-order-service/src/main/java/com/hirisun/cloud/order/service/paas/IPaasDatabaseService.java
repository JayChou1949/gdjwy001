package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasDatabase;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * PaaS 数据库申请信息 服务类
 */
public interface IPaasDatabaseService extends IService<PaasDatabase>,IApplicationHandler<PaasDatabase> {

}
