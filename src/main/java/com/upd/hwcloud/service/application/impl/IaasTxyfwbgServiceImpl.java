package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.IaasTxyfwbg;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.dao.application.IaasTxyfwbgMapper;
import com.upd.hwcloud.service.application.IIaasTxyfwbgService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 弹性云服务器变更申请表 服务实现类
 * </p>
 *
 * @author huru
 * @since 2019-01-22
 */
@Service
public class IaasTxyfwbgServiceImpl extends ServiceImpl<IaasTxyfwbgMapper, IaasTxyfwbg> implements IIaasTxyfwbgService {




    @Override
    public void saveShoppingCart(ShoppingCart<IaasTxyfwbg> shoppingCart) {

    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<IaasTxyfwbg, Object> info) {
        List<IaasTxyfwbg> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfwbg txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setAppInfoId(info.getId());
                this.save(txyfw);
            }
        }
    }

    @Override
    public List<IaasTxyfwbg> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<IaasTxyfwbg>().lambda()
                .eq(IaasTxyfwbg::getAppInfoId, appInfoId)
                .orderByAsc(IaasTxyfwbg::getCreateTime));
    }

    @Override
    public List<IaasTxyfwbg> getByShoppingCartId(String shoppingCartId) {
        return null;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<IaasTxyfwbg, Object> info) {
        this.remove(new QueryWrapper<IaasTxyfwbg>().lambda().eq(IaasTxyfwbg::getAppInfoId, info.getId()));

        save(info);
    }

    @Override
    public void updateShoppingCart(ShoppingCart<IaasTxyfwbg> shoppingCart) {

    }

    @Override
    public Integer getTotalNum(String appInfoId) {
        return 1;
    }

    @Override
    public Integer getTotalNumInShoppingCart(String shoppingCartId) {
        return null;
    }

    /**
     * 购物车删除
     *
     * @param shoppingCartId 购物车ID
     */
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {

    }

    /**
     * 服务关联订单
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
//    @Override
//    public void oldDataMove(String shoppingCartId, String appInfoId) {
//
//    }

}
