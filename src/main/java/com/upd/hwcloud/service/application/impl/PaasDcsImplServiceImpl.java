package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.paas.dcs.PaasDcsImpl;
import com.upd.hwcloud.dao.application.PaasDcsImplMapper;
import com.upd.hwcloud.service.application.IImplHandler;
import com.upd.hwcloud.service.application.IPaasDcsImplService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基于虚拟机的DCS分布式缓存实施表 服务实现类
 * </p>
 *
 * @author yyc
 * @since 2020-05-07
 */
@Service
public class PaasDcsImplServiceImpl extends ServiceImpl<PaasDcsImplMapper, PaasDcsImpl> implements IPaasDcsImplService {

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
