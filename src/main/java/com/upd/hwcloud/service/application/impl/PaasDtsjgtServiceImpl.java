package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.PaasDtsjgt;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.dao.application.PaasDtsjgtMapper;
import com.upd.hwcloud.service.application.IPaasDtsjgtService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 飞识二期授权码申请信息 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-05-14
 */
@Service
public class PaasDtsjgtServiceImpl extends ServiceImpl<PaasDtsjgtMapper, PaasDtsjgt> implements IPaasDtsjgtService {

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<PaasDtsjgt> shoppingCart) {
        List<PaasDtsjgt> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasDtsjgt database : serverList) {
                database.setId(null);
                database.setShoppingCartId(shoppingCart.getId());
                this.save(database);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<PaasDtsjgt, Object> info) {
        List<PaasDtsjgt> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasDtsjgt database : serverList) {
                database.setId(null);
                database.setAppInfoId(info.getId());
                this.save(database);
            }
        }
    }

    @Override
    public List<PaasDtsjgt> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<PaasDtsjgt>().lambda()
                .eq(PaasDtsjgt::getAppInfoId, appInfoId)
                .orderByAsc(PaasDtsjgt::getCreateTime));
    }

    @Override
    public List<PaasDtsjgt> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasDtsjgt>().lambda()
                .eq(PaasDtsjgt::getShoppingCartId,shoppingCartId )
                .orderByAsc(PaasDtsjgt::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasDtsjgt, Object> info) {
        this.remove(new QueryWrapper<PaasDtsjgt>().lambda().eq(PaasDtsjgt::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasDtsjgt> shoppingCart) {
        this.remove(new QueryWrapper<PaasDtsjgt>().lambda().eq(PaasDtsjgt::getShoppingCartId, shoppingCart.getId()));
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
        this.remove(new QueryWrapper<PaasDtsjgt>().lambda().eq(PaasDtsjgt::getShoppingCartId, shoppingCartId));
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
        this.update(new PaasDtsjgt(),new UpdateWrapper<PaasDtsjgt>().lambda().eq(PaasDtsjgt::getShoppingCartId,shoppingCartId)
                .set(PaasDtsjgt::getAppInfoId,appInfoId));
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
//        this.update(new PaasDtsjgt(),new UpdateWrapper<PaasDtsjgt>().lambda().eq(PaasDtsjgt::getAppInfoId,appInfoId)
//                .set(PaasDtsjgt::getShoppingCartId,shoppingCartId));
//
//
//        this.update(new PaasDtsjgt(),new UpdateWrapper<PaasDtsjgt>().lambda().eq(PaasDtsjgt::getShoppingCartId,shoppingCartId)
//                .set(PaasDtsjgt::getAppInfoId,""));
//    }
}
