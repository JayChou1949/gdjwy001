package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.IaasDxccExt;
import com.hirisun.cloud.iaas.bean.IaasSfswjfw;
import com.hirisun.cloud.iaas.mapper.IaasSfswjfwMapper;
import com.hirisun.cloud.iaas.service.IIaasSfswjfwService;
import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

/**
 * sfs弹性文件服务申请信息 服务实现类
 */
@Service
public class IaasSfswjfwServiceImpl extends ServiceImpl<IaasSfswjfwMapper, 
	IaasSfswjfw> implements IIaasSfswjfwService {


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
        List<IaasSfswjfw> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasSfswjfw txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setShoppingCartId(shoppingCart.getId());
                this.save(txyfw);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<IaasSfswjfw> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<IaasSfswjfw>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasSfswjfw txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setAppInfoId(info.getId());
                this.save(txyfw);
            }
        }
    }

    @Override
    public List<IaasSfswjfw> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<IaasSfswjfw>().lambda()
                .eq(IaasSfswjfw::getAppInfoId, appInfoId)
                .orderByAsc(IaasSfswjfw::getCreateTime));
    }

    @Override
    public List<IaasSfswjfw> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<IaasSfswjfw>().lambda()
                .eq(IaasSfswjfw::getShoppingCartId, shoppingCartId)
                .orderByAsc(IaasSfswjfw::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<IaasSfswjfw>().lambda().eq(IaasSfswjfw::getAppInfoId, info.getId()));
        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<IaasSfswjfw>().lambda().eq(IaasSfswjfw::getShoppingCartId,shoppingCart.getId()));
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
        this.remove(new QueryWrapper<IaasSfswjfw>().lambda().eq(IaasSfswjfw::getShoppingCartId,shoppingCartId));
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
        this.update(new IaasSfswjfw(),new UpdateWrapper<IaasSfswjfw>().lambda()
            .eq(IaasSfswjfw::getShoppingCartId,shoppingCartId)
            .set(IaasSfswjfw::getAppInfoId,appInfoId));
    }

}
