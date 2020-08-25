package com.hirisun.cloud.saas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;
import com.hirisun.cloud.saas.enumeration.FormNum;
import com.hirisun.cloud.saas.enumeration.HandlerWrapper;
import com.hirisun.cloud.saas.service.IApplicationHandler;
import com.hirisun.cloud.saas.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ApplicationContext context;
	
	/**
	 * 保存iaas购物车购物项
	 */
	@Transactional(rollbackFor = Exception.class)
	public void itemSave(ShoppingCartVo shoppingCartVo) {
		HandlerWrapper handlerWrapper = FormNum.getHandlerWrapperByName(context, shoppingCartVo.getFormNum());
		IApplicationHandler handler = handlerWrapper.getHandler();
		handler.saveShoppingCart(shoppingCartVo);
	}

	/**
	 * 根据购物车id获取购物项
	 */
	public List getShoppingCartItemList(ShoppingCartVo shoppingCartVo) {
		
		HandlerWrapper handlerWrapper = FormNum.getHandlerWrapperByName(context, shoppingCartVo.getFormNum());
		IApplicationHandler handler = handlerWrapper.getHandler();
		List list = handler.getByShoppingCartId(shoppingCartVo.getId());
		return list;
	}

	/**
	 * 根据购物车id统计购物项数量
	 */
	public Integer getTotalNumInShoppingCart(ShoppingCartVo shoppingCartVo) {
		
		HandlerWrapper handlerWrapper = FormNum.getHandlerWrapperByName(context, shoppingCartVo.getFormNum());
		IApplicationHandler handler = handlerWrapper.getHandler();
		Integer totalNum = handler.getTotalNumInShoppingCart(shoppingCartVo.getId());
		return totalNum;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void updateShoppingCartItem(ShoppingCartVo shoppingCartVo) {
		
		HandlerWrapper handlerWrapper = FormNum.getHandlerWrapperByName(context, shoppingCartVo.getFormNum());
		IApplicationHandler handler = handlerWrapper.getHandler();
		handler.updateShoppingCart(shoppingCartVo);
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteItemByShoppingCartId(ShoppingCartVo shoppingCartVo) {
		
		HandlerWrapper handlerWrapper = FormNum.getHandlerWrapperByName(context, shoppingCartVo.getFormNum());
		IApplicationHandler handler = handlerWrapper.getHandler();
		handler.deleteByShoppingCart(shoppingCartVo.getId());
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void refAppInfoFromShoppingCart(ShoppingCartVo shoppingCartVo) {
		
		HandlerWrapper hw  = FormNum.getHandlerWrapperByName(context,shoppingCartVo.getFormNum());
        IApplicationHandler handler = hw.getHandler();
        //根据购物车生成订单(其中包括订单选择哪个流程，用于后面的发起流程)
        if(handler != null){
            //关联订单,清楚购物车Item及关联
            handler.refAppInfoFromShoppingCart(shoppingCartVo.getId(),shoppingCartVo.getAppInfoId());
        }
		
	}

	
}
