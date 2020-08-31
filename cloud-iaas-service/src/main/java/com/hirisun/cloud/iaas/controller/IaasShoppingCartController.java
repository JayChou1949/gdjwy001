package com.hirisun.cloud.iaas.controller;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hirisun.cloud.iaas.service.ShoppingCartService;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("iaas 购物车保存购物项")
@RequestMapping("/iaas/shopping")
@RestController
public class IaasShoppingCartController {

	@Autowired
    private ShoppingCartService shoppingCartService;

    private static final Logger logger = LoggerFactory.getLogger(IaasShoppingCartController.class);

    @ApiOperation("购物车保存购物项")
    @PostMapping(value = "/item/save")
    public void itemSave(@RequestBody ShoppingCartVo shoppingCartVo) throws IOException {
        logger.debug("origin -> {}",shoppingCartVo);
        shoppingCartService.itemSave(shoppingCartVo);
    }
    
    @ApiOperation("根据购物车id获取购物项")
    @PostMapping(value = "/item/get")
    public List getShoppingCartItemList(@RequestBody ShoppingCartVo shoppingCartVo) throws IOException {
        logger.debug("origin -> {}",shoppingCartVo);
        return shoppingCartService.getShoppingCartItemList(shoppingCartVo);
    }

    @ApiOperation("根据购物车id统计购物项数量")
    @PostMapping(value = "/item/total")
    public Integer getTotalNumInShoppingCart(@RequestBody ShoppingCartVo shoppingCartVo) throws IOException {
        logger.debug("origin -> {}",shoppingCartVo);
        return shoppingCartService.getTotalNumInShoppingCart(shoppingCartVo);
    }
    
    @ApiOperation("根据购物车id统计购物项数量")
    @PostMapping(value = "/item/update")
    public void updateShoppingCartItem(@RequestBody ShoppingCartVo shoppingCartVo) throws IOException {
        logger.debug("origin -> {}",shoppingCartVo);
        shoppingCartService.updateShoppingCartItem(shoppingCartVo);
    }
    
    @ApiOperation("根据购物车id删除购物项")
    @PostMapping(value = "/item/delete")
    public void deleteItemByShoppingCartId(@RequestBody ShoppingCartVo shoppingCartVo) throws IOException {
        logger.debug("origin -> {}",shoppingCartVo);
        shoppingCartService.deleteItemByShoppingCartId(shoppingCartVo);
    }
    
    @ApiOperation("关联购物车购物项")
    @PostMapping(value = "/item/ref")
    public void refAppInfoFromShoppingCart(@RequestBody ShoppingCartVo shoppingCartVo) {
    	shoppingCartService.refAppInfoFromShoppingCart(shoppingCartVo);
    }

    @ApiOperation("根据表单类型和工单信息获取表单列表")
    @PostMapping(value = "/getByAppInfoId")
    public List<JSONObject> getByAppInfoId(@RequestParam String formNum, @RequestParam String applyInfoId) {
        return shoppingCartService.getByAppInfoId(formNum,applyInfoId);
    }

    @ApiOperation("根据表单类型和工单信息获取实施列表")
    @PostMapping(value = "/getImplServerListByAppInfoId")
    public List<JSONObject> getImplServerListByAppInfoId(@RequestParam String formNum, @RequestParam String applyInfoId) {
        return shoppingCartService.getImplServerListByAppInfoId(formNum,applyInfoId);
    }
}
