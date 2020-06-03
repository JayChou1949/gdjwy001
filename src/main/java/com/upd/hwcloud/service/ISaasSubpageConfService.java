package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.SaasSubpageConf;

/**
 * <p>
 * SAAS服务资源配置 服务类
 * </p>
 *
 * @author wuc
 * @since 2019-08-06
 */
public interface ISaasSubpageConfService extends IService<SaasSubpageConf> {

    void save(String saasId, SaasSubpageConf conf);

}
