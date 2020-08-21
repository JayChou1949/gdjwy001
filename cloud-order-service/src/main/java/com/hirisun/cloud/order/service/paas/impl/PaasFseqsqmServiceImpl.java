package com.hirisun.cloud.order.service.paas.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.paas.PaasFseqsqm;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.mapper.paas.PaasFseqsqmMapper;
import com.hirisun.cloud.order.service.paas.IPaasFseqsqmService;

/**
 * 飞识二期授权码申请信息 服务实现类
 */
@Service
public class PaasFseqsqmServiceImpl extends ServiceImpl<PaasFseqsqmMapper, PaasFseqsqm> implements IPaasFseqsqmService {

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<PaasFseqsqm> shoppingCart) {
        List<PaasFseqsqm> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasFseqsqm database : serverList) {
                database.setId(null);
                database.setShoppingCartId(shoppingCart.getId());
                this.save(database);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<PaasFseqsqm, Object> info) {
        List<PaasFseqsqm> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasFseqsqm database : serverList) {
                database.setId(null);
                database.setAppInfoId(info.getId());
                this.save(database);
            }
        }
    }

    @Override
    public List<PaasFseqsqm> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<PaasFseqsqm>().lambda()
                .eq(PaasFseqsqm::getAppInfoId, appInfoId)
                .orderByAsc(PaasFseqsqm::getCreateTime));
    }

    @Override
    public List<PaasFseqsqm> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasFseqsqm>().lambda()
                .eq(PaasFseqsqm::getShoppingCartId, shoppingCartId)
                .orderByAsc(PaasFseqsqm::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasFseqsqm, Object> info) {
        this.remove(new QueryWrapper<PaasFseqsqm>().lambda().eq(PaasFseqsqm::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasFseqsqm> shoppingCart) {
        this.remove(new QueryWrapper<PaasFseqsqm>().lambda().eq(PaasFseqsqm::getShoppingCartId, shoppingCart.getId()));

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
        this.remove(new QueryWrapper<PaasFseqsqm>().lambda().eq(PaasFseqsqm::getShoppingCartId, shoppingCartId));
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
        this.update(new PaasFseqsqm(),new UpdateWrapper<PaasFseqsqm>().lambda().eq(PaasFseqsqm::getShoppingCartId,shoppingCartId)
                .set(PaasFseqsqm::getAppInfoId,appInfoId));
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
//        this.update(new PaasFseqsqm(),new UpdateWrapper<PaasFseqsqm>().lambda().eq(PaasFseqsqm::getAppInfoId,appInfoId)
//                .set(PaasFseqsqm::getShoppingCartId,shoppingCartId));
//
//
//        this.update(new PaasFseqsqm(),new UpdateWrapper<PaasFseqsqm>().lambda().eq(PaasFseqsqm::getShoppingCartId,shoppingCartId)
//                .set(PaasFseqsqm::getAppInfoId,""));
//    }

}
