package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.IaasLjsfwImpl;
import com.hirisun.cloud.iaas.mapper.IaasLjsfwImplMapper;
import com.hirisun.cloud.iaas.service.IIaasLjsfwImplService;

/**
 * 裸金属服务实施信息表 服务实现类
 */
@Service
public class IaasLjsfwImplServiceImpl extends ServiceImpl<IaasLjsfwImplMapper, IaasLjsfwImpl> implements IIaasLjsfwImplService {

    @Override
    public List<IaasLjsfwImpl> getImplServerListByAppInfoId(String appInfoId) {
        List<IaasLjsfwImpl> list = this.list(new QueryWrapper<IaasLjsfwImpl>().lambda()
                .eq(IaasLjsfwImpl::getAppInfoId, appInfoId)
                .orderByAsc(IaasLjsfwImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<IaasLjsfwImpl> serverList) {
        this.remove(new QueryWrapper<IaasLjsfwImpl>().lambda()
                .eq(IaasLjsfwImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (IaasLjsfwImpl ljsfwImpl : serverList) {
                ljsfwImpl.setId(null);
                ljsfwImpl.setAppInfoId(appInfoId);
                this.save(ljsfwImpl);
            }
        }
    }
}
