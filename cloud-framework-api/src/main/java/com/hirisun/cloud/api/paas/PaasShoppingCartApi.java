package com.hirisun.cloud.api.paas;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

import io.swagger.annotations.ApiOperation;

@FeignClient("cloud-paas-service")
public interface PaasShoppingCartApi {

	@ApiOperation(value = "购物车保存购物项")
	@PutMapping("/paas/shopping/item/save")
	public void itemSave(@RequestBody ShoppingCartVo shoppingCartVo);
	
	@ApiOperation("根据购物车id获取购物项")
    @PutMapping(value = "/paas/shopping/item/get")
    public List getShoppingCartItemList(@RequestBody ShoppingCartVo shoppingCartVo);

    @ApiOperation("根据购物车id统计购物项数量")
    @PutMapping(value = "/paas/shopping/item/total")
    public Integer getTotalNumInShoppingCart(@RequestBody ShoppingCartVo shoppingCartVo);
    
    @ApiOperation("根据购物车id统计购物项数量")
    @PutMapping(value = "/paas/shopping/item/update")
    public void updateShoppingCartItem(@RequestBody ShoppingCartVo shoppingCartVo);
    
    @ApiOperation("根据购物车id删除购物项")
    @PutMapping(value = "/paas/shopping/item/delete")
    public void deleteItemByShoppingCartId(@RequestBody ShoppingCartVo shoppingCartVo);
    
    @ApiOperation("关联购物车购物项")
    @PutMapping(value = "/paas/shopping/item/ref")
    public void refAppInfoFromShoppingCart(@RequestBody ShoppingCartVo shoppingCartVo);

    @ApiOperation("根据表单类型和工单信息获取表单列表")
    @GetMapping(value = "/paas/shopping/getByAppInfoId")
    public List<JSONObject> getByAppInfoId(@RequestParam String formNum, @RequestParam String applyInfoId);

    @ApiOperation("根据表单类型和工单信息获取实施列表")
    @GetMapping(value = "/paas/shopping/getImplServerListByAppInfoId")
    public List<JSONObject> getImplServerListByAppInfoId(@RequestParam String formNum,@RequestParam String applyInfoId);
}
