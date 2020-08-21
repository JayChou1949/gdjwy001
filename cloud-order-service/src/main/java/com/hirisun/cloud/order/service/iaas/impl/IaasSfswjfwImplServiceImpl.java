package com.hirisun.cloud.order.service.iaas.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.iaas.IaasSfswjfwImpl;
import com.hirisun.cloud.order.mapper.iaas.IaasSfswjfwImplMapper;
import com.hirisun.cloud.order.service.iaas.IIaasSfswjfwImplService;

/**
 * sfs弹性文件服务实施信息 服务实现类
 */
@Service
public class IaasSfswjfwImplServiceImpl extends ServiceImpl<IaasSfswjfwImplMapper, 
	IaasSfswjfwImpl> implements IIaasSfswjfwImplService {

    @Override
    public List<IaasSfswjfwImpl> getImplServerListByAppInfoId(String id) {
        List<IaasSfswjfwImpl> list = this.list(new QueryWrapper<IaasSfswjfwImpl>().lambda()
                .eq(IaasSfswjfwImpl::getAppInfoId, id)
                .orderByAsc(IaasSfswjfwImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<IaasSfswjfwImpl> serverList) {
        this.remove(new QueryWrapper<IaasSfswjfwImpl>().lambda()
                .eq(IaasSfswjfwImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (IaasSfswjfwImpl database : serverList) {
                database.setId(null);
                database.setAppInfoId(appInfoId);
                this.save(database);
            }
        }
    }
}
