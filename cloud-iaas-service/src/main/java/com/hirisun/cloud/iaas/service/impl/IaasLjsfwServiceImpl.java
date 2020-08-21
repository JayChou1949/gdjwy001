package com.hirisun.cloud.iaas.service.impl;

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
import com.hirisun.cloud.iaas.bean.IaasLjsfw;
import com.hirisun.cloud.iaas.mapper.IaasLjsfwMapper;
import com.hirisun.cloud.iaas.service.IIaasLjsfwService;
import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

/**
 * 裸金属服务器申请信息 服务实现类
 */
@Service
public class IaasLjsfwServiceImpl extends ServiceImpl<IaasLjsfwMapper, IaasLjsfw> implements IIaasLjsfwService {

    private static final Logger logger = LoggerFactory.getLogger(IaasLjsfwServiceImpl.class);

    @Autowired
    private IaasLjsfwMapper iaasLjsfwMapper;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<IaasLjsfw> serverList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<IaasLjsfw>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasLjsfw ljsfw : serverList) {
                ljsfw.setId(null);
                ljsfw.setShoppingCartId(shoppingCart.getId());
                this.save(ljsfw);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<IaasLjsfw> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<IaasLjsfw>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasLjsfw ljsfw : serverList) {
                ljsfw.setId(null);
                ljsfw.setAppInfoId(info.getId());
                this.save(ljsfw);
            }
        }
    }

    @Override
    public List<IaasLjsfw> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<IaasLjsfw>().lambda()
                .eq(IaasLjsfw::getAppInfoId, appInfoId)
                .orderByAsc(IaasLjsfw::getCreateTime));
    }

    @Override
    public List<IaasLjsfw> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<IaasLjsfw>().lambda()
                .eq(IaasLjsfw::getShoppingCartId, shoppingCartId)
                .orderByAsc(IaasLjsfw::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<IaasLjsfw>().lambda().eq(IaasLjsfw::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<IaasLjsfw>().lambda().eq(IaasLjsfw::getShoppingCartId, shoppingCart.getId()));

        saveShoppingCart(shoppingCart);
    }

    @Override
    public Integer getTotalNum(String appInfoId) {
        return iaasLjsfwMapper.getTotalNum(appInfoId);
    }

    @Override
    public Integer getTotalNumInShoppingCart(String shoppingCartId) {
        return iaasLjsfwMapper.getTotalNumOfShoppingCart(shoppingCartId);
    }

    /**
     * 购物车删除
     *
     * @param shoppingCartId 购物车ID
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {
        this.remove(new QueryWrapper<IaasLjsfw>().lambda().eq(IaasLjsfw::getShoppingCartId,shoppingCartId));
    }

    /**
     * 服务关联订单
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {
        this.update(new IaasLjsfw(),new UpdateWrapper<IaasLjsfw>().lambda()
                .eq(IaasLjsfw::getShoppingCartId,shoppingCartId)
                .set(IaasLjsfw::getAppInfoId,appInfoId));
    }
}
