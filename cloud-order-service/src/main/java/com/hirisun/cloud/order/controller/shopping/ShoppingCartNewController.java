package com.hirisun.cloud.order.controller.shopping;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.service.shopping.ShoppingCartService;
import com.hirisun.cloud.order.vo.SubmitRequest;

import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 购物车
 */
@Api(tags = "购物车")
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartNewController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartNewController.class);


    @ApiOperation("添加到购物车")
    @PutMapping("/create")
    public QueryResponseResult<ShoppingCartVo> create(@LoginUser UserVO user,
                                                      @RequestBody ShoppingCartVo shoppingCartVo) throws IOException {
        shoppingCartService.create(user,shoppingCartVo);
        return QueryResponseResult.success("添加到购物车成功");
    }

    @ApiOperation("购物车数目总览-类别分组")
    @GetMapping("/num-overview")
    public QueryResponseResult numOverview(@LoginUser UserVO user){
        if(user == null)return QueryResponseResult.fail("请登录");
        return QueryResponseResult.success(shoppingCartService.getNumGroupByType(user.getIdCard()));
    }

    @ApiOperation("购物车总数")
    @GetMapping("/num-total")
    public QueryResponseResult totalNum(@LoginUser UserVO user){
    	if(user == null)return QueryResponseResult.fail("请登录");
        return QueryResponseResult.success(shoppingCartService.count(user.getIdCard()));
    }

    @ApiOperation("购物车列表")
    @GetMapping("/list")
    public QueryResponseResult list(@LoginUser UserVO user,
                                    @ApiParam(value = "资源类型，1SAAS 2DAAS 3PAAS 5SAAS",required = true,defaultValue = "1") @RequestParam Long type,
                                    @ApiParam(value = "资源名称",required = false,defaultValue = "") @RequestParam String name){
    	return QueryResponseResult.success(shoppingCartService.getShoppingCartList(user.getIdCard(),type,name));
    }

    @ApiOperation("购物车详情")
    @GetMapping("detail")
    public QueryResponseResult detail(@ApiParam(value = "购物车id",required = true) @RequestParam String id){
    	return QueryResponseResult.success(shoppingCartService.detail(id));
    }


    @ApiOperation("购物车更新")
    @PutMapping("/update")
    public ResponseResult<ShoppingCart> update(@RequestBody ShoppingCart shoppingCart) throws IOException {
        shoppingCartService.update(shoppingCart);
        return QueryResponseResult.success();
    }

    @ApiOperation("购物车删除")
    @PostMapping("/delete")
    public ResponseResult delete(@ApiParam(value = "购物车id,多个使用逗号隔开",required = true) @RequestParam String ids){
        logger.debug("id -> {}",ids);
        shoppingCartService.delete(ids);
        return QueryResponseResult.success();
    }

    @ApiOperation("购物车提交")
    @PutMapping("/submit")
    public ResponseResult<SubmitRequest> submit(@LoginUser UserVO user, @RequestBody SubmitRequest submitRequest) throws Exception{
        shoppingCartService.submit(user,submitRequest);
        return QueryResponseResult.success();
    }


}

