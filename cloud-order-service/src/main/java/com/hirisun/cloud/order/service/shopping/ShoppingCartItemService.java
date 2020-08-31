package com.hirisun.cloud.order.service.shopping;

import java.util.List;

import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

public interface ShoppingCartItemService {

	public void saveShoppingCartItem(ShoppingCartVo shoppingCartVo);
	
	public List getByShoppingCartId(ShoppingCartVo shoppingCartVo);
	
	public Integer getTotalNumInShoppingCart(ShoppingCartVo shoppingCartVo);
	
	public void updateShoppingCartItem(ShoppingCartVo shoppingCartVo);
	
	public void deleteItemByShoppingCartId(ShoppingCartVo shoppingCartVo);
	
	public void refAppInfoFromShoppingCart(ShoppingCartVo shoppingCartVo);
}
