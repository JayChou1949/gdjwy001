package com.hirisun.cloud.paas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasRqzyImpl;
import com.hirisun.cloud.paas.mapper.PaasRqzyImplMapper;
import com.hirisun.cloud.paas.service.IPaasRqzyImplService;

/**
 * 容器资源申请实施信息 服务实现类
 */
@Service
public class PaasRqzyImplServiceImpl extends ServiceImpl<PaasRqzyImplMapper, PaasRqzyImpl> implements IPaasRqzyImplService {

    @Override
    public List<PaasRqzyImpl> getImplServerListByAppInfoId(String appInfoId) {
        List<PaasRqzyImpl> list = this.list(new QueryWrapper<PaasRqzyImpl>().lambda()
                .eq(PaasRqzyImpl::getAppInfoId, appInfoId)
                .orderByAsc(PaasRqzyImpl::getCreateTime));
        return list;
    }

    @Override
    public void update(String appInfoId, List<PaasRqzyImpl> serverList) {
        this.remove(new QueryWrapper<PaasRqzyImpl>().lambda()
                .eq(PaasRqzyImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (PaasRqzyImpl database : serverList) {
                database.setId(null);
                database.setAppInfoId(appInfoId);
                this.save(database);
            }
        }
    }
}
