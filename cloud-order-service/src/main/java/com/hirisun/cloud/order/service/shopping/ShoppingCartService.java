package com.hirisun.cloud.order.service.shopping;

import java.util.List;
import java.util.Map;

import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.vo.SubmitRequest;

public interface ShoppingCartService {
	


    /**
     * 提交到购物车
     * @param user session用户
     * @param shoppingCartVo 根据json转换的基础实体(无泛型信息)
     */
    void create(UserVO user,ShoppingCartVo shoppingCartVo);

    /**
     * 购物车列表
     * @param idCard 用户身份证号
     * @return 购物车列表
     */
    List<ShoppingCart> getShoppingCartList(String idCard,Long resourceType,String name);

    /**
     * 购物车详情
     * @param id 购物车ID
     * @return
     */
    ShoppingCart detail(String id);

    /**
     * 更新购物车信息
     * @param shoppingCart 购物车
     */
    void update(ShoppingCart shoppingCart);

    /**
     * 删除购物车
     * @param id
     */
    void delete(String id);

    /**
     * 购物车提交
     * @param submitRequest 提交VO
     * @throws Exception 
     */
    void submit(UserVO user,SubmitRequest submitRequest) throws Exception;

    /**
     * 各类资源购物车数目
     * @return map
     */
    Map<String,Integer> getNumGroupByType(String idCard);
    
    Integer count(String idCard);


}
