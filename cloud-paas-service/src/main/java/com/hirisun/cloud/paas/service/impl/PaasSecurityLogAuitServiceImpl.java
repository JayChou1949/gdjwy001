package com.hirisun.cloud.paas.service.impl;

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
import com.hirisun.cloud.paas.bean.PaasSecurityLogAuit;
import com.hirisun.cloud.paas.mapper.PaasSecurityLogAuitMapper;
import com.hirisun.cloud.paas.service.IPaasSecurityLogAuitService;

/**
 * 大数据安全体系-综合日志审计 服务实现类
 */
@Service
public class PaasSecurityLogAuitServiceImpl extends ServiceImpl<PaasSecurityLogAuitMapper, 
	PaasSecurityLogAuit> implements IPaasSecurityLogAuitService {


    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<PaasSecurityLogAuit> serverList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<PaasSecurityLogAuit>>(){});
    	
        if(CollectionUtils.isNotEmpty(serverList)){
            for (PaasSecurityLogAuit paasSecurityLogAuit : serverList) {
                paasSecurityLogAuit.setId(null);
                paasSecurityLogAuit.setShoppingCartId(shoppingCart.getId());
            }
            this.saveBatch(serverList);
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<PaasSecurityLogAuit> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<PaasSecurityLogAuit>>(){});
    	
        if(CollectionUtils.isNotEmpty(serverList)){
            for (PaasSecurityLogAuit paasSecurityLogAuit : serverList) {
                paasSecurityLogAuit.setId(null);
                paasSecurityLogAuit.setAppInfoId(info.getId());
                this.save(paasSecurityLogAuit);
            }
        }
    }

    /**
     * 获取申请信息
     *
     * @param appInfoId
     */
    @Override
    public List<PaasSecurityLogAuit> getByAppInfoId(String appInfoId) {
        List<PaasSecurityLogAuit> list = this.list(new QueryWrapper<PaasSecurityLogAuit>().lambda()
                .eq(PaasSecurityLogAuit::getAppInfoId,appInfoId)
                .orderByDesc(PaasSecurityLogAuit::getCreateTime));
        return list;
    }

    @Override
    public List<PaasSecurityLogAuit> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasSecurityLogAuit>().lambda()
                .eq(PaasSecurityLogAuit::getShoppingCartId,shoppingCartId)
                .orderByDesc(PaasSecurityLogAuit::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<PaasSecurityLogAuit>().lambda()
                .eq(PaasSecurityLogAuit::getAppInfoId,info.getId()));
        this.save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<PaasSecurityLogAuit>().lambda()
                .eq(PaasSecurityLogAuit::getShoppingCartId,shoppingCart.getId()));
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
        this.remove(new QueryWrapper<PaasSecurityLogAuit>().lambda()
                .eq(PaasSecurityLogAuit::getShoppingCartId,shoppingCartId));
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
        this.update(new PaasSecurityLogAuit(),new UpdateWrapper<PaasSecurityLogAuit>().lambda().eq(PaasSecurityLogAuit::getShoppingCartId,shoppingCartId)
                .set(PaasSecurityLogAuit::getAppInfoId,appInfoId));
    }

}
