package com.hirisun.cloud.paas.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasTyyhImpl;
import com.hirisun.cloud.paas.mapper.PaasTyyhImplMapper;
import com.hirisun.cloud.paas.service.IPaasTyyhImplService;

/**
 * 统一用户统一消息实施信息 服务实现类
 */
@Service
public class PaasTyyhImplServiceImpl extends ServiceImpl<PaasTyyhImplMapper, 
	PaasTyyhImpl> implements IPaasTyyhImplService {

    @Override
    public List<PaasTyyhImpl> getImplServerListByAppInfoId(String appInfoId) {
        List<PaasTyyhImpl> list = this.list(new QueryWrapper<PaasTyyhImpl>().lambda()
                .eq(PaasTyyhImpl::getAppInfoId, appInfoId)
                .orderByAsc(PaasTyyhImpl::getCreateTime));
        return list;
    }

    @Override
    public void update(String appInfoId, List<PaasTyyhImpl> serverList) {
        this.remove(new QueryWrapper<PaasTyyhImpl>().lambda()
                .eq(PaasTyyhImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (PaasTyyhImpl txyfwImpl : serverList) {
                txyfwImpl.setAppInfoId(appInfoId);
                this.save(txyfwImpl);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<PaasTyyhImpl> serverList, Map<String,String> map) {
        this.remove(new QueryWrapper<PaasTyyhImpl>().lambda()
                .eq(PaasTyyhImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (PaasTyyhImpl database : serverList) {
                database.setId(null);
                database.setAppInfoId(appInfoId);
                if (null != map) {
                    database.setAppKey(map.get("appKey"));
                    database.setAppSecret(map.get("appSecret"));
                }
                this.save(database);
            }
        } else if (null != map) {
            PaasTyyhImpl database = new PaasTyyhImpl();
            database.setAppInfoId(appInfoId);
            database.setAppKey(map.get("appKey"));
            database.setAppSecret(map.get("appSecret"));
            this.save(database);
        }
    }
}
