package com.hirisun.cloud.iaas.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        shoppingCartService.create(shoppingCartVo);
    }

    
}
