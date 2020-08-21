package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.IaasDxccImpl;
import com.hirisun.cloud.iaas.bean.IaasDxccImplExt;
import com.hirisun.cloud.iaas.mapper.IaasDxccImplMapper;
import com.hirisun.cloud.iaas.service.IIaasDxccImplExtService;
import com.hirisun.cloud.iaas.service.IIaasDxccImplService;

/**
 * IaaS对象存储实施信息表 服务实现类
 */
@Service
public class IaasDxccImplServiceImpl extends ServiceImpl<IaasDxccImplMapper, IaasDxccImpl> implements IIaasDxccImplService {

    @Autowired
    private IIaasDxccImplExtService extService;

    @Override
    public List<IaasDxccImpl> getImplServerListByAppInfoId(String id) {
        List<IaasDxccImpl> list = this.list(new QueryWrapper<IaasDxccImpl>().lambda()
                .eq(IaasDxccImpl::getAppInfoId, id)
                .orderByAsc(IaasDxccImpl::getCreateTime));
        List<IaasDxccImplExt> extList = extService.list(new QueryWrapper<IaasDxccImplExt>().lambda()
                .eq(IaasDxccImplExt::getAppInfoId, id)
                .orderByAsc(IaasDxccImplExt::getCreateTime));
        if (list != null && extList != null) {
            for (IaasDxccImpl dxcc : list) {
                dxcc.setCcxx(extList);
            }
        }
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<IaasDxccImpl> serverList) {
        this.remove(new QueryWrapper<IaasDxccImpl>().lambda()
                .eq(IaasDxccImpl::getAppInfoId, appInfoId));
        extService.remove(new QueryWrapper<IaasDxccImplExt>().lambda()
                .eq(IaasDxccImplExt::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (IaasDxccImpl txyfwImpl : serverList) {
                txyfwImpl.setAppInfoId(appInfoId);
                this.save(txyfwImpl);
                // 保存存储信息
                this.saveCcxx(txyfwImpl, appInfoId);
            }
        }
    }

    private void saveCcxx(IaasDxccImpl txyfwImpl, String appInfoId) {
        List<IaasDxccImplExt> ccxx = txyfwImpl.getCcxx();
        if (ccxx != null && !ccxx.isEmpty()) {
            for (IaasDxccImplExt dxcc : ccxx) {
                dxcc.setId(null);
                dxcc.setAppInfoId(appInfoId);
                extService.save(dxcc);
            }
        }
    }

}
