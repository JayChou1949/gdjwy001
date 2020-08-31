package com.hirisun.cloud.paas.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.paas.bean.PaasZhzy;
import com.hirisun.cloud.paas.service.IPaasZhzyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * PAAS租户资源 前端控制器
 */
@Api("paas租户资源管理")
@RestController
@RequestMapping("/paas/tenant/resource")
public class PaasTenantResourceController {

    @Autowired
    private IPaasZhzyService paasZhzyService;

    @ApiOperation(value = "PaaS资源管理")
    @GetMapping(value = "/get")
    public QueryResponseResult getPaasZhzy(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police){
    	
        Map<String,PaasZhzy> paasZhzyMap = paasZhzyService.getPaasZhzy(area,police);
        return QueryResponseResult.success(paasZhzyMap);
    }

    @ApiOperation(value = "应用列表")
    @GetMapping(value = "/application/list")
    public QueryResponseResult appFromAreaOrPolice(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police){
    	
        List<String> apps = paasZhzyService.appFromAreaOrPolice(area,police);
        return QueryResponseResult.success(apps);
    }

    @ApiOperation(value = "应用集群概览详细信息")
    @GetMapping(value = "/application/cluster/detail")
    public QueryResponseResult appClusterDetails(@RequestParam(value = "appName") String appName,
    		@RequestParam(value = "clusterName") String clusterName){
    	
        PaasZhzy paasZhzy = paasZhzyService.appClusterDetails(appName,clusterName);
        return QueryResponseResult.success(paasZhzy);
    }

    @ApiOperation(value = "应用集群概览tab")
    @GetMapping(value = "/application/cluster/tabs")
    public QueryResponseResult clusterTabs(@RequestParam(value = "appName") String appName){
        List<String> clusterList = paasZhzyService.clusterTabs(appName);
        return QueryResponseResult.success(clusterList);
    }

    @ApiOperation(value = "地市或警种的集群tabs")
    @GetMapping(value = "/areaOrPolice/cluster/tabs")
    public QueryResponseResult clusterTabsByAreaOrPolice(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police){
    	
        List<String> clusterList = paasZhzyService.clusterTabsByAreaOrPolice(area,police);
        return QueryResponseResult.success(clusterList);
    }

    @ApiOperation(value = "YARN资源总览")
    @GetMapping(value = "/yarn/overview")
    public QueryResponseResult paasOverviewByYarn(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police){
    	
        PaasZhzy paasZhzy = paasZhzyService.paasOverviewByYarn(area, police);
        return QueryResponseResult.success(paasZhzy);
    }

    @ApiOperation(value = "Elasticsearch总览")
    @GetMapping(value = "/elasticsearch/overview")
    public QueryResponseResult paasOverviewByElasticsearch(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police){
    	
        List<PaasZhzy> list = paasZhzyService.paasOverviewByElasticsearch(area, police);
        return QueryResponseResult.success(list);
    }

    @ApiOperation(value = "Redis总览")
    @GetMapping(value = "/redis/overview")
    public QueryResponseResult paasOverviewByRedis(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police){
    	
        PaasZhzy paasZhzy = paasZhzyService.paasOverviewByRedis(area, police);
        return QueryResponseResult.success(paasZhzy);
    }

    @ApiOperation(value = "Libra集群总览")
    @GetMapping(value = "/libra/overview")
    public QueryResponseResult paasOverviewByLibra(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police){
    	
        PaasZhzy paasZhzy = paasZhzyService.paasOverviewByLibra(area, police);
        return QueryResponseResult.success(paasZhzy);
    }

    @ApiOperation(value = "Yarn资源峰值")
    @GetMapping(value = "/yarn/peak")
    public QueryResponseResult paasMaxByYarn(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police,
    		@RequestParam(value = "day") String day){
    	
        Map<String,Double> zhzyMap = paasZhzyService.paasMaxByYarn(area, police,day);
        return QueryResponseResult.success(zhzyMap);
    }

    @ApiOperation(value = "Libra资源峰值")
    @GetMapping(value = "/libra/peak")
    public QueryResponseResult paasMaxByLibra(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police,
    		@RequestParam(value = "day") String day){
    	
        Map<String,Double> zhzyMap = paasZhzyService.paasMaxByLibra(area, police,day);
        return QueryResponseResult.success(zhzyMap);
    }

    @ApiOperation(value = "Elasticsearch资源峰值")
    @GetMapping(value = "/elasticsearch/peak")
    public QueryResponseResult paasMaxByElasticsearch(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police,
    		@RequestParam(value = "day") String day,
    		@RequestParam(value = "clusterName") String clusterName){
    	
        Map<String,Double> zhzyMap = paasZhzyService.paasMaxByElasticsearch(area, police,day,clusterName);
        return QueryResponseResult.success(zhzyMap);
    }

    @ApiOperation(value = "Redis资源峰值")
    @GetMapping(value = "/redis/peak")
    public QueryResponseResult paasMaxByRedis(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police,
    		@RequestParam(value = "day") String day){
    	
        Map<String,Double> zhzyMap = paasZhzyService.paasMaxByRedis(area, police,day);
        return QueryResponseResult.success(zhzyMap);
    }

    @ApiOperation(value = "Yarn资源使用情况")
    @GetMapping(value = "/yarn/use/situation")
    public QueryResponseResult situationByYarn(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police,
    		@RequestParam(value = "day") String day){
    	
        List<PaasZhzy> list = paasZhzyService.situationByYarn(area, police,day);
        return QueryResponseResult.success(list);
    }

    @ApiOperation(value = "Libra资源使用情况")
    @GetMapping(value = "/libra/use/situation")
    public QueryResponseResult situationByLibra(@RequestParam(value = "area",required = false) String area,@RequestParam(value = "police",required = false) String police,@RequestParam(value = "day") String day){
        List<PaasZhzy> list = paasZhzyService.situationByLibra(area, police,day);
        return QueryResponseResult.success(list);
    }

    @ApiOperation(value = "Elasticsearch资源使用情况")
    @GetMapping(value = "/elasticsearch/use/situation")
    public QueryResponseResult situationByElasticsearch(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police,
    		@RequestParam(value = "day") String day,
    		@RequestParam(value = "clusterName") String clusterName){
    	
        List<PaasZhzy> list = paasZhzyService.situationByElasticsearch(area, police,day,clusterName);
        return QueryResponseResult.success(list);
    }

    @ApiOperation(value = "Redis资源使用情况")
    @GetMapping(value = "/redis/use/situation")
    public QueryResponseResult situationByRedis(@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police,
    		@RequestParam(value = "day") String day){
        List<PaasZhzy> list = paasZhzyService.situationByRedis(area, police,day);
        return QueryResponseResult.success(list);
    }


    @ApiOperation(value = "paas资源yarn")
    @GetMapping(value = "/v1/get/yarn")
    public QueryResponseResult getPaasYarnResource(@RequestParam(value = "appName") String appName,
    		@RequestParam(value = "area" ,required = false) String area,
    		@RequestParam(value = "police" ,required = false) String police,
            @RequestParam(value = "day") Integer day){
    	
        Map map=new HashMap();
        
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
        
        return QueryResponseResult.success(map);
    }
    @ApiOperation(value = "paas资源Libra")
    @GetMapping(value = "/v1/get/libra")
    public QueryResponseResult getPaasLibraResource(@RequestParam(value = "appName") String appName,
    		@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police,
            @RequestParam(value = "day") Integer day){
    	
    	Map map=new HashMap();
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

        return QueryResponseResult.success(map);
    }
    @ApiOperation(value = "paas资源ES")
    @GetMapping(value = "/v1/get/es")
    public QueryResponseResult getPaasEsResource(@RequestParam(value = "appName") String appName,
    		@RequestParam(value = "area" ,required = false) String area,
    		@RequestParam(value = "police" ,required = false) String police,
            @RequestParam(value = "day") Integer day){
    	
        Map map=new HashMap();

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

        return QueryResponseResult.success(map);
    }

    @ApiOperation(value = "paas资源redis")
    @GetMapping(value = "/v1/get/redis")
    public QueryResponseResult getPaasRedisResource(@RequestParam(value = "appName") String appName,
    		@RequestParam(value = "area",required = false) String area,
    		@RequestParam(value = "police",required = false) String police,
            @RequestParam(value = "day") Integer day){
        Map map=new HashMap();

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

        return QueryResponseResult.success(map);
    }

}

