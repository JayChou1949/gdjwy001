package com.hirisun.cloud.api.saas;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

import io.swagger.annotations.ApiOperation;

@FeignClient("cloud-saas-service")
public interface SaasShoppingCartApi {

	@ApiOperation(value = "购物车保存购物项")
    @PostMapping("/saas/shopping/item/save")
	public void itemSave(@RequestBody ShoppingCartVo shoppingCartVo);
	
	@ApiOperation("根据购物车id获取购物项")
    @PostMapping(value = "/saas/shopping/item/get")
    public List getShoppingCartItemList(@RequestBody ShoppingCartVo shoppingCartVo);

    @ApiOperation("根据购物车id统计购物项数量")
    @PostMapping(value = "/saas/shopping/item/total")
    public Integer getTotalNumInShoppingCart(@RequestBody ShoppingCartVo shoppingCartVo);
    
    @ApiOperation("根据购物车id统计购物项数量")
    @PostMapping(value = "/saas/shopping/item/update")
    public void updateShoppingCartItem(@RequestBody ShoppingCartVo shoppingCartVo);
    
    @ApiOperation("根据购物车id删除购物项")
    @PostMapping(value = "/saas/shopping/item/delete")
    public void deleteItemByShoppingCartId(@RequestBody ShoppingCartVo shoppingCartVo);
    
    @ApiOperation("关联购物车购物项")
    @PostMapping(value = "/saas/shopping/item/ref")
    public void refAppInfoFromShoppingCart(@RequestBody ShoppingCartVo shoppingCartVo);
    
}
