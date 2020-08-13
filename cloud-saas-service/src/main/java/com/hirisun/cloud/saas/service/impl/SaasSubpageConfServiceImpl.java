package com.hirisun.cloud.saas.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.saas.bean.SaasSubpageConf;
import com.hirisun.cloud.saas.mapper.SaasSubpageConfMapper;
import com.hirisun.cloud.saas.service.SaasSubpageConfService;

/**
 * SAAS服务资源配置 服务实现类
 */
@Service
public class SaasSubpageConfServiceImpl extends ServiceImpl<SaasSubpageConfMapper, SaasSubpageConf> 
		implements SaasSubpageConfService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(String saasId, SaasSubpageConf conf) {
        this.remove(new QueryWrapper<SaasSubpageConf>().lambda().eq(SaasSubpageConf::getMasterId, saasId));
        conf.setMasterId(saasId);
        conf.setId(null);
        this.save(conf);
    }

}
