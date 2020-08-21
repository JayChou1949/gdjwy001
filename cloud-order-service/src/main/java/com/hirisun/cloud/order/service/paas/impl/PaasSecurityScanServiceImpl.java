package com.hirisun.cloud.order.service.paas.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.paas.PaasSecurityScan;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.mapper.paas.PaasSecurityScanMapper;
import com.hirisun.cloud.order.service.paas.IPaasSecurityScanService;

/**
 * 大数据安全体系-漏洞扫描 服务实现类
 */
@Service
public class PaasSecurityScanServiceImpl extends ServiceImpl<PaasSecurityScanMapper, 
	PaasSecurityScan> implements IPaasSecurityScanService {

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<PaasSecurityScan> shoppingCart) {
        List<PaasSecurityScan> serverList = shoppingCart.getServerList();
        if(CollectionUtils.isNotEmpty(serverList)){
            serverList.forEach(server ->{
                server.setId(null);
                server.setShoppingCartId(shoppingCart.getId());
            });
            this.saveBatch(serverList);
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<PaasSecurityScan, Object> info) {
        List<PaasSecurityScan> serverList = info.getServerList();
        if(CollectionUtils.isNotEmpty(serverList)){
            serverList.forEach(server ->{
                server.setId(null);
                server.setAppInfoId(info.getId());
            });
            this.saveBatch(serverList);
        }
    }

    /**
     * 获取申请信息
     *
     * @param appInfoId
     */
    @Override
    public List<PaasSecurityScan> getByAppInfoId(String appInfoId) {
        List<PaasSecurityScan> list = this.list(new QueryWrapper<PaasSecurityScan>().lambda()
                .eq(PaasSecurityScan::getAppInfoId,appInfoId)
                .orderByDesc(PaasSecurityScan::getCreateTime));
        return list;
    }

    @Override
    public List<PaasSecurityScan> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasSecurityScan>().lambda()
                .eq(PaasSecurityScan::getShoppingCartId,shoppingCartId)
                .orderByDesc(PaasSecurityScan::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasSecurityScan, Object> info) {
        this.remove(new QueryWrapper<PaasSecurityScan>().lambda()
                .eq(PaasSecurityScan::getAppInfoId,info.getId()));
        this.save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasSecurityScan> shoppingCart) {
        this.remove(new QueryWrapper<PaasSecurityScan>().lambda()
                .eq(PaasSecurityScan::getShoppingCartId,shoppingCart.getId()));
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
        this.remove(new QueryWrapper<PaasSecurityScan>().lambda()
                .eq(PaasSecurityScan::getShoppingCartId,shoppingCartId));
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
        this.update(new PaasSecurityScan(),new UpdateWrapper<PaasSecurityScan>().lambda().eq(PaasSecurityScan::getShoppingCartId,shoppingCartId)
                .set(PaasSecurityScan::getAppInfoId,appInfoId));
    }

}
