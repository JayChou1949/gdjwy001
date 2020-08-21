package com.hirisun.cloud.order.service.paas.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.paas.PaasSecurityTamperProof;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.mapper.paas.PaasSecurityTamperProofMapper;
import com.hirisun.cloud.order.service.paas.IPaasSecurityTamperProofService;

/**
 * 大数据安全体系-网页防篡改 服务实现类
 */
@Service
public class PaasSecurityTamperProofServiceImpl extends ServiceImpl<PaasSecurityTamperProofMapper, 
	PaasSecurityTamperProof> implements IPaasSecurityTamperProofService {

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<PaasSecurityTamperProof> shoppingCart) {
        List<PaasSecurityTamperProof> serverList = shoppingCart.getServerList();
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
    public void save(ApplicationInfo<PaasSecurityTamperProof, Object> info) {
        List<PaasSecurityTamperProof> serverList = info.getServerList();
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
    public List<PaasSecurityTamperProof> getByAppInfoId(String appInfoId) {
        List<PaasSecurityTamperProof> list = this.list(new QueryWrapper<PaasSecurityTamperProof>().lambda()
                .eq(PaasSecurityTamperProof::getAppInfoId,appInfoId)
                .orderByDesc(PaasSecurityTamperProof::getCreateTime));
        return list;
    }

    @Override
    public List<PaasSecurityTamperProof> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasSecurityTamperProof>().lambda()
                .eq(PaasSecurityTamperProof::getShoppingCartId,shoppingCartId)
                .orderByDesc(PaasSecurityTamperProof::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasSecurityTamperProof, Object> info) {
        this.remove(new QueryWrapper<PaasSecurityTamperProof>().lambda()
                .eq(PaasSecurityTamperProof::getAppInfoId,info.getId()));
        this.save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasSecurityTamperProof> shoppingCart) {
        this.remove(new QueryWrapper<PaasSecurityTamperProof>().lambda()
                .eq(PaasSecurityTamperProof::getShoppingCartId,shoppingCart.getId()));
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
        this.remove(new QueryWrapper<PaasSecurityTamperProof>().lambda()
                .eq(PaasSecurityTamperProof::getShoppingCartId,shoppingCartId));
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
        this.update(new PaasSecurityTamperProof(),new UpdateWrapper<PaasSecurityTamperProof>().lambda().eq(PaasSecurityTamperProof::getShoppingCartId,shoppingCartId)
                .set(PaasSecurityTamperProof::getAppInfoId,appInfoId));
    }

}
