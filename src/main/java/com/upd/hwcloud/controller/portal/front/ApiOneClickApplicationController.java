package com.upd.hwcloud.controller.portal.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.*;
import com.upd.hwcloud.service.application.IShoppingCartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(description = "门户网站一键申请")
@RestController
@RequestMapping("/api/oneclick")
public class ApiOneClickApplicationController {

    @Autowired
    private ISaasService saasService;
    @Autowired
    private IPaasService paasService;
    @Autowired
    private IIaasService iaasService;
    @Autowired
    private IIaasNewService iaasNewService;
    @Autowired
    private IIaasSubpageService iaasSubpageService;
    @Autowired
    private IPaasSubpageService paasSubpageService;
    @Autowired
    private ISaasSubpageService saasSubpageService;

    @Autowired
    private IShoppingCartService shoppingCartService;

    @Value("${onclick.paas.bigdata.component.id}")
    private String bigDataComponentId;

    @ApiOperation("SaaS应用标签")
    @ApiImplicitParam(name="typeId", value="分类类型id", required=true,
            paramType="query", dataType="String")
    @RequestMapping(value = "/saas/label", method = RequestMethod.GET)
    public R saasLabel(@RequestParam(required = false) String typeId) {
        List<Map<String, Object>> label = saasService.getLabelWithCount(typeId);

        LambdaQueryWrapper<Saas> qw = new QueryWrapper<Saas>().lambda()
                .eq(Saas::getStatus, ReviewStatus.ONLINE.getCode())
                .eq(Saas::getCanApplication, 1);
        if (StringUtils.isNotEmpty(typeId)) {
            qw.eq(Saas::getSubType, typeId);
        }
        int count = saasService.count(qw);
        Map<String, Object> result = new HashMap<>(2);
        result.put("count", count);
        result.put("label", label);
        return R.ok(result);
    }

    @ApiOperation("SaaS应用分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="form", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="form", dataType="String"),
            @ApiImplicitParam(name="label", value="标签", paramType="form", dataType="String"),
            @ApiImplicitParam(name="typeId", value="分类类型id", paramType="form", dataType="String"),
            @ApiImplicitParam(name="keyword", value="搜索关键字", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/saas/page", method = {RequestMethod.GET, RequestMethod.POST})
    public R saasPage(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                      @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                      @RequestParam(required = false) String label,
                          @RequestParam(required = false) String typeId,
                          @RequestParam(required = false) String keyword) {
        IPage<Saas> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        IPage<Saas> result = saasService.getOneClickPage(page, typeId, label, keyword);
        return R.ok(result);
    }

    @ApiOperation("SaaS应用列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="keyword", value="搜索关键字", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/saas/list", method = {RequestMethod.GET, RequestMethod.POST})
    public R saasList(@RequestParam(required = true) String keyword) {
        List<Saas> result = saasService.saasList(keyword);
        return R.ok(result);
    }

    @ApiOperation("SaaS服务分页")
    @RequestMapping(value = "/saas/service/page",method = RequestMethod.GET)
    public R saasServicePage(@RequestParam(defaultValue = "1") Long pageNum,@RequestParam(defaultValue = "20") Long pageSize,String name){
        IPage<Saas> page = new Page<>(pageNum,pageSize);
        if(StringUtils.isNotEmpty(name)){
            name = "%"+name.trim()+"%";
            page = saasService.page(page,new QueryWrapper<Saas>().lambda().eq(Saas::getServiceFlag,1).eq(Saas::getStatus,2).eq(Saas::getCanApplication,1).like(Saas::getName,name));
        }else{
            page = saasService.page(page,new QueryWrapper<Saas>().lambda().eq(Saas::getServiceFlag,1).eq(Saas::getStatus,2).eq(Saas::getCanApplication,1));
        }
        return R.ok(page);
    }

    @ApiOperation("SaaS服务分页-V2")
    @RequestMapping(value = "/v2/saas/service/page",method = RequestMethod.GET)
    public R v2SaasServicePage(@LoginUser User user,@RequestParam(defaultValue = "1") Long pageNum,@RequestParam(defaultValue = "20") Long pageSize,String name){
        IPage<Saas> page = new Page<>(pageNum,pageSize);
        if(StringUtils.isNotEmpty(name)){
            name = "%"+name.trim()+"%";
            page = saasService.page(page,new QueryWrapper<Saas>().lambda().eq(Saas::getServiceFlag,1).eq(Saas::getStatus,2).eq(Saas::getCanApplication,1).like(Saas::getName,name));
        }else{
            page = saasService.page(page,new QueryWrapper<Saas>().lambda().eq(Saas::getServiceFlag,1).eq(Saas::getStatus,2).eq(Saas::getCanApplication,1));
        }
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(new QueryWrapper<ShoppingCart>().lambda()
                                                    .eq(ShoppingCart::getCreatorIdCard,user.getIdcard())
                                                    .eq(ShoppingCart::getResourceType, ResourceType.SAAS_SERVICE.getCode())
                                                    .isNotNull(ShoppingCart::getDsId));
        List<Saas> records = page.getRecords();
        if(CollectionUtils.isNotEmpty(shoppingCartList)){
            List<String> apiGuidList = shoppingCartList.stream().map(ShoppingCart::getDsId).distinct().collect(Collectors.toList());
            for(Saas saas:records){
                if(apiGuidList.contains(saas.getId())){
                    //如果购物车类存在，标记不能添加到购物车中
                    saas.setCanAddShoppingCart(false);
                }
            }
        }
        return R.ok(page);
    }

    @ApiOperation("PaaS服务标签")
    @ApiImplicitParam(name="typeId", value="分类类型id", required=true,
            paramType="query", dataType="String")
    @RequestMapping(value = "/paas/label", method = RequestMethod.GET)
    public R paasLabel(@RequestParam(required = false) String typeId) {
        List<Map<String, Object>> label = paasService.getLabelWithCount(typeId);

        LambdaQueryWrapper<Paas> qw = new QueryWrapper<Paas>().lambda()
                .eq(Paas::getStatus, ReviewStatus.ONLINE.getCode())
                .eq(Paas::getApplicationShow,1)
                .eq(Paas::getCanApplication, 1);
        if (StringUtils.isNotEmpty(typeId)) {
            qw.eq(Paas::getSubType, typeId);
        }
        int count = paasService.count(qw);
        Map<String, Object> result = new HashMap<>(2);
        result.put("count", count);
        result.put("label", label);
        return R.ok(result);
    }

    @ApiOperation("PaaS服务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="label", value="标签", paramType="form", dataType="String"),
            @ApiImplicitParam(name="typeId", value="分类类型id", paramType="form", dataType="String"),
            @ApiImplicitParam(name="keyword", value="搜索关键字", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/paas/list", method = {RequestMethod.GET, RequestMethod.POST})
    public R paasList(@RequestParam(required = false) String label,
                          @RequestParam(required = false) String typeId,
                          @RequestParam(required = false) String keyword) {
        List<Paas> result = paasService.getOneClickPage(typeId, label, keyword);
        return R.ok(result);
    }

    @ApiOperation("PaaS大数据组件信息")
    @RequestMapping(value = "/paas/bigdata-component/info",method = RequestMethod.GET)
    public R paasBigdataComponentInfo(){
        Paas bigdataComponent = paasService.getById(bigDataComponentId);
        if(bigdataComponent == null){
            return R.error("找不到大数据组件");
        }
        Map<String,String> info = Maps.newHashMapWithExpectedSize(7);
        info.put("id", bigdataComponent.getId());
        info.put("FORM_NUM",bigdataComponent.getFormNum());
        return R.ok(info);
    }

    @ApiOperation("IaaS服务标签")
    @RequestMapping(value = "/iaas/label", method = RequestMethod.GET)
    public R iaasLabel() {
        List<Map<String, Object>> label = iaasService.getLabelWithCount();

        LambdaQueryWrapper<Iaas> qw = new QueryWrapper<Iaas>().lambda()
                .eq(Iaas::getStatus, ReviewStatus.ONLINE.getCode())
                .eq(Iaas::getCanApplication, 1);
        int count = iaasService.count(qw);
        Map<String, Object> result = new HashMap<>(2);
        result.put("count", count);
        result.put("label", label);
        return R.ok(result);
    }

    @ApiOperation("IaaS服务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="label", value="标签", paramType="form", dataType="String"),
            @ApiImplicitParam(name="keyword", value="搜索关键字", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/iaas/list", method = {RequestMethod.GET, RequestMethod.POST})
    public R iaasList(@RequestParam(required = false) String label,
                      @RequestParam(required = false) String keyword) {
        List<Iaas> result = iaasService.getOneClickPage(label, keyword);
        return R.ok(result);
    }
    @ApiOperation("IaaS服务标签(新)")
    @RequestMapping(value = "/iaasNew/label", method = RequestMethod.GET)
    public R iaasNewLabel() {
        List<Map<String, Object>> label = iaasNewService.getLabelWithCount();

        LambdaQueryWrapper<IaasNew> qw = new QueryWrapper<IaasNew>().lambda()
                .eq(IaasNew::getStatus, ReviewStatus.ONLINE.getCode())
                .eq(IaasNew::getCanApplication, 1);
        int count = iaasNewService.count(qw);
        Map<String, Object> result = new HashMap<>(2);
        result.put("count", count);
        result.put("label", label);
        return R.ok(result);
    }
    @ApiOperation("IaaS服务列表（新）")
    @ApiImplicitParams({
            @ApiImplicitParam(name="keyword", value="搜索关键字", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/iaasNew/list", method = {RequestMethod.GET, RequestMethod.POST})
    public R iaasNewList(
             @RequestParam(required = false) String label,
                      @RequestParam(required = false) String keyword) {
        List<IaasNew> result = iaasNewService.getOneClickPage(label, keyword);
        return R.ok(result);
    }

    @ApiOperation("iaas服务二级页面详情")
    @ApiImplicitParam(name="iaasId", value="服务id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "iaasSubpage/{iaasId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R detail(@LoginUser User user, @PathVariable String iaasId) {
        IaasSubpage iaas = iaasSubpageService.getDetail(iaasId);
        R r =  R.ok(iaas);
        r.put("service",iaasNewService.getById(iaasId));
        return r;
    }
    @ApiOperation("paas服务二级页面详情")
    @ApiImplicitParam(name="paasId", value="服务id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "paasSubpage/{paasId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R paasSubpageDetail(@LoginUser User user, @PathVariable String paasId) {
        PaasSubpage iaas = paasSubpageService.getDetail(paasId);
        R r =  R.ok(iaas);
        r.put("service",paasService.getById(paasId));
        return r;
    }

    @ApiOperation("saas服务二级页面详情")
    @ApiImplicitParam(name="saasId", value="服务id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "saasSubpage/{saasId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R saasSubpageDetail(@LoginUser User user, @PathVariable String saasId) {
        SaasSubpage saasSubpage = saasSubpageService.getDetail(saasId);
        R r =  R.ok(saasSubpage);
        Saas saas = saasService.getById(saasId);
        r.put("service", saas);
        saasService.updateViewCountById(saasId);
        return r;
    }
}
