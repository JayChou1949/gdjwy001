package com.upd.hwcloud.controller.portal.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.RedisKey;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.AreaPoliceCategoryUtils;
import com.upd.hwcloud.service.*;
import com.upd.hwcloud.service.factory.WorkbenchFactory;
import com.upd.hwcloud.service.workbench.Workbench;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Api(description = "门户网站服务接口")
@RestController
@RequestMapping("/api")
public class FrontController {

    @Autowired
    private ISaasService saasService;
    @Autowired
    private IPaasService paasService;
    @Autowired
    private IIaasNewService iaasService;
    @Autowired
    private IServiceFeedbackService serviceFeedbackService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RestHighLevelClient highLevelClient;
    @Autowired
    private IServiceFeedbackReplyService serviceFeedbackReplyService;
    @Autowired
    private WorkbenchFactory workbenchFactory;
    @Autowired
    private IDictService dictService;
    @Autowired
    private ISubPortalViewService subPortalViewService;


    @ApiOperation("旧版SaaS应用列表")
    @RequestMapping(value = "/saas", method = RequestMethod.GET)
    public R saas() {

        Map<String, Object> data = saasService.getFrontData(0);
        stringRedisTemplate.opsForValue().increment(RedisKey.KEY_APP_VIEW_COUNT, 1L);
        return R.ok(data);
    }

    @ApiOperation("新版SaaS应用列表")
    @RequestMapping(value = "/saas/application", method = RequestMethod.GET)
    public R saasApplication(@RequestParam(required = false,defaultValue = "") String projectName) {

        List<Map<String, Object>> data = saasService.getApplicationFrontData(projectName);
        stringRedisTemplate.opsForValue().increment(RedisKey.KEY_APP_VIEW_COUNT, 1L);
        return R.ok(data);
    }


    @ApiOperation("新SaaS二级列表")
    @RequestMapping(value = "/saas/application/page", method = RequestMethod.GET)
    public R saasPoliceApplication(QueryVO queryVO,@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                 @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        IPage<Saas> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        IPage<Saas> data = saasService.getNewPage(page,queryVO);
        return R.ok(data);
    }
    @ApiOperation("SaaS应用的IPDS服务列表")
    @ApiImplicitParam(paramType="path",name = "name",value = "SaaS应用名",required = true,dataType = "String")
    @RequestMapping(value = "/saas/application/{name}",method = RequestMethod.GET)
    public R serviceOfSaas(@PathVariable String name){
        Workbench daasWorkbench  =workbenchFactory.getWorkbench("daas");
        List<GeneralDTO> daas  = daasWorkbench.getServiceOfSaas(name);

        Workbench saasWorkbench = workbenchFactory.getWorkbench("saas");
        List<GeneralDTO> saas = saasWorkbench.getServiceOfSaas(name);

        Workbench paasWorkbench = workbenchFactory.getWorkbench("paas");
        List<GeneralDTO> paas = paasWorkbench.getServiceOfSaas(name);

        Workbench iaasWorkbench = workbenchFactory.getWorkbench("iaas");
        List<GeneralDTO> iaas =iaasWorkbench.getServiceOfSaas(name);
        Map<String,List<GeneralDTO>> res = Maps.newHashMap();
        res.put("DaaS",daas);
        res.put("SaaS",saas);
        res.put("PaaS",paas);
        res.put("IaaS",iaas);
        return R.ok(res);
    }

    /**
     * 三级页面统计(除应用信息外的数据)
     * @param resourceType
     * @param name 传saas应用Name不传saas应用ID 是因为门户和统计数据只能通过名字关联
     * @return
     */
    @ApiOperation("SaaS应用的IPDS服务统计")
    @RequestMapping(value = "/saas/application/{resourceType}/{name}/statistics",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path",name = "resourceType",value = "资源类型(Saas,Daas,Paas)",required = true,dataType = "String"),
            @ApiImplicitParam(paramType="path",name = "name",value = "SaaS应用名",required = true,dataType = "String")
    })
    public R  serviceOfSaasStatistics(@PathVariable String resourceType,@PathVariable String name){
        Workbench workbench = workbenchFactory.getWorkbench(resourceType);
        return  workbench.serviceOfSaas(name);
    }

    /**
     * 三级页面统计-应用信息-应用访问次数
     * @param id
     * @return
     */
    @ApiOperation("SaaS应用-三级页面统计-应用信息-应用访问次数")
    @ApiImplicitParam(paramType="path",name = "id",value = "SaaS应用id",required = true,dataType = "String")
    @RequestMapping(value = "/saas/application/system/{id}/count",method = RequestMethod.GET)
    public R systemCount(@PathVariable String id){
        Saas saas = saasService.getOne(new QueryWrapper<Saas>().lambda().eq(Saas::getId,id));
        Map<String,Long> res = Maps.newHashMap();
        res.put("count",saas.getSysView());
        return R.ok(res);
    }



    /**
     *三级页面统计-应用信息-用户数
     * @param id
     * @return
     */
    @ApiOperation("SaaS应用-三级页面统计-应用信息-用户数")
    @ApiImplicitParam(paramType="path",name = "id",value = "SaaS应用id",required = true,dataType = "String")
    @RequestMapping(value = "/saas/application/user/{id}/count",method = RequestMethod.GET)
    public R userCount(@PathVariable String id){
        Integer count = saasService.userCountOfSaasApplication(id);
        Map<String,Integer> res = Maps.newHashMap();
        res.put("count",count);
        return R.ok(res);
    }

    /**
     *三级页面统计-应用信息-地市数
     * @param id
     * @return
     */
    @ApiOperation("SaaS应用-三级页面统计-应用信息-地市数")
    @ApiImplicitParam(paramType="path",name = "id",value = "SaaS应用id",required = true,dataType = "String")
    @RequestMapping(value = "/saas/application/area/{id}/count",method = RequestMethod.GET)
    public R areaCount(@PathVariable String id){
        Integer count = saasService.areaCountOfSaasApplication(id);
        Map<String,Integer> res = Maps.newHashMap();
        res.put("count",count);
        return R.ok(res);
    }

    /**
     *三级页面统计-应用信息-警种数
     * @param id
     * @return
     */
    @ApiOperation("SaaS应用-三级页面统计-应用信息-警种数")
    @ApiImplicitParam(paramType="path",name = "id",value = "SaaS应用id",required = true,dataType = "String")
    @RequestMapping(value = "/saas/application/police/{id}/count",method = RequestMethod.GET)
    public R policeCount(@PathVariable String id){
        Integer count = saasService.policeCountOfSaasApplication(id);
        Map<String,Integer> res = Maps.newHashMap();
        res.put("count",count);
        return R.ok(res);
    }



    @ApiOperation("SaaS服务列表")
    @RequestMapping(value = "/saas/service", method = RequestMethod.GET)
    public R saasService() {
        List<Saas> saasList = saasService.getServiceFrontData();
        return R.ok(saasList);
    }

    @ApiOperation("SaaS进入系统统计")
    @ApiImplicitParam(paramType="path",name = "id",value = "SaaS应用Id",required = true,dataType = "String")
    @RequestMapping(value = "/saas/application/{id}/sysview",method = RequestMethod.GET)
    public R saasSysCountView(@PathVariable String id){

        Saas saas = saasService.getById(id);
        if(saas != null){
            saas.setSysView(saas.getSysView()+1);
            saas.insertOrUpdate();
        }
        return R.ok();
    }

    @ApiOperation("二级门户浏览量统计")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "type", value = "police/area 警种/地市", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "name", value = "警种地市名", required = true, dataType = "String")
        }
    )
    @RequestMapping(value = "/subPortal/{type}/{name}/view",method = RequestMethod.POST)
    public R incrementViewCount(@PathVariable String type,@PathVariable String name){
        return subPortalViewService.incrementCount(type,name);
    }

    @ApiOperation("获取二级门户浏览量")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "type", value = "police/area 警种/地市", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "name", value = "警种地市名", required = true, dataType = "String")
    }
    )
    @RequestMapping(value = "/subPortal/{type}/{name}/view",method = RequestMethod.GET)
    public R getSubPortalViewCount(@PathVariable String type,@PathVariable String name){
        return subPortalViewService.getViewCount(type,name);
    }




    @ApiOperation("PaaS服务列表")
    @RequestMapping(value = "/paas", method = RequestMethod.GET)
    public R paas() {
        Map<String, Object> data = paasService.getFrontData();
        return R.ok(data);
    }

    @ApiOperation("PaaS服务列表")
    @RequestMapping(value = "/paasSearch", method = RequestMethod.GET)
    public R paasSearch(@RequestParam(required = true) String keyword) {
        List<Paas> paas = paasService.search(keyword);
        return R.ok(paas);
    }

    @ApiOperation("IaaS服务列表")
    @RequestMapping(value = "/iaas", method = RequestMethod.GET)
    public R iaas() {
        List<IaasNew> data = iaasService.getFrontPage();
        return R.ok(data);
    }
    @ApiOperation("PaaS服务更多列表标签")
    @ApiImplicitParam(name="typeId", value="分类类型id", required=true,
            paramType="query", dataType="String")
    @RequestMapping(value = "/paas/label", method = RequestMethod.GET)
    public R paasLabel(String typeId) {
        Set<String> data = paasService.getLabel(typeId);
        return R.ok(data);
    }

    @ApiOperation("PaaS服务更多分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="form", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="form", dataType="String"),
            @ApiImplicitParam(name="typeId", value="分类类型id", paramType="form", dataType="String"),
            @ApiImplicitParam(name="keyword", value="搜索关键字", paramType="form", dataType="String", required = false)
    })
    @RequestMapping(value = "/paas/more/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R paasMorePage(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                          @RequestParam(required = false) String keyword,
                          String typeId) {
        IPage<Paas> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = paasService.getMorePage(page, typeId, keyword);
        return R.ok(page);
    }

    @ApiOperation("SaaS服务更多列表标签")
    @ApiImplicitParam(name="typeId", value="分类类型id", required=true,
            paramType="query", dataType="String")
    @RequestMapping(value = "/saas/label", method = RequestMethod.GET)
    public R saasLabel(String typeId) {
        Set<String> data = saasService.getLabel(typeId);
        return R.ok(data);
    }

    @ApiOperation("SaaS服务更多分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="form", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="form", dataType="String"),
            @ApiImplicitParam(name="typeId", value="分类类型id", paramType="form", dataType="String"),
            @ApiImplicitParam(name="keyword", value="搜索关键字", paramType="form", dataType="String", required = false),
            @ApiImplicitParam(name="areaName", value="地区", paramType="form", dataType="String", required = false),
            @ApiImplicitParam(name="policeCategory", value="警种", paramType="form", dataType="String", required = false)
    })
    @RequestMapping(value = "/saas/more/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R saasMorePage(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                          @RequestParam(required = false) String keyword,
                          @RequestParam(required = false) String areaName,
                          @RequestParam(required = false) String policeCategory,
                          String typeId) {
        IPage<Saas> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = saasService.getMorePage(page, typeId, keyword, areaName, policeCategory);
        return R.ok(page);
    }

    @RequestMapping(value = "/saas/service/more/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R saasServiceMorePage(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                          @RequestParam(required = false) String keyword) {
        IPage<Saas> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = saasService.getServiceMorePage(page, keyword);
        return R.ok(page);
    }

    @ApiOperation("获取警种地市/IaaS字典")
    @RequestMapping(value = "/dict/{value}",method = RequestMethod.GET)
    public R dictOfFront(@PathVariable String value){
        if("iaasEachDistrict".equals(value) || "policeCategoryNew".equals(value)){
            List<Dict> dictList = dictService.getChildByValue(value);
            return R.ok(dictList);
        }
        return R.error("no permission of query: "+value);
    }

    @ApiOperation("获取登录用户信息")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public R getUserInfo(@LoginUser User user) {
        if(user != null){
            String orgName = user.getOrgName();
            if(!orgName.isEmpty()){
                //根据用户单位名称中的信息去匹配所属地区和警种
                String policeCategory = AreaPoliceCategoryUtils.getPoliceCategory(orgName);
                String areaName = AreaPoliceCategoryUtils.getAreaName(orgName);
                user.setBelongArea(areaName);
                user.setBelongPoliceType(policeCategory);
            }
        }
        return R.ok(user);
    }




    @ApiOperation("缉私租户资源统计")
    @RequestMapping("/BeatSmuggling/{resourceType}/statistics")
    public R tenantStatistics(@PathVariable String resourceType, QueryVO queryVO, @LoginUser User user){
        //用户置为缉私
        user = new User();
        user.setType(20L);
        user.setTenantArea(null);
        user.setTenantPoliceCategory("缉私");


        Workbench workbench = workbenchFactory.getWorkbench(resourceType);
        if(!"paas".equals(resourceType)){
            List<GeneralDTO>  useResource = workbench.getUseResource(user,queryVO);
            return R.ok(useResource);
        }
        List<Dict> dicts = dictService.getChildByName("PAAS类别");

        String baseTypeId ="";
        String appId = "";
        for(Dict dict:dicts){
            if("基础服务".equals(dict.getName())){
                baseTypeId = dict.getId();
            }
            if("应用支撑服务".equals(dict.getName())){
                appId = dict.getId();
            }
        }
        queryVO.setCategory(baseTypeId);
        List<GeneralDTO> baseService = workbench.getUseResource(user,queryVO);
        queryVO.setCategory(appId);
        List<GeneralDTO> appService = workbench.getUseResource(user,queryVO);
        Map<String,List<GeneralDTO>> res = Maps.newHashMap();
        res.put("baseService",baseService);
        res.put("appService",appService);
        return R.ok(res);

    }

    @ApiOperation("获取访问量")
    @RequestMapping(value = "/getViewCount", method = RequestMethod.GET)
    public R getViewCount() {
        String count = stringRedisTemplate.opsForValue().get(RedisKey.KEY_APP_VIEW_COUNT);
        if (count == null) {
            count = "0";
        }
        return R.ok(count);
    }

    @ApiOperation("服务评价回复列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="serviceId", value="服务id", required = true, paramType="form", dataType="String")
    })
    @RequestMapping(value = "/reply/list", method = RequestMethod.POST)
    @ResponseBody
    public R replyList( String serviceId) {

        List<ServiceFeedbackReply> replyList = serviceFeedbackReplyService.getList(serviceId);
        return R.ok(replyList);
    }
    @ApiOperation("分页查询服务反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="form", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="form", dataType="String"),
            @ApiImplicitParam(name="serviceId", value="服务id", required = true, paramType="form", dataType="String")
    })
    @RequestMapping(value = "/serviceFeedback/page", method = RequestMethod.POST)
    @ResponseBody
    public R page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize, String serviceId) {
        IPage<ServiceFeedback> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = serviceFeedbackService.getPage(page, serviceId);
        List<ServiceFeedback> serviceFeedbacks = page.getRecords();
        //计算平均评价
        Map<String, Object> average = average(serviceFeedbacks);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("average",average);
        return R.ok(map);
    }
    private Map<String,Object> average(List<ServiceFeedback> list) {
        Map<String,Object> map = new HashMap<>();
        ServiceFeedback serviceFeedback = null;
        Double gnpjSum = 0.0;
        Double yhtySum = 0.0;
        Double sfbjSum = 0.0;
        for(int i=0;i<list.size();i++) {
            serviceFeedback = list.get(i);
            if(serviceFeedback.getGnpj() != null && serviceFeedback.getSfbj() != null && serviceFeedback.getYhty() != null) {
                gnpjSum += Double.valueOf(serviceFeedback.getGnpj());
                yhtySum += Double.valueOf(serviceFeedback.getYhty());
                sfbjSum += Double.valueOf(serviceFeedback.getSfbj());
            }
        }
        map.put("gnpjAverage",Math.round(gnpjSum/list.size()));
        map.put("yhtyAverage",Math.round(yhtySum/list.size()));
        map.put("sfbjAverage",Math.round(sfbjSum/list.size()));
        return map;
    }

    @ApiOperation("站内搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="form", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="form", dataType="String"),
            @ApiImplicitParam(name="keyword", value="搜索关键字", required = true, paramType="form", dataType="String"),
            @ApiImplicitParam(name="type", value="搜索类型,all:全部,其它分别是:docs,news,saas,paas,daas,iaas", required = true, paramType="form", dataType="String"),
    })
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public R search(String keyword, String type,
                    @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                    @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        if (StringUtils.isEmpty(keyword) || StringUtils.isEmpty(type)) {
            return R.error("参数错误");
        }
        keyword = keyword.trim();

        SearchSourceBuilder ssb = new SearchSourceBuilder();
        ssb.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("status", 2))
                .should(QueryBuilders.matchQuery("title", keyword))
                .should(QueryBuilders.matchQuery("content", keyword))
                .should(QueryBuilders.matchQuery("label", keyword)));
        ssb.highlighter(new HighlightBuilder().field("title").field("content"));
        ssb.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        ssb.sort("modified_time", SortOrder.DESC);
        ssb.minScore(1.1f);
        ssb.from((pageNum - 1) * pageSize);
        ssb.size(pageSize);

        SearchRequest searchRequest;
        if ("all".equals(type)) {
            searchRequest = new SearchRequest("docs", "news", "saas", "paas", "daas", "iaas");
        } else {
            searchRequest = new SearchRequest(type);
        }
        searchRequest.source(ssb);
        try {
            SearchResponse search = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            JSONObject jsonObject = JSON.parseObject(search.toString());
            return R.ok(jsonObject);
        } catch (IOException e) {
            return R.error("搜索发生错误,请稍后重试!");
        }
    }

}
