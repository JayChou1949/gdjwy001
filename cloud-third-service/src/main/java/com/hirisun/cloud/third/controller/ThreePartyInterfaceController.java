package com.hirisun.cloud.third.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.third.bean.ThreePartyInterface;
import com.hirisun.cloud.third.service.ThreePartyInterfaceService;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

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

    private static Logger logger = LoggerFactory.getLogger(ThreePartyInterfaceController.class);

    @Autowired
    private ThreePartyInterfaceService threePartyInterfaceService;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 根据第三方资源标识名查找数据
     * 已修复数据表数据，所有name字段不为空的数据为有用数据，name空字段的数据为无用数据，待清除
     *
     * @param name
     * @return
     */
    @ApiOperation(value = "根据第三方资源标识名查找数据")
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


}

