package com.hirisun.cloud.order.service.paas.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.hirisun.cloud.common.enumer.RdbApplyType;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.paas.PaasRdbAcc;
import com.hirisun.cloud.order.bean.paas.PaasRdbAccImpl;
import com.hirisun.cloud.order.bean.paas.PaasRdbBase;
import com.hirisun.cloud.order.bean.paas.PaasRdbInfo;
import com.hirisun.cloud.order.bean.paas.PaasRdbInfoImpl;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.mapper.paas.PaasRdbBaseMapper;
import com.hirisun.cloud.order.service.paas.IPaasRdbAccImplService;
import com.hirisun.cloud.order.service.paas.IPaasRdbAccService;
import com.hirisun.cloud.order.service.paas.IPaasRdbBaseService;
import com.hirisun.cloud.order.service.paas.IPaasRdbInfoImplService;
import com.hirisun.cloud.order.service.paas.IPaasRdbInfoService;

/**
 * 关系型数据库组申请信息 服务实现类
 */
@Service
public class PaasRdbBaseServiceImpl extends ServiceImpl<PaasRdbBaseMapper, 
	PaasRdbBase> implements IPaasRdbBaseService {

    @Autowired
    private IPaasRdbInfoService rdbInfoService;

    @Autowired
    private IPaasRdbInfoImplService rdbInfoImplService;

    @Autowired
    private IPaasRdbAccService rdbAccService;

    @Autowired
    private IPaasRdbAccImplService rdbAccImplService;


    private static final Logger logger = LoggerFactory.getLogger(PaasRdbBaseServiceImpl.class);

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<PaasRdbBase> shoppingCart) {
        List<PaasRdbBase> baseList = shoppingCart.getServerList();
        logger.debug("save::baseList -> {}",baseList);
        if(baseList.size() != 1){
        	throw new CustomException(CommonCode.INVALID_PARAM);
        }
        for (PaasRdbBase base:baseList){

            //保存基本组信息，拿到组ID
            String groupUUID  = UUIDUtil.getUUID();
            base.setId(groupUUID);
            base.setShoppingCartId(shoppingCart.getId());
            logger.debug("save::base -> {}",base);
            this.save(base);
            // base.insert();

            //获取账号信息
            List<PaasRdbAcc> rdbAccList = base.getRdbAccList();
            logger.debug("rdbAccList -> {}",rdbAccList);

            //新增数据库类型处理
            if(base.getApplyType().equals(RdbApplyType.ADD_DATABASE.getCode())){
                logger.debug("ADD_DATABASE");
                //获取数据库信息
                List<PaasRdbInfo> rdbInfoList = base.getRdbInfoList();
                /*if(CollectionUtils.isEmpty(rdbInfoList)){
                    throw  new BaseException("数据库列表为空,无效订单");
                }*/
                //保存数据库信息
                logger.debug("save::saveDb");
                saveDb(rdbInfoList,rdbAccList,null,shoppingCart.getId(),groupUUID,base.getDatabaseType());

                //保存数据库账号信息
                logger.debug("save::saveAccount");
                saveAccount(rdbAccList,null,shoppingCart.getId(),groupUUID,base.getDatabaseType());

                logger.debug("save::dealConnection");
                //dealConnection(groupUUID);
            }else if(base.getApplyType().equals(RdbApplyType.ADD_ACCOUNT.getCode())){ //新增账号类型处理
                logger.debug("ADD_ACCOUNT");
                saveAccount(rdbAccList,null,shoppingCart.getId(),groupUUID,base.getDatabaseType());
            }

        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<PaasRdbBase, Object> info) {
        List<PaasRdbBase> baseList = info.getServerList();
        logger.debug("save::baseList -> {}",baseList);
        if(baseList.size() != 1){
        	throw new CustomException(CommonCode.INVALID_PARAM);
        }
        for (PaasRdbBase base:baseList){

            //保存基本组信息，拿到组ID
            String groupUUID  = UUIDUtil.getUUID();
            base.setId(groupUUID);
            base.setAppInfoId(info.getId());
            logger.debug("save::base -> {}",base);
            this.save(base);
           // base.insert();

            //获取账号信息
            List<PaasRdbAcc> rdbAccList = base.getRdbAccList();
            logger.debug("rdbAccList -> {}",rdbAccList);

            //新增数据库类型处理
            if(base.getApplyType().equals(RdbApplyType.ADD_DATABASE.getCode())){
                logger.debug("ADD_DATABASE");
                //获取数据库信息
                List<PaasRdbInfo> rdbInfoList = base.getRdbInfoList();
                /*if(CollectionUtils.isEmpty(rdbInfoList)){
                    throw  new BaseException("数据库列表为空,无效订单");
                }*/
                //保存数据库信息
                logger.debug("save::saveDb");
                saveDb(rdbInfoList,rdbAccList,info.getId(),null,groupUUID,base.getDatabaseType());

                //保存数据库账号信息
                logger.debug("save::saveAccount");
                saveAccount(rdbAccList,info.getId(),null,groupUUID,base.getDatabaseType());

                logger.debug("save::dealConnection");
                //dealConnection(groupUUID);
            }else if(base.getApplyType().equals(RdbApplyType.ADD_ACCOUNT.getCode())){ //新增账号类型处理
                logger.debug("ADD_ACCOUNT");
                saveAccount(rdbAccList,info.getId(),null,groupUUID,base.getDatabaseType());
            }

        }
    }


    /**
     * 保存数据库信息
     * @param rdbInfoList 数据库列表
     * @param rdbAccList  数据库账号列表
     * @param infoId  订单ID
     * @param groupUUID  基本组信息ID
     * @param databaseType 数据库类型
     */
    private void saveDb(List<PaasRdbInfo> rdbInfoList,List<PaasRdbAcc> rdbAccList,String infoId,String shoppingCartId,String groupUUID,Integer databaseType){
        for(PaasRdbInfo rdbInfo:rdbInfoList){
            String rdbInfoUUID = UUIDUtil.getUUID();
            rdbInfo.setId(rdbInfoUUID);
            rdbInfo.setDbType(databaseType);
            rdbInfo.setMasterId(groupUUID);
            rdbInfo.setAppInfoId(infoId);
            rdbInfo.setShoppingCartId(shoppingCartId);

            logger.debug("==start associated==");
            //通过名字关联
            List<String> associated = rdbAccList.stream()
                    .filter(acc -> rdbInfo.getName().contains(acc.getAssociatedDb()))
                    .map(PaasRdbAcc::getAccount)
                    .distinct()
                    .collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(associated)){
                String associatedStr = Joiner.on(",").skipNulls().join(associated);
                rdbInfo.setAssociatedAcc(associatedStr);
            }
            logger.debug("==end associated==");

        }
        rdbInfoService.saveBatch(rdbInfoList);
    }

    /**
     * todo: 减少重复代码
     * 保存实施信息
     * @param rdbInfoImplList
     * @param rdbAccImplList
     * @param infoId
     * @param groupUUID
     * @param databaseType
     */
    private void saveDbImpl(List<PaasRdbInfoImpl> rdbInfoImplList,List<PaasRdbAccImpl> rdbAccImplList,String infoId,String groupUUID,Integer databaseType){
        for(PaasRdbInfoImpl rdbInfoImpl : rdbInfoImplList){
            String rdbInfoUUID = UUIDUtil.getUUID();
            rdbInfoImpl.setId(rdbInfoUUID);
            rdbInfoImpl.setDbType(databaseType);
            rdbInfoImpl.setMasterId(groupUUID);
            rdbInfoImpl.setAppInfoId(infoId);

            //通过名字关联
            List<String> associated = rdbAccImplList.stream()
                    .filter(acc -> rdbInfoImpl.getName().contains(acc.getAssociatedDb()))
                    .map(PaasRdbAccImpl::getAccount)
                    .distinct()
                    .collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(associated)){
                String associatedStr = Joiner.on(",").skipNulls().join(associated);
                rdbInfoImpl.setAssociatedAcc(associatedStr);
            }
        }
        if(CollectionUtils.isNotEmpty(rdbInfoImplList)){
            rdbInfoImplService.saveBatch(rdbInfoImplList);
        }
    }

    /**
     * 保存账号实施信息
     * @param rdbAccImplList
     * @param infoId
     * @param groupUUID
     * @param databaseType
     */
    private void saveAccImpl(List<PaasRdbAccImpl> rdbAccImplList,String infoId,String groupUUID,Integer databaseType){
        for(PaasRdbAccImpl rdbAccImpl:rdbAccImplList){
            String rdbAccUUID = UUIDUtil.getUUID();
            rdbAccImpl.setId(rdbAccUUID);
            rdbAccImpl.setAppInfoId(infoId);
            rdbAccImpl.setDbType(databaseType);
            rdbAccImpl.setMasterId(groupUUID);
        }
        if(CollectionUtils.isNotEmpty(rdbAccImplList)){
            rdbAccImplService.saveBatch(rdbAccImplList);
        }
    }

    /**
     * 保存数据库账号信息
     * @param rdbAccList 数据库账号列表
     * @param infoId   订单ID
     * @param groupUUID  基本组ID
     * @param databaseType 数据库类型
     */
    private void saveAccount(List<PaasRdbAcc> rdbAccList,String infoId,String shoppingCartId,String groupUUID,Integer databaseType){
        for(PaasRdbAcc rdbAcc:rdbAccList){
            String rdbAccUUID = UUIDUtil.getUUID();
            rdbAcc.setId(rdbAccUUID);
            rdbAcc.setAppInfoId(infoId);
            rdbAcc.setShoppingCartId(shoppingCartId);
            rdbAcc.setDbType(databaseType);
            rdbAcc.setMasterId(groupUUID);
        }
        rdbAccService.saveBatch(rdbAccList);
    }

    /**
     * 处理多对多关系便于以后查找
     * @param baseId
     */
    /*private void dealConnection(String baseId){
        List<PaasRdbInfo> rdbInfoList = rdbInfoService.list(new QueryWrapper<PaasRdbInfo>().lambda().eq(PaasRdbInfo::getMasterId,baseId));

        List<PaasRdbAcc> rdbAccList = rdbAccService.list(new QueryWrapper<PaasRdbAcc>().lambda().eq(PaasRdbAcc::getMasterId,baseId));

        List<PaasRdbInfoacc> infoaccList = Lists.newArrayList();

        rdbAccList.forEach(rdbAcc->{

            List<String> dbIdList = rdbInfoList.stream()
                    .filter(rdbInfo -> rdbAcc.getAssociatedDb().contains(rdbInfo.getName()))
                    .map(PaasRdbInfo::getId).collect(Collectors.toList());
            logger.debug("dbIdList -> {}",dbIdList);
            if(CollectionUtils.isNotEmpty(dbIdList)){
                for(String id:dbIdList){
                    PaasRdbInfoacc infoacc = new PaasRdbInfoacc();
                    infoacc.setId(UUIDUtil.getUUID());
                    infoacc.setAccountId(rdbAcc.getId());
                    infoacc.setDbId(id);
                    infoaccList.add(infoacc);
                }
                logger.debug("infoaccList -> {}",infoaccList);
                rdbInfoaccService.saveBatch(infoaccList);
            }
        });
    }*/


    /**
     * 处理实施数据库和账号关联数据
     * @param baseId
     */
    /*private void dealImplConnection(String baseId){
        List<PaasRdbInfoImpl> rdbInfoImplList = rdbInfoImplService.list(new QueryWrapper<PaasRdbInfoImpl>().lambda().eq(PaasRdbInfoImpl::getMasterId,baseId));

        List<PaasRdbAccImpl> rdbAccImplList = rdbAccImplService.list(new QueryWrapper<PaasRdbAccImpl>().lambda().eq(PaasRdbAccImpl::getMasterId,baseId));

        List<PaasRdbInfoaccImpl> infoaccImplList = Lists.newArrayList();

        rdbAccImplList.forEach(rdbAcc->{

            List<String> dbIdList = rdbInfoImplList.stream()
                    .filter(rdbInfo -> rdbAcc.getAssociatedDb().contains(rdbInfo.getName()))
                    .map(PaasRdbInfoImpl::getId).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(dbIdList)){
                for(String id:dbIdList){
                    PaasRdbInfoaccImpl infoaccImpl = new PaasRdbInfoaccImpl();
                    infoaccImpl.setId(UUIDUtil.getUUID());
                    infoaccImpl.setAccountId(rdbAcc.getId());
                    infoaccImpl.setDbId(id);
                    infoaccImplList.add(infoaccImpl);
                }
                rdbInfoaccImplService.saveBatch(infoaccImplList);
            }
        });
    }*/


    /**
     * 获取申请信息
     *
     * @param appInfoId 订单ID
     */
    @Override
    public List<PaasRdbBase> getByAppInfoId(String appInfoId) {
        List<PaasRdbBase> rdbBases = this.list(new QueryWrapper<PaasRdbBase>().lambda().eq(PaasRdbBase::getAppInfoId,appInfoId));
        /*if(rdbBases.size() != 1){
            logger.debug("appInfoId -> {} , rdbBases -> {}",appInfoId,rdbBases);
            throw new BaseException("ServerList Num error!");
        }*/
        for(PaasRdbBase rdbBase:rdbBases){
            List<PaasRdbInfo> infoList = rdbInfoService.list(new QueryWrapper<PaasRdbInfo>().lambda().eq(PaasRdbInfo::getAppInfoId,appInfoId).orderByDesc(PaasRdbInfo::getCreateTime));
            List<PaasRdbAcc> accList= rdbAccService.list(new QueryWrapper<PaasRdbAcc>().lambda().eq(PaasRdbAcc::getAppInfoId,appInfoId).orderByDesc(PaasRdbAcc::getCreateTime));
            rdbBase.setRdbAccList(accList);
            rdbBase.setRdbInfoList(infoList);
        }
        return rdbBases;
    }

    @Override
    public List<PaasRdbBase> getByShoppingCartId(String shoppingCartId) {
        List<PaasRdbBase> rdbBases = this.list(new QueryWrapper<PaasRdbBase>().lambda().eq(PaasRdbBase::getShoppingCartId,shoppingCartId));
        /*if(rdbBases.size() != 1){
            logger.debug("appInfoId -> {} , rdbBases -> {}",appInfoId,rdbBases);
            throw new BaseException("ServerList Num error!");
        }*/
        for(PaasRdbBase rdbBase:rdbBases){
            List<PaasRdbInfo> infoList = rdbInfoService.list(new QueryWrapper<PaasRdbInfo>().lambda().eq(PaasRdbInfo::getShoppingCartId,shoppingCartId).orderByDesc(PaasRdbInfo::getCreateTime));
            List<PaasRdbAcc> accList= rdbAccService.list(new QueryWrapper<PaasRdbAcc>().lambda().eq(PaasRdbAcc::getShoppingCartId,shoppingCartId).orderByDesc(PaasRdbAcc::getCreateTime));
            rdbBase.setRdbAccList(accList);
            rdbBase.setRdbInfoList(infoList);
        }
        return rdbBases;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<PaasRdbBase, Object> info) {
        List<PaasRdbBase> serverList = info.getServerList();
        /*if(serverList.size() != 1){
            throw new BaseException("ServerList Num error!");
        }*/
        for (PaasRdbBase base:serverList){
            if(base.getApplyType().equals(RdbApplyType.ADD_DATABASE.getCode())){
                //获取数据库旧数据
            // List<PaasRdbInfo> rdbInfoList =  rdbInfoService.list(new QueryWrapper<PaasRdbInfo>().lambda()
                                                            // .eq(PaasRdbInfo::getAppInfoId,info.getId())
                                                             //.eq(PaasRdbInfo::getMasterId,base.getId()));
           //  List<String> infoIdList = rdbInfoList.stream().map(PaasRdbInfo::getId).distinct().collect(Collectors.toList());
                //删除数据库和账号的关联关系
            // rdbInfoaccService.remove(new QueryWrapper<PaasRdbInfoacc>().lambda().in(PaasRdbInfoacc::getDbId,infoIdList));
                //删除数据库
             rdbInfoService.remove(new QueryWrapper<PaasRdbInfo>().lambda().eq(PaasRdbInfo::getAppInfoId,info.getId()));
                //删除账号
             rdbAccService.remove(new QueryWrapper<PaasRdbAcc>().lambda().eq(PaasRdbAcc::getAppInfoId,info.getId()));
                //删除基本组
             this.remove(new QueryWrapper<PaasRdbBase>().lambda().eq(PaasRdbBase::getAppInfoId,info.getId()));
                //保存新的订单
             save(info);

            }else if(base.getApplyType().equals(RdbApplyType.ADD_ACCOUNT.getCode())){
                //删除账号
                rdbAccService.remove(new QueryWrapper<PaasRdbAcc>().lambda().eq(PaasRdbAcc::getAppInfoId,info.getId()));
                //删除基本组
                this.remove(new QueryWrapper<PaasRdbBase>().lambda().eq(PaasRdbBase::getAppInfoId,info.getId()));
                save(info);
            }
        }


    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<PaasRdbBase> shoppingCart) {
        List<PaasRdbBase> serverList = shoppingCart.getServerList();
        /*if(serverList.size() != 1){
            throw new BaseException("ServerList Num error!");
        }*/
        for (PaasRdbBase base:serverList){
            if(base.getApplyType().equals(RdbApplyType.ADD_DATABASE.getCode())){
                //获取数据库旧数据
                // List<PaasRdbInfo> rdbInfoList =  rdbInfoService.list(new QueryWrapper<PaasRdbInfo>().lambda()
                // .eq(PaasRdbInfo::getAppInfoId,info.getId())
                //.eq(PaasRdbInfo::getMasterId,base.getId()));
                //  List<String> infoIdList = rdbInfoList.stream().map(PaasRdbInfo::getId).distinct().collect(Collectors.toList());
                //删除数据库和账号的关联关系
                // rdbInfoaccService.remove(new QueryWrapper<PaasRdbInfoacc>().lambda().in(PaasRdbInfoacc::getDbId,infoIdList));
                //删除数据库
                rdbInfoService.remove(new QueryWrapper<PaasRdbInfo>().lambda().eq(PaasRdbInfo::getShoppingCartId,shoppingCart.getId()));
                //删除账号
                rdbAccService.remove(new QueryWrapper<PaasRdbAcc>().lambda().eq(PaasRdbAcc::getShoppingCartId,shoppingCart.getId()));
                //删除基本组
                this.remove(new QueryWrapper<PaasRdbBase>().lambda().eq(PaasRdbBase::getShoppingCartId,shoppingCart.getId()));
                //保存新的订单
                saveShoppingCart(shoppingCart);

            }else if(base.getApplyType().equals(RdbApplyType.ADD_ACCOUNT.getCode())){
                //删除账号
                rdbAccService.remove(new QueryWrapper<PaasRdbAcc>().lambda().eq(PaasRdbAcc::getShoppingCartId,shoppingCart.getId()));
                //删除基本组
                this.remove(new QueryWrapper<PaasRdbBase>().lambda().eq(PaasRdbBase::getShoppingCartId,shoppingCart.getId()));
                saveShoppingCart(shoppingCart);
            }
        }
    }


    /**
     * 获取申请总数(购物车显示字段)
     *
     * @param appInfoId
     */
    @Override
    public Integer getTotalNum(String appInfoId) {
        return null;
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
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {
     rdbInfoService.remove(new QueryWrapper<PaasRdbInfo>().lambda().eq(PaasRdbInfo::getShoppingCartId,shoppingCartId));
     rdbAccService.remove(new QueryWrapper<PaasRdbAcc>().lambda().eq(PaasRdbAcc::getShoppingCartId,shoppingCartId));
     this.remove(new QueryWrapper<PaasRdbBase>().lambda().eq(PaasRdbBase::getShoppingCartId,shoppingCartId));
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
        rdbInfoService.update(new PaasRdbInfo(),new UpdateWrapper<PaasRdbInfo>().lambda().eq(PaasRdbInfo::getShoppingCartId,shoppingCartId)
                .set(PaasRdbInfo::getAppInfoId,appInfoId));
        rdbAccService.update(new PaasRdbAcc(),new UpdateWrapper<PaasRdbAcc>().lambda().eq(PaasRdbAcc::getShoppingCartId,shoppingCartId)
                .set(PaasRdbAcc::getAppInfoId,appInfoId));
        this.update(new PaasRdbBase(),new UpdateWrapper<PaasRdbBase>().lambda().eq(PaasRdbBase::getShoppingCartId,shoppingCartId)
                .set(PaasRdbBase::getAppInfoId,appInfoId));
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
//        this.update(new PaasRdbBase(),new UpdateWrapper<PaasRdbBase>().lambda().eq(PaasRdbBase::getAppInfoId,appInfoId)
//                .set(PaasRdbBase::getShoppingCartId,shoppingCartId));
//        this.update(new PaasRdbBase(),new UpdateWrapper<PaasRdbBase>().lambda().eq(PaasRdbBase::getShoppingCartId,shoppingCartId)
//                .set(PaasRdbBase::getAppInfoId,""));
//
//
//        rdbInfoService.update(new PaasRdbInfo(),new UpdateWrapper<PaasRdbInfo>().lambda().eq(PaasRdbInfo::getAppInfoId,appInfoId)
//                .set(PaasRdbInfo::getShoppingCartId,shoppingCartId));
//        rdbInfoService.update(new PaasRdbInfo(),new UpdateWrapper<PaasRdbInfo>().lambda().eq(PaasRdbInfo::getShoppingCartId,shoppingCartId)
//                .set(PaasRdbInfo::getAppInfoId,""));
//
//        rdbAccService.update(new PaasRdbAcc(),new UpdateWrapper<PaasRdbAcc>().lambda().eq(PaasRdbAcc::getAppInfoId,appInfoId)
//                .set(PaasRdbAcc::getShoppingCartId,shoppingCartId));
//        rdbAccService.update(new PaasRdbAcc(),new UpdateWrapper<PaasRdbAcc>().lambda().eq(PaasRdbAcc::getShoppingCartId,shoppingCartId)
//                .set(PaasRdbAcc::getAppInfoId,""));
//
//
//
//    }

    /**
     * 根据申请信息 id 查询实施服务器信息列表
     *
     * @param appInfoId 申请信息 id
     */
    @Override
    public List<PaasRdbBase> getImplServerListByAppInfoId(String appInfoId) {

        List<PaasRdbBase> baseList = this.list(new QueryWrapper<PaasRdbBase>().lambda().eq(PaasRdbBase::getAppInfoId,appInfoId));
        //base
        /*if(baseList.size() != 1){
            throw new BaseException("ServerList Num error!");
        }*/
        for (PaasRdbBase base : baseList){
            if(base.getApplyType().equals(RdbApplyType.ADD_DATABASE.getCode())){
                List<PaasRdbInfoImpl> rdbInfoImplList = rdbInfoImplService.list(new QueryWrapper<PaasRdbInfoImpl>().lambda().eq(PaasRdbInfoImpl::getAppInfoId,appInfoId));
                if(CollectionUtils.isEmpty(rdbInfoImplList)){
                    return Lists.newArrayList();
                }
                List<PaasRdbAccImpl>  rdbAccImplList = rdbAccImplService.list(new QueryWrapper<PaasRdbAccImpl>().lambda().eq(PaasRdbAccImpl::getAppInfoId,appInfoId));
                base.setRdbInfoImplList(rdbInfoImplList);
                base.setRdbAccImplList(rdbAccImplList);
            }else if(base.getApplyType().equals(RdbApplyType.ADD_ACCOUNT.getCode())){
                List<PaasRdbAccImpl>  rdbAccImplList = rdbAccImplService.list(new QueryWrapper<PaasRdbAccImpl>().lambda().eq(PaasRdbAccImpl::getAppInfoId,appInfoId));
                if(CollectionUtils.isEmpty(rdbAccImplList)){
                    return Lists.newArrayList();
                }
                base.setRdbAccImplList(rdbAccImplList);
            }
        }

        return baseList;
    }

    /**
     * 更新实施服务器信息(先删除,后添加)
     *
     * @param appInfoId
     * @param serverList
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<PaasRdbBase> serverList) {
        /*if(serverList.size() != 1){
            throw new BaseException("ServerList Num error!");
        }*/
        for(PaasRdbBase base : serverList){
            if(base.getApplyType().equals(RdbApplyType.ADD_DATABASE.getCode())){
                List<PaasRdbInfoImpl> oldInfoImpl = rdbInfoImplService.list(new QueryWrapper<PaasRdbInfoImpl>().lambda().eq(PaasRdbInfoImpl::getAppInfoId,appInfoId));
                if(CollectionUtils.isNotEmpty(oldInfoImpl)){
                    //List<String> infoImplIds = oldInfoImpl.stream().map(PaasRdbInfoImpl::getId).distinct().collect(Collectors.toList());

                    //rdbInfoaccImplService.remove(new QueryWrapper<PaasRdbInfoaccImpl>().lambda().in(PaasRdbInfoaccImpl::getDbId,infoImplIds));

                    rdbInfoImplService.remove(new QueryWrapper<PaasRdbInfoImpl>().lambda().eq(PaasRdbInfoImpl::getAppInfoId,appInfoId));

                    rdbAccImplService.remove(new QueryWrapper<PaasRdbAccImpl>().lambda().eq(PaasRdbAccImpl::getAppInfoId,appInfoId));
                }
                saveImpl(base);
            }else if(base.getApplyType().equals(RdbApplyType.ADD_ACCOUNT.getCode())){
                List<PaasRdbAccImpl> rdbAccImplList = rdbAccImplService.list(new QueryWrapper<PaasRdbAccImpl>().lambda().eq(PaasRdbAccImpl::getAppInfoId,appInfoId));
                if(CollectionUtils.isNotEmpty(rdbAccImplList)){
                    rdbAccImplService.remove(new QueryWrapper<PaasRdbAccImpl>().lambda().eq(PaasRdbAccImpl::getAppInfoId,appInfoId));
                }
                saveImpl(base);
            }
        }

    }

    /**
     * 保存实施信息
     * @param paasRdbBase
     */
    private void saveImpl(PaasRdbBase paasRdbBase){
        if(paasRdbBase.getApplyType().equals(RdbApplyType.ADD_DATABASE.getCode())){
            logger.debug("新增数据实施信息保存");
            //更新Base
            this.update(new PaasRdbBase(),new UpdateWrapper<PaasRdbBase>().lambda()
                    .eq(PaasRdbBase::getId,paasRdbBase.getId())
                    .set(PaasRdbBase::getIp,paasRdbBase.getIp())
                    .set(PaasRdbBase::getPort,paasRdbBase.getPort()));
            if(CollectionUtils.isNotEmpty(paasRdbBase.getRdbInfoImplList())){
                saveDbImpl(paasRdbBase.getRdbInfoImplList(),paasRdbBase.getRdbAccImplList(),paasRdbBase.getAppInfoId(),paasRdbBase.getId(),paasRdbBase.getDatabaseType());
            }
            if(CollectionUtils.isNotEmpty(paasRdbBase.getRdbAccImplList())){
                saveAccImpl(paasRdbBase.getRdbAccImplList(),paasRdbBase.getAppInfoId(),paasRdbBase.getId(),paasRdbBase.getDatabaseType());
            }
            //dealImplConnection(paasRdbBase.getId());
        }else if(paasRdbBase.getApplyType().equals(RdbApplyType.ADD_ACCOUNT.getCode())){
            logger.debug("新增账号实施信息保存");
            if(CollectionUtils.isNotEmpty(paasRdbBase.getRdbAccImplList())){
                saveAccImpl(paasRdbBase.getRdbAccImplList(),paasRdbBase.getAppInfoId(),paasRdbBase.getId(),paasRdbBase.getDatabaseType());
            }
        }
    }

    /**
     * 清空实施
     * @param infoId
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void emptyImplServerList(String infoId){

        rdbInfoImplService.remove(new QueryWrapper<PaasRdbInfoImpl>().lambda().eq(PaasRdbInfoImpl::getAppInfoId,infoId));

        rdbAccImplService.remove(new QueryWrapper<PaasRdbAccImpl>().lambda().eq(PaasRdbAccImpl::getAppInfoId,infoId));

    }

}
