package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.IaasYzmImpl;
import com.upd.hwcloud.dao.application.IaasYzmImplMapper;
import com.upd.hwcloud.service.application.IIaasYzmImplService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * IaaS 云桌面实施信息 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2019-01-07
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
