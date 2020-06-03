package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.IaasTxyfzjh;
import com.upd.hwcloud.bean.entity.application.IaasTxyfzjhExt;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.dao.application.IaasTxyfzjhMapper;
import com.upd.hwcloud.service.application.IIaasTxyfzjhExtService;
import com.upd.hwcloud.service.application.IIaasTxyfzjhService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 飞识二期授权码申请信息 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-04-16
 */
@Service
public class IaasTxyfzjhServiceImpl extends ServiceImpl<IaasTxyfzjhMapper, IaasTxyfzjh> implements IIaasTxyfzjhService {

    @Autowired
    private IIaasTxyfzjhExtService iaasTxyfzjhExtService;


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<IaasTxyfzjh> shoppingCart) {
        List<IaasTxyfzjh> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfzjh database : serverList) {
                database.setId(null);
                database.setShoppingCartId(shoppingCart.getId());
                this.save(database);
                List<IaasTxyfzjhExt> exts = database.getIaasTxyfzjhExts();
                for (IaasTxyfzjhExt ext:exts){
                    ext.setId(null);
                    ext.setShoppingCartId(shoppingCart.getId());
                    ext.setMasterId(database.getId());
                    iaasTxyfzjhExtService.save(ext);
                }

            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<IaasTxyfzjh, Object> info) {
        List<IaasTxyfzjh> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfzjh database : serverList) {
                database.setId(null);
                database.setAppInfoId(info.getId());
                this.save(database);
                List<IaasTxyfzjhExt> exts = database.getIaasTxyfzjhExts();
                for (IaasTxyfzjhExt ext:exts){
                    ext.setId(null);
                    ext.setAppInfoId(info.getId());
                    ext.setMasterId(database.getId());
                    iaasTxyfzjhExtService.save(ext);
                }

            }
        }
    }

    @Override
    public List<IaasTxyfzjh> getByAppInfoId(String appInfoId) {
        List<IaasTxyfzjh> datas = this.list(new QueryWrapper<IaasTxyfzjh>().lambda()
                .eq(IaasTxyfzjh::getAppInfoId, appInfoId)
                .orderByAsc(IaasTxyfzjh::getCreateTime));
        for (IaasTxyfzjh data:datas){
        List<IaasTxyfzjhExt> exts = iaasTxyfzjhExtService.list(new QueryWrapper<IaasTxyfzjhExt>().lambda()
                .eq(IaasTxyfzjhExt::getMasterId, data.getId())
                .orderByAsc(IaasTxyfzjhExt::getCreateTime));
        data.setIaasTxyfzjhExts(exts);
        }
        return datas;
    }

    @Override
    public List<IaasTxyfzjh> getByShoppingCartId(String shoppingCartId) {
        List<IaasTxyfzjh> datas = this.list(new QueryWrapper<IaasTxyfzjh>().lambda()
                .eq(IaasTxyfzjh::getShoppingCartId, shoppingCartId)
                .orderByAsc(IaasTxyfzjh::getCreateTime));
        for (IaasTxyfzjh data:datas){
            List<IaasTxyfzjhExt> exts = iaasTxyfzjhExtService.list(new QueryWrapper<IaasTxyfzjhExt>().lambda()
                    .eq(IaasTxyfzjhExt::getMasterId, data.getId())
                    .orderByAsc(IaasTxyfzjhExt::getCreateTime));
            data.setIaasTxyfzjhExts(exts);
        }
        return datas;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<IaasTxyfzjh, Object> info) {
        this.remove(new QueryWrapper<IaasTxyfzjh>().lambda().eq(IaasTxyfzjh::getAppInfoId, info.getId()));
        iaasTxyfzjhExtService.remove(new QueryWrapper<IaasTxyfzjhExt>().lambda().eq(IaasTxyfzjhExt::getAppInfoId, info.getId()));
        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<IaasTxyfzjh> shoppingCart) {
        this.remove(new QueryWrapper<IaasTxyfzjh>().lambda().eq(IaasTxyfzjh::getShoppingCartId, shoppingCart.getId()));
        iaasTxyfzjhExtService.remove(new QueryWrapper<IaasTxyfzjhExt>().lambda().eq(IaasTxyfzjhExt::getShoppingCartId, shoppingCart.getId()));
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
        this.remove(new QueryWrapper<IaasTxyfzjh>().lambda().eq(IaasTxyfzjh::getShoppingCartId, shoppingCartId));
        iaasTxyfzjhExtService.remove(new QueryWrapper<IaasTxyfzjhExt>().lambda().eq(IaasTxyfzjhExt::getShoppingCartId, shoppingCartId));
    }

    /**
     * 服务关联订单
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {
        this.update(new IaasTxyfzjh(),new UpdateWrapper<IaasTxyfzjh>().lambda().eq(IaasTxyfzjh::getShoppingCartId,shoppingCartId)
                .set(IaasTxyfzjh::getAppInfoId,appInfoId));
        iaasTxyfzjhExtService.update(new IaasTxyfzjhExt(),new UpdateWrapper<IaasTxyfzjhExt>().lambda().eq(IaasTxyfzjhExt::getShoppingCartId,shoppingCartId)
            .set(IaasTxyfzjhExt::getAppInfoId,appInfoId));
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
//        this.update(new IaasTxyfzjh(),new UpdateWrapper<IaasTxyfzjh>().lambda().eq(IaasTxyfzjh::getAppInfoId,appInfoId)
//                .set(IaasTxyfzjh::getShoppingCartId,shoppingCartId));
//
//
//        this.update(new IaasTxyfzjh(),new UpdateWrapper<IaasTxyfzjh>().lambda().eq(IaasTxyfzjh::getShoppingCartId,shoppingCartId)
//                .set(IaasTxyfzjh::getAppInfoId,""));
//
//        iaasTxyfzjhExtService.update(new IaasTxyfzjhExt(),new UpdateWrapper<IaasTxyfzjhExt>().lambda().eq(IaasTxyfzjhExt::getShoppingCartId,shoppingCartId)
//                .set(IaasTxyfzjhExt::getAppInfoId,""));
//
//        iaasTxyfzjhExtService.update(new IaasTxyfzjhExt(),new UpdateWrapper<IaasTxyfzjhExt>().lambda().eq(IaasTxyfzjhExt::getShoppingCartId,shoppingCartId)
//                .set(IaasTxyfzjhExt::getAppInfoId,""));
//    }


}
