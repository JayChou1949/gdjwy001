package com.hirisun.cloud.saas.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;
import com.hirisun.cloud.saas.bean.SaasServiceApplication;
import com.hirisun.cloud.saas.mapper.SaasServiceApplicationMapper;
import com.hirisun.cloud.saas.service.ISaasServiceApplicationService;

/**
 * saas 服务实现类
 */
@Service
public class SaasServiceApplicationServiceImpl extends ServiceImpl<SaasServiceApplicationMapper, 
	SaasServiceApplication> implements ISaasServiceApplicationService {

    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<SaasServiceApplication> serverList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<SaasServiceApplication>>(){});
    	
        if(CollectionUtils.isNotEmpty(serverList)){
            serverList.forEach(server->{
                server.setShoppingCartId(shoppingCart.getId());
            });
            this.saveBatch(serverList);
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<SaasServiceApplication> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<SaasServiceApplication>>(){});
    	
        if(serverList != null && !serverList.isEmpty()){
            serverList.forEach(server->{
                server.setAppInfoId(info.getId());
            });
            this.saveBatch(serverList);
        }
    }

    @Override
    public List<SaasServiceApplication> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<SaasServiceApplication>().lambda()
                    .eq(SaasServiceApplication::getAppInfoId,appInfoId)
                    .orderByAsc(SaasServiceApplication::getCreateTime));
    }

    @Override
    public List<SaasServiceApplication> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<SaasServiceApplication>().lambda()
                .eq(SaasServiceApplication::getShoppingCartId,shoppingCartId)
                .orderByAsc(SaasServiceApplication::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getAppInfoId,info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getShoppingCartId,shoppingCart.getId()));

        saveShoppingCart(shoppingCart);
    }

    @Override
    public Integer getTotalNum(String appInfoId) {
        return this.count(new QueryWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getAppInfoId,appInfoId));
    }

    @Override
    public Integer getTotalNumInShoppingCart(String shoppingCartId) {
        return this.count(new QueryWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getShoppingCartId,shoppingCartId));
    }

    /**
     * 购物车删除
     *
     * @param shoppingCartId 购物车ID
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {
        this.remove(new QueryWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getShoppingCartId,shoppingCartId));
    }

    /**
     * 服务关联订单(特殊处理)
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {
        //关联订单
        this.update(new SaasServiceApplication(),new UpdateWrapper<SaasServiceApplication>().lambda()
                .eq(SaasServiceApplication::getShoppingCartId,shoppingCartId)
                .set(SaasServiceApplication::getAppInfoId,appInfoId));

        //清空购物车关联
        this.update(new SaasServiceApplication(),new UpdateWrapper<SaasServiceApplication>().lambda()
                .eq(SaasServiceApplication::getAppInfoId,appInfoId)
                .set(SaasServiceApplication::getShoppingCartId,""));

    }

    @Override
    public List<SaasServiceApplication> getImplServerListByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<SaasServiceApplication>().lambda()
                .eq(SaasServiceApplication::getAppInfoId,appInfoId)
                .orderByAsc(SaasServiceApplication::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<SaasServiceApplication> serverList) {

    }


    @Override
    public List<SaasServiceApplication> getUnsucessByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<SaasServiceApplication>().lambda()
                        .eq(SaasServiceApplication::getAppInfoId,appInfoId)
                        .isNull(SaasServiceApplication::getAppKey)
                        .isNull(SaasServiceApplication::getAppSecret)
                        .orderByAsc(SaasServiceApplication::getCreateTime));

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
        this.update(new SaasServiceApplication(),new UpdateWrapper<SaasServiceApplication>().lambda()
                .in(SaasServiceApplication::getShoppingCartId,shoppingCartIdList)
                .set(SaasServiceApplication::getAppInfoId,appInfoId));

        //清空购物车关联
        this.update(new SaasServiceApplication(),new UpdateWrapper<SaasServiceApplication>().lambda()
                .eq(SaasServiceApplication::getAppInfoId,appInfoId)
                .set(SaasServiceApplication::getShoppingCartId,""));
    }

}
