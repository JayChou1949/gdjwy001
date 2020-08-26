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
import com.hirisun.cloud.paas.bean.PaasDms;
import com.hirisun.cloud.paas.mapper.PaasDmsMapper;
import com.hirisun.cloud.paas.service.IPaasDmsService;

/**
 * 基于虚拟机的DMS分布式消息 服务实现类
 */
@Service
public class PaasDmsServiceImpl extends ServiceImpl<PaasDmsMapper, PaasDms> implements IPaasDmsService {

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<PaasDms> paasDmsList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<PaasDms>>(){});
    	
        for(PaasDms dms:paasDmsList){
            dms.setId(null);
            dms.setShoppingCartId(shoppingCart.getId());
            dms.insert();
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<PaasDms> paasDmsList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<PaasDms>>(){});
    	
        for(PaasDms dms:paasDmsList){
            dms.setId(null);
            dms.setAppInfoId(info.getId());
            dms.insert();
        }
    }

    /**
     * 获取申请信息
     *
     * @param appInfoId
     */
    @Override
    public List<PaasDms> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<PaasDms>().lambda().eq(PaasDms::getAppInfoId,appInfoId)
                .orderByDesc(PaasDms::getModifiedTime));
    }

    @Override
    public List<PaasDms> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasDms>().lambda().eq(PaasDms::getShoppingCartId,shoppingCartId)
                    .orderByDesc(PaasDms::getModifiedTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<PaasDms>().lambda().eq(PaasDms::getAppInfoId,info.getId()));

        this.save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<PaasDms>().lambda().eq(PaasDms::getShoppingCartId,shoppingCart.getId()));
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
        this.remove(new QueryWrapper<PaasDms>().lambda().eq(PaasDms::getShoppingCartId,shoppingCartId));
    }

    /**
     * 服务关联订单
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {
        this.update(new PaasDms(),new UpdateWrapper<PaasDms>().lambda()
                .eq(PaasDms::getShoppingCartId,shoppingCartId)
                .set(PaasDms::getAppInfoId,appInfoId));
    }
}
