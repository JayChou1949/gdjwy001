package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.bean.entity.application.paas.PaasElb;
import com.upd.hwcloud.dao.application.PaasElbMapper;
import com.upd.hwcloud.service.application.IPaasElbService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * ELB弹性负载均衡 服务实现类
 * </p>
 *
 * @author yyc
 * @since 2020-05-07
 */
@Service
public class PaasElbServiceImpl extends ServiceImpl<PaasElbMapper, PaasElb> implements IPaasElbService {

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<PaasElb> shoppingCart) {
        List<PaasElb> elbList = shoppingCart.getServerList();
        for(PaasElb elb:elbList){
            elb.setId(null);
            elb.setShoppingCartId(shoppingCart.getId());
            elb.insert();
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<PaasElb, Object> info) {
        List<PaasElb> elbList = info.getServerList();
        for(PaasElb elb:elbList){
            elb.setId(null);
            elb.setAppInfoId(info.getId());
            elb.insert();
        }
    }

    /**
     * 获取申请信息
     *
     * @param appInfoId
     */
    @Override
    public List<PaasElb> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<PaasElb>().lambda().eq(PaasElb::getAppInfoId,appInfoId)
                    .orderByDesc(PaasElb::getModifiedTime));
    }

    @Override
    public List<PaasElb> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<PaasElb>().lambda().eq(PaasElb::getShoppingCartId,shoppingCartId)
                .orderByDesc(PaasElb::getModifiedTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasElb, Object> info) {
        this.remove(new QueryWrapper<PaasElb>().lambda().eq(PaasElb::getAppInfoId,info.getId()));
        this.save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasElb> shoppingCart) {
        this.remove(new QueryWrapper<PaasElb>().lambda().eq(PaasElb::getShoppingCartId,shoppingCart.getId()));
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
        this.remove(new QueryWrapper<PaasElb>().lambda().eq(PaasElb::getShoppingCartId,shoppingCartId));
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
        this.update(new PaasElb(),new UpdateWrapper<PaasElb>().lambda()
                .eq(PaasElb::getShoppingCartId,shoppingCartId)
                .set(PaasElb::getAppInfoId,appInfoId));
    }

//    /**
//     * 老数据迁移
//     *
//     * @param shoppingCartId
//     * @param appInfoId
//     */
//    @Override
//    public void oldDataMove(String shoppingCartId, String appInfoId) {
//
//    }
}
