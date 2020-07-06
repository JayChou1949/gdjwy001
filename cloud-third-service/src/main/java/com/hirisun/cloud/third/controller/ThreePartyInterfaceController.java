package com.hirisun.cloud.third.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.third.bean.ThreePartyInterface;
import com.hirisun.cloud.third.service.ThreePartyInterfaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



    /**
     * 根据第三方资源id查找数据
     * 兼容前端代码，开放post、getMapping
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据第三方资源id查找数据", notes = "{"
            + "resourceTotal:资源目录总数;        "

            + "}")
    @GetMapping("/{id}")
    public Object getById(@ApiParam(name = "id", value = "第三方数据id", required = true) @PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            return QueryResponseResult.fail("缺少参数");
        }
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getById(id);
        if (threePartyInterface == null) {
            return QueryResponseResult.fail("无数据返回");
        }
        JSONObject json = null;
        //特殊处理，对部分接口内容进行排序
        if(id.equals("ncovImportRecentAll")||id.equals("ncovImportImportantData")){
            json=JSONObject.parseObject(threePartyInterface.getData(), Feature.OrderedField);
        }else{
            json=JSONObject.parseObject(threePartyInterface.getData());
        }
        return JSONObject.parseObject(threePartyInterface.getData());
    }

    /**
     * 根据第三方资源id查找数据
     * 此处和旧接口不一样，新接口需要传label，即中文
     *
     * @param label
     * @return
     */
    @ApiOperation(value = "根据第三方资源label查找数据", notes = "旧接口中如果接口为/fwzl,则前端需要改为/服务总量。数据：" +
            "{"
            + "fwzl:服务总量;        "

            + "}")
    @GetMapping("/getByLabel/{label}")
    public Object getByLabel(@ApiParam(name = "label", value = "第三方数据标识", required = true) @PathVariable String label) {
        if (StringUtils.isEmpty(label)) {
            return QueryResponseResult.fail("缺少参数");
        }
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getLabel,label));
        if (threePartyInterface == null) {
            return QueryResponseResult.fail("无数据返回");
        }
        return JSONObject.parseObject(threePartyInterface.getData());
    }


}

