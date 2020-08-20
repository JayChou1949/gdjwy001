package com.hirisun.cloud.order.controller;


import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.service.apply.ShoppingCartService;
import com.hirisun.cloud.order.util.SubmitRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-08
 */
@Api(tags = "购物车")
@RestController
@RequestMapping("/workflow/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    /**
     * TODO 待添加表单信息
     * 添加到购物车
     * @param user
     * @param shoppingCart
     * @return
     * @throws IOException
     */
    @ApiOperation("添加到购物车")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public QueryResponseResult<ShoppingCart> create(@LoginUser UserVO user,
                                                    @ModelAttribute ShoppingCart shoppingCart) throws IOException {

        shoppingCartService.createCart(user,shoppingCart);
        return QueryResponseResult.success(null);
    }

    @ApiOperation("提交")
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public  QueryResponseResult<ShoppingCart> submit(@LoginUser UserVO user, @RequestBody SubmitRequest submitRequest){
        shoppingCartService.submit(user,submitRequest);
        return QueryResponseResult.success(null);
    }

    /**
     * 流程申请提交 提供地市平台使用
     */
//    @ApiIgnore
    @ApiOperation("流程申请提交")
    @PostMapping("/init")
    public QueryResponseResult  init(@RequestBody SubmitRequest submitRequest) {

        /**
         *  TODO 此接口移至外部接口模块
         *  1.验证token
         *  2.验证表单信息
         *  3.保存第三方信息
         *  4.地市发起流程，选择地市流程配置
         *  保存申请人信息
         */
        shoppingCartService.workflowSubmitByArea(submitRequest,null,null);

        return QueryResponseResult.success(null);
    }
}

