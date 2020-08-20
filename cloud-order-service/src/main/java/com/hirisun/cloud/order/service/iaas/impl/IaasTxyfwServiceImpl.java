package com.hirisun.cloud.order.service.iaas.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.OkHttpUtils;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.model.iaas.vo.EcsVO;
import com.hirisun.cloud.order.bean.ApplicationInfo;
import com.hirisun.cloud.order.bean.iaas.IaasTxyfw;
import com.hirisun.cloud.order.bean.iaas.IaasTxyfwImpl;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.mapper.iaas.IaasTxyfwMapper;
import com.hirisun.cloud.order.service.iaas.IIaasTxyfwService;

import okhttp3.Response;

/**
 * 弹性云服务器申请信息 服务实现类
 */
@Service
public class IaasTxyfwServiceImpl extends ServiceImpl<IaasTxyfwMapper, IaasTxyfw> implements IIaasTxyfwService {


    private static final Logger logger = LoggerFactory.getLogger(IaasTxyfwServiceImpl.class);

    @Autowired
    private IaasTxyfwMapper iaasTxyfwMapper;


    @Value("${iMoc.pre.url}")
    private String iMocPreURL;





    @Override
    public IPage<IaasTxyfwImpl> getEcsWorkbenchPage(IPage<IaasTxyfwImpl> page, Map<String, Object> param) {
        return iaasTxyfwMapper.getEcsListInWorkbench(page,param);
    }


    /**
     * 请求定时器获取iMoc虚拟机详情
     * @param ecsId
     * @return
     */
    @Override
    public EcsVO getiMocEcsDetail(String ecsId) {

        String data = requestDetail(ecsId);
        JSONObject jsonObject =JSONObject.parseObject(data);
        if(jsonObject.getInteger("code") == 200){
            return convert2EcsVO(jsonObject);
        }

        return null;
    }



    @Override
    public List<EcsVO> getEcsRecent(String ecsId) {

        String  url = iMocPreURL+"/imocSearch/last7DaysByName?name="+ecsId;
        String data = requestList(url);
        List<EcsVO> ecsVOList = Lists.newArrayList();
        JSONArray jsonArray = JSONArray.parseArray(data);
        for(int i=0;i<jsonArray.size();i++){
            JSONObject dataObject = jsonArray.getJSONObject(i);
            EcsVO ecsVO = JSONObject.toJavaObject(dataObject,EcsVO.class);
            ecsVOList.add(ecsVO);
        }
        return ecsVOList;
    }

    @Override
    public List<EcsVO> getEcsByAppName(String name) {

        String  url = iMocPreURL+"/imocSearch/vmByAppName?name="+name;
        String data = requestList(url);
        List<EcsVO> ecsVOList = Lists.newArrayList();
        JSONArray jsonArray = JSONArray.parseArray(data);
        for(int i=0;i<jsonArray.size();i++){
            JSONObject dataObject = jsonArray.getJSONObject(i);
            EcsVO ecsVO = JSONObject.toJavaObject(dataObject,EcsVO.class);
            ecsVOList.add(ecsVO);
        }
        return ecsVOList;
    }




    /**
     * 请求定时器获取数据
     * @param
     * @return
     */
    private String requestList(String url){
        Response response = null;
        try{
            response = OkHttpUtils.get(url, null);
            String data = response.body().string();
            return data;
        }catch (Exception e){
            logger.error("获取虚拟机列表失败 "+url,e);
            throw new CustomException(CommonCode.SERVER_ERROR);
        } finally {
            if (response!=null){
                response.close();
            }
        }
    }


    private  List<EcsVO> convert2EcsList(JSONObject jsonObject){
        try{
            List<EcsVO> ecsVOList = Lists.newArrayList();
            JSONArray jsonArray =  jsonObject.getJSONArray("data");
            for(int i=0;i<jsonArray.size();i++){
                JSONObject dataObject = jsonArray.getJSONObject(0);
                EcsVO ecsVO = JSONObject.toJavaObject(dataObject,EcsVO.class);
                ecsVOList.add(ecsVO);
            }
            return ecsVOList;
        }catch (Exception e){
            logger.error("转换历史数据失败",e);
            throw new CustomException(CommonCode.SERVER_ERROR);
        }
    }


    private EcsVO convert2EcsVO(JSONObject jsonObject){
        try{
            JSONArray jsonArray =  jsonObject.getJSONArray("data");
            JSONObject dataObject = jsonArray.getJSONObject(0);
            EcsVO ecsVO = JSONObject.toJavaObject(dataObject,EcsVO.class);
            return ecsVO;
        }catch (Exception e){
            logger.error("转换实体失败",e);
            throw new CustomException(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 请求定时器获取数据
     * @param ecsId
     * @return
     */
    private String requestDetail(String ecsId){
        Response response = null;
        try{
            response = OkHttpUtils.get(iMocPreURL + "/imocSearch/selectByName?name=" + ecsId, null);
            String data = response.body().string();
            return data;
        }catch (Exception e){
            logger.error("获取虚拟机详情失败 "+ecsId,e);
            throw new CustomException(CommonCode.SERVER_ERROR);
        }finally {
            if (response!=null){
                response.close();
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCart<IaasTxyfw> shoppingCart) {
        List<IaasTxyfw> serverList = shoppingCart.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfw txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setShoppingCartId(shoppingCart.getId());
                this.save(txyfw);
            }
        }
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfo<IaasTxyfw, Object> info) {
        List<IaasTxyfw> serverList = info.getServerList();
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfw txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setAppInfoId(info.getId());
                this.save(txyfw);
            }
        }
    }

    @Override
    public List<IaasTxyfw> getByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<IaasTxyfw>().lambda()
                .eq(IaasTxyfw::getAppInfoId, appInfoId)
                .orderByAsc(IaasTxyfw::getCreateTime));
    }

    @Override
    public List<IaasTxyfw> getByShoppingCartId(String shoppingCartId) {
        return this.list(new QueryWrapper<IaasTxyfw>().lambda()
                .eq(IaasTxyfw::getShoppingCartId, shoppingCartId)
                .orderByAsc(IaasTxyfw::getCreateTime));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfo<IaasTxyfw, Object> info) {
        this.remove(new QueryWrapper<IaasTxyfw>().lambda().eq(IaasTxyfw::getAppInfoId, info.getId()));

        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCart<IaasTxyfw> shoppingCart) {
        this.remove(new QueryWrapper<IaasTxyfw>().lambda().eq(IaasTxyfw::getShoppingCartId, shoppingCart.getId()));

        saveShoppingCart(shoppingCart);
    }

    @Override
    public Integer getTotalNum(String appInfoId) {
        return iaasTxyfwMapper.getTotalNum(appInfoId);
    }

    @Override
    public Integer getTotalNumInShoppingCart(String shoppingCartId) {
        return iaasTxyfwMapper.getTotalNumOfShoppingCart(shoppingCartId);
    }

    /**
     * 购物车删除
     *
     * @param shoppingCartId 购物车ID
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {
        this.remove(new QueryWrapper<IaasTxyfw>().lambda().eq(IaasTxyfw::getShoppingCartId,shoppingCartId));
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
        this.update(new IaasTxyfw(),new UpdateWrapper<IaasTxyfw>().lambda()
                .eq(IaasTxyfw::getShoppingCartId,shoppingCartId)
                .set(IaasTxyfw::getAppInfoId,appInfoId));
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
//        this.update(new IaasTxyfw(),new UpdateWrapper<IaasTxyfw>().lambda().eq(IaasTxyfw::getAppInfoId,appInfoId)
//                .set(IaasTxyfw::getShoppingCartId,shoppingCartId));
//
//
//        this.update(new IaasTxyfw(),new UpdateWrapper<IaasTxyfw>().lambda().eq(IaasTxyfw::getShoppingCartId,shoppingCartId)
//                .set(IaasTxyfw::getAppInfoId,""));
//    }


}
