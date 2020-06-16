package com.upd.hwcloud.controller.backend.workbench;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.dto.DaasApplicationExt;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.dto.ResourceCount;
import com.upd.hwcloud.bean.entity.SaasApplication;
import com.upd.hwcloud.bean.entity.SaasRegister;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.IaasTxyfw;
import com.upd.hwcloud.bean.entity.application.IaasTxyfwImpl;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.workbench.*;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.AreaPoliceCategoryUtils;
import com.upd.hwcloud.common.utils.OkHttpUtils;
import com.upd.hwcloud.service.ISaasApplicationService;
import com.upd.hwcloud.service.ISaasRegisterService;
import com.upd.hwcloud.service.IServicePublishService;
import com.upd.hwcloud.service.application.IApplicationInfoService;
import com.upd.hwcloud.service.application.IIaasTxyfwService;
import com.upd.hwcloud.service.application.IIaasYzmyzyService;
import com.upd.hwcloud.service.factory.WorkbenchFactory;
import com.upd.hwcloud.service.workbench.Workbench;
import com.upd.hwcloud.service.workbench.impl.CommonHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "工作台")
@RestController
@RequestMapping("/workbench")
public class WorkbenchController {

    @Autowired
    private IApplicationInfoService applicationInfoService;
    @Autowired
    private ISaasApplicationService saasApplicationService;

    @Autowired
    private WorkbenchFactory workbenchFactory;

    @Autowired
    private IIaasTxyfwService iaasTxyfwService;

    @Autowired
    private IServicePublishService servicePublishService;

    @Autowired
    private ISaasRegisterService saasRegisterService;

    @Autowired
    private IIaasYzmyzyService iaasYzmyzyService;

    /**
     * 警种全称获取
     */

    @RequestMapping("/getFullPoliceCategoryName")
    public R getFullPoliceName(String name){
        if("缉私".equals(name)){
            name = "打私";
        }
        String fullName =AreaPoliceCategoryUtils.getFullPoliceName(name);
        return R.ok(fullName);
    }


    /**
     * 非租户返回当前用户的
     * @param resourceType
     * @param queryVO
     * @param user
     * @return
     */
    @ApiOperation("租户资源统计")
    @RequestMapping("/tenant/{resourceType}/statistics")
    public R tenantStatistics(@PathVariable String resourceType, QueryVO queryVO, @LoginUser User user){

            Workbench workbench = workbenchFactory.getWorkbench(resourceType);
            List<GeneralDTO>  useResource = workbench.getUseResource(user,queryVO);
            return R.ok(useResource);
    }

    @ApiOperation("新-资源服务统计分页列表")
    @RequestMapping(value = "/resource/service/{resourceType}/page",method = {RequestMethod.GET,RequestMethod.POST})
    public R resourcePage(@RequestParam(required = false, defaultValue = "1")Long pageNum,@RequestParam(required = false, defaultValue = "20")Long pageSize,@PathVariable String resourceType,@LoginUser User user,String serviceName,String appName){
        Workbench workbench = workbenchFactory.getWorkbench(resourceType);
        IPage<ResourceOverviewVO> page = workbench.resourcePage(pageNum,pageSize,user,serviceName,appName);
        return  R.ok(page);
    }

    @ApiOperation("新-资源服务统计概览")
    @RequestMapping(value = "/resource/service/{resourceType}/overview",method = {RequestMethod.GET,RequestMethod.POST})
    public R resourceOverview(@PathVariable String resourceType,@LoginUser User user){
        Workbench workbench = workbenchFactory.getWorkbench(resourceType);
        HashMap<String,Long> res = workbench.resourceOverview(user);
        return  R.ok(res);
    }


    @ApiOperation("新-资源应用统计分页")
    @RequestMapping(value = "/resource/application/page",method = {RequestMethod.GET,RequestMethod.POST})
    public R resourceApplicationPage(@RequestParam(required = false, defaultValue = "1")Long pageNum,@RequestParam(required = false, defaultValue = "20")Long pageSize,@LoginUser User user,String appName){
        IPage<ResourceOverviewVO> page = saasApplicationService.getWorkbenchNewPage(pageNum,pageSize,user,appName);
        return R.ok(page);
    }

    @ApiOperation("新-资源应用统计概览")
    @RequestMapping(value = "/resource/application/overview",method = {RequestMethod.GET,RequestMethod.POST})
    public R resourceApplicationOverview(@LoginUser User user){
       HashMap<String,Long> res = saasApplicationService.getWorkbenchOverview(user);
        return R.ok(res);
    }



    @ApiOperation("新-服务发布总览")
    @RequestMapping("/public/overview")
    public R publicOverview(@PathVariable String resourceType, @RequestParam(defaultValue = "1") Long pageNum,@RequestParam(defaultValue = "20") Long pageSize,QueryVO queryVO, @LoginUser User user){

        IPage<ResourceOverviewVO> page = servicePublishService.getResourcePage(pageNum,pageSize,resourceType,queryVO,user);
        return  R.ok(page);
    }

    @ApiOperation("新-服务发布分页")
    @RequestMapping("/public/{resourceType}/statistics")
    public R publicStatistics(@PathVariable String resourceType, @RequestParam(defaultValue = "1") Long pageNum,@RequestParam(defaultValue = "20") Long pageSize,QueryVO queryVO, @LoginUser User user){

        IPage<ResourceOverviewVO> page = servicePublishService.getResourcePage(pageNum,pageSize,resourceType,queryVO,user);
        return  R.ok(page);
    }

    @ApiOperation("查询所有应用名称")
    @RequestMapping(value = "/appName", method = RequestMethod.GET)
    public R appName(@LoginUser User user) {
        List<String> appNameList = applicationInfoService.getAppNameList(user);
        return R.ok(appNameList);
    }

    @ApiOperation("IAAS资源总览")
    @RequestMapping(value = "/iaas/res", method = RequestMethod.GET)
    public R iaasRes(@LoginUser User user, @RequestParam(required = false) String appName) {
        List<ResourceCount> appNameList = applicationInfoService.getIaasPaasUseRes(ResourceType.IAAS, user, appName);
        return R.ok(appNameList);
    }

    @ApiOperation("PAAS资源总览")
    @RequestMapping(value = "/paas/res", method = RequestMethod.GET)
    public R paasRes(@LoginUser User user, @RequestParam(required = false) String appName) {
        List<ResourceCount> appNameList = applicationInfoService.getIaasPaasUseRes(ResourceType.PAAS, user, appName);
        return R.ok(appNameList);
    }

    @ApiOperation("DAAS资源总览")
    @RequestMapping(value = "/daas/res", method = RequestMethod.GET)
    public R daasRes(@LoginUser User user, @RequestParam(required = false) String appName) {
        Integer count = applicationInfoService.getDaasUseRes(user, appName);
        return R.ok(count);
    }

    @ApiOperation("SAAS资源总览")
    @RequestMapping(value = "/saas/res", method = RequestMethod.GET)
    public R saasRes(@LoginUser User user, @RequestParam(required = false) String appName) {
        Integer count = saasApplicationService.getSaasUseRes(user, appName);
        return R.ok(count);
    }

    @ApiOperation("IAAS资源申请")
    @RequestMapping(value = "/iaas/apply", method = RequestMethod.GET)
    public R iaasApply(@LoginUser User user,QueryVO queryVO) {
        return R.ok(count(ResourceType.IAAS, user,queryVO));
    }


    @ApiOperation("申请单-IPD状态对应分页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path",name = "resourceType",value = "资源类型(saas,daas,paas,iaas)",required = true,dataType = "String"),
            @ApiImplicitParam(paramType="path",name = "status",value = "0:待审核 1:待实施 2:驳回 3:使用中",required = true,dataType = "String"),
            //@ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            //@ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
           // @ApiImplicitParam(name="creatorName", value="申请人名或订单号", dataType="String"),
            //@ApiImplicitParam(name="startDate", value="开始日期 yyyy-MM-dd", dataType="String"),
            //@ApiImplicitParam(name="endDate", value="结束日期 yyyy-MM-dd", dataType="String")
    })
    @RequestMapping(value = "/{resourceType}/apply/{status}",method = {RequestMethod.GET,RequestMethod.POST})
    public R applyPageOfIPD(@LoginUser User user,QueryVO queryVO,@PathVariable String resourceType,@PathVariable String status,@RequestParam(required = false, defaultValue = "1") Integer pageNum,@RequestParam(required = false, defaultValue = "20") Integer pageSize){

        if(user==null){
            return R.error("请登录");
        }
        Workbench workbench = workbenchFactory.getWorkbench(resourceType);
        //路径上的status值也会赋值给queryVoid，可不传
        IPage page = workbench.applyPage(pageNum,pageSize,queryVO,status,user);
        return R.ok(page);

    }

    @ApiOperation("申请单-服务发布状态对应分页")
    @ApiImplicitParam(paramType="path",name = "status",value = "0:待审核 1:待实施 2:驳回 3:使用中",required = true,dataType = "String")
    @RequestMapping(value = "/service/publish/apply/{status}",method = {RequestMethod.GET,RequestMethod.POST})
    public R applyPageOfPublish(@LoginUser User user,QueryVO queryVO,@PathVariable String status,@RequestParam(required = false, defaultValue = "1") Integer pageNum,@RequestParam(required = false, defaultValue = "20") Integer pageSize){

        if(user==null){
            return R.error("请登录");
        }
        IPage<ServicePublish> page = new Page<>(pageNum,pageSize);
        page  = servicePublishService.getWorkbenchApplyPage(user,page,queryVO);
        return R.ok(page);
    }

    @ApiOperation("申请单-应用注册状态对应分页")
    @ApiImplicitParam(paramType="path",name = "status",value = "0:待审核 1:待实施 2:驳回 3:使用中",required = true,dataType = "String")
    @RequestMapping(value = "/application/register/apply/{status}",method = {RequestMethod.GET,RequestMethod.POST})
    public R applyPageOfRegister(@LoginUser User user,QueryVO queryVO,@PathVariable String status,@RequestParam(required = false, defaultValue = "1") Integer pageNum,@RequestParam(required = false, defaultValue = "20") Integer pageSize){

        //路径上的status同时放在了queryVO里
        if(user==null){
            return R.error("请登录");
        }
        IPage<SaasRegister> page = new Page<>(pageNum,pageSize);
        page  = saasRegisterService.getWorkbenchApplyPage(user,page,queryVO);
        return R.ok(page);
    }


    @ApiOperation("DAAS资源申请")
    @RequestMapping(value = "/daas/apply", method = RequestMethod.GET)
    public R daasApply(@LoginUser User user,QueryVO queryVO) {
        return R.ok(count(ResourceType.DAAS, user,queryVO));
    }

    @ApiOperation("DAAS资源申请")
    @RequestMapping(value = "/paas/apply", method = RequestMethod.GET)
    public R paasApply(@LoginUser User user,QueryVO queryVO) {
        return R.ok(count(ResourceType.PAAS, user,queryVO));
    }

    @ApiOperation("SAAS资源申请")
    @RequestMapping(value = "/saas/apply", method = RequestMethod.GET)
    public R saasApply(@LoginUser User user,QueryVO queryVO) {
        queryVO.setStatus("0");
        int reviewCount = saasApplicationService.getWorkbenchApplyCount(user,queryVO);
        queryVO.setStatus("1");
        int implCount = saasApplicationService.getWorkbenchApplyCount(user,queryVO);
        queryVO.setStatus("2");
        int rejectCount = saasApplicationService.getWorkbenchApplyCount(user,queryVO);
        queryVO.setStatus("3");
        int useCount = saasApplicationService.getWorkbenchApplyCount(user,queryVO);
        Map<String, Integer> data = Maps.newHashMap();
        data.put("reviewCount", reviewCount);
        data.put("implCount", implCount);
        data.put("rejectCount", rejectCount);
        data.put("useCount", useCount);
        return R.ok(data);
    }

    @ApiOperation("服务发布申请统计")
    @RequestMapping("/servicePublish/apply")
    public R servicePublishApply(@LoginUser User user,QueryVO queryVO){
        return servicePublishService.servicePublishApply(user,queryVO);
    }


    @ApiOperation("应用注册申请统计")
    @RequestMapping("/saasRegister/apply")
    public R saasRegisterRegisterApply(@LoginUser User user,QueryVO queryVO){
        return saasRegisterService.applicationRegisterApply(user,queryVO);
    }


    @ApiOperation("应用注册一级页面分页")
    @RequestMapping(value = "/saasRegister/statistic/page",method = {RequestMethod.GET,RequestMethod.POST})
    public R saasRegisterStatisticPage(@LoginUser User user, @RequestParam(required = false, defaultValue = "1")Long pageNum,
                                   @RequestParam(required = false, defaultValue = "20")Long pageSize,
                                   @RequestParam(required = false) String creatorName,
                                   @RequestParam(required = false) String serviceName){
        IPage<SaasStatisticsVO> page = saasRegisterService.saasRegisterStatisticsPage(user,pageNum,pageSize,creatorName,serviceName);
        return  R.ok(page);
    }

    @ApiOperation("新-应用注册-一级页面分页")
    @RequestMapping(value = "/saasRegister/resource/page",method = {RequestMethod.GET,RequestMethod.POST})
    public R saasRegisterResourcePage(@LoginUser User user, @RequestParam(required = false, defaultValue = "1")Long pageNum,
                                      @RequestParam(required = false, defaultValue = "20")Long pageSize,
                                      @RequestParam(required = false) String appName){

        IPage<ResourceOverviewVO> page = saasRegisterService.getWorkbenchResourcePag(user,pageNum,pageSize,appName);
        return R.ok(page);

    }

    @ApiOperation("应用注册一级页面总览")
    @RequestMapping(value = "/saasRegister/statistic/overview",method = RequestMethod.GET)
    public R saasRegisterStatisticOverview(@LoginUser User user){

        return  R.ok(saasRegisterService.saasRegisterStatisticsOverview(user));
    }

    @ApiOperation("新-应用注册一级页面总览")
    @RequestMapping(value = "/saasRegister/resource/overview",method = RequestMethod.GET)
    public R saasRegisterResourceOverview(@LoginUser User user){

        return  R.ok(saasRegisterService.getWorkbenchResourceOverview(user));
    }



    private Map<String, Integer> count(ResourceType resourceType, User user,QueryVO queryVO) {
        int reviewCount = applicationInfoService.getReviewCount(resourceType, user,queryVO);
        int implCount = applicationInfoService.getImplCount(resourceType, user,queryVO);
        int rejectCount = applicationInfoService.getRejectCount(resourceType, user,queryVO);
        int useCount = applicationInfoService.getUseCount(resourceType, user,queryVO);
        Map<String, Integer> data = Maps.newHashMap();
        data.put("reviewCount", reviewCount);
        data.put("implCount", implCount);
        data.put("rejectCount", rejectCount);
        data.put("useCount", useCount);
        return data;
    }


    /**
     * 从实施表单获取虚拟机列表
     * @param user
     * @param pageNum
     * @param pageSize
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "/tenant/iaas/ecs",method = RequestMethod.GET)
    public R ecsPage(@LoginUser User user,@RequestParam(required = false, defaultValue = "1") Integer pageNum,@RequestParam(required = false, defaultValue = "20") Integer pageSize,QueryVO queryVO){
        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
            queryVO.setArea(user.getTenantArea());
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);
        IPage<IaasTxyfwImpl> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = iaasTxyfwService.getEcsWorkbenchPage(page,param);
        return R.ok(page);

    }


    @RequestMapping(value = "/tenant/iaas/ecs/{ecsId}",method = RequestMethod.GET)
    public R ecsDetail(@PathVariable("ecsId") String ecsId){
        //停用
       EcsVO ecsVO = new EcsVO();


        return R.ok(ecsVO);
    }

    @RequestMapping(value = "/tenant/iaas/ecs/{ecsId}/recent",method = RequestMethod.GET)
    public R ecsRecent(@PathVariable("ecsId") String ecsId){
        List<EcsVO> ecsVOList = iaasTxyfwService.getEcsRecent(ecsId);
        return R.ok(ecsVOList);
    }

    @ApiOperation("工作台-资源总览-云桌面分页")
    @RequestMapping(value = "/tenant/iaas/cloudDesktop")
    public R cloudDesktop(@LoginUser User user,@RequestParam(defaultValue = "1") Long pageNum,@RequestParam(defaultValue = "20") Long pageSize,QueryVO queryVO){
        IPage<CloudDesktopVO> page = iaasYzmyzyService.getResourcePage(pageNum,pageSize,user,queryVO);
        return R.ok(page);
    }


    @ApiOperation("工作台IAAS分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
            @ApiImplicitParam(name="startDate", value="开始日期 yyyy-MM-dd", paramType="query", dataType="String"),
            @ApiImplicitParam(name="endDate", value="结束日期 yyyy-MM-dd", paramType="query", dataType="String"),
            @ApiImplicitParam(name="serviceName", value="服务名称", paramType="query", dataType="String"),
            @ApiImplicitParam(name="appName", value="应用名称", paramType="query", dataType="String"),
            @ApiImplicitParam(name="orderNumber", value="单号", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/iaas/res/page", method = RequestMethod.GET)
    public R iaasResPage(@LoginUser User user,
                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String serviceName,
                  @RequestParam(required = false) String appName,
                  @RequestParam(required = false) String orderNumber,
                  @RequestParam(required = false) String startDate,
                  @RequestParam(required = false) String endDate) {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("serviceName", serviceName);
        param.put("appName", appName);
        param.put("orderNumber", orderNumber);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("resourceType", ResourceType.IAAS.getCode());

        IPage<ApplicationInfo> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = applicationInfoService.getIaasPaasWorkbenchPage(page, param);
        return R.ok(page);
    }

    @ApiOperation("工作台PAAS分页列表")
    @RequestMapping(value = "/paas/res/page", method = RequestMethod.GET)
    public R paasResPage(@LoginUser User user,
                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String serviceName,
                  @RequestParam(required = false) String appName,
                  @RequestParam(required = false) String orderNumber,
                  @RequestParam(required = false) String startDate,
                  @RequestParam(required = false) String endDate) {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("serviceName", serviceName);
        param.put("appName", appName);
        param.put("orderNumber", orderNumber);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("resourceType", ResourceType.PAAS.getCode());

        IPage<ApplicationInfo> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = applicationInfoService.getIaasPaasWorkbenchPage(page, param);
        return R.ok(page);
    }

    @ApiOperation("工作台DAAS分页列表")
    @RequestMapping(value = "/daas/res/page", method = RequestMethod.GET)
    public R daasResPage(@LoginUser User user,
                         @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                         @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                         @RequestParam(required = false) String serviceName,
                         @RequestParam(required = false) String appName,
                         @RequestParam(required = false) String orderNumber,
                         @RequestParam(required = false) String startDate,
                         @RequestParam(required = false) String endDate) {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("serviceName", serviceName);
        param.put("appName", appName);
        param.put("orderNumber", orderNumber);
        param.put("startDate", startDate);
        param.put("endDate", endDate);

        IPage<DaasApplicationExt> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = applicationInfoService.getDaasWorkbenchPage(page, param);
        return R.ok(page);
    }

    @ApiOperation("工作台SAAS分页列表")
    @RequestMapping(value = "/saas/res/page", method = RequestMethod.GET)
    public R saasResPage(@LoginUser User user,
                         @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                         @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                         @RequestParam(required = false) String serviceName,
                         @RequestParam(required = false) String appName,
                         @RequestParam(required = false) String orderNumber,
                         @RequestParam(required = false) String startDate,
                         @RequestParam(required = false) String endDate) {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("serviceName", serviceName);
        param.put("appName", appName);
        param.put("orderNumber", orderNumber);
        param.put("startDate", startDate);
        param.put("endDate", endDate);

        IPage<SaasApplication> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = saasApplicationService.getWorkbenchPage(page, param);
        return R.ok(page);
    }

}
