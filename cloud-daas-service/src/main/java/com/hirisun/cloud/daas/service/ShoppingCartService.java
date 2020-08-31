package com.hirisun.cloud.daas.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;

import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

public interface ShoppingCartService {
	
    /**
     * 保存购物车购物项
     */
    void itemSave(ShoppingCartVo shoppingCartVo);

    /**
     * 根据购物车id获取对应的购物项
     * @param idCard 用户身份证号
     * @return 购物车列表
     */
    List getShoppingCartItemList(ShoppingCartVo shoppingCartVo);
    
    /**
     * 根据购物车id统计购物车购物项
     * @param shoppingCartVo
     * @return
     */
    Integer getTotalNumInShoppingCart(ShoppingCartVo shoppingCartVo);
    
    /**
     * 更新购物车购物项
     * @param shoppingCartVo
     * @return
     */
    void updateShoppingCartItem(ShoppingCartVo shoppingCartVo);
    
    /**
     * 根据购物车id删除购物项
     * @param shoppingCartVo
     */
    void deleteItemByShoppingCartId(ShoppingCartVo shoppingCartVo);

    /**
     * 关联购物车购物项
     * @param shoppingCartVo
     */
    public void refAppInfoFromShoppingCart(@RequestBody ShoppingCartVo shoppingCartVo);

    /**
     * 根据表单类型和工单信息获取表单列表
     * @param formNum 表单编码
     * @param applyInfoId 工单id
     * @return
     */
    public List<JSONObject> getByAppInfoId(String formNum, String applyInfoId);
    /**
     * 根据表单类型和工单信息获取实施列表
     * @param formNum 表单编码
     * @param applyInfoId 工单id
     * @return
     */
    public List<JSONObject> getImplServerListByAppInfoId(String formNum, String applyInfoId);
}
