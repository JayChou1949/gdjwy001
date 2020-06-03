package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.PaasSecurityTamperProof;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.dao.application.PaasSecurityTamperProofMapper;
import com.upd.hwcloud.service.application.IPaasSecurityTamperProofService;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 大数据安全体系-网页防篡改 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2020-04-07
 */
@Service
public class PaasSecurityTamperProofServiceImpl extends ServiceImpl<PaasSecurityTamperProofMapper, PaasSecurityTamperProof> implements IPaasSecurityTamperProofService {

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

//    /**
//     * 老数据迁移
//     *
//     * @param shoppingCartId
//     * @param appInfoId
//     */
//    @Transactional(rollbackFor = Throwable.class)
//    @Override
//    public void oldDataMove(String shoppingCartId, String appInfoId) {
//        this.update(new PaasSecurityTamperProof(),new UpdateWrapper<PaasSecurityTamperProof>().lambda().eq(PaasSecurityTamperProof::getAppInfoId,appInfoId)
//                .set(PaasSecurityTamperProof::getShoppingCartId,shoppingCartId));
//
//
//        this.update(new PaasSecurityTamperProof(),new UpdateWrapper<PaasSecurityTamperProof>().lambda().eq(PaasSecurityTamperProof::getShoppingCartId,shoppingCartId)
//                .set(PaasSecurityTamperProof::getAppInfoId,""));
//    }
}
