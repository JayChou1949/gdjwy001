package com.hirisun.cloud.order.service.daas.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.daas.DaasApplication;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.continer.ShoppingCartStatus;
import com.hirisun.cloud.order.mapper.daas.DaasApplicationMapper;
import com.hirisun.cloud.order.service.daas.IDaasApplicationService;
import com.hirisun.cloud.order.service.shopping.IShoppingCartService;

/**
 * DaaS服务申请信息 服务实现类
 */
@Service
public class DaasApplicationServiceImpl extends ServiceImpl<DaasApplicationMapper, 
	DaasApplication> implements IDaasApplicationService {


    private static final Logger logger = LoggerFactory.getLogger(DaasApplicationServiceImpl.class);

    @Autowired
    private IShoppingCartService shoppingCartService;

    /**
     * DaaS服务一个购物车只有一个
     * @param shoppingCart
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<DaasApplication> shoppingCart) {
        List<DaasApplication> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (DaasApplication daas : serverList) {
                daas.setId(null);
                daas.setShoppingCartId(shoppingCart.getId());
                this.save(daas);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<DaasApplication, Object> info) {
        List<DaasApplication> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (DaasApplication daas : serverList) {
                daas.setId(null);
                daas.setAppInfoId(info.getId());
                this.save(daas);
            }
        }
    }

    @Override
    public List<DaasApplication> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<DaasApplication>().lambda()
                .eq(DaasApplication::getAppInfoId, appInfoId)
                .orderByAsc(DaasApplication::getCreateTime));
    }

    @Override
    public List<DaasApplication> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<DaasApplication>().lambda()
                .eq(DaasApplication::getShoppingCartId,shoppingCartId)
                .orderByDesc(DaasApplication::getCreateTime));
    }

    @Override
    public List<DaasApplication> getUnsucessByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<DaasApplication>().lambda()
                .eq(DaasApplication::getAppInfoId, appInfoId)
                .isNull(DaasApplication::getAppKey)
                .isNull(DaasApplication::getAppSecret)
                .orderByAsc(DaasApplication::getCreateTime));
    }

    /**
     * 合并提交DaaS服务
     *
     * @param appInfoId
     * @param shoppingCartIdList
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void submitMerge(String appInfoId, List<String> shoppingCartIdList) {
        //关联订单
        this.update(new DaasApplication(),new UpdateWrapper<DaasApplication>().lambda()
                                            .in(DaasApplication::getShoppingCartId,shoppingCartIdList)
                                            .set(DaasApplication::getAppInfoId,appInfoId));

        //清空购物车关联
        this.update(new DaasApplication(),new UpdateWrapper<DaasApplication>().lambda()
                .eq(DaasApplication::getAppInfoId,appInfoId)
                .set(DaasApplication::getShoppingCartId,""));

    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<DaasApplication, Object> info) {
        this.remove(new QueryWrapper<DaasApplication>().lambda().eq(DaasApplication::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<DaasApplication> shoppingCart) {
        this.remove(new QueryWrapper<DaasApplication>().lambda().eq(DaasApplication::getShoppingCartId,shoppingCart.getId()));
        saveShoppingCart(shoppingCart);
    }

    @Override
    public Integer getTotalNum(String appInfoId) {
        return this.count(new QueryWrapper<DaasApplication>().lambda().eq(DaasApplication::getAppInfoId, appInfoId));
    }

    @Override
    public Integer getTotalNumInShoppingCart(String shoppingCartId) {
        return this.count(new QueryWrapper<DaasApplication>().lambda().eq(DaasApplication::getShoppingCartId, shoppingCartId));
    }

    /**
     * 删除购物车
     *
     * @param shoppingCartId
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {
        this.remove(new QueryWrapper<DaasApplication>().lambda().eq(DaasApplication::getShoppingCartId,shoppingCartId));
    }

    /**
     * 服务关联订单(submitMerge处理)
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {

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
//        this.update(new DaasApplication(),new UpdateWrapper<DaasApplication>().lambda().eq(DaasApplication::getAppInfoId,appInfoId)
//        .set(DaasApplication::getShoppingCartId,shoppingCartId));
//
//        this.update(new DaasApplication(),new UpdateWrapper<DaasApplication>().lambda().eq(DaasApplication::getShoppingCartId,shoppingCartId)
//                .set(DaasApplication::getAppInfoId,""));
//    }

    /**
     * DaaS老数据特殊处理
     * @param info
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void specialOldDataDeal(ApplicationInfo<DaasApplication,Object> info){
        List<DaasApplication> daasApplicationList = this.list(new QueryWrapper<DaasApplication>().lambda()
                                                                .eq(DaasApplication::getAppInfoId,info.getId()));
        logger.debug("daasApplicationList size -> {}",daasApplicationList.size());
        for(DaasApplication daasApplication: daasApplicationList){
            ShoppingCart shoppingCart = new ShoppingCart();
            //基本信息
            shoppingCart.setServiceTypeId(info.getServiceTypeId());
            shoppingCart.setServiceTypeName(info.getServiceTypeName());
            shoppingCart.setResourceType(info.getResourceType());
            if(StringUtils.equals("1",info.getDraft())){
                shoppingCart.setStatus(ShoppingCartStatus.DRAFT.getCode());
            }else if(StringUtils.equals("0",info.getDraft())){
                shoppingCart.setStatus(ShoppingCartStatus.WAIT_SUBMIT.getCode());
            }
            shoppingCart.setCreateTime(info.getCreateTime());
            shoppingCart.setModifiedTime(info.getModifiedTime());
            shoppingCart.setCreatorName(info.getCreatorName());
            shoppingCart.setCreatorIdCard(info.getCreator());
            shoppingCart.setFormNum(info.getFormNum());
            shoppingCart.setDsId(daasApplication.getServiceId());
            shoppingCart.setDsName(daasApplication.getServiceName());
            shoppingCartService.save(shoppingCart);

            this.update(new DaasApplication(),new UpdateWrapper<DaasApplication>().lambda().eq(DaasApplication::getAppInfoId,info.getId())
                    .set(DaasApplication::getShoppingCartId,shoppingCart.getId()));

            this.update(new DaasApplication(),new UpdateWrapper<DaasApplication>().lambda().eq(DaasApplication::getShoppingCartId,shoppingCart.getId())
                    .set(DaasApplication::getAppInfoId,""));

        }
    }

    @Override
    public List<DaasApplication> getImplServerListByAppInfoId(String id) {
        List<DaasApplication> list = this.list(new QueryWrapper<DaasApplication>().lambda()
                .eq(DaasApplication::getAppInfoId, id)
                .orderByAsc(DaasApplication::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<DaasApplication> serverList) {
//        this.remove(new QueryWrapper<DaasApplication>().lambda()
//                .eq(DaasApplication::getAppInfoId, appInfoId));
//
//        if (serverList != null && !serverList.isEmpty()) {
//            for (DaasApplication txyfwImpl : serverList) {
//                txyfwImpl.setAppInfoId(appInfoId);
//                this.merge(txyfwImpl);
//            }
//        }
    }


}
