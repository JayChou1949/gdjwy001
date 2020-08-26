package com.hirisun.cloud.paas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
import com.hirisun.cloud.paas.bean.PassGaussdbAccountInfo;
import com.hirisun.cloud.paas.bean.PassGaussdbInfo;
import com.hirisun.cloud.paas.mapper.PassGaussdbInfoMapper;
import com.hirisun.cloud.paas.service.IPassGaussdbAccountInfoService;
import com.hirisun.cloud.paas.service.IPassGaussdbInfoService;

/**
 *   GAUSSDB 申请信息 服务实现类
 */
@Service
public class PassGaussdbInfoServiceImpl extends ServiceImpl<PassGaussdbInfoMapper, 
	PassGaussdbInfo> implements IPassGaussdbInfoService {
    @Autowired
    private IPassGaussdbAccountInfoService dbInfoService;


    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {
    	
    	List<PassGaussdbInfo> serverList = JSON.parseObject(JSON.toJSON(shoppingCart.getServerList()).toString(), 
    			new TypeReference<List<PassGaussdbInfo>>(){});
    	
        if(CollectionUtils.isNotEmpty(serverList)){
            for (PassGaussdbInfo db:serverList){
                db.setId(null);
                db.setShoppingCartId(shoppingCart.getId());
                this.save(db);

                List<PassGaussdbAccountInfo> accountInfoList = db.getAccountDBList();
                if(CollectionUtils.isNotEmpty(accountInfoList)){
                    accountInfoList.forEach(account ->{
                        account.setId(null);
                        account.setDbId(db.getId());
                    });
                    dbInfoService.saveBatch(accountInfoList);
                }

            }
        }
    }

    @Transactional (rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<PassGaussdbInfo> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<PassGaussdbInfo>>(){});
    	
        if(!serverList.isEmpty()){
            for(PassGaussdbInfo pgi : serverList){
                pgi.setId(null);
                pgi.setAppInfoId(info.getId());
                this.save(pgi);
                //数据库账户信息
                List<PassGaussdbAccountInfo> dblist = pgi.getAccountDBList();
                for(PassGaussdbAccountInfo dbinfo : dblist){
                    dbinfo.setId(null);
                    dbinfo.setDbId(pgi.getId());
                    dbInfoService.save(dbinfo);
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
    public List<PassGaussdbInfo> getByAppInfoId(String appInfoId) {
        List<PassGaussdbInfo> data = this.list(new QueryWrapper<PassGaussdbInfo>().lambda()
                .eq(PassGaussdbInfo::getAppInfoId,appInfoId)
                .orderByAsc(PassGaussdbInfo::getCreateTime)
        );
        //赋值数据库账户信息集合
        for(PassGaussdbInfo pgi : data){
            List<PassGaussdbAccountInfo> dblist = dbInfoService.list(new QueryWrapper<PassGaussdbAccountInfo>().lambda()
                .eq(PassGaussdbAccountInfo::getDbId,pgi.getId())
            );
            pgi.setAccountDBList(dblist);
        }
        return data;
    }

    @Override
    public List<PassGaussdbInfo> getByShoppingCartId(String shoppingCartId) {
        List<PassGaussdbInfo> data = this.list(new QueryWrapper<PassGaussdbInfo>().lambda()
                .eq(PassGaussdbInfo::getShoppingCartId,shoppingCartId)
                .orderByAsc(PassGaussdbInfo::getCreateTime)
        );
        //赋值数据库账户信息集合
        for(PassGaussdbInfo pgi : data){
            List<PassGaussdbAccountInfo> dblist = dbInfoService.list(new QueryWrapper<PassGaussdbAccountInfo>().lambda()
                    .eq(PassGaussdbAccountInfo::getDbId,pgi.getId())
            );
            pgi.setAccountDBList(dblist);
        }
        return data;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<PassGaussdbInfo>().lambda().eq(PassGaussdbInfo::getAppInfoId,info.getId()));
        dbInfoService.remove(new QueryWrapper<PassGaussdbAccountInfo>().lambda().eq(PassGaussdbAccountInfo::getDbId,info.getId()));
        this.save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {
        this.remove(new QueryWrapper<PassGaussdbInfo>().lambda().eq(PassGaussdbInfo::getShoppingCartId,shoppingCart.getId()));
        dbInfoService.remove(new QueryWrapper<PassGaussdbAccountInfo>().lambda().eq(PassGaussdbAccountInfo::getDbId,shoppingCart.getId()));
        this.saveShoppingCart(shoppingCart);
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
        List<PassGaussdbInfo> passGaussdbInfoList = this.list(new QueryWrapper<PassGaussdbInfo>().lambda().eq(PassGaussdbInfo::getShoppingCartId,shoppingCartId));
        if(CollectionUtils.isNotEmpty(passGaussdbInfoList)){
            List<String> idList = passGaussdbInfoList.stream().map(PassGaussdbInfo::getId).collect(Collectors.toList());
            dbInfoService.remove(new QueryWrapper<PassGaussdbAccountInfo>().lambda().in(PassGaussdbAccountInfo::getDbId,idList));
            this.remove(new QueryWrapper<PassGaussdbInfo>().lambda().eq(PassGaussdbInfo::getShoppingCartId,shoppingCartId));
        }
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
        this.update(new PassGaussdbInfo(),new UpdateWrapper<PassGaussdbInfo>().lambda().eq(PassGaussdbInfo::getShoppingCartId,shoppingCartId)
                .set(PassGaussdbInfo::getAppInfoId,appInfoId));
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
//        this.update(new PassGaussdbInfo(),new UpdateWrapper<PassGaussdbInfo>().lambda().eq(PassGaussdbInfo::getAppInfoId,appInfoId)
//                .set(PassGaussdbInfo::getShoppingCartId,shoppingCartId));
//        this.update(new PassGaussdbInfo(),new UpdateWrapper<PassGaussdbInfo>().lambda().eq(PassGaussdbInfo::getShoppingCartId,shoppingCartId)
//                .set(PassGaussdbInfo::getAppInfoId,""));
//    }
}
