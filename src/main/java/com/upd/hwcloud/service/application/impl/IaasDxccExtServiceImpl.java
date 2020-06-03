package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.IaasDxcc;
import com.upd.hwcloud.bean.entity.application.IaasDxccExt;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.dao.application.IaasDxccExtMapper;
import com.upd.hwcloud.service.application.IIaasDxccExtService;
import com.upd.hwcloud.service.application.IIaasDxccService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * IaaS 对象存储申请信息 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2019-07-30
 */
@Service
public class IaasDxccExtServiceImpl extends ServiceImpl<IaasDxccExtMapper, IaasDxccExt> implements IIaasDxccExtService {

    @Autowired
    private IIaasDxccService iaasDxccService;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<IaasDxccExt> shoppingCart) {
        List<IaasDxccExt> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasDxccExt txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setShoppingCartId(shoppingCart.getId());
                this.save(txyfw);
                // 保存存储信息
                this.saveCcxxOfShoppingCart(txyfw, shoppingCart.getId());
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<IaasDxccExt, Object> info) {
        List<IaasDxccExt> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasDxccExt txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setAppInfoId(info.getId());
                this.save(txyfw);
                // 保存存储信息
                this.saveCcxx(txyfw, info.getId());
            }
        }
    }

    private void saveCcxxOfShoppingCart(IaasDxccExt txyfw, String shoppingCartId) {
        List<IaasDxcc> ccxx = txyfw.getCcxx();
        if (ccxx != null && !ccxx.isEmpty()) {
            for (IaasDxcc dxcc : ccxx) {
                dxcc.setId(null);
                dxcc.setShoppingCartId(shoppingCartId);
                iaasDxccService.save(dxcc);
            }
        }
    }

    private void saveCcxx(IaasDxccExt txyfw, String infoId) {
        List<IaasDxcc> ccxx = txyfw.getCcxx();
        if (ccxx != null && !ccxx.isEmpty()) {
            for (IaasDxcc dxcc : ccxx) {
                dxcc.setId(null);
                dxcc.setAppInfoId(infoId);
                iaasDxccService.save(dxcc);
            }
        }
    }

    @Override
    public List<IaasDxccExt> getByAppInfoId(String appInfoId) {
        List<IaasDxccExt> list = this.list(new QueryWrapper<IaasDxccExt>().lambda()
                .eq(IaasDxccExt::getAppInfoId, appInfoId)
                .orderByAsc(IaasDxccExt::getCreateTime));
        List<IaasDxcc> ccxx = iaasDxccService.list(new QueryWrapper<IaasDxcc>().lambda()
                .eq(IaasDxcc::getAppInfoId, appInfoId)
                .orderByAsc(IaasDxcc::getCreateTime));
        // 为了适配旧数据,如果没有扩展数据,只有列表数据也要显示出来
        if (list == null || list.isEmpty()) {
            if (ccxx != null && !ccxx.isEmpty()) {
                IaasDxccExt ext = new IaasDxccExt();
                ext.setCcxx(ccxx);
                list = Collections.singletonList(ext);
            }
        } else {
            for (IaasDxccExt ext : list) {
                ext.setCcxx(ccxx);
            }
        }
        return list;
    }

    @Override
    public List<IaasDxccExt> getByShoppingCartId(String shoppingCartId) {
        List<IaasDxccExt> list = this.list(new QueryWrapper<IaasDxccExt>().lambda()
                .eq(IaasDxccExt::getShoppingCartId, shoppingCartId)
                .orderByAsc(IaasDxccExt::getCreateTime));
        List<IaasDxcc> ccxx = iaasDxccService.list(new QueryWrapper<IaasDxcc>().lambda()
                .eq(IaasDxcc::getShoppingCartId, shoppingCartId)
                .orderByAsc(IaasDxcc::getCreateTime));
        // 为了适配旧数据,如果没有扩展数据,只有列表数据也要显示出来
        if (list == null || list.isEmpty()) {
            if (ccxx != null && !ccxx.isEmpty()) {
                IaasDxccExt ext = new IaasDxccExt();
                ext.setCcxx(ccxx);
                list = Collections.singletonList(ext);
            }
        } else {
            for (IaasDxccExt ext : list) {
                ext.setCcxx(ccxx);
            }
        }
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<IaasDxccExt, Object> info) {
        this.remove(new QueryWrapper<IaasDxccExt>().lambda().eq(IaasDxccExt::getAppInfoId, info.getId()));
        iaasDxccService.remove(new QueryWrapper<IaasDxcc>().lambda().eq(IaasDxcc::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<IaasDxccExt> shoppingCart) {
        this.remove(new QueryWrapper<IaasDxccExt>().lambda().eq(IaasDxccExt::getShoppingCartId, shoppingCart.getId()));
        iaasDxccService.remove(new QueryWrapper<IaasDxcc>().lambda().eq(IaasDxcc::getShoppingCartId, shoppingCart.getId()));
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
     * 删除购物车
     *
     * @param shoppingCartId
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {
        this.remove(new QueryWrapper<IaasDxccExt>().lambda().eq(IaasDxccExt::getShoppingCartId, shoppingCartId));
        iaasDxccService.remove(new QueryWrapper<IaasDxcc>().lambda().eq(IaasDxcc::getShoppingCartId, shoppingCartId));
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
        this.update(new IaasDxccExt(),new UpdateWrapper<IaasDxccExt>().lambda()
                .eq(IaasDxccExt::getShoppingCartId,shoppingCartId)
                .set(IaasDxccExt::getAppInfoId,appInfoId));
        iaasDxccService.update(new IaasDxcc(),new UpdateWrapper<IaasDxcc>().lambda()
                .eq(IaasDxcc::getShoppingCartId,shoppingCartId)
                .set(IaasDxcc::getAppInfoId,appInfoId));

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
//        this.update(new IaasDxccExt(),new UpdateWrapper<IaasDxccExt>().lambda().eq(IaasDxccExt::getAppInfoId,appInfoId)
//                .set(IaasDxccExt::getShoppingCartId,shoppingCartId));
//
//
//        this.update(new IaasDxccExt(),new UpdateWrapper<IaasDxccExt>().lambda().eq(IaasDxccExt::getShoppingCartId,shoppingCartId)
//                .set(IaasDxccExt::getAppInfoId,""));
//
//        iaasDxccService.update(new IaasDxcc(),new UpdateWrapper<IaasDxcc>().lambda().eq(IaasDxcc::getShoppingCartId,shoppingCartId)
//                .set(IaasDxcc::getAppInfoId,""));
//
//        iaasDxccService.update(new IaasDxcc(),new UpdateWrapper<IaasDxcc>().lambda().eq(IaasDxcc::getShoppingCartId,shoppingCartId)
//                .set(IaasDxcc::getAppInfoId,""));
//    }

}
