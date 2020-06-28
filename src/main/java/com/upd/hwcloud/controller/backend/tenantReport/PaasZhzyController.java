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

import java.util.ArrayList;
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

    @ApiOperation(value = "应用集群概览详细信息")
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

    @ApiOperation(value = "地市或警种的集群tabs")
    @RequestMapping(value = "/clusterTabsByAreaOrPolice",method = RequestMethod.GET)
    public R clusterTabsByAreaOrPolice(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police){
        List<String> clusterList;
        try {
            clusterList = paasZhzyService.clusterTabsByAreaOrPolice(area,police);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(clusterList);
    }

    @ApiOperation(value = "YARN资源总览")
    @RequestMapping(value = "/paasOverviewByYarn",method = RequestMethod.GET)
    public R paasOverviewByYarn(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police){
        PaasZhzy paasZhzy = new PaasZhzy();
        try {
            paasZhzy = paasZhzyService.paasOverviewByYarn(area, police);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(paasZhzy);
    }

    @ApiOperation(value = "Elasticsearch总览")
    @RequestMapping(value = "/paasOverviewByElasticsearch",method = RequestMethod.GET)
    public R paasOverviewByElasticsearch(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police){
        List<PaasZhzy> list = new ArrayList<>();
        try {
            list = paasZhzyService.paasOverviewByElasticsearch(area, police);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(list);
    }

    @ApiOperation(value = "Redis总览")
    @RequestMapping(value = "/paasOverviewByRedis",method = RequestMethod.GET)
    public R paasOverviewByRedis(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police){
        PaasZhzy paasZhzy = new PaasZhzy();
        try {
            paasZhzy = paasZhzyService.paasOverviewByRedis(area, police);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(paasZhzy);
    }

    @ApiOperation(value = "Libra集群总览")
    @RequestMapping(value = "/paasOverviewByLibra",method = RequestMethod.GET)
    public R paasOverviewByLibra(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police){
        PaasZhzy paasZhzy = new PaasZhzy();
        try {
            paasZhzy = paasZhzyService.paasOverviewByLibra(area, police);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(paasZhzy);
    }

    @ApiOperation(value = "Yarn资源峰值")
    @RequestMapping(value = "/paasMaxByYarn",method = RequestMethod.GET)
    public R paasMaxByYarn(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police,@RequestParam(value = "day") String day){
        Map<String,Double> zhzyMap = new HashMap<>();
        try {
            zhzyMap = paasZhzyService.paasMaxByYarn(area, police,day);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(zhzyMap);
    }

    @ApiOperation(value = "Libra资源峰值")
    @RequestMapping(value = "/paasMaxByLibra",method = RequestMethod.GET)
    public R paasMaxByLibra(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police,@RequestParam(value = "day") String day){
        Map<String,Double> zhzyMap = new HashMap<>();
        try {
            zhzyMap = paasZhzyService.paasMaxByLibra(area, police,day);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(zhzyMap);
    }

    @ApiOperation(value = "Elasticsearch资源峰值")
    @RequestMapping(value = "/paasMaxByElasticsearch",method = RequestMethod.GET)
    public R paasMaxByElasticsearch(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police,@RequestParam(value = "day") String day,@RequestParam(value = "clusterName") String clusterName){
        Map<String,Double> zhzyMap = new HashMap<>();
        try {
            zhzyMap = paasZhzyService.paasMaxByElasticsearch(area, police,day,clusterName);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(zhzyMap);
    }

    @ApiOperation(value = "Redis资源峰值")
    @RequestMapping(value = "/paasMaxByRedis",method = RequestMethod.GET)
    public R paasMaxByRedis(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police,@RequestParam(value = "day") String day){
        Map<String,Double> zhzyMap = new HashMap<>();
        try {
            zhzyMap = paasZhzyService.paasMaxByRedis(area, police,day);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(zhzyMap);
    }

    @ApiOperation(value = "Yarn资源使用情况")
    @RequestMapping(value = "/situationByYarn",method = RequestMethod.GET)
    public R situationByYarn(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police,@RequestParam(value = "day") String day){
        List<PaasZhzy> list = new ArrayList<>();
        try {
            list = paasZhzyService.situationByYarn(area, police,day);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(list);
    }

    @ApiOperation(value = "Libra资源使用情况")
    @RequestMapping(value = "/situationByLibra",method = RequestMethod.GET)
    public R situationByLibra(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police,@RequestParam(value = "day") String day){
        List<PaasZhzy> list = new ArrayList<>();
        try {
            list = paasZhzyService.situationByLibra(area, police,day);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(list);
    }

    @ApiOperation(value = "Elasticsearch资源使用情况")
    @RequestMapping(value = "/situationByElasticsearch",method = RequestMethod.GET)
    public R situationByElasticsearch(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police,@RequestParam(value = "day") String day,@RequestParam(value = "clusterName") String clusterName){
        List<PaasZhzy> list = new ArrayList<>();
        try {
            list = paasZhzyService.situationByElasticsearch(area, police,day,clusterName);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(list);
    }

    @ApiOperation(value = "Redis资源使用情况")
    @RequestMapping(value = "/situationByRedis",method = RequestMethod.GET)
    public R situationByRedis(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police,@RequestParam(value = "day") String day){
        List<PaasZhzy> list = new ArrayList<>();
        try {
            list = paasZhzyService.situationByRedis(area, police,day);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(list);
    }


    @ApiOperation(value = "paas资源yarn")
    @RequestMapping(value = "/v1/getPaasYarnResource",method = RequestMethod.GET)
    public R getPaasYarnResource(@RequestParam(value = "appName") String appName,@RequestParam(value = "area" ,required = false) String area,@RequestParam(value = "police" ,required = false) String police,
                                 @RequestParam(value = "day") Integer day){
        Map map=new HashMap();
        try {
            List<PaasZhzy>  paasYarnResource = paasZhzyService.getPaasYarnResource(appName, area, police, day);
            map.put("paasYarnResource",paasYarnResource);
            PaasZhzy maxCpu = paasZhzyService.maxYarnCpu(appName, area, police, day);
            PaasZhzy  maxMemory = paasZhzyService.maxYarnMemory(appName, area, police, day);
            PaasZhzy totalYarnCpu = paasZhzyService.totalYarnCpu(appName, area, police, day);
            if(null!=maxCpu){
                map.put("maxCpu",maxCpu.getCpuUsage());
            }else{
                map.put("maxCpu",0);
            }

            if(null!=maxMemory){
                map.put("maxMemory",maxMemory.getMemoryUsage());
            }else{
                map.put("maxMemory",0);
            }
            if(null!=totalYarnCpu){
                map.put("totalYarnCpu",totalYarnCpu.getCpuTotal());
                map.put("totalYarnMermory",totalYarnCpu.getMemoryTotal());
            }else{
                map.put("totalYarnCpu",0);
                map.put("totalYarnMermory",0);
            }



        }catch (Exception e) {
            return R.error();
        }
        return R.ok(map);
    }
    @ApiOperation(value = "paas资源Libra")
    @RequestMapping(value = "/v1/getPaasLibraResource",method = RequestMethod.GET)
    public R getPaasLibraResource(@RequestParam(value = "appName") String appName,@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police,
                                  @RequestParam(value = "day") Integer day){
        Map map=new HashMap();

        try {
            List<PaasZhzy>  paasLibraResource = paasZhzyService.getPaasLibraResource(appName, area, police, day);
            map.put("paasLibraResource",paasLibraResource);
            PaasZhzy maxCpu = paasZhzyService.maxLibraCpu(appName, area, police, day);
            PaasZhzy  maxMemory = paasZhzyService.maxLibraMemary(appName, area, police, day);
            PaasZhzy  maxStorage = paasZhzyService.maxLibraStorage(appName, area, police, day);
            PaasZhzy totalLibraCpu = paasZhzyService.totalLibraMemary(appName, area, police, day);
            if(null!=maxCpu){
                map.put("maxCpu",maxCpu.getCpuUsage());
            }else{
                map.put("maxCpu",0);
            }
            map.put("maxCpu",maxCpu.getCpuUsage());
            if(null!=maxMemory){
                map.put("maxMemory",maxMemory.getMemoryUsage());
            }else{
                map.put("maxMemory",0);
            }
            if(null!=totalLibraCpu){
                map.put("totalLibraCpu",totalLibraCpu.getCpuTotal());
                map.put("totalLibraMemory",totalLibraCpu.getMemoryTotal());
                map.put("totalLibraStorage",totalLibraCpu.getStorageTotal());
            }else{
                map.put("totalLibraCpu",0);
                map.put("totalLibraMemory",0);
                map.put("totalLibraStorage",0);
            }
            if(null!=maxStorage){
                map.put("maxStorage",maxStorage.getStorageUsage());
            }else{
                map.put("maxStorage",0);
            }

        }catch (Exception e) {
            return R.error();
        }
        return R.ok(map);
    }
    @ApiOperation(value = "paas资源ES")
    @RequestMapping(value = "/v1/getPaasEsResource",method = RequestMethod.GET)
    public R getPaasEsResource(@RequestParam(value = "appName") String appName,@RequestParam(value = "area" ,required = false) String area,@RequestParam(value = "police" ,required = false) String police,
                               @RequestParam(value = "day") Integer day){
        Map map=new HashMap();

        try {
            List<PaasZhzy>  paasEsResource = paasZhzyService.getPaasEsResource(appName, area, police, day);
            map.put("paasEsResource",paasEsResource);
            PaasZhzy maxCpu = paasZhzyService.maxEsCpu(appName, area, police, day);
            Double  maxMemory = paasZhzyService.maxEsMemary(appName, area, police, day);

            PaasZhzy totalEsCpu = paasZhzyService.totalEsMemary(appName, area, police, day);

            PaasZhzy  maxStorage = paasZhzyService.maxEsStorage(appName, area, police, day);
            PaasZhzy cpuTotal = paasZhzyService.cpuTotal(appName, area, police, day);
            if(cpuTotal!=null){
                map.put("cpuToal",cpuTotal.getElasticsearchCpuTotal());
                map.put("cluserNameEn",cpuTotal.getSuperClusterEn());
                map.put("cluserNameCn",cpuTotal.getSuperClusterCn());
                map.put("cluserNumber",cpuTotal.getElasticsearchHostsNumber());
            }

            if(null!=maxCpu){
                map.put("maxCpu",maxCpu.getElasticsearchCpuUsage());
            }else{
                map.put("maxCpu",0);
            }
            map.put("maxMemory",maxMemory);
            if(null!=totalEsCpu){
                map.put("totalEsCpu",totalEsCpu.getElasticsearchCpuTotal());
                map.put("totalEsMemory",totalEsCpu.getElasticsearchMemoryTotal());
                map.put("totalEsStorage",totalEsCpu.getStorageTotal());
            }else{
                map.put("totalEsCpu",0);
                map.put("totalEsMemory",0);
                map.put("totalEsStorage",0);
            }
            if(null!=maxCpu){
                map.put("maxStorage",maxStorage.getStorageUsage());
            }else{
                map.put("maxStorage",0);
            }

        }catch (Exception e) {
            return R.error();
        }
        return R.ok(map);
    }

    @ApiOperation(value = "paas资源redis")
    @RequestMapping(value = "/v1/getPaasRedisResource",method = RequestMethod.GET)
    public R getPaasRedisResource(@RequestParam(value = "appName") String appName,@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police,
                                  @RequestParam(value = "day") Integer day){
        Map map=new HashMap();

        try {
            List<PaasZhzy>  paasRedisResource = paasZhzyService.getPaasRedisResource(appName, area, police, day);
            map.put("paasRedisResource",paasRedisResource);
            PaasZhzy  maxMemory = paasZhzyService.maxRedisMemary(appName, area, police, day);
            if(null!=maxMemory){
                map.put("maxMemory",maxMemory.getRedisMemoryUsage());
            }else{
                map.put("maxMemory",0);
            }
            PaasZhzy totalRedisMemary = paasZhzyService.totalRedisMemary(appName, area, police, day);
            if(null!=totalRedisMemary){
                map.put("totalRedisMemary",totalRedisMemary.getRedisMemoryTotal());
            }else{
                map.put("totalRedisMemary",0);
            }

        }catch (Exception e) {
            return R.error();
        }
        return R.ok(map);
    }




}

