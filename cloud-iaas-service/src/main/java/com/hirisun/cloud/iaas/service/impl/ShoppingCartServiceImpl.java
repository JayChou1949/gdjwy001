package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hirisun.cloud.iaas.service.IImplHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.cloud.iaas.enumeration.FormNum;
import com.hirisun.cloud.iaas.enumeration.HandlerWrapper;
import com.hirisun.cloud.iaas.service.IApplicationHandler;
import com.hirisun.cloud.iaas.service.ShoppingCartService;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

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
	@Override
	public List<JSONObject> getByAppInfoId(String formNum, String applyInfoId) {
		HandlerWrapper hw  = FormNum.getHandlerWrapperByName(context,formNum);
		IApplicationHandler handler = hw.getHandler();
		if (handler != null) {
			List list=handler.getByAppInfoId(applyInfoId);
			if (CollectionUtils.isNotEmpty(list)) {
				List<JSONObject> newList = JSON.parseObject(JSON.toJSON(list).toString(),
						new TypeReference<List<JSONObject>>(){});
				return newList;
			}
		}
		return null;
	}
	@Override
	public List<JSONObject> getImplServerListByAppInfoId(String formNum,String applyInfoId) {
		HandlerWrapper hw  = FormNum.getHandlerWrapperByName(context,formNum);
		IImplHandler implHandler = hw.getImplHandler();
		if (implHandler != null) {
			List list=implHandler.getImplServerListByAppInfoId(applyInfoId);
			if (CollectionUtils.isNotEmpty(list)) {
				List<JSONObject> newList = JSON.parseObject(JSON.toJSON(list).toString(),
						new TypeReference<List<JSONObject>>(){});
				return newList;
			}
		}
		return null;
	}
	
}
