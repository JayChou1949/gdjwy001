package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.application.PaasDtImpl;
import com.upd.hwcloud.bean.entity.application.PaasDtImpl;
import com.upd.hwcloud.bean.entity.application.PaasFseqsqmImpl;
import com.upd.hwcloud.dao.application.PaasDtImplMapper;
import com.upd.hwcloud.service.application.IPaasDtImplService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地图服务实施信息 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-05-14
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
