package com.hirisun.cloud.order.service.shopping.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirisun.cloud.api.daas.DaasShoppingCartApi;
import com.hirisun.cloud.api.iaas.IaasShoppingCartApi;
import com.hirisun.cloud.api.paas.PaasShoppingCartApi;
import com.hirisun.cloud.api.saas.SaasShoppingCartApi;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;
import com.hirisun.cloud.order.service.shopping.ShoppingCartItemService;

@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

	@Autowired
	private IaasShoppingCartApi iaasShoppingCartApi;
	@Autowired
	private SaasShoppingCartApi saasShoppingCartApi;
	@Autowired
	private DaasShoppingCartApi daasShoppingCartApi;
	@Autowired
	private PaasShoppingCartApi paasShoppingCartApi;
	
	@Override
	public void saveShoppingCartItem(ShoppingCartVo shoppingCartVo) {
		
		String formNum = shoppingCartVo.getFormNum();
		if(formNum.startsWith("IAAS")) {
			iaasShoppingCartApi.itemSave(shoppingCartVo);
		}else if(formNum.startsWith("DAAS")) {
			daasShoppingCartApi.itemSave(shoppingCartVo);
		}else if(formNum.startsWith("SAAS")) {
			saasShoppingCartApi.itemSave(shoppingCartVo);
		}else if(formNum.startsWith("PAAS")) {
			paasShoppingCartApi.itemSave(shoppingCartVo);
		}
	}

	@Override
	public List getByShoppingCartId(ShoppingCartVo shoppingCartVo) {
		
		String formNum = shoppingCartVo.getFormNum();
		if(formNum.startsWith("IAAS")) {
			return iaasShoppingCartApi.getShoppingCartItemList(shoppingCartVo);
		}else if(formNum.startsWith("DAAS")) {
			return daasShoppingCartApi.getShoppingCartItemList(shoppingCartVo);
		}else if(formNum.startsWith("SAAS")) {
			return saasShoppingCartApi.getShoppingCartItemList(shoppingCartVo);
		}else if(formNum.startsWith("PAAS")) {
			return paasShoppingCartApi.getShoppingCartItemList(shoppingCartVo);
		}
		return null;
		
	}

	@Override
	public void updateShoppingCartItem(ShoppingCartVo shoppingCartVo) {
		
		String formNum = shoppingCartVo.getFormNum();
		if(formNum.startsWith("IAAS")) {
			iaasShoppingCartApi.updateShoppingCartItem(shoppingCartVo);
		}else if(formNum.startsWith("DAAS")) {
			daasShoppingCartApi.updateShoppingCartItem(shoppingCartVo);
		}else if(formNum.startsWith("SAAS")) {
			saasShoppingCartApi.updateShoppingCartItem(shoppingCartVo);
		}else if(formNum.startsWith("PAAS")) {
			paasShoppingCartApi.updateShoppingCartItem(shoppingCartVo);
		}
	}

	@Override
	public void deleteItemByShoppingCartId(ShoppingCartVo shoppingCartVo) {
		
		String formNum = shoppingCartVo.getFormNum();
		if(formNum.startsWith("IAAS")) {
			iaasShoppingCartApi.deleteItemByShoppingCartId(shoppingCartVo);
		}else if(formNum.startsWith("DAAS")) {
			daasShoppingCartApi.deleteItemByShoppingCartId(shoppingCartVo);
		}else if(formNum.startsWith("SAAS")) {
			saasShoppingCartApi.deleteItemByShoppingCartId(shoppingCartVo);
		}else if(formNum.startsWith("PAAS")) {
			paasShoppingCartApi.deleteItemByShoppingCartId(shoppingCartVo);
		}
		
	}

	@Override
	public void refAppInfoFromShoppingCart(ShoppingCartVo shoppingCartVo) {
		
		String formNum = shoppingCartVo.getFormNum();
		if(formNum.startsWith("IAAS")) {
			iaasShoppingCartApi.refAppInfoFromShoppingCart(shoppingCartVo);
		}else if(formNum.startsWith("DAAS")) {
			daasShoppingCartApi.refAppInfoFromShoppingCart(shoppingCartVo);
		}else if(formNum.startsWith("SAAS")) {
			saasShoppingCartApi.refAppInfoFromShoppingCart(shoppingCartVo);
		}else if(formNum.startsWith("PAAS")) {
			paasShoppingCartApi.refAppInfoFromShoppingCart(shoppingCartVo);
		}
		
	}

	@Override
	public Integer getTotalNumInShoppingCart(ShoppingCartVo shoppingCartVo) {
		
		String formNum = shoppingCartVo.getFormNum();
		if(formNum.startsWith("IAAS")) {
			return iaasShoppingCartApi.getTotalNumInShoppingCart(shoppingCartVo);
		}else if(formNum.startsWith("DAAS")) {
			return daasShoppingCartApi.getTotalNumInShoppingCart(shoppingCartVo);
		}else if(formNum.startsWith("SAAS")) {
			return saasShoppingCartApi.getTotalNumInShoppingCart(shoppingCartVo);
		}else if(formNum.startsWith("PAAS")) {
			return paasShoppingCartApi.getTotalNumInShoppingCart(shoppingCartVo);
		}
		return 0;
	}

}
