package com.hirisun.cloud.order.service.iaas.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.iaas.IaasSfswjfw;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.mapper.iaas.IaasSfswjfwMapper;
import com.hirisun.cloud.order.service.iaas.IIaasSfswjfwService;

/**
 * sfs弹性文件服务申请信息 服务实现类
 */
@Service
public class IaasSfswjfwServiceImpl extends ServiceImpl<IaasSfswjfwMapper, 
	IaasSfswjfw> implements IIaasSfswjfwService {


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<IaasSfswjfw> shoppingCart) {
        List<IaasSfswjfw> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasSfswjfw txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setShoppingCartId(shoppingCart.getId());
                this.save(txyfw);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<IaasSfswjfw, Object> info) {
        List<IaasSfswjfw> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasSfswjfw txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setAppInfoId(info.getId());
                this.save(txyfw);
            }
        }
    }

    @Override
    public List<IaasSfswjfw> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<IaasSfswjfw>().lambda()
                .eq(IaasSfswjfw::getAppInfoId, appInfoId)
                .orderByAsc(IaasSfswjfw::getCreateTime));
    }

    @Override
    public List<IaasSfswjfw> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<IaasSfswjfw>().lambda()
                .eq(IaasSfswjfw::getShoppingCartId, shoppingCartId)
                .orderByAsc(IaasSfswjfw::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<IaasSfswjfw, Object> info) {
        this.remove(new QueryWrapper<IaasSfswjfw>().lambda().eq(IaasSfswjfw::getAppInfoId, info.getId()));
        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<IaasSfswjfw> shoppingCart) {
        this.remove(new QueryWrapper<IaasSfswjfw>().lambda().eq(IaasSfswjfw::getShoppingCartId,shoppingCart.getId()));
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
        this.remove(new QueryWrapper<IaasSfswjfw>().lambda().eq(IaasSfswjfw::getShoppingCartId,shoppingCartId));
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
        this.update(new IaasSfswjfw(),new UpdateWrapper<IaasSfswjfw>().lambda()
            .eq(IaasSfswjfw::getShoppingCartId,shoppingCartId)
            .set(IaasSfswjfw::getAppInfoId,appInfoId));
    }

}
