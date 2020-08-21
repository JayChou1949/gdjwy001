package com.hirisun.cloud.order.service.paas.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.paas.PaasApiwg;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.mapper.paas.PaasApiwgMapper;
import com.hirisun.cloud.order.service.paas.IPaasApiwgService;

/**
 * <p>
 * PaaS API 网关申请信息 服务实现类
 * </p>
 *
 * @author huru
 * @since 2019-01-04
 */
@Service
public class PaasApiwgServiceImpl extends ServiceImpl<PaasApiwgMapper, PaasApiwg> implements IPaasApiwgService {

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<PaasApiwg> shoppingCart) {
        List<PaasApiwg> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasApiwg database : serverList) {
                database.setId(null);
                database.setShoppingCartId(shoppingCart.getId());
                this.save(database);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<PaasApiwg, Object> info) {
        List<PaasApiwg> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasApiwg database : serverList) {
                database.setId(null);
                database.setAppInfoId(info.getId());
                this.save(database);
            }
        }
    }

    @Override
    public List<PaasApiwg> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<PaasApiwg>().lambda()
                .eq(PaasApiwg::getAppInfoId, appInfoId)
                .orderByAsc(PaasApiwg::getCreateTime));
    }

    @Override
    public List<PaasApiwg> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasApiwg>().lambda()
                            .eq(PaasApiwg::getShoppingCartId,shoppingCartId)
                            .orderByDesc(PaasApiwg::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasApiwg, Object> info) {
        this.remove(new QueryWrapper<PaasApiwg>().lambda().eq(PaasApiwg::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasApiwg> shoppingCart) {
        this.remove(new QueryWrapper<PaasApiwg>().lambda().eq(PaasApiwg::getShoppingCartId, shoppingCart.getId()));

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
        this.remove(new QueryWrapper<PaasApiwg>().lambda().eq(PaasApiwg::getShoppingCartId, shoppingCartId));
    }

    /**
     * 服务关联订单
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {
        this.update(new PaasApiwg(),new UpdateWrapper<PaasApiwg>().lambda().eq(PaasApiwg::getShoppingCartId,shoppingCartId)
            .set(PaasApiwg::getAppInfoId,appInfoId));
    }

}
