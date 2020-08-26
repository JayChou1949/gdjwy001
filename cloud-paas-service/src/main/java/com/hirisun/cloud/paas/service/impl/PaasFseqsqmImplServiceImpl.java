package com.hirisun.cloud.paas.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasFseqsqmImpl;
import com.hirisun.cloud.paas.mapper.PaasFseqsqmImplMapper;
import com.hirisun.cloud.paas.service.IPaasFseqsqmImplService;

/**
 * 飞识二期授权码实施信息 服务实现类
 */
@Service
public class PaasFseqsqmImplServiceImpl extends ServiceImpl<PaasFseqsqmImplMapper, 
	PaasFseqsqmImpl> implements IPaasFseqsqmImplService {

    @Override
    public List<PaasFseqsqmImpl> getImplServerListByAppInfoId(String id) {
        List<PaasFseqsqmImpl> list = this.list(new QueryWrapper<PaasFseqsqmImpl>().lambda()
                .eq(PaasFseqsqmImpl::getAppInfoId, id)
                .orderByAsc(PaasFseqsqmImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<PaasFseqsqmImpl> serverList) {
        this.remove(new QueryWrapper<PaasFseqsqmImpl>().lambda()
                .eq(PaasFseqsqmImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (PaasFseqsqmImpl database : serverList) {
                database.setId(null);
                database.setAppInfoId(appInfoId);
                this.save(database);
            }
        }
    }
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<PaasFseqsqmImpl> serverList, Map<String,String> map){
        this.remove(new QueryWrapper<PaasFseqsqmImpl>().lambda()
                .eq(PaasFseqsqmImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (PaasFseqsqmImpl database : serverList) {
                database.setId(null);
                database.setAppInfoId(appInfoId);
                if (null!=map){
                    database.setAppKey(map.get("appKey"));
                    database.setAppSecret(map.get("appSecret"));
                }
                this.save(database);
            }
        }else if (null!=map){
            PaasFseqsqmImpl database = new PaasFseqsqmImpl();
            database.setAppInfoId(appInfoId);
            database.setAppKey(map.get("appKey"));
            database.setAppSecret(map.get("appSecret"));
            this.save(database);
        }

    }
}
