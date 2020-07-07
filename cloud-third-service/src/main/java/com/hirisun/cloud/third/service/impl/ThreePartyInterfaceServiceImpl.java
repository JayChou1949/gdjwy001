package com.hirisun.cloud.third.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.util.OkHttpUtils;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.third.bean.ThreePartyInterface;
import com.hirisun.cloud.third.mapper.ThreePartyInterfaceMapper;
import com.hirisun.cloud.third.service.ThreePartyInterfaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 第三方接口表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-06-28
 */
@Service
public class ThreePartyInterfaceServiceImpl extends ServiceImpl<ThreePartyInterfaceMapper, ThreePartyInterface> implements ThreePartyInterfaceService {

    private static Logger logger = LoggerFactory.getLogger(ThreePartyInterfaceServiceImpl.class);


    /**
     * 美亚接口处理
     * @param name 接口名
     * @param label 接口标识
     */
//    @Override
    public void saveData(String data,String name,String label) {
        ThreePartyInterface threePartyInterface=null;
        List<ThreePartyInterface> interfaceList = this.list(
                new QueryWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getName,name));
        if (interfaceList != null && interfaceList.size() > 0) {
            threePartyInterface=interfaceList.get(0);
            threePartyInterface.setData(data);
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            threePartyInterface.setUpdateTime(date);
            this.updateById(threePartyInterface);
        }else{
            threePartyInterface = new ThreePartyInterface();
            threePartyInterface.setId(UUIDUtil.getUUID());
            threePartyInterface.setLabel(label);
            threePartyInterface.setName(name);
            threePartyInterface.setData(data);
            threePartyInterface.setType("99");//美亚类型接口
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            threePartyInterface.setUpdateTime(date);
            this.save(threePartyInterface);
        }
    }

    @Override
    public void getDataHandler(String url, String name, String label) {
        Response response=null;
        try {
            response=OkHttpUtils.get(url, null);
            String data = response.body().string();
            if(JSONObject.parseObject(data).getInteger("code") != 0) {
                logger.info("美亚接口异常[url:{}, label:{}, name:{}]",url,label,name);
                return;
            }
            saveData(data,name,label);
            logger.info("美亚接口调用成功[url:{}, label:{}, name:{}]",url,label,name);
        } catch (Exception e) {
            logger.info("美亚接口异常[url:{}, label:{}, name:{}]",url,label,name);
        }finally {
            if(response!=null){
                response.close();
            }
        }
    }

    @Override
    public void postDataHandler(String url, String name, String label,Map<String, String> map) {
        Response response=null;
        try {
            if("模型超市按模型上线时间排行".equals(label)) {
                map = new HashMap<>();
                map.put("type", "1");
            } else if("模型超市按模型热度排行".equals(label)) {
                map = new HashMap<>();
                map.put("type", "2");
            }
            response=OkHttpUtils.post(url, map);
            String data = response.body().string();
            saveData(data,name,label);
            logger.info("美亚接口调用成功[url:{}, label:{}, name:{}]",url,label,name);
        } catch (Exception e) {
            logger.info("美亚接口异常[url:{}, label:{}, name:{}]",url,label,name);
        }finally {
            if(response!=null){
                response.close();
            }
        }
    }

    @Override
    public void jsonDataHandler(String url, String name, String label, String json) {
        Response response=null;
        try {
            response=OkHttpUtils.postJson(url, json);
            String data = response.body().string();
            if(JSONObject.parseObject(data).getInteger("code") != 0) {
                logger.info("美亚接口异常[url:{}, label:{}, name:{}]",url,label,name);
                return;
            }
            saveData(data,name,label);
            logger.info("美亚接口调用成功[url:{}, label:{}, name:{}]",url,label,name);
        } catch (Exception e) {
            logger.info("美亚接口异常[url:{}, label:{}, name:{}]",url,label,name);
        }finally {
            if(response!=null){
                response.close();
            }
        }
    }
}
