package com.hirisun.cloud.order.service.apply;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.util.SubmitRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-08
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    /**
     * 加入购物车
     * @param user
     * @param shoppingCart
     */
    void createCart(UserVO user, ShoppingCart shoppingCart);

    /**
     * 提交购物车
     * @param user
     * @param submitRequest
     */
    void submit(UserVO user, SubmitRequest submitRequest);

}
