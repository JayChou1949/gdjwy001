package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hirisun.cloud.iaas.enumeration.FormNum;
import com.hirisun.cloud.iaas.enumeration.HandlerWrapper;
import com.hirisun.cloud.iaas.service.IApplicationHandler;
import com.hirisun.cloud.iaas.service.ShoppingCartService;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ApplicationContext context;
	
	@Override
	public void itemSave(ShoppingCartVo shoppingCartVo) {
		HandlerWrapper handlerWrapperByName = FormNum.getHandlerWrapperByName(context, shoppingCartVo.getFormNum());
		IApplicationHandler handler = handlerWrapperByName.getHandler();
		handler.saveShoppingCart(shoppingCartVo);
	}

	@Override
	public List getShoppingCartItemList(ShoppingCartVo shoppingCartVo) {
		
		HandlerWrapper handlerWrapperByName = FormNum.getHandlerWrapperByName(context, shoppingCartVo.getFormNum());
		IApplicationHandler handler = handlerWrapperByName.getHandler();
		List list = handler.getByShoppingCartId(shoppingCartVo.getId());
		return list;
		
	}


}
