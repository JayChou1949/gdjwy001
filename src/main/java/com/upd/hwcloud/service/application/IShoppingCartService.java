package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.bean.vo.shoppingcart.SubmitRequest;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yyc
 * @since 2020-04-16
 */
public interface IShoppingCartService extends IService<ShoppingCart> {

    /**
     * 提交到购物车
     * @param user session用户
     * @param json 前端请求json
     * @param origin 根据json转换的基础实体(无泛型信息)
     */
    void create(User user,String json,ShoppingCart origin);

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
     * @param json 前端请求json
     */
    <S> void update(String json);

    /**
     * 删除购物车
     * @param id
     */
    void delete(String id);

    /**
     * 购物车提交
     * @param submitRequest 提交VO
     */
    void submit(User user,SubmitRequest submitRequest);

    /**
     * 初次老数据对接
     */
   // void oldDataMove(Long resourceType);

    /**
     * 删除旧购物车
     * @param resourceType
     */
    void deleteOldShoppingCart(Long resourceType);


    /**
     * 各类资源购物车数目
     * @return map
     */
    Map<String,Integer> getNumGroupByType(String idCard);


    /**
     * daas、saas购物车去重
     */
    void distinct();
}
