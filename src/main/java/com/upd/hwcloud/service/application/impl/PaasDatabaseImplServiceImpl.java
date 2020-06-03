package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.PaasDatabaseImpl;
import com.upd.hwcloud.dao.application.PaasDatabaseImplMapper;
import com.upd.hwcloud.service.application.IPaasDatabaseImplService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * PaaS 数据库实施信息 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-12-27
 */
@Service
public class PaasDatabaseImplServiceImpl extends ServiceImpl<PaasDatabaseImplMapper, PaasDatabaseImpl> implements IPaasDatabaseImplService {

    @Override
    public List<PaasDatabaseImpl> getImplServerListByAppInfoId(String id) {
        List<PaasDatabaseImpl> list = this.list(new QueryWrapper<PaasDatabaseImpl>().lambda()
                .eq(PaasDatabaseImpl::getAppInfoId, id)
                .orderByAsc(PaasDatabaseImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<PaasDatabaseImpl> serverList) {
        this.remove(new QueryWrapper<PaasDatabaseImpl>().lambda()
                .eq(PaasDatabaseImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (PaasDatabaseImpl database : serverList) {
                database.setId(null);
                database.setAppInfoId(appInfoId);
                this.save(database);
            }
        }
    }

}
