package com.hirisun.cloud.order.controller.shopping;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.service.shopping.IShoppingCartService;
import com.hirisun.cloud.order.util.SubmitRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 购物车
 */
@Api("购物车")
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService shoppingCartService;

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);


    @ApiOperation("添加到购物车")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public QueryResponseResult create(@LoginUser UserVO user, HttpServletRequest request) throws IOException {
        String json = IOUtils.toString(request.getInputStream(), "UTF-8");
        logger.debug("json -> {}",json);
        ShoppingCart origin = JSONObject.parseObject(json,ShoppingCart.class);
        logger.debug("origin -> {}",origin);
        shoppingCartService.create(user,json,origin);
        return QueryResponseResult.success("添加到购物车成功");
    }

    @ApiOperation("购物车数目总览-类别分组")
    @RequestMapping(value = "/num-overview",method = RequestMethod.GET)
    public QueryResponseResult numOverview(@LoginUser UserVO user){
        if(user == null)return QueryResponseResult.fail("请登录");
        return QueryResponseResult.success(shoppingCartService.getNumGroupByType(user.getIdCard()));
    }

    @ApiOperation("购物车总数")
    @RequestMapping(value = "/num-total",method = RequestMethod.GET)
    public QueryResponseResult totalNum(@LoginUser UserVO user){
    	if(user == null)return QueryResponseResult.fail("请登录");
        return QueryResponseResult.success(shoppingCartService.count(new QueryWrapper<ShoppingCart>()
        		.lambda().eq(ShoppingCart::getCreatorIdCard,user.getIdCard())));
    }

    @ApiOperation("购物车列表")
    @RequestMapping(value = "/list/{type}",method = RequestMethod.GET)
    public QueryResponseResult list(@LoginUser UserVO user,@PathVariable Long type,String name){
    	return QueryResponseResult.success(shoppingCartService.getShoppingCartList(user.getIdCard(),type,name));
    }

    @ApiOperation("详情")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public QueryResponseResult detail(@PathVariable String id){
    	return QueryResponseResult.success(shoppingCartService.detail(id));
    }


    @ApiOperation("更新")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseResult update(HttpServletRequest request) throws IOException {
        String json = IOUtils.toString(request.getInputStream(), "UTF-8");
        logger.debug("json -> {}",json);
        shoppingCartService.update(json);
        return QueryResponseResult.success();
    }

    @ApiOperation("删除")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResponseResult delete(String ids){
        logger.debug("id -> {}",ids);
        shoppingCartService.delete(ids);
        return QueryResponseResult.success();
    }

    @ApiOperation("提交")
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseResult submit(@LoginUser UserVO user, @RequestBody SubmitRequest submitRequest){
        shoppingCartService.submit(user,submitRequest);
        return QueryResponseResult.success();
    }


}

