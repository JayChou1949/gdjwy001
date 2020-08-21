package com.hirisun.cloud.iaas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.hirisun.cloud.iaas.bean.PaasRqzy;
import com.hirisun.cloud.iaas.bean.PaasRqzyFwq;
import com.hirisun.cloud.iaas.bean.PaasRqzyImpl;
import com.hirisun.cloud.iaas.mapper.PaasRqzyMapper;
import com.hirisun.cloud.iaas.service.IPaasRqzyFwqService;
import com.hirisun.cloud.iaas.service.IPaasRqzyImplService;
import com.hirisun.cloud.iaas.service.IPaasRqzyService;
import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

/**
 * PAAS容器资源申请信息 服务实现类
 */
@Service
public class PaasRqzyServiceImpl extends ServiceImpl<PaasRqzyMapper, PaasRqzy> implements IPaasRqzyService {

    @Autowired
    IPaasRqzyFwqService paasRqzyFwqService;
    @Autowired
    IPaasRqzyImplService paasRqzyImplService;


    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<PaasRqzy> serverList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<PaasRqzy>>(){});
    	
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
    public void save(ApplicationInfoVo info) {
    	
    	List<PaasRqzy> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<PaasRqzy>>(){});
    	
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
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<PaasRqzy>().lambda().eq(PaasRqzy::getAppInfoId, info.getId()));
        paasRqzyFwqService.remove(new QueryWrapper<PaasRqzyFwq>().lambda().eq(PaasRqzyFwq::getAppInfoId, info.getId()));
        paasRqzyImplService.remove(new QueryWrapper<PaasRqzyImpl>().lambda().eq(PaasRqzyImpl::getAppInfoId, info.getId()));
        save(info);
    }

    @Transactional
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
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

}
