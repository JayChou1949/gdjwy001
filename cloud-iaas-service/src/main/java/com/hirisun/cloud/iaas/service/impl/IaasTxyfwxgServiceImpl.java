package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.IaasTxyfwxg;
import com.hirisun.cloud.iaas.mapper.IaasTxyfwxgMapper;
import com.hirisun.cloud.iaas.service.IIaasTxyfwxgService;
import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

/**
 *  服务实现类
 */
@Service
public class IaasTxyfwxgServiceImpl extends ServiceImpl<IaasTxyfwxgMapper, 
	IaasTxyfwxg> implements IIaasTxyfwxgService {


    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<IaasTxyfwxg> serverList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<IaasTxyfwxg>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfwxg txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setShoppingCartId(shoppingCart.getId());
                this.save(txyfw);
            }
        }
    }

    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<IaasTxyfwxg> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<IaasTxyfwxg>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfwxg txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setAppInfoId(info.getId());
                this.save(txyfw);
            }
        }
    }

    @Override
    public List<IaasTxyfwxg> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<IaasTxyfwxg>().lambda()
                .eq(IaasTxyfwxg::getAppInfoId, appInfoId)
                .orderByAsc(IaasTxyfwxg::getCreateTime));
    }

    @Override
    public List<IaasTxyfwxg> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<IaasTxyfwxg>().lambda()
                .eq(IaasTxyfwxg::getShoppingCartId, shoppingCartId)
                .orderByAsc(IaasTxyfwxg::getCreateTime));
    }

    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<IaasTxyfwxg>().lambda().eq(IaasTxyfwxg::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<IaasTxyfwxg>().lambda().eq(IaasTxyfwxg::getShoppingCartId, shoppingCart.getId()));
        saveShoppingCart(shoppingCart);
    }

    @Override
    public Integer getTotalNum(String appInfoId) {
        return null;
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
        this.remove(new QueryWrapper<IaasTxyfwxg>().lambda().eq(IaasTxyfwxg::getShoppingCartId, shoppingCartId));
    }

    /**
     * 服务关联订单
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {
        this.update(new IaasTxyfwxg(),new UpdateWrapper<IaasTxyfwxg>().lambda().eq(IaasTxyfwxg::getShoppingCartId,shoppingCartId)
            .set(IaasTxyfwxg::getAppInfoId,appInfoId));
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
//        this.update(new IaasTxyfwxg(),new UpdateWrapper<IaasTxyfwxg>().lambda().eq(IaasTxyfwxg::getAppInfoId,appInfoId)
//                .set(IaasTxyfwxg::getShoppingCartId,shoppingCartId));
//
//
//        this.update(new IaasTxyfwxg(),new UpdateWrapper<IaasTxyfwxg>().lambda().eq(IaasTxyfwxg::getShoppingCartId,shoppingCartId)
//                .set(IaasTxyfwxg::getAppInfoId,""));
//    }
}
