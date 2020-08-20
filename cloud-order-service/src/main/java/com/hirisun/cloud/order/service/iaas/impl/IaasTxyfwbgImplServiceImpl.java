package com.hirisun.cloud.order.service.iaas.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.iaas.IaasTxyfwbgImpl;
import com.hirisun.cloud.order.mapper.iaas.IaasTxyfwbgImplMapper;
import com.hirisun.cloud.order.service.iaas.IIaasTxyfwbgImplService;

/**
 * 弹性云服务器变更申请实施信息表 服务实现类
 */
@Service
public class IaasTxyfwbgImplServiceImpl extends ServiceImpl<IaasTxyfwbgImplMapper, IaasTxyfwbgImpl> implements IIaasTxyfwbgImplService {

    @Override
    public List<IaasTxyfwbgImpl> getImplServerListByAppInfoId(String id) {
        List<IaasTxyfwbgImpl> list = this.list(new QueryWrapper<IaasTxyfwbgImpl>().lambda()
                .eq(IaasTxyfwbgImpl::getAppInfoId, id)
                .orderByAsc(IaasTxyfwbgImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<IaasTxyfwbgImpl> serverList) {
        this.remove(new QueryWrapper<IaasTxyfwbgImpl>().lambda()
                .eq(IaasTxyfwbgImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfwbgImpl txyfwImpl : serverList) {
                txyfwImpl.setAppInfoId(appInfoId);
                this.save(txyfwImpl);
            }
        }
    }

}
