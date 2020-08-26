package com.hirisun.cloud.paas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.paas.bean.PaasDatabase;

/**
 * PaaS 数据库申请信息 服务类
 */
public interface IPaasDatabaseService extends IService<PaasDatabase>,IApplicationHandler<PaasDatabase> {

}
