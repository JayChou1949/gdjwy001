package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.IaasYzm;
import com.hirisun.cloud.iaas.mapper.IaasYzmMapper;
import com.hirisun.cloud.iaas.service.IIaasYzmService;
import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

/**
 * IaaS 云桌面申请信息 服务实现类
 */
@Service
public class IaasYzmServiceImpl extends ServiceImpl<IaasYzmMapper, IaasYzm> implements IIaasYzmService {

    @Autowired
    private IaasYzmMapper iaasYzmMapper;


    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<IaasYzm> serverList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<IaasYzm>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasYzm txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setShoppingCartId(shoppingCart.getId());
                this.save(txyfw);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<IaasYzm> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<IaasYzm>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasYzm txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setAppInfoId(info.getId());
                this.save(txyfw);
            }
        }
    }

    @Override
    public List<IaasYzm> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<IaasYzm>().lambda()
                .eq(IaasYzm::getAppInfoId, appInfoId)
                .orderByAsc(IaasYzm::getCreateTime));
    }

    @Override
    public List<IaasYzm> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<IaasYzm>().lambda()
                .eq(IaasYzm::getShoppingCartId, shoppingCartId)
                .orderByAsc(IaasYzm::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<IaasYzm>().lambda().eq(IaasYzm::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<IaasYzm>().lambda().eq(IaasYzm::getShoppingCartId, shoppingCart.getId()));
        saveShoppingCart(shoppingCart);
    }

    @Override
    public Integer getTotalNum(String appInfoId) {
        return iaasYzmMapper.getTotalNum(appInfoId);
    }

    @Override
    public Integer getTotalNumInShoppingCart(String shoppingCartId) {
        return iaasYzmMapper.getTotalNumOfShoppingCart(shoppingCartId);
    }

    /**
     * 购物车删除
     *
     * @param shoppingCartId 购物车ID
     */
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {

    }

    /**
     * 服务关联订单
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {

    }

}
