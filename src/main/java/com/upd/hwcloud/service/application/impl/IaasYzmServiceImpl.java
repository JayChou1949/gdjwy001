package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.IaasYzm;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.dao.application.IaasYzmMapper;
import com.upd.hwcloud.service.application.IIaasYzmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * IaaS 云桌面申请信息 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2019-01-07
 */
@Service
public class IaasYzmServiceImpl extends ServiceImpl<IaasYzmMapper, IaasYzm> implements IIaasYzmService {

    @Autowired
    private IaasYzmMapper iaasYzmMapper;


    @Override
    public void saveShoppingCart(ShoppingCart<IaasYzm> shoppingCart) {
        List<IaasYzm> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasYzm txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setShoppingCartId(shoppingCart.getId());
                this.save(txyfw);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<IaasYzm, Object> info) {
        List<IaasYzm> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasYzm txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setAppInfoId(info.getId());
                this.save(txyfw);
            }
        }
    }

    @Override
    public List<IaasYzm> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<IaasYzm>().lambda()
                .eq(IaasYzm::getAppInfoId, appInfoId)
                .orderByAsc(IaasYzm::getCreateTime));
    }

    @Override
    public List<IaasYzm> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<IaasYzm>().lambda()
                .eq(IaasYzm::getShoppingCartId, shoppingCartId)
                .orderByAsc(IaasYzm::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<IaasYzm, Object> info) {
        this.remove(new QueryWrapper<IaasYzm>().lambda().eq(IaasYzm::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<IaasYzm> shoppingCart) {
        this.remove(new QueryWrapper<IaasYzm>().lambda().eq(IaasYzm::getShoppingCartId, shoppingCart.getId()));
        saveShoppingCart(shoppingCart);
    }

    @Override
    public Integer getTotalNum(String appInfoId) {
        return iaasYzmMapper.getTotalNum(appInfoId);
    }

    @Override
    public Integer getTotalNumInShoppingCart(String shoppingCartId) {
        return iaasYzmMapper.getTotalNumOfShoppingCart(shoppingCartId);
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
