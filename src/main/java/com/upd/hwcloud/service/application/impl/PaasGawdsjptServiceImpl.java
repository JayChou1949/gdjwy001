package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.PaasGawdsjpt;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.dao.application.PaasGawdsjptMapper;
import com.upd.hwcloud.service.application.IPaasGawdsjptService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 公安网大数据平台资源申请信息 服务实现类
 * </p>
 *
 * @author huru
 * @since 2019-03-26
 */
@Service
public class PaasGawdsjptServiceImpl extends ServiceImpl<PaasGawdsjptMapper, PaasGawdsjpt> implements IPaasGawdsjptService {


    @Override
    public void saveShoppingCart(ShoppingCart<PaasGawdsjpt> shoppingCart) {
        List<PaasGawdsjpt> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasGawdsjpt database : serverList) {
                database.setId(null);
                database.setShoppingCartId(shoppingCart.getId());
                this.save(database);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<PaasGawdsjpt, Object> info) {
        List<PaasGawdsjpt> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasGawdsjpt database : serverList) {
                database.setId(null);
                database.setAppInfoId(info.getId());
                this.save(database);
            }
        }
    }

    @Override
    public List<PaasGawdsjpt> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<PaasGawdsjpt>().lambda()
                .eq(PaasGawdsjpt::getAppInfoId, appInfoId)
                .orderByAsc(PaasGawdsjpt::getCreateTime));
    }

    @Override
    public List<PaasGawdsjpt> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasGawdsjpt>().lambda()
                .eq(PaasGawdsjpt::getShoppingCartId, shoppingCartId)
                .orderByAsc(PaasGawdsjpt::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasGawdsjpt, Object> info) {
        this.remove(new QueryWrapper<PaasGawdsjpt>().lambda().eq(PaasGawdsjpt::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasGawdsjpt> shoppingCart) {
        this.remove(new QueryWrapper<PaasGawdsjpt>().lambda().eq(PaasGawdsjpt::getShoppingCartId, shoppingCart.getId()));

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
        this.remove(new QueryWrapper<PaasGawdsjpt>().lambda().eq(PaasGawdsjpt::getShoppingCartId,shoppingCartId));
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
        this.update(new PaasGawdsjpt(),new UpdateWrapper<PaasGawdsjpt>().lambda().eq(PaasGawdsjpt::getShoppingCartId,shoppingCartId)
                .set(PaasGawdsjpt::getAppInfoId,appInfoId));
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
//        this.update(new PaasGawdsjpt(),new UpdateWrapper<PaasGawdsjpt>().lambda().eq(PaasGawdsjpt::getAppInfoId,appInfoId)
//                .set(PaasGawdsjpt::getShoppingCartId,shoppingCartId));
//
//
//        this.update(new PaasGawdsjpt(),new UpdateWrapper<PaasGawdsjpt>().lambda().eq(PaasGawdsjpt::getShoppingCartId,shoppingCartId)
//                .set(PaasGawdsjpt::getAppInfoId,""));
//    }

}
