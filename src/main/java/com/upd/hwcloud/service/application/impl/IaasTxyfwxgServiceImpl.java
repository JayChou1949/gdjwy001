package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.IaasTxyfwxg;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.dao.application.IaasTxyfwxgMapper;
import com.upd.hwcloud.service.application.IIaasTxyfwxgService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangwy
 * @since 2019-09-11
 */
@Service
public class IaasTxyfwxgServiceImpl extends ServiceImpl<IaasTxyfwxgMapper, IaasTxyfwxg> implements IIaasTxyfwxgService {


    @Override
    public void saveShoppingCart(ShoppingCart<IaasTxyfwxg> shoppingCart) {
        List<IaasTxyfwxg> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfwxg txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setShoppingCartId(shoppingCart.getId());
                this.save(txyfw);
            }
        }
    }

    @Override
    public void save(ApplicationInfo<IaasTxyfwxg, Object> info) {
        List<IaasTxyfwxg> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfwxg txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setAppInfoId(info.getId());
                this.save(txyfw);
            }
        }
    }

    @Override
    public List<IaasTxyfwxg> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<IaasTxyfwxg>().lambda()
                .eq(IaasTxyfwxg::getAppInfoId, appInfoId)
                .orderByAsc(IaasTxyfwxg::getCreateTime));
    }

    @Override
    public List<IaasTxyfwxg> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<IaasTxyfwxg>().lambda()
                .eq(IaasTxyfwxg::getShoppingCartId, shoppingCartId)
                .orderByAsc(IaasTxyfwxg::getCreateTime));
    }

    @Override
    public void update(ApplicationInfo<IaasTxyfwxg, Object> info) {
        this.remove(new QueryWrapper<IaasTxyfwxg>().lambda().eq(IaasTxyfwxg::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<IaasTxyfwxg> shoppingCart) {
        this.remove(new QueryWrapper<IaasTxyfwxg>().lambda().eq(IaasTxyfwxg::getShoppingCartId, shoppingCart.getId()));
        saveShoppingCart(shoppingCart);
    }

    @Override
    public Integer getTotalNum(String appInfoId) {
        return null;
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
        this.remove(new QueryWrapper<IaasTxyfwxg>().lambda().eq(IaasTxyfwxg::getShoppingCartId, shoppingCartId));
    }

    /**
     * 服务关联订单
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {
        this.update(new IaasTxyfwxg(),new UpdateWrapper<IaasTxyfwxg>().lambda().eq(IaasTxyfwxg::getShoppingCartId,shoppingCartId)
            .set(IaasTxyfwxg::getAppInfoId,appInfoId));
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
//        this.update(new IaasTxyfwxg(),new UpdateWrapper<IaasTxyfwxg>().lambda().eq(IaasTxyfwxg::getAppInfoId,appInfoId)
//                .set(IaasTxyfwxg::getShoppingCartId,shoppingCartId));
//
//
//        this.update(new IaasTxyfwxg(),new UpdateWrapper<IaasTxyfwxg>().lambda().eq(IaasTxyfwxg::getShoppingCartId,shoppingCartId)
//                .set(IaasTxyfwxg::getAppInfoId,""));
//    }
}
