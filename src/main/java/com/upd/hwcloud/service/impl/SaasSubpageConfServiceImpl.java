package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.SaasSubpageConf;
import com.upd.hwcloud.dao.SaasSubpageConfMapper;
import com.upd.hwcloud.service.ISaasSubpageConfService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * SAAS服务资源配置 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2019-08-06
 */
@Service
public class SaasSubpageConfServiceImpl extends ServiceImpl<SaasSubpageConfMapper, SaasSubpageConf> implements ISaasSubpageConfService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(String saasId, SaasSubpageConf conf) {
        this.remove(new QueryWrapper<SaasSubpageConf>().lambda().eq(SaasSubpageConf::getMasterId, saasId));
        conf.setMasterId(saasId);
        conf.setId(null);
        this.save(conf);
    }

}
