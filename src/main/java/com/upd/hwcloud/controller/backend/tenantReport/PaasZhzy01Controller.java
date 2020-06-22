package com.upd.hwcloud.controller.backend.tenantReport;


import com.upd.hwcloud.bean.entity.PaasZhzy;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IPaasZhzyService;
import com.upd.hwcloud.service.IPaasZhzyService01;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class PaasZhzy01Controller {

    @Autowired
    private IPaasZhzyService paasZhzyService;
    @Autowired
    private IPaasZhzyService01 paasZhzyService01;



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

    @ApiOperation(value = "应用集群概览tab")
    @RequestMapping(value = "/clusterTabs",method = RequestMethod.GET)
    public R clusterTabs(@RequestParam(value = "appName") String appName){
        List<String> clusterList;
        try {
            clusterList = paasZhzyService.clusterTabs(appName);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(clusterList);
    }

    @ApiOperation(value = "paas资源yarn")
    @RequestMapping(value = "/v1/getPaasYarnResource",method = RequestMethod.GET)
    public R getPaasYarnResource(@RequestParam(value = "appName") String appName,@RequestParam(value = "area") String area,@RequestParam(value = "police") String police,
                           @RequestParam(value = "day") Integer day){
        Map map=new HashMap();

        try {
            PaasZhzy paasYarnResource = paasZhzyService01.getPaasYarnResource(appName, area, police, day);
            map.put("paasYarnResource",paasYarnResource);
            PaasZhzy maxCpu = paasZhzyService01.maxYarnCpu(appName, area, police, day);
            PaasZhzy  maxMemory = paasZhzyService01.maxYarnMemory(appName, area, police, day);
            PaasZhzy totalYarnCpu = paasZhzyService01.totalYarnCpu(appName, area, police, day);
            map.put("maxCpu",maxCpu.getCpuUsage());
            map.put("maxMemory",maxMemory.getMemoryUsage());
            map.put("totalYarnCpu",totalYarnCpu);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(map);
    }
    @ApiOperation(value = "paas资源Libra")
    @RequestMapping(value = "/v1/getPaasLibraResource",method = RequestMethod.GET)
    public R getPaasLibraResource(@RequestParam(value = "appName") String appName,@RequestParam(value = "area") String area,@RequestParam(value = "police") String police,
                                 @RequestParam(value = "day") Integer day){
        Map map=new HashMap();

        try {
            PaasZhzy paasLibraResource = paasZhzyService01.getPaasLibraResource(appName, area, police, day);
            map.put("paasLibraResource",paasLibraResource);
            PaasZhzy maxCpu = paasZhzyService01.maxLibraCpu(appName, area, police, day);
            PaasZhzy  maxMemory = paasZhzyService01.maxLibraMemary(appName, area, police, day);
            PaasZhzy  maxStorage = paasZhzyService01.maxLibraStorage(appName, area, police, day);
            PaasZhzy totalYarnCpu = paasZhzyService01.totalLibraMemary(appName, area, police, day);
            map.put("maxCpu",maxCpu.getCpuUsage());
            map.put("maxMemory",maxMemory.getMemoryUsage());
            map.put("totalYarnCpu",totalYarnCpu);
            map.put("maxStorage",maxStorage);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(map);
    }
    @ApiOperation(value = "paas资源ES")
    @RequestMapping(value = "/v1/getPaasEsResource",method = RequestMethod.GET)
    public R getPaasEsResource(@RequestParam(value = "appName") String appName,@RequestParam(value = "area") String area,@RequestParam(value = "police") String police,
                                 @RequestParam(value = "day") Integer day){
        Map map=new HashMap();

        try {
            PaasZhzy paasEsResource = paasZhzyService01.getPaasEsResource(appName, area, police, day);
            map.put("paasEsResource",paasEsResource);
            PaasZhzy maxCpu = paasZhzyService01.maxEsCpu(appName, area, police, day);
            PaasZhzy  maxMemory = paasZhzyService01.maxEsMemary(appName, area, police, day);
            PaasZhzy totalYarnCpu = paasZhzyService01.totalEsMemary(appName, area, police, day);
            PaasZhzy  maxStorage = paasZhzyService01.maxEsStorage(appName, area, police, day);
            map.put("maxCpu",maxCpu.getCpuUsage());
            map.put("maxMemory",maxMemory.getMemoryUsage());
            map.put("totalYarnCpu",totalYarnCpu);
            map.put("maxStorage",maxStorage);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(map);
    }

    @ApiOperation(value = "paas资源redis")
    @RequestMapping(value = "/v1/getPaasRedisResource",method = RequestMethod.GET)
    public R getPaasRedisResource(@RequestParam(value = "appName") String appName,@RequestParam(value = "area") String area,@RequestParam(value = "police") String police,
                                 @RequestParam(value = "day") Integer day){
        Map map=new HashMap();

        try {
            PaasZhzy paasRedisResource = paasZhzyService01.getPaasRedisResource(appName, area, police, day);
            map.put("paasRedisResource",paasRedisResource);
            PaasZhzy  maxMemory = paasZhzyService01.maxRedisMemary(appName, area, police, day);
            PaasZhzy totalYarnCpu = paasZhzyService01.totalRedisMemary(appName, area, police, day);
            map.put("maxMemory",maxMemory.getMemoryUsage());
            map.put("totalYarnCpu",totalYarnCpu);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(map);
    }

}

