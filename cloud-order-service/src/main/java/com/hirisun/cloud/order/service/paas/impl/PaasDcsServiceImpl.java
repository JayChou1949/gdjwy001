package com.hirisun.cloud.order.service.paas.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.paas.PaasDcs;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.mapper.paas.PaasDcsMapper;
import com.hirisun.cloud.order.service.paas.IPaasDcsService;

/**
 *  基于虚拟机的DCS分布式缓存 服务实现类
 */
@Service
public class PaasDcsServiceImpl extends ServiceImpl<PaasDcsMapper, PaasDcs> implements IPaasDcsService {

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<PaasDcs> shoppingCart) {
        List<PaasDcs> dcsList = shoppingCart.getServerList();
        for (PaasDcs dcs:dcsList){
            dcs.setId(null);
            dcs.setShoppingCartId(shoppingCart.getId());
            this.save(dcs);
        }
    }

    @Override
    public void save(ApplicationInfo<PaasDcs, Object> info) {
        List<PaasDcs> dcsList = info.getServerList();
        for (PaasDcs dcs:dcsList){
            dcs.setId(null);
            dcs.setAppInfoId(info.getId());
            this.save(dcs);
        }
    }

    /**
     * 获取申请信息
     *
     * @param appInfoId
     */
    @Override
    public List<PaasDcs> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<PaasDcs>().lambda().eq(PaasDcs::getAppInfoId,appInfoId).orderByDesc(PaasDcs::getModifiedTime));
    }

    @Override
    public List<PaasDcs> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasDcs>().lambda().eq(PaasDcs::getShoppingCartId,shoppingCartId).orderByDesc(PaasDcs::getModifiedTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasDcs, Object> info) {
        this.remove(new QueryWrapper<PaasDcs>().lambda().eq(PaasDcs::getAppInfoId,info.getId()));
        this.save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasDcs> shoppingCart) {
        this.remove(new QueryWrapper<PaasDcs>().lambda().eq(PaasDcs::getShoppingCartId,shoppingCart.getId()));
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
        this.remove(new QueryWrapper<PaasDcs>().lambda().eq(PaasDcs::getShoppingCartId,shoppingCartId));
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
        this.update(new PaasDcs(),new UpdateWrapper<PaasDcs>().lambda()
                .eq(PaasDcs::getShoppingCartId,shoppingCartId)
                .set(PaasDcs::getAppInfoId,appInfoId));
    }

}
