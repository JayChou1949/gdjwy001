package com.hirisun.cloud.api.iaas;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

import io.swagger.annotations.ApiOperation;

@FeignClient("cloud-iaas-service")
public interface IaasShoppingCartApi {

	@ApiOperation(value = "购物车保存购物项")
    @PostMapping("/iaas/shopping/item/save")
	public void itemSave(@RequestBody ShoppingCartVo shoppingCartVo);
}
