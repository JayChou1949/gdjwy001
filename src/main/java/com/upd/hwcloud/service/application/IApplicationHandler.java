package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.DaasApplication;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;

import java.util.List;

public interface IApplicationHandler<S> {

    void saveShoppingCart(ShoppingCart<S> shoppingCart);

    void save(ApplicationInfo<S, Object> info);

    /**
     * 获取申请信息
     */
    List<S> getByAppInfoId(String appInfoId);

    List<S> getByShoppingCartId(String shoppingCartId);

    void update(ApplicationInfo<S, Object> info);

    void updateShoppingCart(ShoppingCart<S> shoppingCart);

    /**
     * 获取申请总数(购物车显示字段)
     */
    Integer getTotalNum(String appInfoId);

    Integer getTotalNumInShoppingCart(String shoppingCartId);

    /**
     * 购物车删除
     * @param shoppingCartId  购物车ID
     */
    void deleteByShoppingCart(String shoppingCartId);

    /**
     * 服务关联订单
     */
    void refAppInfoFromShoppingCart(String shoppingCartId,String appInfoId);

}
