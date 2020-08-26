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
import com.hirisun.cloud.paas.bean.PaasFbsbxsjk;
import com.hirisun.cloud.paas.mapper.PaasFbsbxsjkMapper;
import com.hirisun.cloud.paas.service.IPaasFbsbxsjkService;

/**
 * 分布式并行数据库申请信息表 服务实现类
 */
@Service
public class PaasFbsbxsjkServiceImpl extends ServiceImpl<PaasFbsbxsjkMapper, PaasFbsbxsjk> implements IPaasFbsbxsjkService {


    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<PaasFbsbxsjk> serverList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<PaasFbsbxsjk>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasFbsbxsjk database : serverList) {
                database.setId(null);
                database.setShoppingCartId(shoppingCart.getId());
                this.save(database);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<PaasFbsbxsjk> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<PaasFbsbxsjk>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasFbsbxsjk database : serverList) {
                database.setId(null);
                database.setAppInfoId(info.getId());
                this.save(database);
            }
        }
    }

    @Override
    public List<PaasFbsbxsjk> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<PaasFbsbxsjk>().lambda()
                .eq(PaasFbsbxsjk::getAppInfoId, appInfoId)
                .orderByAsc(PaasFbsbxsjk::getCreateTime));
    }

    @Override
    public List<PaasFbsbxsjk> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasFbsbxsjk>().lambda()
                .eq(PaasFbsbxsjk::getShoppingCartId, shoppingCartId)
                .orderByAsc(PaasFbsbxsjk::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<PaasFbsbxsjk>().lambda().eq(PaasFbsbxsjk::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<PaasFbsbxsjk>().lambda().eq(PaasFbsbxsjk::getShoppingCartId, shoppingCart.getId()));

        saveShoppingCart(shoppingCart);
    }

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
        this.remove(new QueryWrapper<PaasFbsbxsjk>().lambda().eq(PaasFbsbxsjk::getShoppingCartId, shoppingCartId));
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
        this.update(new PaasFbsbxsjk(),new UpdateWrapper<PaasFbsbxsjk>().lambda().eq(PaasFbsbxsjk::getShoppingCartId,shoppingCartId)
                .set(PaasFbsbxsjk::getAppInfoId,appInfoId));
    }

//    /**
//     * 老数据迁移
//     *
//     * @param shoppingCartId
//     * @param appInfoId
//     */
//    @Transactional(rollbackFor = Throwable.class)
//    @Override
//    public void oldDataMove(String shoppingCartId, String appInfoId) {
//        this.update(new PaasFbsbxsjk(),new UpdateWrapper<PaasFbsbxsjk>().lambda().eq(PaasFbsbxsjk::getAppInfoId,appInfoId)
//                .set(PaasFbsbxsjk::getShoppingCartId,shoppingCartId));
//
//
//        this.update(new PaasFbsbxsjk(),new UpdateWrapper<PaasFbsbxsjk>().lambda().eq(PaasFbsbxsjk::getShoppingCartId,shoppingCartId)
//                .set(PaasFbsbxsjk::getAppInfoId,""));
//    }

}
