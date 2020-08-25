package com.hirisun.cloud.daas.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.daas.bean.DaasApplication;
import com.hirisun.cloud.daas.mapper.DaasApplicationMapper;
import com.hirisun.cloud.daas.service.IDaasApplicationService;
import com.hirisun.cloud.daas.service.ShoppingCartService;
import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

/**
 * DaaS服务申请信息 服务实现类
 */
@Service
public class DaasApplicationServiceImpl extends ServiceImpl<DaasApplicationMapper, 
	DaasApplication> implements IDaasApplicationService {


    private static final Logger logger = LoggerFactory.getLogger(DaasApplicationServiceImpl.class);

    /**
     * DaaS服务一个购物车只有一个
     * @param shoppingCart
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<DaasApplication> serverList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<DaasApplication>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (DaasApplication daas : serverList) {
                daas.setId(null);
                daas.setShoppingCartId(shoppingCart.getId());
                this.save(daas);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<DaasApplication> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<DaasApplication>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (DaasApplication daas : serverList) {
                daas.setId(null);
                daas.setAppInfoId(info.getId());
                this.save(daas);
            }
        }
    }

    @Override
    public List<DaasApplication> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<DaasApplication>().lambda()
                .eq(DaasApplication::getAppInfoId, appInfoId)
                .orderByAsc(DaasApplication::getCreateTime));
    }

    @Override
    public List<DaasApplication> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<DaasApplication>().lambda()
                .eq(DaasApplication::getShoppingCartId,shoppingCartId)
                .orderByDesc(DaasApplication::getCreateTime));
    }

    @Override
    public List<DaasApplication> getUnsucessByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<DaasApplication>().lambda()
                .eq(DaasApplication::getAppInfoId, appInfoId)
                .isNull(DaasApplication::getAppKey)
                .isNull(DaasApplication::getAppSecret)
                .orderByAsc(DaasApplication::getCreateTime));
    }

    /**
     * 合并提交DaaS服务
     *
     * @param appInfoId
     * @param shoppingCartIdList
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void submitMerge(String appInfoId, List<String> shoppingCartIdList) {
        //关联订单
        this.update(new DaasApplication(),new UpdateWrapper<DaasApplication>().lambda()
                                            .in(DaasApplication::getShoppingCartId,shoppingCartIdList)
                                            .set(DaasApplication::getAppInfoId,appInfoId));

        //清空购物车关联
        this.update(new DaasApplication(),new UpdateWrapper<DaasApplication>().lambda()
                .eq(DaasApplication::getAppInfoId,appInfoId)
                .set(DaasApplication::getShoppingCartId,""));

    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<DaasApplication>().lambda().eq(DaasApplication::getAppInfoId, info.getId()));
        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<DaasApplication>().lambda().eq(DaasApplication::getShoppingCartId,shoppingCart.getId()));
        saveShoppingCart(shoppingCart);
    }

    @Override
    public Integer getTotalNum(String appInfoId) {
        return this.count(new QueryWrapper<DaasApplication>().lambda().eq(DaasApplication::getAppInfoId, appInfoId));
    }

    @Override
    public Integer getTotalNumInShoppingCart(String shoppingCartId) {
        return this.count(new QueryWrapper<DaasApplication>().lambda().eq(DaasApplication::getShoppingCartId, shoppingCartId));
    }

    /**
     * 删除购物车
     *
     * @param shoppingCartId
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {
        this.remove(new QueryWrapper<DaasApplication>().lambda().eq(DaasApplication::getShoppingCartId,shoppingCartId));
    }

    /**
     * 服务关联订单(submitMerge处理)
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {

    }

    @Override
    public List<DaasApplication> getImplServerListByAppInfoId(String id) {
        List<DaasApplication> list = this.list(new QueryWrapper<DaasApplication>().lambda()
                .eq(DaasApplication::getAppInfoId, id)
                .orderByAsc(DaasApplication::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<DaasApplication> serverList) {
//        this.remove(new QueryWrapper<DaasApplication>().lambda()
//                .eq(DaasApplication::getAppInfoId, appInfoId));
//
//        if (serverList != null && !serverList.isEmpty()) {
//            for (DaasApplication txyfwImpl : serverList) {
//                txyfwImpl.setAppInfoId(appInfoId);
//                this.merge(txyfwImpl);
//            }
//        }
    }


}
