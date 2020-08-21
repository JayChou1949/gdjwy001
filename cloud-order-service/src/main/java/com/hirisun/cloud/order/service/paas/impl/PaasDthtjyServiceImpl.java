package com.hirisun.cloud.order.service.paas.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.paas.PaasDthtjy;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.mapper.paas.PaasDthtjyMapper;
import com.hirisun.cloud.order.service.paas.IPaasDthtjyService;

/**
 * 地图-航天精一申请信息 服务实现类
 */
@Service
public class PaasDthtjyServiceImpl extends ServiceImpl<PaasDthtjyMapper, 
	PaasDthtjy> implements IPaasDthtjyService {


    @Override
    public void saveShoppingCart(ShoppingCart<PaasDthtjy> shoppingCart) {
        List<PaasDthtjy> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasDthtjy database : serverList) {
                database.setId(null);
                database.setShoppingCartId(shoppingCart.getId());
                this.save(database);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<PaasDthtjy, Object> info) {
        List<PaasDthtjy> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasDthtjy database : serverList) {
                database.setId(null);
                database.setAppInfoId(info.getId());
                this.save(database);
            }
        }
    }

    @Override
    public List<PaasDthtjy> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<PaasDthtjy>().lambda()
                .eq(PaasDthtjy::getAppInfoId, appInfoId)
                .orderByAsc(PaasDthtjy::getCreateTime));
    }

    @Override
    public List<PaasDthtjy> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasDthtjy>().lambda()
                .eq(PaasDthtjy::getShoppingCartId, shoppingCartId)
                .orderByAsc(PaasDthtjy::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasDthtjy, Object> info) {
        this.remove(new QueryWrapper<PaasDthtjy>().lambda().eq(PaasDthtjy::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasDthtjy> shoppingCart) {
        this.remove(new QueryWrapper<PaasDthtjy>().lambda().eq(PaasDthtjy::getShoppingCartId, shoppingCart.getId()));
        saveShoppingCart(shoppingCart);
    }


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
        this.remove(new QueryWrapper<PaasDthtjy>().lambda().eq(PaasDthtjy::getShoppingCartId, shoppingCartId));
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
        this.update(new PaasDthtjy(),new UpdateWrapper<PaasDthtjy>().lambda().eq(PaasDthtjy::getShoppingCartId,shoppingCartId)
                .set(PaasDthtjy::getAppInfoId,appInfoId));
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
//        this.update(new PaasDthtjy(),new UpdateWrapper<PaasDthtjy>().lambda().eq(PaasDthtjy::getAppInfoId,appInfoId)
//                .set(PaasDthtjy::getShoppingCartId,shoppingCartId));
//
//
//        this.update(new PaasDthtjy(),new UpdateWrapper<PaasDthtjy>().lambda().eq(PaasDthtjy::getShoppingCartId,shoppingCartId)
//                .set(PaasDthtjy::getAppInfoId,""));
//    }
}
