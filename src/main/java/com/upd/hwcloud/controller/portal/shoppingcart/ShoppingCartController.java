package com.upd.hwcloud.controller.portal.shoppingcart;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.shoppingcart.SubmitRequest;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.application.IShoppingCartService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
 * <p> 前端控制器
 * </p>
 *
 * @author yyc
 * @since 2020-04-16
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
    public R create(@LoginUser User user, HttpServletRequest request) throws IOException {
        String json = IOUtils.toString(request.getInputStream(), "UTF-8");
        logger.debug("json -> {}",json);
        ShoppingCart origin = JSONObject.parseObject(json,ShoppingCart.class);
        logger.debug("origin -> {}",origin);
        shoppingCartService.create(user,json,origin);
        return R.ok("添加到购物车成功");
    }

    @ApiOperation("购物车数目总览-类别分组")
    @RequestMapping(value = "/num-overview",method = RequestMethod.GET)
    public R numOverview(@LoginUser User user){
        if(user == null){
            return  R.error("请登录");
        }
        return R.ok(shoppingCartService.getNumGroupByType(user.getIdcard()));
    }

    @ApiOperation("购物车总数")
    @RequestMapping(value = "/num-total",method = RequestMethod.GET)
    public R totalNum(@LoginUser User user){
        if(user == null){
            return  R.error("请登录");
        }
        return R.ok( shoppingCartService.count(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getCreatorIdCard,user.getIdcard())));
    }

    @ApiOperation("购物车列表")
    @RequestMapping(value = "/list/{type}",method = RequestMethod.GET)
    public R list(@LoginUser User user,@PathVariable Long type,String name){
        return R.ok(shoppingCartService.getShoppingCartList(user.getIdcard(),type,name));
    }

    @ApiOperation("详情")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public R detail(@PathVariable String id){
        return R.ok(shoppingCartService.detail(id));
    }


    @ApiOperation("更新")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public R update(HttpServletRequest request) throws IOException {
        String json = IOUtils.toString(request.getInputStream(), "UTF-8");
        logger.debug("json -> {}",json);
        shoppingCartService.update(json);
        return R.ok();
    }

    @ApiOperation("删除")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public R delete(String ids){
        logger.debug("id -> {}",ids);
        shoppingCartService.delete(ids);
        return R.ok();
    }

    @ApiOperation("提交")
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public  R submit(@LoginUser User user, @RequestBody SubmitRequest submitRequest){
        shoppingCartService.submit(user,submitRequest);
        return R.ok();
    }


}

