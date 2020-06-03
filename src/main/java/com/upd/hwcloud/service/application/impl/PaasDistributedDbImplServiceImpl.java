package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.application.PaasDistributedDbImpl;
import com.upd.hwcloud.dao.application.PaasDistributedDbImplMapper;
import com.upd.hwcloud.service.application.IPaasDistributedDbImplService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yyc
 * @since 2020-05-11
 */
@Service
public class PaasDistributedDbImplServiceImpl extends ServiceImpl<PaasDistributedDbImplMapper, PaasDistributedDbImpl> implements IPaasDistributedDbImplService {

    /**
     * 根据申请信息 id 查询实施服务器信息列表
     *
     * @param appInfoId 申请信息 id
     */
    @Override
    public List<PaasDistributedDbImpl> getImplServerListByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<PaasDistributedDbImpl>().lambda().eq(PaasDistributedDbImpl::getAppInfoId,appInfoId)
                            .orderByDesc(PaasDistributedDbImpl::getModifiedTime));
    }

    /**
     * 更新实施服务器信息(先删除,后添加)
     *
     * @param appInfoId
     * @param serverList
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<PaasDistributedDbImpl> serverList) {
        this.remove(new QueryWrapper<PaasDistributedDbImpl>().lambda().eq(PaasDistributedDbImpl::getAppInfoId,appInfoId));
        if(CollectionUtils.isNotEmpty(serverList)){
            for(PaasDistributedDbImpl impl:serverList){
                impl.setId(null);
                impl.setAppInfoId(appInfoId);
            }
            this.saveBatch(serverList);
        }
    }
}
