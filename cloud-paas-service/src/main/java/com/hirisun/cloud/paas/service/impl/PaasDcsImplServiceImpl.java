package com.hirisun.cloud.paas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasDcsImpl;
import com.hirisun.cloud.paas.mapper.PaasDcsImplMapper;
import com.hirisun.cloud.paas.service.IPaasDcsImplService;

/**
 * 基于虚拟机的DCS分布式缓存实施表 服务实现类
 */
@Service
public class PaasDcsImplServiceImpl extends ServiceImpl<PaasDcsImplMapper, 
	PaasDcsImpl> implements IPaasDcsImplService {

    /**
     * 根据申请信息 id 查询实施服务器信息列表
     *
     * @param appInfoId 申请信息 id
     */
    @Override
    public List<PaasDcsImpl> getImplServerListByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<PaasDcsImpl>().lambda().eq(PaasDcsImpl::getAppInfoId,appInfoId).orderByDesc(PaasDcsImpl::getModifiedTime));
    }

    /**
     * 更新实施服务器信息(先删除,后添加)
     *
     * @param appInfoId
     * @param serverList
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<PaasDcsImpl> serverList) {
        this.remove(new QueryWrapper<PaasDcsImpl>().lambda().eq(PaasDcsImpl::getAppInfoId,appInfoId));
        for (PaasDcsImpl dcsImpl:serverList){
            dcsImpl.setId(null);
            dcsImpl.setAppInfoId(appInfoId);
        }
        this.saveBatch(serverList);
    }
}
