package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.entity.application.*;
import com.upd.hwcloud.service.application.IPaasDistributedDbApplyService;
import com.upd.hwcloud.service.application.IPaasDistributedDbInfoService;
import com.upd.hwcloud.service.application.IPaasLibraDbWhitelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author junglefisher
 * @date 2020/5/9 10:04
 */
@Service
public class IPaasDistributedDbApplyServiceImpl implements IPaasDistributedDbApplyService {

    @Autowired
    private IPaasDistributedDbInfoService paasDistributedDbInfoService;
    @Autowired
    private IPaasLibraDbWhitelistService paasLibraDbWhitelistService;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<PaasDistributedDbApply> shoppingCart) {
        List<PaasDistributedDbApply> serverList = shoppingCart.getServerList();
        for (PaasDistributedDbApply paasDistributedDbApply : serverList) {
            List<PaasDistributedDbInfo> paasDistributedDbInfos = paasDistributedDbApply.getPaasDistributedDbInfos();
            for (PaasDistributedDbInfo dbInfo : paasDistributedDbInfos) {
                dbInfo.setId(null);
                dbInfo.setShoppingCartId(shoppingCart.getId());
                paasDistributedDbInfoService.save(dbInfo);
            }
            List<PaasLibraDbWhitelist> paasLibraDbWhitelists = paasDistributedDbApply.getPaasLibraDbWhitelists();
            for (PaasLibraDbWhitelist dbWhitelist : paasLibraDbWhitelists) {
                dbWhitelist.setId(null);
                dbWhitelist.setShoppingCartId(shoppingCart.getId());
                paasLibraDbWhitelistService.save(dbWhitelist);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<PaasDistributedDbApply, Object> info) {
        List<PaasDistributedDbApply> serverList = info.getServerList();
        for (PaasDistributedDbApply paasDistributedDbApply : serverList) {
            List<PaasDistributedDbInfo> paasDistributedDbInfos = paasDistributedDbApply.getPaasDistributedDbInfos();
            for (PaasDistributedDbInfo dbInfo : paasDistributedDbInfos) {
                dbInfo.setId(null);
                dbInfo.setAppInfoId(info.getId());
                paasDistributedDbInfoService.save(dbInfo);
            }
            List<PaasLibraDbWhitelist> paasLibraDbWhitelists = paasDistributedDbApply.getPaasLibraDbWhitelists();
            for (PaasLibraDbWhitelist dbWhitelist : paasLibraDbWhitelists) {
                dbWhitelist.setId(null);
                dbWhitelist.setAppInfoId(info.getId());
                paasLibraDbWhitelistService.save(dbWhitelist);
            }
        }
    }

    /**
     * 获取申请信息
     *
     * @param appInfoId
     */
    @Override
    public List<PaasDistributedDbApply> getByAppInfoId(String appInfoId) {
        List<PaasDistributedDbInfo> distributedDbInfos = paasDistributedDbInfoService.list(new QueryWrapper<PaasDistributedDbInfo>().lambda()
                .eq(PaasDistributedDbInfo::getAppInfoId, appInfoId)
                .orderByAsc(PaasDistributedDbInfo::getModifiedTime));
        List<PaasLibraDbWhitelist> libraDbWhitelists = paasLibraDbWhitelistService.list(new QueryWrapper<PaasLibraDbWhitelist>().lambda()
                .eq(PaasLibraDbWhitelist::getAppInfoId, appInfoId)
                .orderByAsc(PaasLibraDbWhitelist::getModifiedTime));
        PaasDistributedDbApply apply = new PaasDistributedDbApply();
        apply.setPaasDistributedDbInfos(distributedDbInfos);
        apply.setPaasLibraDbWhitelists(libraDbWhitelists);
        List<PaasDistributedDbApply> paasDistributedDbApplies = Lists.newArrayList();
        paasDistributedDbApplies.add(apply);
        return paasDistributedDbApplies;
    }

    @Override
    public List<PaasDistributedDbApply> getByShoppingCartId(String shoppingCartId) {
        List<PaasDistributedDbApply> paasDistributedDbApplies = Lists.newArrayList();
        PaasDistributedDbApply apply = new PaasDistributedDbApply();
        List<PaasDistributedDbInfo> distributedDbInfos = paasDistributedDbInfoService.list(new QueryWrapper<PaasDistributedDbInfo>().lambda()
                .eq(PaasDistributedDbInfo::getShoppingCartId, shoppingCartId)
                .orderByAsc(PaasDistributedDbInfo::getModifiedTime));
        List<PaasLibraDbWhitelist> libraDbWhitelists = paasLibraDbWhitelistService.list(new QueryWrapper<PaasLibraDbWhitelist>().lambda()
                .eq(PaasLibraDbWhitelist::getShoppingCartId, shoppingCartId)
                .orderByAsc(PaasLibraDbWhitelist::getModifiedTime));
        apply.setPaasDistributedDbInfos(distributedDbInfos);
        apply.setPaasLibraDbWhitelists(libraDbWhitelists);
        paasDistributedDbApplies.add(apply);
        return paasDistributedDbApplies;
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasDistributedDbApply, Object> info) {
        paasDistributedDbInfoService.remove(new QueryWrapper<PaasDistributedDbInfo>().lambda()
                .eq(PaasDistributedDbInfo::getAppInfoId, info.getId()));
        paasLibraDbWhitelistService.remove(new QueryWrapper<PaasLibraDbWhitelist>().lambda()
                .eq(PaasLibraDbWhitelist::getAppInfoId, info.getId()));
        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasDistributedDbApply> shoppingCart) {
        paasDistributedDbInfoService.remove(new QueryWrapper<PaasDistributedDbInfo>().lambda()
                .eq(PaasDistributedDbInfo::getShoppingCartId, shoppingCart.getId()));
        paasLibraDbWhitelistService.remove(new QueryWrapper<PaasLibraDbWhitelist>().lambda()
                .eq(PaasLibraDbWhitelist::getShoppingCartId, shoppingCart.getId()));
        saveShoppingCart(shoppingCart);
    }

    /**
     * 获取申请总数(购物车显示字段)
     *
     * @param appInfoId
     */
    @Override
    public Integer getTotalNum(String appInfoId) {
        return 1;
    }

    @Override
    public Integer getTotalNumInShoppingCart(String shoppingCartId) {
        return 1;
    }

    /**
     * 购物车删除
     *
     * @param shoppingCartId 购物车ID
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {
        paasDistributedDbInfoService.remove(new QueryWrapper<PaasDistributedDbInfo>().lambda()
                .eq(PaasDistributedDbInfo::getShoppingCartId, shoppingCartId));
        paasLibraDbWhitelistService.remove(new QueryWrapper<PaasLibraDbWhitelist>().lambda()
                .eq(PaasLibraDbWhitelist::getShoppingCartId, shoppingCartId));
    }

    /**
     * 服务关联订单
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {
        paasDistributedDbInfoService.update(new PaasDistributedDbInfo(),new UpdateWrapper<PaasDistributedDbInfo>().lambda().eq(PaasDistributedDbInfo::getShoppingCartId,shoppingCartId)
                .set(PaasDistributedDbInfo::getAppInfoId,appInfoId));
        paasLibraDbWhitelistService.update(new PaasLibraDbWhitelist(),new UpdateWrapper<PaasLibraDbWhitelist>().lambda().eq(PaasLibraDbWhitelist::getShoppingCartId,shoppingCartId)
                .set(PaasLibraDbWhitelist::getAppInfoId,appInfoId));
    }

//    /**
//     * 老数据迁移
//     *
//     * @param shoppingCartId
//     * @param appInfoId
//     */
//    @Transactional(rollbackFor = Throwable.class)
//    @Override
//    public void oldDataMove(String shoppingCartId, String appInfoId) {
//    }
}
