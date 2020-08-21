package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.IaasTxyfwxgImpl;
import com.hirisun.cloud.iaas.mapper.IaasTxyfwxgImplMapper;
import com.hirisun.cloud.iaas.service.IIaasTxyfwxgImplService;

/**
 *  服务实现类
 */
@Service
public class IaasTxyfwxgImplServiceImpl extends ServiceImpl<IaasTxyfwxgImplMapper, IaasTxyfwxgImpl> implements IIaasTxyfwxgImplService {

    @Override
    public List<IaasTxyfwxgImpl> getImplServerListByAppInfoId(String id) {
        List<IaasTxyfwxgImpl> list = this.list(new QueryWrapper<IaasTxyfwxgImpl>().lambda()
                .eq(IaasTxyfwxgImpl::getAppInfoId, id)
                .orderByAsc(IaasTxyfwxgImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<IaasTxyfwxgImpl> serverList) {
        this.remove(new QueryWrapper<IaasTxyfwxgImpl>().lambda()
                .eq(IaasTxyfwxgImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfwxgImpl txyfwImpl : serverList) {
                txyfwImpl.setId(null);
                txyfwImpl.setAppInfoId(appInfoId);
                this.save(txyfwImpl);
            }
        }
    }

}
