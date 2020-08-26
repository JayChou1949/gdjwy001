package com.hirisun.cloud.paas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;
import com.hirisun.cloud.paas.bean.PaasDcs;
import com.hirisun.cloud.paas.mapper.PaasDcsMapper;
import com.hirisun.cloud.paas.service.IPaasDcsService;

/**
 *  基于虚拟机的DCS分布式缓存 服务实现类
 */
@Service
public class PaasDcsServiceImpl extends ServiceImpl<PaasDcsMapper, PaasDcs> implements IPaasDcsService {

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<PaasDcs> dcsList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<PaasDcs>>(){});
    	
        for (PaasDcs dcs:dcsList){
            dcs.setId(null);
            dcs.setShoppingCartId(shoppingCart.getId());
            this.save(dcs);
        }
    }

    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<PaasDcs> dcsList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<PaasDcs>>(){});
    	
        for (PaasDcs dcs:dcsList){
            dcs.setId(null);
            dcs.setAppInfoId(info.getId());
            this.save(dcs);
        }
    }

    /**
     * 获取申请信息
     *
     * @param appInfoId
     */
    @Override
    public List<PaasDcs> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<PaasDcs>().lambda().eq(PaasDcs::getAppInfoId,appInfoId).orderByDesc(PaasDcs::getModifiedTime));
    }

    @Override
    public List<PaasDcs> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasDcs>().lambda().eq(PaasDcs::getShoppingCartId,shoppingCartId).orderByDesc(PaasDcs::getModifiedTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<PaasDcs>().lambda().eq(PaasDcs::getAppInfoId,info.getId()));
        this.save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<PaasDcs>().lambda().eq(PaasDcs::getShoppingCartId,shoppingCart.getId()));
        this.saveShoppingCart(shoppingCart);
    }

    /**
     * 获取申请总数(购物车显示字段)
     *
     * @param appInfoId
     */
    @Override
    public Integer getTotalNum(String appInfoId) {
        return 1;
    }

    @Override
    public Integer getTotalNumInShoppingCart(String shoppingCartId) {
        return 1;
    }

    /**
     * 购物车删除
     *
     * @param shoppingCartId 购物车ID
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {
        this.remove(new QueryWrapper<PaasDcs>().lambda().eq(PaasDcs::getShoppingCartId,shoppingCartId));
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
        this.update(new PaasDcs(),new UpdateWrapper<PaasDcs>().lambda()
                .eq(PaasDcs::getShoppingCartId,shoppingCartId)
                .set(PaasDcs::getAppInfoId,appInfoId));
    }

}
