package com.hirisun.cloud.iaas.service;

import java.util.List;

import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

public interface IApplicationHandler<S> {

    void saveShoppingCart(ShoppingCartVo shoppingCart);

    void save(ApplicationInfoVo info);

    /**
     * 获取申请信息
     */
    List<S> getByAppInfoId(String appInfoId);

    List<S> getByShoppingCartId(String shoppingCartId);

    void update(ApplicationInfoVo info);

    void updateShoppingCart(ShoppingCartVo shoppingCart);

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
