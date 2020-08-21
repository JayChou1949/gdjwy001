package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.IaasTxyfwbg;
import com.hirisun.cloud.iaas.mapper.IaasTxyfwbgMapper;
import com.hirisun.cloud.iaas.service.IIaasTxyfwbgService;
import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;

/**
 * 弹性云服务器变更申请表 服务实现类
 */
@Service
public class IaasTxyfwbgServiceImpl extends ServiceImpl<IaasTxyfwbgMapper, 
	IaasTxyfwbg> implements IIaasTxyfwbgService {


    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {

    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<IaasTxyfwbg> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<IaasTxyfwbg>>(){});
    	
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
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<IaasTxyfwbg>().lambda().eq(IaasTxyfwbg::getAppInfoId, info.getId()));

        save(info);
    }

    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {

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
