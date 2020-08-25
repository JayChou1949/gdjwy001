package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.IaasTxyfzjh;
import com.hirisun.cloud.iaas.bean.IaasTxyfzjhExt;
import com.hirisun.cloud.iaas.mapper.IaasTxyfzjhMapper;
import com.hirisun.cloud.iaas.service.IIaasTxyfzjhExtService;
import com.hirisun.cloud.iaas.service.IIaasTxyfzjhService;
import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

/**
 * <p>
 * 飞识二期授权码申请信息 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-04-16
 */
@Service
public class IaasTxyfzjhServiceImpl extends ServiceImpl<IaasTxyfzjhMapper, IaasTxyfzjh> implements IIaasTxyfzjhService {

    @Autowired
    private IIaasTxyfzjhExtService iaasTxyfzjhExtService;


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<IaasTxyfzjh> serverList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<IaasTxyfzjh>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfzjh database : serverList) {
                database.setId(null);
                database.setShoppingCartId(shoppingCart.getId());
                this.save(database);
                List<IaasTxyfzjhExt> exts = database.getIaasTxyfzjhExts();
                for (IaasTxyfzjhExt ext:exts){
                    ext.setId(null);
                    ext.setShoppingCartId(shoppingCart.getId());
                    ext.setMasterId(database.getId());
                    iaasTxyfzjhExtService.save(ext);
                }

            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<IaasTxyfzjh> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<IaasTxyfzjh>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfzjh database : serverList) {
                database.setId(null);
                database.setAppInfoId(info.getId());
                this.save(database);
                List<IaasTxyfzjhExt> exts = database.getIaasTxyfzjhExts();
                for (IaasTxyfzjhExt ext:exts){
                    ext.setId(null);
                    ext.setAppInfoId(info.getId());
                    ext.setMasterId(database.getId());
                    iaasTxyfzjhExtService.save(ext);
                }

            }
        }
    }

    @Override
    public List<IaasTxyfzjh> getByAppInfoId(String appInfoId) {
        List<IaasTxyfzjh> datas = this.list(new QueryWrapper<IaasTxyfzjh>().lambda()
                .eq(IaasTxyfzjh::getAppInfoId, appInfoId)
                .orderByAsc(IaasTxyfzjh::getCreateTime));
        for (IaasTxyfzjh data:datas){
        List<IaasTxyfzjhExt> exts = iaasTxyfzjhExtService.list(new QueryWrapper<IaasTxyfzjhExt>().lambda()
                .eq(IaasTxyfzjhExt::getMasterId, data.getId())
                .orderByAsc(IaasTxyfzjhExt::getCreateTime));
        data.setIaasTxyfzjhExts(exts);
        }
        return datas;
    }

    @Override
    public List<IaasTxyfzjh> getByShoppingCartId(String shoppingCartId) {
        List<IaasTxyfzjh> datas = this.list(new QueryWrapper<IaasTxyfzjh>().lambda()
                .eq(IaasTxyfzjh::getShoppingCartId, shoppingCartId)
                .orderByAsc(IaasTxyfzjh::getCreateTime));
        for (IaasTxyfzjh data:datas){
            List<IaasTxyfzjhExt> exts = iaasTxyfzjhExtService.list(new QueryWrapper<IaasTxyfzjhExt>().lambda()
                    .eq(IaasTxyfzjhExt::getMasterId, data.getId())
                    .orderByAsc(IaasTxyfzjhExt::getCreateTime));
            data.setIaasTxyfzjhExts(exts);
        }
        return datas;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<IaasTxyfzjh>().lambda().eq(IaasTxyfzjh::getAppInfoId, info.getId()));
        iaasTxyfzjhExtService.remove(new QueryWrapper<IaasTxyfzjhExt>().lambda().eq(IaasTxyfzjhExt::getAppInfoId, info.getId()));
        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<IaasTxyfzjh>().lambda().eq(IaasTxyfzjh::getShoppingCartId, shoppingCart.getId()));
        iaasTxyfzjhExtService.remove(new QueryWrapper<IaasTxyfzjhExt>().lambda().eq(IaasTxyfzjhExt::getShoppingCartId, shoppingCart.getId()));
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
        this.remove(new QueryWrapper<IaasTxyfzjh>().lambda().eq(IaasTxyfzjh::getShoppingCartId, shoppingCartId));
        iaasTxyfzjhExtService.remove(new QueryWrapper<IaasTxyfzjhExt>().lambda().eq(IaasTxyfzjhExt::getShoppingCartId, shoppingCartId));
    }

    /**
     * 服务关联订单
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {
        this.update(new IaasTxyfzjh(),new UpdateWrapper<IaasTxyfzjh>().lambda().eq(IaasTxyfzjh::getShoppingCartId,shoppingCartId)
                .set(IaasTxyfzjh::getAppInfoId,appInfoId));
        iaasTxyfzjhExtService.update(new IaasTxyfzjhExt(),new UpdateWrapper<IaasTxyfzjhExt>().lambda().eq(IaasTxyfzjhExt::getShoppingCartId,shoppingCartId)
            .set(IaasTxyfzjhExt::getAppInfoId,appInfoId));
    }

}
