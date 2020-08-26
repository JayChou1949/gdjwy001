package com.hirisun.cloud.paas.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;
import com.hirisun.cloud.paas.bean.PaasTyyh;
import com.hirisun.cloud.paas.bean.PassTyxxUser;
import com.hirisun.cloud.paas.mapper.PaasTyyhMapper;
import com.hirisun.cloud.paas.service.IPaasTyyhService;
import com.hirisun.cloud.paas.service.IPassTyxxUserService;

/**
 * 统一消息申请信息 服务实现类
 */
@Service
public class PaasTyyhServiceImpl extends ServiceImpl<PaasTyyhMapper, PaasTyyh> implements IPaasTyyhService {
    @Autowired
    private IPassTyxxUserService passTyxxUserService;


    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<PaasTyyh> serverList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<PaasTyyh>>(){});
    	
        if(CollectionUtils.isNotEmpty(serverList)){
            for(PaasTyyh tyyh:serverList){
                tyyh.setId(null);
                tyyh.setShoppingCartId(shoppingCart.getId());
                this.save(tyyh);

                List<PassTyxxUser> userList = tyyh.getUserList();
                if(CollectionUtils.isNotEmpty(userList)){
                    for(PassTyxxUser user:userList){
                        user.setId(null);
                        user.setShoppingCartId(shoppingCart.getId());
                    }
                    passTyxxUserService.saveBatch(userList);
                }
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<PaasTyyh> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<PaasTyyh>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (PaasTyyh txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setAppInfoId(info.getId());
                this.save(txyfw);
                // 统一用户人员信息
                List<PassTyxxUser> userList = txyfw.getUserList();
                if (userList != null && !userList.isEmpty()) {
                    for (PassTyxxUser tyxxUser : userList) {
                        tyxxUser.setId(null);
                        tyxxUser.setAppInfoId(info.getId());
                        passTyxxUserService.save(tyxxUser);
                    }
                }
            }
        }
    }

    @Override
    public List<PaasTyyh> getByAppInfoId(String appInfoId) {
        List<PaasTyyh> tyxxList = this.list(new QueryWrapper<PaasTyyh>().lambda()
                .eq(PaasTyyh::getAppInfoId, appInfoId)
                .orderByAsc(PaasTyyh::getCreateTime));
        if (tyxxList != null && !tyxxList.isEmpty()) {
            for (PaasTyyh tyxx : tyxxList) {
                List<PassTyxxUser> userList = passTyxxUserService.list(new QueryWrapper<PassTyxxUser>().lambda()
                        .eq(PassTyxxUser::getAppInfoId, appInfoId)
                        .orderByAsc(PassTyxxUser::getCreateTime));
                tyxx.setUserList(userList);
            }
        }
        return tyxxList;
    }

    @Override
    public List<PaasTyyh> getByShoppingCartId(String shoppingCartId) {
        List<PaasTyyh> tyxxList = this.list(new QueryWrapper<PaasTyyh>().lambda()
                .eq(PaasTyyh::getShoppingCartId, shoppingCartId)
                .orderByAsc(PaasTyyh::getCreateTime));
        if (tyxxList != null && !tyxxList.isEmpty()) {
            for (PaasTyyh tyxx : tyxxList) {
                List<PassTyxxUser> userList = passTyxxUserService.list(new QueryWrapper<PassTyxxUser>().lambda()
                        .eq(PassTyxxUser::getShoppingCartId, shoppingCartId)
                        .orderByAsc(PassTyxxUser::getCreateTime));
                tyxx.setUserList(userList);
            }
        }
        return tyxxList;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getAppInfoId, info.getId()));
        passTyxxUserService.remove(new QueryWrapper<PassTyxxUser>().lambda().eq(PassTyxxUser::getAppInfoId, info.getId()));
        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getShoppingCartId, shoppingCart.getId()));
        passTyxxUserService.remove(new QueryWrapper<PassTyxxUser>().lambda().eq(PassTyxxUser::getShoppingCartId, shoppingCart.getId()));
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
        this.remove(new QueryWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getShoppingCartId, shoppingCartId));
        passTyxxUserService.remove(new QueryWrapper<PassTyxxUser>().lambda().eq(PassTyxxUser::getAppInfoId, shoppingCartId));
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
        this.update(new PaasTyyh(),new UpdateWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getShoppingCartId,shoppingCartId)
                .set(PaasTyyh::getAppInfoId,appInfoId));
        passTyxxUserService.update(new PassTyxxUser(),new UpdateWrapper<PassTyxxUser>().lambda().eq(PassTyxxUser::getShoppingCartId,shoppingCartId)
                .set(PassTyxxUser::getAppInfoId,appInfoId));
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
//        this.update(new PaasTyyh(),new UpdateWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getAppInfoId,appInfoId)
//                .set(PaasTyyh::getShoppingCartId,shoppingCartId));
//        this.update(new PaasTyyh(),new UpdateWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getShoppingCartId,shoppingCartId)
//                .set(PaasTyyh::getAppInfoId,""));
//
//        passTyxxUserService.update(new PassTyxxUser(),new UpdateWrapper<PassTyxxUser>().lambda().eq(PassTyxxUser::getAppInfoId,appInfoId)
//                .set(PassTyxxUser::getShoppingCartId,shoppingCartId));
//        passTyxxUserService.update(new PassTyxxUser(),new UpdateWrapper<PassTyxxUser>().lambda().eq(PassTyxxUser::getShoppingCartId,shoppingCartId)
//                .set(PassTyxxUser::getAppInfoId,""));
//
//
//    }
}
