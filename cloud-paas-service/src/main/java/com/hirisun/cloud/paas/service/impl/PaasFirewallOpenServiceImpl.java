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
import com.hirisun.cloud.paas.bean.PaasFirewallOpen;
import com.hirisun.cloud.paas.bean.PaasFirewallOpenExt;
import com.hirisun.cloud.paas.mapper.PaasFirewallOpenMapper;
import com.hirisun.cloud.paas.service.IPaasFirewallOpenExtService;
import com.hirisun.cloud.paas.service.IPaasFirewallOpenService;

/**
 * 防火墙开通基本信息 服务实现类
 */
@Service
public class PaasFirewallOpenServiceImpl extends ServiceImpl<PaasFirewallOpenMapper, PaasFirewallOpen> implements IPaasFirewallOpenService {

    @Autowired
    private IPaasFirewallOpenExtService firewallOpenExtService;

    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<PaasFirewallOpen> serverList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<PaasFirewallOpen>>(){});
    	
        if(CollectionUtils.isNotEmpty(serverList)){
            for(PaasFirewallOpen firewallOpen:serverList){
                firewallOpen.setId(null);
                firewallOpen.setShoppingCartId(shoppingCart.getId());
                firewallOpen.insert();

                List<PaasFirewallOpenExt> extList = firewallOpen.getExtList();

                if(CollectionUtils.isNotEmpty(extList)){
                    for(PaasFirewallOpenExt ext:extList){
                        ext.setId(null);
                        ext.setMasterId(firewallOpen.getId());
                        ext.setShoppingCartId(shoppingCart.getId());
                    }
                    firewallOpenExtService.saveBatch(extList);
                }

            }
        }
    }

    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<PaasFirewallOpen> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<PaasFirewallOpen>>(){});
    	
        if(CollectionUtils.isNotEmpty(serverList)){
            for(PaasFirewallOpen firewallOpen:serverList){
                firewallOpen.setId(null);
                firewallOpen.setAppInfoId(info.getId());
                firewallOpen.insert();

                List<PaasFirewallOpenExt> extList = firewallOpen.getExtList();

                if(CollectionUtils.isNotEmpty(extList)){
                    for(PaasFirewallOpenExt ext:extList){
                        ext.setId(null);
                        ext.setMasterId(firewallOpen.getId());
                        ext.setAppInfoId(info.getId());
                    }
                    firewallOpenExtService.saveBatch(extList);
                }

            }
        }
    }

    /**
     * 获取申请信息
     *
     * @param appInfoId
     */
    @Override
    public List<PaasFirewallOpen> getByAppInfoId(String appInfoId) {
        List<PaasFirewallOpen> firewallOpenList =this.list(new QueryWrapper<PaasFirewallOpen>().lambda()
                    .eq(PaasFirewallOpen::getAppInfoId,appInfoId)
                    .orderByDesc(PaasFirewallOpen::getModifiedTime));

        firewallOpenList.forEach(item -> {
           List<PaasFirewallOpenExt> extList =  firewallOpenExtService.list(new QueryWrapper<PaasFirewallOpenExt>().lambda()
                                        .eq(PaasFirewallOpenExt::getMasterId,item.getId())
                                        .orderByDesc(PaasFirewallOpenExt::getModifiedTime));

            item.setExtList(extList);
        });

        return firewallOpenList;
    }

    @Override
    public List<PaasFirewallOpen> getByShoppingCartId(String shoppingCartId) {
        List<PaasFirewallOpen> firewallOpenList =this.list(new QueryWrapper<PaasFirewallOpen>().lambda()
                .eq(PaasFirewallOpen::getShoppingCartId,shoppingCartId)
                .orderByDesc(PaasFirewallOpen::getModifiedTime));

        firewallOpenList.forEach(item -> {
            List<PaasFirewallOpenExt> extList =  firewallOpenExtService.list(new QueryWrapper<PaasFirewallOpenExt>().lambda()
                    .eq(PaasFirewallOpenExt::getMasterId,item.getId())
                    .orderByDesc(PaasFirewallOpenExt::getModifiedTime));

            item.setExtList(extList);
        });

        return firewallOpenList;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<PaasFirewallOpen>().lambda().eq(PaasFirewallOpen::getAppInfoId,info.getId()));
        firewallOpenExtService.remove(new QueryWrapper<PaasFirewallOpenExt>().lambda().eq(PaasFirewallOpenExt::getAppInfoId,info.getId()));
        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<PaasFirewallOpen>().lambda().eq(PaasFirewallOpen::getShoppingCartId,shoppingCart.getId()));
        firewallOpenExtService.remove(new QueryWrapper<PaasFirewallOpenExt>().lambda().eq(PaasFirewallOpenExt::getShoppingCartId,shoppingCart.getId()));
        saveShoppingCart(shoppingCart);
    }

    /**
     * 获取申请总数(购物车显示字段)
     *
     * @param appInfoId
     */
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
        this.remove(new QueryWrapper<PaasFirewallOpen>().lambda().eq(PaasFirewallOpen::getShoppingCartId,shoppingCartId));
        firewallOpenExtService.remove(new QueryWrapper<PaasFirewallOpenExt>().lambda().eq(PaasFirewallOpenExt::getShoppingCartId,shoppingCartId));
    }

    /**
     * 服务关联订单
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {
        this.update(new PaasFirewallOpen(),new UpdateWrapper<PaasFirewallOpen>().lambda()
                .eq(PaasFirewallOpen::getShoppingCartId,shoppingCartId)
                .set(PaasFirewallOpen::getAppInfoId,appInfoId));

        firewallOpenExtService.update(new PaasFirewallOpenExt(),new UpdateWrapper<PaasFirewallOpenExt>().lambda()
                .eq(PaasFirewallOpenExt::getShoppingCartId,shoppingCartId)
                .set(PaasFirewallOpenExt::getAppInfoId,shoppingCartId));

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
