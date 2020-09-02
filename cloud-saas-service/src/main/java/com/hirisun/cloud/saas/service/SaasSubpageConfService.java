package com.hirisun.cloud.saas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.saas.bean.SaasSubpageConf;

/**
 * SAAS服务资源配置 服务类
 */
public interface SaasSubpageConfService extends IService<SaasSubpageConf> {

    void save(String saasId, SaasSubpageConf conf);

}
