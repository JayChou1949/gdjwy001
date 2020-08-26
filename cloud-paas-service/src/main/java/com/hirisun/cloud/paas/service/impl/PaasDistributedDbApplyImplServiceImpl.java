package com.hirisun.cloud.paas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.hirisun.cloud.paas.bean.PaasLibraAccountImpl;
import com.hirisun.cloud.paas.bean.PaasLibraInfoImpl;
import com.hirisun.cloud.paas.bean.PaasLibraWhitelistImpl;
import com.hirisun.cloud.paas.service.IPaasDistributedDbApplyImplService;
import com.hirisun.cloud.paas.service.IPaasLibraAccountImplService;
import com.hirisun.cloud.paas.service.IPaasLibraInfoImplService;
import com.hirisun.cloud.paas.service.IPaasLibraWhitelistImplService;

@Service
public class PaasDistributedDbApplyImplServiceImpl implements IPaasDistributedDbApplyImplService {

    @Autowired
    private IPaasLibraInfoImplService libraInfoImplService;

    @Autowired
    private IPaasLibraAccountImplService libraAccountImplService;

    @Autowired
    private IPaasLibraWhitelistImplService libraWhitelistImplService;

    /**
     * 根据申请信息 id 查询实施服务器信息列表
     *
     * @param appInfoId 申请信息 id
     */
    @Override
    public List<PaasDistributedDbApplyImpl> getImplServerListByAppInfoId(String appInfoId) {
        PaasLibraInfoImpl libraInfoImpl = libraInfoImplService.getOne(new QueryWrapper<PaasLibraInfoImpl>().lambda()
                                                            .eq(PaasLibraInfoImpl::getAppInfoId,appInfoId));
        List<PaasLibraAccountImpl> libraAccountImplList = libraAccountImplService.list(new QueryWrapper<PaasLibraAccountImpl>().lambda()
                                                            .eq(PaasLibraAccountImpl::getAppInfoId,appInfoId).orderByDesc(PaasLibraAccountImpl::getModifiedTime));
        List<PaasLibraWhitelistImpl> libraWhitelistImplList = libraWhitelistImplService.list(new QueryWrapper<PaasLibraWhitelistImpl>().lambda()
                .eq(PaasLibraWhitelistImpl::getAppInfoId,appInfoId).orderByDesc(PaasLibraWhitelistImpl::getModifiedTime));
        List<PaasDistributedDbApplyImpl> distributedDbApplyList = Lists.newArrayList();
        PaasDistributedDbApplyImpl impl = new PaasDistributedDbApplyImpl(libraInfoImpl,libraAccountImplList,libraWhitelistImplList);
        distributedDbApplyList.add(impl);
        return distributedDbApplyList;
    }

    /**
     * 更新实施服务器信息(先删除,后添加)
     *
     * @param appInfoId
     * @param serverList
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<PaasDistributedDbApplyImpl> serverList) {
        libraInfoImplService.remove(new QueryWrapper<PaasLibraInfoImpl>().lambda().eq(PaasLibraInfoImpl::getAppInfoId,appInfoId));
        libraAccountImplService.remove(new QueryWrapper<PaasLibraAccountImpl>().lambda().eq(PaasLibraAccountImpl::getAppInfoId,appInfoId));
        libraWhitelistImplService.remove(new QueryWrapper<PaasLibraWhitelistImpl>().lambda().eq(PaasLibraWhitelistImpl::getAppInfoId,appInfoId));
        for(PaasDistributedDbApplyImpl impl:serverList){

            PaasLibraInfoImpl libraInfoImpl = impl.getPaasLibraInfoImpl();
            libraInfoImpl.setId(null);
            libraInfoImpl.setAppInfoId(appInfoId);
            libraInfoImplService.save(libraInfoImpl);

            List<PaasLibraAccountImpl> accountImplList = impl.getPaasLibraAccountImplList();
            for(PaasLibraAccountImpl accountImpl:accountImplList){
                accountImpl.setId(null);
                accountImpl.setAppInfoId(appInfoId);
            }
            libraAccountImplService.saveBatch(accountImplList);

            List<PaasLibraWhitelistImpl> whitelistList = impl.getPaasLibraWhitelistImplList();
            for(PaasLibraWhitelistImpl whitelistImpl:whitelistList){
                whitelistImpl.setId(null);
                whitelistImpl.setAppInfoId(appInfoId);
            }
            libraWhitelistImplService.saveBatch(whitelistList);

        }
    }
}
