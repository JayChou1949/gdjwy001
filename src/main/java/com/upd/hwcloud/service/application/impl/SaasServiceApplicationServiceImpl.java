package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upd.hwcloud.bean.contains.ShoppingCartStatus;
import com.upd.hwcloud.bean.entity.SaasApplication;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.DaasApplication;
import com.upd.hwcloud.bean.entity.application.PassGaussdbInfo;
import com.upd.hwcloud.bean.entity.application.SaasServiceApplication;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.dao.application.SaasServiceApplicationMapper;
import com.upd.hwcloud.service.application.ISaasServiceApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.service.application.IShoppingCartService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuc
 * @since 2019-12-24
 */
@Service
public class SaasServiceApplicationServiceImpl extends ServiceImpl<SaasServiceApplicationMapper, SaasServiceApplication> implements ISaasServiceApplicationService {


    @Autowired
    private IShoppingCartService shoppingCartService;

    @Override
    public void saveShoppingCart(ShoppingCart<SaasServiceApplication> shoppingCart) {
        List<SaasServiceApplication> serverList = shoppingCart.getServerList();
        if(CollectionUtils.isNotEmpty(serverList)){
            serverList.forEach(server->{
                server.setShoppingCartId(shoppingCart.getId());
            });
            this.saveBatch(serverList);
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<SaasServiceApplication, Object> info) {
        List<SaasServiceApplication> serverList = info.getServerList();
        if(serverList != null && !serverList.isEmpty()){
            serverList.forEach(server->{
                server.setAppInfoId(info.getId());
            });
            this.saveBatch(serverList);
        }
    }

    @Override
    public List<SaasServiceApplication> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<SaasServiceApplication>().lambda()
                    .eq(SaasServiceApplication::getAppInfoId,appInfoId)
                    .orderByAsc(SaasServiceApplication::getCreateTime));
    }

    @Override
    public List<SaasServiceApplication> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<SaasServiceApplication>().lambda()
                .eq(SaasServiceApplication::getShoppingCartId,shoppingCartId)
                .orderByAsc(SaasServiceApplication::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<SaasServiceApplication, Object> info) {
        this.remove(new QueryWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getAppInfoId,info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<SaasServiceApplication> shoppingCart) {
        this.remove(new QueryWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getShoppingCartId,shoppingCart.getId()));

        saveShoppingCart(shoppingCart);
    }

    @Override
    public Integer getTotalNum(String appInfoId) {
        return this.count(new QueryWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getAppInfoId,appInfoId));
    }

    @Override
    public Integer getTotalNumInShoppingCart(String shoppingCartId) {
        return this.count(new QueryWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getShoppingCartId,shoppingCartId));
    }

    /**
     * 购物车删除
     *
     * @param shoppingCartId 购物车ID
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {
        this.remove(new QueryWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getShoppingCartId,shoppingCartId));
    }

    /**
     * 服务关联订单(特殊处理)
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
//        this.update(new SaasServiceApplication(),new UpdateWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getAppInfoId,appInfoId)
//                .set(SaasServiceApplication::getShoppingCartId,shoppingCartId));
//        this.update(new SaasServiceApplication(),new UpdateWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getShoppingCartId,shoppingCartId)
//                .set(SaasServiceApplication::getAppInfoId,""));
//    }

    @Override
    public List<SaasServiceApplication> getImplServerListByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<SaasServiceApplication>().lambda()
                .eq(SaasServiceApplication::getAppInfoId,appInfoId)
                .orderByAsc(SaasServiceApplication::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<SaasServiceApplication> serverList) {

    }


    @Override
    public List<SaasServiceApplication> getUnsucessByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<SaasServiceApplication>().lambda()
                        .eq(SaasServiceApplication::getAppInfoId,appInfoId)
                        .isNull(SaasServiceApplication::getAppKey)
                        .isNull(SaasServiceApplication::getAppSecret)
                        .orderByAsc(SaasServiceApplication::getCreateTime));

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
        this.update(new SaasServiceApplication(),new UpdateWrapper<SaasServiceApplication>().lambda()
                .in(SaasServiceApplication::getShoppingCartId,shoppingCartIdList)
                .set(SaasServiceApplication::getAppInfoId,appInfoId));

        //清空购物车关联
        this.update(new SaasServiceApplication(),new UpdateWrapper<SaasServiceApplication>().lambda()
                .eq(SaasServiceApplication::getAppInfoId,appInfoId)
                .set(SaasServiceApplication::getShoppingCartId,""));
    }

    /**
     * 老数据迁移特殊处理
     *
     * @param info
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void specialOldDataDeal(ApplicationInfo<SaasServiceApplication, Object> info) {
        List<SaasServiceApplication> daasApplicationList = this.list(new QueryWrapper<SaasServiceApplication>().lambda()
                .eq(SaasServiceApplication::getAppInfoId,info.getId()));
        for(SaasServiceApplication saasServiceApplication: daasApplicationList){
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
            shoppingCart.setDsId(saasServiceApplication.getServiceId());
            shoppingCart.setDsName(saasServiceApplication.getName());
            shoppingCartService.save(shoppingCart);

            this.update(new SaasServiceApplication(),new UpdateWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getAppInfoId,info.getId())
                    .set(SaasServiceApplication::getShoppingCartId,shoppingCart.getId()));

            this.update(new SaasServiceApplication(),new UpdateWrapper<SaasServiceApplication>().lambda().eq(SaasServiceApplication::getShoppingCartId,shoppingCart.getId())
                    .set(SaasServiceApplication::getAppInfoId,""));

        }
    }
}
