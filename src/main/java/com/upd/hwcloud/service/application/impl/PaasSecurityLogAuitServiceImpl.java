package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.PaasDistributedDbInfo;
import com.upd.hwcloud.bean.entity.application.PaasRqzy;
import com.upd.hwcloud.bean.entity.application.PaasSecurityLogAuit;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.dao.application.PaasSecurityLogAuitMapper;
import com.upd.hwcloud.service.application.IPaasSecurityLogAuitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 大数据安全体系-综合日志审计 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2020-04-07
 */
@Service
public class PaasSecurityLogAuitServiceImpl extends ServiceImpl<PaasSecurityLogAuitMapper, PaasSecurityLogAuit> implements IPaasSecurityLogAuitService {


    @Override
    public void saveShoppingCart(ShoppingCart<PaasSecurityLogAuit> shoppingCart) {
        List<PaasSecurityLogAuit> serverList = shoppingCart.getServerList();
        if(CollectionUtils.isNotEmpty(serverList)){
            for (PaasSecurityLogAuit paasSecurityLogAuit : serverList) {
                paasSecurityLogAuit.setId(null);
                paasSecurityLogAuit.setShoppingCartId(shoppingCart.getId());
            }
            this.saveBatch(serverList);
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<PaasSecurityLogAuit, Object> info) {
        List<PaasSecurityLogAuit> serverList = info.getServerList();
        if(CollectionUtils.isNotEmpty(serverList)){
            for (PaasSecurityLogAuit paasSecurityLogAuit : serverList) {
                paasSecurityLogAuit.setId(null);
                paasSecurityLogAuit.setAppInfoId(info.getId());
                this.save(paasSecurityLogAuit);
            }
        }
    }

    /**
     * 获取申请信息
     *
     * @param appInfoId
     */
    @Override
    public List<PaasSecurityLogAuit> getByAppInfoId(String appInfoId) {
        List<PaasSecurityLogAuit> list = this.list(new QueryWrapper<PaasSecurityLogAuit>().lambda()
                .eq(PaasSecurityLogAuit::getAppInfoId,appInfoId)
                .orderByDesc(PaasSecurityLogAuit::getCreateTime));
        return list;
    }

    @Override
    public List<PaasSecurityLogAuit> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasSecurityLogAuit>().lambda()
                .eq(PaasSecurityLogAuit::getShoppingCartId,shoppingCartId)
                .orderByDesc(PaasSecurityLogAuit::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasSecurityLogAuit, Object> info) {
        this.remove(new QueryWrapper<PaasSecurityLogAuit>().lambda()
                .eq(PaasSecurityLogAuit::getAppInfoId,info.getId()));
        this.save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasSecurityLogAuit> shoppingCart) {
        this.remove(new QueryWrapper<PaasSecurityLogAuit>().lambda()
                .eq(PaasSecurityLogAuit::getShoppingCartId,shoppingCart.getId()));
        this.saveShoppingCart(shoppingCart);
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
        this.remove(new QueryWrapper<PaasSecurityLogAuit>().lambda()
                .eq(PaasSecurityLogAuit::getShoppingCartId,shoppingCartId));
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
        this.update(new PaasSecurityLogAuit(),new UpdateWrapper<PaasSecurityLogAuit>().lambda().eq(PaasSecurityLogAuit::getShoppingCartId,shoppingCartId)
                .set(PaasSecurityLogAuit::getAppInfoId,appInfoId));
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
//        this.update(new PaasSecurityLogAuit(),new UpdateWrapper<PaasSecurityLogAuit>().lambda().eq(PaasSecurityLogAuit::getAppInfoId,appInfoId)
//                .set(PaasSecurityLogAuit::getShoppingCartId,shoppingCartId));
//
//
//        this.update(new PaasSecurityLogAuit(),new UpdateWrapper<PaasSecurityLogAuit>().lambda().eq(PaasSecurityLogAuit::getShoppingCartId,shoppingCartId)
//                .set(PaasSecurityLogAuit::getAppInfoId,""));
//    }
}
