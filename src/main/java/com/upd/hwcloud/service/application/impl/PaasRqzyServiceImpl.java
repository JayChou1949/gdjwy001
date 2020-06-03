package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.PaasRqzy;
import com.upd.hwcloud.bean.entity.application.PaasRqzyFwq;
import com.upd.hwcloud.bean.entity.application.PaasRqzyImpl;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.bean.entity.application.paas.rdb.PaasRdbAcc;
import com.upd.hwcloud.bean.entity.application.paas.rdb.PaasRdbBase;
import com.upd.hwcloud.bean.entity.application.paas.rdb.PaasRdbInfo;
import com.upd.hwcloud.dao.application.PaasRqzyMapper;
import com.upd.hwcloud.service.application.IPaasRqzyFwqService;
import com.upd.hwcloud.service.application.IPaasRqzyImplService;
import com.upd.hwcloud.service.application.IPaasRqzyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * PAAS容器资源申请信息 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-05-28
 */
@Service
public class PaasRqzyServiceImpl extends ServiceImpl<PaasRqzyMapper, PaasRqzy> implements IPaasRqzyService {

    @Autowired
    IPaasRqzyFwqService paasRqzyFwqService;
    @Autowired
    IPaasRqzyImplService paasRqzyImplService;


    @Override
    public void saveShoppingCart(ShoppingCart<PaasRqzy> shoppingCart) {
        List<PaasRqzy> serverList = shoppingCart.getServerList();
        if(CollectionUtils.isNotEmpty(serverList)){
            for(PaasRqzy rqzy : serverList){
                rqzy.setId(null);
                rqzy.setShoppingCartId(shoppingCart.getId());
                this.save(rqzy);

                List<PaasRqzyFwq> rqzyFwqList = rqzy.getRqzyFwqs();
                List<PaasRqzyImpl> rqzyImplList = Lists.newArrayList();
                if(CollectionUtils.isNotEmpty(rqzyFwqList)){
                    for(PaasRqzyFwq rqzyFwq:rqzyFwqList){
                        rqzyFwq.setId(null);
                        rqzyFwq.setMasterId(rqzy.getId());
                        rqzyFwq.setShoppingCartId(shoppingCart.getId());
                        paasRqzyFwqService.save(rqzyFwq);

                        rqzyImplList.addAll(createImpls(null,rqzyFwq,shoppingCart.getId()));
                    }
                }
                if (rqzyImplList.size()>0){
                    paasRqzyImplService.saveBatch(rqzyImplList);
                }
            }
        }

    }

    @Override
    public void save(ApplicationInfo<PaasRqzy, Object> info) {
        List<PaasRqzy> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasRqzy rqzy : serverList) {
                rqzy.setId(null);
                rqzy.setAppInfoId(info.getId());
                this.save(rqzy);

                List<PaasRqzyFwq> rqzyFwqList = rqzy.getRqzyFwqs();
                List<PaasRqzyImpl> rqzyImplList = new ArrayList<>();
                if (rqzyFwqList != null && !rqzyFwqList.isEmpty()) {
                    for (PaasRqzyFwq rqzyFwq : rqzyFwqList) {
                        rqzyFwq.setId(null);
                        rqzyFwq.setAppInfoId(info.getId());
                        rqzyFwq.setMasterId(rqzy.getId());
                        paasRqzyFwqService.save(rqzyFwq);
                        rqzyImplList.addAll(createImpls(info.getId(),rqzyFwq,null));
                    }
                }
                if (rqzyImplList.size()>0){
                    paasRqzyImplService.saveBatch(rqzyImplList);
                }
            }
        }
    }

    private List<PaasRqzyImpl> createImpls(String appId, PaasRqzyFwq rqzyFwq,String shopId) {
        List<PaasRqzyImpl> paasRqzyImpls = new ArrayList<>();
        for (int i=0;i<rqzyFwq.getNodeNum();i++){
            PaasRqzyImpl impl = new PaasRqzyImpl();
            if(StringUtils.isNotBlank(appId)){
                impl.setAppInfoId(appId);
            }
            if(StringUtils.isNotBlank(shopId)){
                impl.setShoppingCartId(shopId);
            }
            impl.setStorages(rqzyFwq.getStorages());
            impl.setNodeConfig(rqzyFwq.getNodeConfig());
            paasRqzyImpls.add(impl);
        }
        return paasRqzyImpls;
    }

    @Override
    public List<PaasRqzy> getByAppInfoId(String appInfoId) {
        List<PaasRqzy> rqzyList = this.list(new QueryWrapper<PaasRqzy>().lambda()
                .eq(PaasRqzy::getAppInfoId, appInfoId)
                .orderByAsc(PaasRqzy::getCreateTime));
        if (rqzyList != null && !rqzyList.isEmpty()) {
            for (PaasRqzy rqzy : rqzyList) {
                List<PaasRqzyFwq> rqzyFwqList = paasRqzyFwqService.list(new QueryWrapper<PaasRqzyFwq>().lambda()
                        .eq(PaasRqzyFwq::getAppInfoId, appInfoId)
                        .orderByAsc(PaasRqzyFwq::getCreateTime));
                rqzy.setRqzyFwqs(rqzyFwqList);
            }
        }
        return rqzyList;
    }

    @Override
    public List<PaasRqzy> getByShoppingCartId(String shoppingCartId) {
        List<PaasRqzy> rqzyList = this.list(new QueryWrapper<PaasRqzy>().lambda()
                .eq(PaasRqzy::getShoppingCartId, shoppingCartId)
                .orderByAsc(PaasRqzy::getCreateTime));
        if (rqzyList != null && !rqzyList.isEmpty()) {
            for (PaasRqzy rqzy : rqzyList) {
                List<PaasRqzyFwq> rqzyFwqList = paasRqzyFwqService.list(new QueryWrapper<PaasRqzyFwq>().lambda()
                        .eq(PaasRqzyFwq::getShoppingCartId, shoppingCartId)
                        .orderByAsc(PaasRqzyFwq::getCreateTime));
                rqzy.setRqzyFwqs(rqzyFwqList);
            }
        }
        return rqzyList;
    }

    @Transactional
    @Override
    public void update(ApplicationInfo<PaasRqzy, Object> info) {
        this.remove(new QueryWrapper<PaasRqzy>().lambda().eq(PaasRqzy::getAppInfoId, info.getId()));
        paasRqzyFwqService.remove(new QueryWrapper<PaasRqzyFwq>().lambda().eq(PaasRqzyFwq::getAppInfoId, info.getId()));
        paasRqzyImplService.remove(new QueryWrapper<PaasRqzyImpl>().lambda().eq(PaasRqzyImpl::getAppInfoId, info.getId()));
        save(info);
    }

    @Transactional
    @Override
    public void updateShoppingCart(ShoppingCart<PaasRqzy> shoppingCart) {
        this.remove(new QueryWrapper<PaasRqzy>().lambda().eq(PaasRqzy::getShoppingCartId, shoppingCart.getId()));
        paasRqzyFwqService.remove(new QueryWrapper<PaasRqzyFwq>().lambda().eq(PaasRqzyFwq::getShoppingCartId, shoppingCart.getId()));
        paasRqzyImplService.remove(new QueryWrapper<PaasRqzyImpl>().lambda().eq(PaasRqzyImpl::getShoppingCartId, shoppingCart.getId()));
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
    @Transactional
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {
        this.remove(new QueryWrapper<PaasRqzy>().lambda().eq(PaasRqzy::getShoppingCartId, shoppingCartId));
        paasRqzyFwqService.remove(new QueryWrapper<PaasRqzyFwq>().lambda().eq(PaasRqzyFwq::getShoppingCartId, shoppingCartId));
        paasRqzyImplService.remove(new QueryWrapper<PaasRqzyImpl>().lambda().eq(PaasRqzyImpl::getShoppingCartId, shoppingCartId));
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
        paasRqzyFwqService.update(new PaasRqzyFwq(),new UpdateWrapper<PaasRqzyFwq>().lambda().eq(PaasRqzyFwq::getShoppingCartId,shoppingCartId)
                .set(PaasRqzyFwq::getAppInfoId,appInfoId));
        paasRqzyImplService.update(new PaasRqzyImpl(),new UpdateWrapper<PaasRqzyImpl>().lambda().eq(PaasRqzyImpl::getShoppingCartId,shoppingCartId)
                .set(PaasRqzyImpl::getAppInfoId,appInfoId));
        this.update(new PaasRqzy(),new UpdateWrapper<PaasRqzy>().lambda().eq(PaasRqzy::getShoppingCartId,shoppingCartId)
                .set(PaasRqzy::getAppInfoId,appInfoId));
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
//        this.update(new PaasRqzy(),new UpdateWrapper<PaasRqzy>().lambda().eq(PaasRqzy::getAppInfoId,appInfoId)
//                .set(PaasRqzy::getShoppingCartId,shoppingCartId));
//        this.update(new PaasRqzy(),new UpdateWrapper<PaasRqzy>().lambda().eq(PaasRqzy::getShoppingCartId,shoppingCartId)
//                .set(PaasRqzy::getAppInfoId,""));
//
//
//        paasRqzyFwqService.update(new PaasRqzyFwq(),new UpdateWrapper<PaasRqzyFwq>().lambda().eq(PaasRqzyFwq::getAppInfoId,appInfoId)
//                .set(PaasRqzyFwq::getShoppingCartId,shoppingCartId));
//        paasRqzyFwqService.update(new PaasRqzyFwq(),new UpdateWrapper<PaasRqzyFwq>().lambda().eq(PaasRqzyFwq::getShoppingCartId,shoppingCartId)
//                .set(PaasRqzyFwq::getAppInfoId,""));
//
//        paasRqzyImplService.update(new PaasRqzyImpl(),new UpdateWrapper<PaasRqzyImpl>().lambda().eq(PaasRqzyImpl::getAppInfoId,appInfoId)
//                .set(PaasRqzyImpl::getShoppingCartId,shoppingCartId));
//        paasRqzyImplService.update(new PaasRqzyImpl(),new UpdateWrapper<PaasRqzyImpl>().lambda().eq(PaasRqzyImpl::getShoppingCartId,shoppingCartId)
//                .set(PaasRqzyImpl::getAppInfoId,""));
//    }
}
