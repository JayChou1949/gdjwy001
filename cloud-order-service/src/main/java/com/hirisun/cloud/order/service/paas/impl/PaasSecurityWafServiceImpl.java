package com.hirisun.cloud.order.service.paas.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.paas.PaasSecurityWaf;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.mapper.paas.PaasSecurityWafMapper;
import com.hirisun.cloud.order.service.paas.IPaasSecurityWafService;

/**
 * 大数据安全体系-WAF 服务实现类
 */
@Service
public class PaasSecurityWafServiceImpl extends ServiceImpl<PaasSecurityWafMapper, 
	PaasSecurityWaf> implements IPaasSecurityWafService {

    @Override
    public void saveShoppingCart(ShoppingCart<PaasSecurityWaf> shoppingCart) {
        List<PaasSecurityWaf> serverList = shoppingCart.getServerList();
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
    public void save(ApplicationInfo<PaasSecurityWaf, Object> info) {
        List<PaasSecurityWaf> serverList = info.getServerList();
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
    public List<PaasSecurityWaf> getByAppInfoId(String appInfoId) {
        List<PaasSecurityWaf> list = this.list(new QueryWrapper<PaasSecurityWaf>().lambda()
                .eq(PaasSecurityWaf::getAppInfoId,appInfoId)
                .orderByDesc(PaasSecurityWaf::getCreateTime));
        return list;
    }

    @Override
    public List<PaasSecurityWaf> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasSecurityWaf>().lambda()
                .eq(PaasSecurityWaf::getShoppingCartId,shoppingCartId)
                .orderByDesc(PaasSecurityWaf::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasSecurityWaf, Object> info) {
        this.remove(new QueryWrapper<PaasSecurityWaf>().lambda()
                .eq(PaasSecurityWaf::getAppInfoId,info.getId()));
        this.save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasSecurityWaf> shoppingCart) {
        this.remove(new QueryWrapper<PaasSecurityWaf>().lambda()
                .eq(PaasSecurityWaf::getShoppingCartId,shoppingCart.getId()));
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
        this.remove(new QueryWrapper<PaasSecurityWaf>().lambda()
                .eq(PaasSecurityWaf::getShoppingCartId,shoppingCartId));
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
        this.update(new PaasSecurityWaf(),new UpdateWrapper<PaasSecurityWaf>().lambda().eq(PaasSecurityWaf::getShoppingCartId,shoppingCartId)
                .set(PaasSecurityWaf::getAppInfoId,appInfoId));
    }

}
