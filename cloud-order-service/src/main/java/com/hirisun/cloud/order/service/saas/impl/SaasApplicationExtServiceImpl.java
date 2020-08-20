package com.hirisun.cloud.order.service.saas.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.saas.SaasApplicationExt;
import com.hirisun.cloud.order.mapper.saas.SaasApplicationExtMapper;
import com.hirisun.cloud.order.service.saas.ISaasApplicationExtService;

/**
 * SaaS申请原始信息扩展表 服务实现类
 */
@Service
public class SaasApplicationExtServiceImpl extends ServiceImpl<SaasApplicationExtMapper, 
	SaasApplicationExt> implements ISaasApplicationExtService {

    @Override
    public List<SaasApplicationExt> getListByMasterId(String id) {
        List<SaasApplicationExt> extList = this.list(new QueryWrapper<SaasApplicationExt>().lambda()
                .eq(SaasApplicationExt::getMasterId, id));
        return extList;
    }

}
