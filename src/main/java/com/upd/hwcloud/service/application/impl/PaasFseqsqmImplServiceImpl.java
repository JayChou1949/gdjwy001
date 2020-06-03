package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.PaasDtImpl;
import com.upd.hwcloud.bean.entity.application.PaasFseqsqmImpl;
import com.upd.hwcloud.dao.application.PaasFseqsqmImplMapper;
import com.upd.hwcloud.service.application.IPaasFseqsqmImplService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 飞识二期授权码实施信息 服务实现类
 * </p>
 *
 * @author huru
 * @since 2019-03-22
 */
@Service
public class PaasFseqsqmImplServiceImpl extends ServiceImpl<PaasFseqsqmImplMapper, PaasFseqsqmImpl> implements IPaasFseqsqmImplService {

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
