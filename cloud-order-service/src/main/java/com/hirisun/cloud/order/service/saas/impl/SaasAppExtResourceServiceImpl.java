package com.hirisun.cloud.order.service.saas.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.saas.SaasAppExtResource;
import com.hirisun.cloud.order.mapper.saas.SaasAppExtResourceMapper;
import com.hirisun.cloud.order.service.saas.ISaasAppExtResourceService;

/**
 * SaaS申请原始信息扩展表——可视化建模平台资源信息 服务实现类
 */
@Service
public class SaasAppExtResourceServiceImpl extends ServiceImpl<SaasAppExtResourceMapper, 
	SaasAppExtResource> implements ISaasAppExtResourceService {

    @Override
    public List<SaasAppExtResource> getListByMasterId(String id) {
        List<SaasAppExtResource> resourceList = this.list(new QueryWrapper<SaasAppExtResource>().lambda()
                .eq(SaasAppExtResource::getMasterId, id));
        return resourceList;
    }
}
