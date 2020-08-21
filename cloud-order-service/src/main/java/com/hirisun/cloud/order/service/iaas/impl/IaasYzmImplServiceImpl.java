package com.hirisun.cloud.order.service.iaas.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.iaas.IaasYzmImpl;
import com.hirisun.cloud.order.mapper.iaas.IaasYzmImplMapper;
import com.hirisun.cloud.order.service.iaas.IIaasYzmImplService;

/**
 * IaaS 云桌面实施信息 服务实现类
 */
@Service
public class IaasYzmImplServiceImpl extends ServiceImpl<IaasYzmImplMapper, IaasYzmImpl> implements IIaasYzmImplService {

    @Override
    public List<IaasYzmImpl> getImplServerListByAppInfoId(String id) {
        List<IaasYzmImpl> list = this.list(new QueryWrapper<IaasYzmImpl>().lambda()
                .eq(IaasYzmImpl::getAppInfoId, id)
                .orderByAsc(IaasYzmImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<IaasYzmImpl> serverList) {
        this.remove(new QueryWrapper<IaasYzmImpl>().lambda()
                .eq(IaasYzmImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (IaasYzmImpl txyfwImpl : serverList) {
                txyfwImpl.setAppInfoId(appInfoId);
                this.save(txyfwImpl);
            }
        }
    }
}
