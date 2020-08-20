package com.hirisun.cloud.order.service.paas.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.hirisun.cloud.order.bean.ApplicationInfo;
import com.hirisun.cloud.order.bean.paas.PaasDistributedDbApply;
import com.hirisun.cloud.order.bean.paas.PaasLibraAccount;
import com.hirisun.cloud.order.bean.paas.PaasLibraDbWhitelist;
import com.hirisun.cloud.order.bean.paas.PaasLibraInfo;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.service.paas.IPaasDistributedDbApplyService;
import com.hirisun.cloud.order.service.paas.IPaasDistributedDbInfoService;
import com.hirisun.cloud.order.service.paas.IPaasLibraAccountService;
import com.hirisun.cloud.order.service.paas.IPaasLibraDbWhitelistService;
import com.hirisun.cloud.order.service.paas.IPaasLibraInfoService;

@Service
public class IPaasDistributedDbApplyServiceImpl implements IPaasDistributedDbApplyService {

    @Autowired
    private IPaasLibraInfoService libraInfoService;

    @Autowired
    private IPaasLibraAccountService libraAccountService;

    @Autowired
    private IPaasDistributedDbInfoService paasDistributedDbInfoService;
    @Autowired
    private IPaasLibraDbWhitelistService paasLibraDbWhitelistService;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<PaasDistributedDbApply> shoppingCart) {
        List<PaasDistributedDbApply> serverList = shoppingCart.getServerList();
        for (PaasDistributedDbApply paasDistributedDbApply : serverList) {
            PaasLibraInfo libraInfo = paasDistributedDbApply.getPaasLibraInfo();
            libraInfo.setId(null);
            libraInfo.setShoppingCartId(shoppingCart.getId());
            libraInfoService.save(libraInfo);

            List<PaasLibraAccount> accountList = paasDistributedDbApply.getPaasLibraAccountList();
            for(PaasLibraAccount account:accountList){
                account.setId(null);
                account.setShoppingCartId(shoppingCart.getId());
            }
            libraAccountService.saveBatch(accountList);

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
            {
                PaasLibraInfo libraInfo = paasDistributedDbApply.getPaasLibraInfo();
                libraInfo.setId(null);
                libraInfo.setAppInfoId(info.getId());
                libraInfoService.save(libraInfo);

                List<PaasLibraAccount> accountList = paasDistributedDbApply.getPaasLibraAccountList();
                for(PaasLibraAccount account:accountList){
                    account.setId(null);
                    account.setAppInfoId(info.getId());
                }
                libraAccountService.saveBatch(accountList);

                List<PaasLibraDbWhitelist> paasLibraDbWhitelists = paasDistributedDbApply.getPaasLibraDbWhitelists();
                for (PaasLibraDbWhitelist dbWhitelist : paasLibraDbWhitelists) {
                    dbWhitelist.setId(null);
                    dbWhitelist.setAppInfoId(info.getId());
                    paasLibraDbWhitelistService.save(dbWhitelist);
                }
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
        PaasLibraInfo libraInfo = libraInfoService.getOne(new QueryWrapper<PaasLibraInfo>().lambda()
                .eq(PaasLibraInfo::getAppInfoId, appInfoId)
                .orderByAsc(PaasLibraInfo::getModifiedTime));

        List<PaasLibraAccount> libraAccountList = libraAccountService.list(new QueryWrapper<PaasLibraAccount>().lambda()
                .eq(PaasLibraAccount::getAppInfoId, appInfoId)
                .orderByAsc(PaasLibraAccount::getModifiedTime));
        List<PaasLibraDbWhitelist> libraDbWhitelists = paasLibraDbWhitelistService.list(new QueryWrapper<PaasLibraDbWhitelist>().lambda()
                .eq(PaasLibraDbWhitelist::getAppInfoId, appInfoId)
                .orderByAsc(PaasLibraDbWhitelist::getModifiedTime));
        PaasDistributedDbApply apply = new PaasDistributedDbApply();
        apply.setPaasLibraInfo(libraInfo);
        apply.setPaasLibraAccountList(libraAccountList);
        apply.setPaasLibraDbWhitelists(libraDbWhitelists);
        List<PaasDistributedDbApply> paasDistributedDbApplies = Lists.newArrayList();
        paasDistributedDbApplies.add(apply);
        return paasDistributedDbApplies;
    }

    @Override
    public List<PaasDistributedDbApply> getByShoppingCartId(String shoppingCartId) {
        PaasLibraInfo libraInfo = libraInfoService.getOne(new QueryWrapper<PaasLibraInfo>().lambda()
                .eq(PaasLibraInfo::getShoppingCartId, shoppingCartId)
                .orderByAsc(PaasLibraInfo::getModifiedTime));

        List<PaasLibraAccount> libraAccountList = libraAccountService.list(new QueryWrapper<PaasLibraAccount>().lambda()
                .eq(PaasLibraAccount::getShoppingCartId, shoppingCartId)
                .orderByAsc(PaasLibraAccount::getModifiedTime));
        List<PaasLibraDbWhitelist> libraDbWhitelists = paasLibraDbWhitelistService.list(new QueryWrapper<PaasLibraDbWhitelist>().lambda()
                .eq(PaasLibraDbWhitelist::getShoppingCartId, shoppingCartId)
                .orderByAsc(PaasLibraDbWhitelist::getModifiedTime));
        PaasDistributedDbApply apply = new PaasDistributedDbApply();
        apply.setPaasLibraInfo(libraInfo);
        apply.setPaasLibraAccountList(libraAccountList);
        apply.setPaasLibraDbWhitelists(libraDbWhitelists);
        List<PaasDistributedDbApply> paasDistributedDbApplies = Lists.newArrayList();
        paasDistributedDbApplies.add(apply);
        return paasDistributedDbApplies;
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasDistributedDbApply, Object> info) {
        libraInfoService.remove(new QueryWrapper<PaasLibraInfo>().lambda()
                .eq(PaasLibraInfo::getAppInfoId, info.getId()));
        libraAccountService.remove(new QueryWrapper<PaasLibraAccount>().lambda()
                .eq(PaasLibraAccount::getAppInfoId,info.getId()));
        paasLibraDbWhitelistService.remove(new QueryWrapper<PaasLibraDbWhitelist>().lambda()
                .eq(PaasLibraDbWhitelist::getAppInfoId, info.getId()));
        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasDistributedDbApply> shoppingCart) {
        libraInfoService.remove(new QueryWrapper<PaasLibraInfo>().lambda()
                .eq(PaasLibraInfo::getShoppingCartId, shoppingCart.getId()));
        libraAccountService.remove(new QueryWrapper<PaasLibraAccount>().lambda()
                .eq(PaasLibraAccount::getShoppingCartId,shoppingCart.getId()));
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
        libraInfoService.remove(new QueryWrapper<PaasLibraInfo>().lambda()
                .eq(PaasLibraInfo::getAppInfoId, shoppingCartId));
        libraAccountService.remove(new QueryWrapper<PaasLibraAccount>().lambda()
                .eq(PaasLibraAccount::getAppInfoId,shoppingCartId));
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
        libraInfoService.update(new PaasLibraInfo(),new UpdateWrapper<PaasLibraInfo>().lambda().eq(PaasLibraInfo::getShoppingCartId,shoppingCartId)
                .set(PaasLibraInfo::getAppInfoId,appInfoId));
        libraAccountService.update(new PaasLibraAccount(),new UpdateWrapper<PaasLibraAccount>().lambda().eq(PaasLibraAccount::getShoppingCartId,shoppingCartId)
                .set(PaasLibraAccount::getAppInfoId,appInfoId));
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
