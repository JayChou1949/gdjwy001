package com.hirisun.cloud.order.service.paas.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.paas.PaasDtImpl;
import com.hirisun.cloud.order.mapper.paas.PaasDtImplMapper;
import com.hirisun.cloud.order.service.paas.IPaasDtImplService;

/**
 * 地图服务实施信息 服务实现类
 */
@Service
public class PaasDtImplServiceImpl extends ServiceImpl<PaasDtImplMapper, PaasDtImpl> implements IPaasDtImplService {

    @Override
    public List<PaasDtImpl> getImplServerListByAppInfoId(String appInfoId) {
        List<PaasDtImpl> list = this.list(new QueryWrapper<PaasDtImpl>().lambda()
                .eq(PaasDtImpl::getAppInfoId, appInfoId)
                .orderByAsc(PaasDtImpl::getCreateTime));
        return list;
    }

    @Override
    public void update(String appInfoId, List<PaasDtImpl> serverList) {
        this.remove(new QueryWrapper<PaasDtImpl>().lambda()
                .eq(PaasDtImpl::getAppInfoId, appInfoId));
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasDtImpl database : serverList) {
                database.setId(null);
                database.setAppInfoId(appInfoId);
                this.save(database);
            }
        }
    }
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<PaasDtImpl> serverList, Map<String,String> map){
        this.remove(new QueryWrapper<PaasDtImpl>().lambda()
                .eq(PaasDtImpl::getAppInfoId, appInfoId));
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasDtImpl database : serverList) {
                database.setId(null);
                database.setAppInfoId(appInfoId);
                if (null!=map){
                     database.setAppKey(map.get("appKey"));
                    database.setAppSecret(map.get("appSecret"));
                    database.setOrderResult(map.get("orderResult"));
                }
                this.save(database);
            }
        }else if (null!=map){
            PaasDtImpl database = new PaasDtImpl();
            database.setAppInfoId(appInfoId);
            database.setAppKey(map.get("appKey"));
            database.setAppSecret(map.get("appSecret"));
            database.setOrderResult(map.get("orderResult"));
            this.save(database);
        }
    }
}
