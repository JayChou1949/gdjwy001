package com.hirisun.cloud.third.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.third.bean.ThreePartyInterface;
import com.hirisun.cloud.third.service.ThreePartyInterfaceService;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("/threePartyInterface")
public class ThreePartyInterfaceController {

    private static Logger logger = LoggerFactory.getLogger(ThreePartyInterfaceController.class);

    @Autowired
    private ThreePartyInterfaceService threePartyInterfaceService;

    /**
     * 根据第三方资源id查找数据
     * @param id
     * @return
     */
    @ApiOperation(value = "根据第三方资源id查找数据",notes = "{"
            + "resourceTotal:资源目录总数;        "
            + "zonglan:daas总览;      "
            + "zlCount10 :daas总览-近10日每日增量趋势图;       "
            + "fiveBigResource:数据接入（数据概况）;        "
            + "fiveBigResource01:原始库数据;        "
            + "fiveBigResource02:资源库数据;        "
            + "fiveBigResource03:主题库数据;        "
            + "fiveBigResource04:知识库数据;        "
            + "fiveBigResource05:业务库数据;"
            + "fiveBigResource06:业务要素索引库（接口）;       "
            + "resourceRelationCount:业务库数据;     "
            + "dataGovern:业务要素索引库（接口）;      "

            +"}")
    @ApiResponses(//相应参数说明
            @ApiResponse(code=200,message="success",response= ThreePartyInterface.class)
    )
    @ApiImplicitParams({//请求参数说明
            @ApiImplicitParam(paramType="query", name = "id", value = "第三方数据id", required = true, dataType = "String")
    })
    @GetMapping("/{id}")
    public Object getById(@PathVariable String id) {
        if(StringUtils.isEmpty(id)){
            Map<String, Object> map = new HashMap<>();
            map.put("code", 201);
            map.put("data", new ArrayList<>());
            return map;
        }
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getById(id);
        if(threePartyInterface==null){
            Map<String, Object> map = new HashMap<>();
            map.put("code", 200);
            map.put("data", new ArrayList<>());
            return map;
        }
        logger.info("threePartyInterface:{}",threePartyInterface);
        return JSONObject.parseObject(threePartyInterface.getData());
    }

    /**
     * 根据第三方资源id查找数据
     * @param label
     * @return
     */
    @ApiOperation(value = "根据第三方资源label查找数据",notes = "{"
            + "fwzl:服务总量;        "
            + "cxjs:查询检索服务总数;      "
            + "bkdy:布控订阅服务总数;       "
            + "mxfx:模型分析服务总数;        "
            + "sjts:数据推送服务总数;        "
            + "xtdytop:近7天系统调用top10;        "
            + "xtdytopAll:系统调用top10(全量);        "
            + "fwfwtop:近7天服务访问top10;        "
            + "fwfwtopAll:服务访问top10(全量);        "
            + "zxgztop:最新挂载服务top10;       "
            + "yyfwtop:应用调用服务Top10;     "
            + "jzdytop5:警种调用Top5;      "
            + "dsdytop5:地市调用Top5;       "
            + "hDyhFwFwTop:高频订阅高频访问服务Top10;     "
            + "lDyhFwFwTop:低频订阅高频访问服务Top10;      "
            + "hDylFwXtTop:高频订阅低频访问系统Top10;       "
            + "ztqs7day:近7天服务请求总体趋势;     "
            + "hsqs7day:近7天服务响应耗时趋势;      "
            + "yqServiceTop10:疫情服务top10;       "
            + "yqServiceInfo:疫情数据服务二级页面;     "
            + "yqServiceLast7:疫情服务近7天调用趋势;      "

            +"}")
    @ApiResponses(//相应参数说明
            @ApiResponse(code=200,message="success",response= ThreePartyInterface.class)
    )
    @ApiImplicitParams({//请求参数说明
            @ApiImplicitParam(paramType="query", name = "id", value = "第三方数据id", required = true, dataType = "String")
    })
    @GetMapping("/getByLabel/{label}")
    public Object getByLabel(@PathVariable String label) {
        if(StringUtils.isEmpty(label)){
            Map<String, Object> map = new HashMap<>();
            map.put("code", 201);
            map.put("data", new ArrayList<>());
            return map;
        }
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", label));
        if(threePartyInterface==null){
            Map<String, Object> map = new HashMap<>();
            map.put("code", 200);
            map.put("data", new ArrayList<>());
            return map;
        }
        logger.info("threePartyInterface:{}",threePartyInterface);
        return JSONObject.parseObject(threePartyInterface.getData());
    }
}

