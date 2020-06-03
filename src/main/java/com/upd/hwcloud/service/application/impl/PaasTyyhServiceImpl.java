package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.PaasTyyh;
import com.upd.hwcloud.bean.entity.application.PassTyxxUser;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.dao.PaasTyyhMapper;
import com.upd.hwcloud.service.application.IPaasTyyhService;
import com.upd.hwcloud.service.application.IPassTyxxUserService;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 统一消息申请信息 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-04-18
 */
@Service
public class PaasTyyhServiceImpl extends ServiceImpl<PaasTyyhMapper, PaasTyyh> implements IPaasTyyhService {
    @Autowired
    private IPassTyxxUserService passTyxxUserService;


    @Override
    public void saveShoppingCart(ShoppingCart<PaasTyyh> shoppingCart) {
        List<PaasTyyh> serverList = shoppingCart.getServerList();
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
    public void save(ApplicationInfo<PaasTyyh, Object> info) {
        List<PaasTyyh> serverList = info.getServerList();
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
    public void update(ApplicationInfo<PaasTyyh, Object> info) {
        this.remove(new QueryWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getAppInfoId, info.getId()));
        passTyxxUserService.remove(new QueryWrapper<PassTyxxUser>().lambda().eq(PassTyxxUser::getAppInfoId, info.getId()));
        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasTyyh> shoppingCart) {
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
