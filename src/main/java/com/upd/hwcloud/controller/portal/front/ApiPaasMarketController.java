package com.upd.hwcloud.controller.portal.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.dto.AppReqServiceDTO;
import com.upd.hwcloud.bean.dto.DelayDTO;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.dto.ReqDTO;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IThreePartyInterfaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description = "PAAS服务市场接口")
@RestController
@RequestMapping("/api/paas/market")
public class ApiPaasMarketController {

    @Autowired
    private IThreePartyInterfaceService threePartyInterfaceService;


    @ApiOperation("警种/地市 - 服务数据统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type", value="查询类型 area:地区 policeCategory:警种", paramType="path", dataType="String"),
            @ApiImplicitParam(name="name", value="警种或地区名称,如:科信,揭阳", paramType="path", dataType="String")
    })
    @RequestMapping(value = "/gk/{type}/{name}", method = RequestMethod.GET)
    public R gk(@PathVariable String type, @PathVariable String name) {
        Map<String, Object> gk = threePartyInterfaceService.areaPoliceGk(type, name);
        return R.ok(gk);
    }

    @ApiOperation("警种/地市 - 近十日调用服务趋势")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type", value="查询类型 area:地区 policeCategory:警种", paramType="path", dataType="String"),
            @ApiImplicitParam(name="name", value="警种或地区名称,如:科信,揭阳", paramType="path", dataType="String")
    })
    @RequestMapping(value = "/recent10ReqService/{type}/{name}", method = RequestMethod.GET)
    public R recent10ReqService(@PathVariable String type, @PathVariable String name) {
        List<GeneralDTO> recent10ReqService = threePartyInterfaceService.areaPoliceRecent10ReqService(type, name);
        return R.ok(recent10ReqService);
    }

    @ApiOperation("警种/地市 - 近7日调用时延统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type", value="查询类型 area:地区 policeCategory:警种", paramType="path", dataType="String"),
            @ApiImplicitParam(name="name", value="警种或地区名称,如:科信,揭阳", paramType="path", dataType="String")
    })
    @RequestMapping(value = "/recent7ReqDelay/{type}/{name}", method = RequestMethod.GET)
    public R recent7ReqDelay(@PathVariable String type, @PathVariable String name) {
        List<DelayDTO> recent7ReqDelay = threePartyInterfaceService.areaPoliceRecent7ReqDelay(type, name);
        return R.ok(recent7ReqDelay);
    }

    @ApiOperation("警种/地市 - 近7日调用情况统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type", value="查询类型 area:地区 policeCategory:警种", paramType="path", dataType="String"),
            @ApiImplicitParam(name="name", value="警种或地区名称,如:科信,揭阳", paramType="path", dataType="String")
    })
    @RequestMapping(value = "/recent7Req/{type}/{name}", method = RequestMethod.GET)
    public R recent7ReqAbnormal(@PathVariable String type, @PathVariable String name) {
        List<ReqDTO> recent7ReqAbnormal = threePartyInterfaceService.areaPoliceRecent7Req(type, name);
        return R.ok(recent7ReqAbnormal);
    }

    @ApiOperation("警种/地市 - 服务被调用排行")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type", value="查询类型 area:地区 policeCategory:警种", paramType="path", dataType="String"),
            @ApiImplicitParam(name="name", value="警种或地区名称,如:科信,揭阳", paramType="path", dataType="String"),
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/serviceReq/page/{type}/{name}", method = RequestMethod.GET)
    public R serviceReqPage(@PathVariable String type, @PathVariable String name,
                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        IPage<GeneralDTO> page = new Page<>(pageNum, pageSize);
        page = threePartyInterfaceService.serviceReqPage(page, type, name);
        return R.ok(page);
    }

    @ApiOperation("警种/地市 - 应用调用排行")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type", value="查询类型 area:地区 policeCategory:警种", paramType="path", dataType="String"),
            @ApiImplicitParam(name="name", value="警种或地区名称,如:科信,揭阳", paramType="path", dataType="String"),
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/appReq/page/{type}/{name}", method = RequestMethod.GET)
    public R appReqPage(@PathVariable String type, @PathVariable String name,
                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        IPage<GeneralDTO> page = new Page<>(pageNum, pageSize);
        page = threePartyInterfaceService.appReqPage(page, type, name);
        return R.ok(page);
    }

    @ApiOperation("警种/地市 - 应用调用服务统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type", value="查询类型 area:地区 policeCategory:警种", paramType="path", dataType="String"),
            @ApiImplicitParam(name="name", value="警种或地区名称,如:科信,揭阳", paramType="path", dataType="String"),
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
            @ApiImplicitParam(name="serviceName", value="服务名", paramType="query", dataType="String"),
            @ApiImplicitParam(name="appName", value="应用名", paramType="query", dataType="String"),
            @ApiImplicitParam(name="orderBy", value="排序规则,格式:[字段名:升降序] 如: appName:desc reqCount:asc", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/appReqService/page/{type}/{name}", method = RequestMethod.GET)
    public R appReqService(@PathVariable String type, @PathVariable String name,
                        @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                        @RequestParam(required = false) String serviceName,
                        @RequestParam(required = false) String appName,
                        @RequestParam(required = false) String orderBy) {
        if (StringUtils.isEmpty(orderBy)) {
            orderBy = "appName:asc";
        }
        boolean containsOrder = orderBy.contains("desc") || orderBy.contains("asc");
        String[] order = orderBy.split(":");
        if (!containsOrder || order.length != 2) {
            return R.error("排序参数错误");
        }
        orderBy = orderBy.replace(":", " ");

        Map<String, Object> param = Maps.newHashMap();
        param.put("appName", appName);
        param.put("serviceName", serviceName);
        param.put("orderBy", orderBy);

        IPage<AppReqServiceDTO> page = new Page<>(pageNum, pageSize);
        page = threePartyInterfaceService.appReqService(page, type, name, param);
        return R.ok(page);
    }


}
