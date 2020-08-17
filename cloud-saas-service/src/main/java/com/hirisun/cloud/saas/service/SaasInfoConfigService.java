package com.hirisun.cloud.saas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.saas.bean.SaasInfoConfig;

/**
 * SAAS服务资源配置 服务类
 */
public interface SaasInfoConfigService extends IService<SaasInfoConfig> {

    void save(String saasId, SaasInfoConfig conf);

}
