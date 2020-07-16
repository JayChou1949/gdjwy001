package com.hirisun.cloud.third.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.util.ExceptionPrintUtil;
import com.hirisun.cloud.common.util.OkHttpUtils;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.third.bean.ThreePartyInterface;
import com.hirisun.cloud.third.service.ThreePartyInterfaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 第三方接口表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-06-28
 */
@RestController
@RequestMapping("/api/threePartyInterface")
@Api(description = "美亚daas数据治理")
public class ThreePartyInterfaceController {

    //美亚ip、开放端口
    @Value("${third.my.url}")
    private String BASE_URL;

    private static Logger logger = LoggerFactory.getLogger(ThreePartyInterfaceController.class);

    @Autowired
    private ThreePartyInterfaceService threePartyInterfaceService;



    /**
     * 根据第三方资源标识名查找数据
     * 已修复数据表数据，所有name字段不为空的数据为有用数据，name空字段的数据为无用数据，待清除
     *
     * @param name
     * @return
     */
    @ApiOperation(value = "根据第三方资源标识名查找数据",notes = "相关name请查看gitbook文档")
    @GetMapping("/{name}")
    public Object getById(@ApiParam(name = "name", value = "第三方资源标识", required = true) @PathVariable String name) {
        if (StringUtils.isEmpty(name)) {
            return QueryResponseResult.fail("缺少参数");
        }
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getName,name));
        if (threePartyInterface == null) {
            return QueryResponseResult.fail("无数据返回");
        }
        JSONObject json = null;
        //特殊处理，对部分接口内容进行排序
        if(name.equals("ncovImportRecentAll")||name.equals("ncovImportImportantData")){
            json=JSONObject.parseObject(threePartyInterface.getData(), Feature.OrderedField);
        }else{
            json=JSONObject.parseObject(threePartyInterface.getData());
        }
        return JSONObject.parseObject(threePartyInterface.getData());
    }

    /**
     * 数据处理二级页面-数据处理实时概况报表
     * @return
     */
    @ApiOperation(value = "获取实时数据24条",notes = "数据处理二级页面-数据处理实时概况报表")
    @GetMapping("/getRealTimeData24")
    public QueryResponseResult getRealTimeData24() {
        List<ThreePartyInterface> listIn = threePartyInterfaceService.list(
                new QueryWrapper<ThreePartyInterface>().lambda()
                        .eq(ThreePartyInterface::getName,"realTimeDataIn")
                        .orderByAsc(ThreePartyInterface::getUpdateTime));
        List<ThreePartyInterface> listOut = threePartyInterfaceService.list(
                new QueryWrapper<ThreePartyInterface>().lambda()
                        .eq(ThreePartyInterface::getName,"realTimeDataOut")
                        .orderByAsc(ThreePartyInterface::getUpdateTime));
        //拼装成前端报表可以展示的数据格式
        List<Map<String, Object>> mapsIn = parseRealTiimeData(listIn);
        List<Map<String, Object>> mapsOut = parseRealTiimeData(listOut);
        Map<String, Object> inOut = new HashMap<>();
        inOut.put("inList", mapsIn);
        inOut.put("outList", mapsOut);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("data", inOut);
        return QueryResponseResult.success(inOut);
    }

    /**
     * 格式化实时处理列表数据
     * 拼装成前端报表可以展示的数据格式
     */
    private List<Map<String, Object>> parseRealTiimeData(List<ThreePartyInterface> list) {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        List<Map<String, Object>> maps = new ArrayList<>();
        list.forEach(item -> {
            //解析data的json数据
            JSONObject jsonObject = JSONObject.parseObject(item.getData()).getJSONObject("data");
            Map<String, Object> map = new HashMap<>();
            map.put("value", jsonObject.getString("value"));
            map.put("ext2", jsonObject.getString("ext2"));
            map.put("time", fmt.format(item.getUpdateTime()));
            maps.add(map);
        });
        return maps;
    }

    /**
     * 数据资源目录列表，作用于daas-数据治理二级页面下查询的目录列表
     * 数据量大，直接去美亚查询，不保存数据到数据库
     * jsonobject 带上分页信息
     */
    @ApiOperation("数据目录列表")
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public Object pageQuery(@RequestBody JSONObject jsonObject)throws Exception {
        String jsonString = jsonObject.getJSONObject("dataJson").toJSONString();
        jsonObject.remove("dataJson");
        jsonObject.put("dataJson", jsonString);
        String url = BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataResource/action/pageQuery";
        Response response=null;
        String data =null;
        try{
            response= OkHttpUtils.postJson(url, jsonObject.toString());
            data=response.body().string();
        }catch (Exception e){
            logger.error("catch exception:{}", e.getMessage());
            logger.info("美亚接口异常[url:{},内容:{}]",url, ExceptionPrintUtil.getStackTraceInfo(e));
        }finally {
            if(response!=null){
                response.close();
            }
        }
        return JSONObject.parseObject(data);
    }
    /**
     * 数据表结构，作用于daas-数据治理二级页面-目录列表-查看表结构
     * 数据量大，直接去美亚查询，不保存数据到数据库
     */
    @ApiOperation("数据表结构")
    @RequestMapping(value = "/sourceStandardFieldMappingList", method = RequestMethod.GET)
    public Object sourceStandardFieldMappingList(String id)throws Exception {
        String url = BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataResource/action/sourceStandardFieldMappingList?resourceId=" + id;
        Response response=null;
        String data =null;
        try{
            response=OkHttpUtils.get(url, null);
            data = response.body().string();
        }catch (Exception e){
            logger.error("catch exception:{}", e.getMessage());
            logger.info("美亚接口异常[url:{},内容:{}]",url, ExceptionPrintUtil.getStackTraceInfo(e));
        }finally {
            if(response!=null){
                response.close();
            }
        }
        return JSONObject.parseObject(data);
    }

    /*
     *数据服务-数据组织-资源库等五大库二级页面接口
     * resourceFirstClass:一级编码，由美亚分配，02资源库 03主题库 04知识库 05业务库 06业务要素索引库
     */
    @ApiOperation("数据组织五大库二级页面类型")
    @GetMapping("/wdkSubPageType")
    public Object wdkSubPageType(String resourceFirstClass) throws Exception {
        String url = BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statisticsSecond/getSecondClass?resourceFirstClass=" + resourceFirstClass;
        Response response=null;
        String data =null;
        try{
            response = OkHttpUtils.get(url, null);
            data = response.body().string();
        }catch (Exception e){
            logger.error("catch exception:{}", e.getMessage());
            logger.info("美亚接口异常[url:{},内容:{}]",url, ExceptionPrintUtil.getStackTraceInfo(e));
        }finally {
            if(response!=null){
                response.close();
            }
        }
        return JSONObject.parseObject(data);
    }

    /*
     *数据服务-数据组织-资源库等五大库二级页面详情接口
     * resourceFirstClass:一级编码，由美亚定义，02资源库 03主题库 04知识库 05业务库 06业务要素索引库
     * resourceSecondClass：二级编码，由页面类型查询返回的列表数据中itemccode
     */
    @ApiOperation("数据组织五大库二级页面详情")
    @GetMapping("/wdkSubPageInfo")
    public Object wdkSubPageInfo(String resourceFirstClass, String resourceSecondClass) throws Exception {
        String url = BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statisticsSecond/getSecondClassCount?resourceFirstClass=" + resourceFirstClass + "&resourceSecondClass=" + resourceSecondClass;
        Response response=null;
        String data =null;
        try{
            response = OkHttpUtils.get(url, null);
            data = response.body().string();
        }catch (Exception e){
            logger.error("catch exception:{}", e.getMessage());
            logger.info("美亚接口异常[url:{},内容:{}]",url, ExceptionPrintUtil.getStackTraceInfo(e));
        }finally {
            if(response!=null){
                response.close();
            }
        }
        return JSONObject.parseObject(data);
    }


}

