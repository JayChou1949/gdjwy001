package com.upd.hwcloud.controller.backend.tenantReport;


import com.upd.hwcloud.bean.entity.PaasZhzy;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IPaasZhzyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * PAAS租户资源 前端控制器
 * </p>
 *
 * @author xqp
 * @since 2020-06-16
 */
@Api(description = "paas租户资源")
@RestController
@RequestMapping("/paasZhzy")
public class PaasZhzyController {

    @Autowired
    private IPaasZhzyService paasZhzyService;

    @ApiOperation(value = "PaaS资源管理")
    @RequestMapping(value = "/getPaasZhzy",method = RequestMethod.GET)
    public R getPaasZhzy(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police){
        Map<String,PaasZhzy> paasZhzyMap = new HashMap<>();
        try {
            paasZhzyMap = paasZhzyService.getPaasZhzy(area,police);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(paasZhzyMap);
    }

    @ApiOperation(value = "应用列表")
    @RequestMapping(value = "/appFromAreaOrPolice",method = RequestMethod.GET)
    public R appFromAreaOrPolice(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police){
        List<String> apps;
        try {
            apps = paasZhzyService.appFromAreaOrPolice(area,police);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(apps);
    }

    @ApiOperation(value = "应用集群概览")
    @RequestMapping(value = "/appClusterDetails",method = RequestMethod.GET)
    public R appClusterDetails(@RequestParam(value = "appName") String appName,@RequestParam(value = "clusterName") String clusterName){
        PaasZhzy paasZhzy = new PaasZhzy();
        try {
            paasZhzy = paasZhzyService.appClusterDetails(appName,clusterName);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(paasZhzy);
    }


}

