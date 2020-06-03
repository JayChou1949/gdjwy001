package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.application.IaasTxyfzjhImpl;
import com.upd.hwcloud.bean.entity.application.PaasFseqsqmImpl;
import com.upd.hwcloud.bean.entity.application.PaasTyyhImpl;
import com.upd.hwcloud.dao.application.PaasTyyhImplMapper;
import com.upd.hwcloud.service.application.IPaasTyyhImplService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 统一用户统一消息实施信息 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-05-10
 */
@Service
public class PaasTyyhImplServiceImpl extends ServiceImpl<PaasTyyhImplMapper, PaasTyyhImpl> implements IPaasTyyhImplService {

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
