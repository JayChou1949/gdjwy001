package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.IaasTxyfzjhImpl;
import com.hirisun.cloud.iaas.mapper.IaasTxyfzjhImplMapper;
import com.hirisun.cloud.iaas.service.IIaasTxyfzjhImplService;

/**
 * <p>
 * 飞识二期授权码实施信息 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-04-16
 */
@Service
public class IaasTxyfzjhImplServiceImpl extends ServiceImpl<IaasTxyfzjhImplMapper, 
	IaasTxyfzjhImpl> implements IIaasTxyfzjhImplService {

    @Override
    public List<IaasTxyfzjhImpl> getImplServerListByAppInfoId(String id) {
        List<IaasTxyfzjhImpl> list = this.list(new QueryWrapper<IaasTxyfzjhImpl>().lambda()
                .eq(IaasTxyfzjhImpl::getAppInfoId, id)
                .orderByAsc(IaasTxyfzjhImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<IaasTxyfzjhImpl> serverList) {
        this.remove(new QueryWrapper<IaasTxyfzjhImpl>().lambda()
                .eq(IaasTxyfzjhImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfzjhImpl txyfwImpl : serverList) {
                txyfwImpl.setAppInfoId(appInfoId);
                this.save(txyfwImpl);
            }
        }
    }
}
