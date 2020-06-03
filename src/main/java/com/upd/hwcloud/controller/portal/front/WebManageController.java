package com.upd.hwcloud.controller.portal.front;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.dto.webManage.IndexDto;
import com.upd.hwcloud.bean.entity.Event;
import com.upd.hwcloud.bean.entity.Iaas;
import com.upd.hwcloud.bean.entity.Paas;
import com.upd.hwcloud.bean.entity.Subpage;
import com.upd.hwcloud.bean.entity.ThreePartyInterface;
import com.upd.hwcloud.bean.entity.webManage.Apply;
import com.upd.hwcloud.bean.entity.webManage.Carousel;
import com.upd.hwcloud.bean.entity.webManage.Case;
import com.upd.hwcloud.bean.entity.webManage.News;
import com.upd.hwcloud.bean.entity.webManage.PoliceClass;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.threeparty.daas.DaasOverview;
import com.upd.hwcloud.bean.vo.threeparty.iaas.IaasOverview;
import com.upd.hwcloud.bean.vo.threeparty.iaas.PoliceCloud;
import com.upd.hwcloud.common.utils.BigDecimalUtil;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IEventService;
import com.upd.hwcloud.service.IIaasService;
import com.upd.hwcloud.service.IPaasService;
import com.upd.hwcloud.service.ISaasService;
import com.upd.hwcloud.service.ISaasSubpageService;
import com.upd.hwcloud.service.ISubpageService;
import com.upd.hwcloud.service.IThreePartyInterfaceService;
import com.upd.hwcloud.service.webManage.IApplyService;
import com.upd.hwcloud.service.webManage.ICarouselService;
import com.upd.hwcloud.service.webManage.ICaseService;
import com.upd.hwcloud.service.webManage.INewsService;
import com.upd.hwcloud.service.webManage.IPoliceClassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 前台首页网站服务模块接口
 */
@Api(description = "门户网站网站服务模块接口")
@RestController
@RequestMapping("/api/webManage")
public class WebManageController {

    @Autowired
    ICaseService iCaseService;
    @Autowired
    IApplyService iApplyService;
    @Autowired
    INewsService iNewsService;
    @Autowired
    ICarouselService iCarouselService;
    @Autowired
    IPoliceClassService iPoliceClassService;
    @Autowired
    IEventService iEventService;
    @Autowired
    ISubpageService iSubpageService;
    @Autowired
    IIaasService iIaasService;
    @Autowired
    IPaasService iPaasService;
    @Autowired
    ISaasSubpageService saasSubpageService;
    @Autowired
    ISaasService saasService;
    @Autowired
    IThreePartyInterfaceService threePartyInterfaceService;

    /**
     * 首页接口
     * @return
     */
    @ApiOperation("获取案例，新闻，轮播，应用，警种上云列表")
    @RequestMapping("/index")
    public R index() {
        IndexDto indexDto = new IndexDto();
        List<Case> cases = iCaseService.list(new QueryWrapper<>());
        List<News> newsList = iNewsService.list(new QueryWrapper<>());
        List<Carousel> carousels = iCarouselService.list(new QueryWrapper<>());
        List<Apply> applies = iApplyService.list(new QueryWrapper<>());
        List<PoliceClass> policeClasses = iPoliceClassService.list(new QueryWrapper<>());
        indexDto.setCases(cases);
        indexDto.setNewsList(newsList);
        indexDto.setCarousels(carousels);
        indexDto.setApplies(applies);
        indexDto.setPoliceClasses(policeClasses);
        return R.ok(indexDto);
    }

    /**
     * 新闻分页接口
     * @return
     */
    @ApiOperation("分页获取新闻列表")
    @RequestMapping("/news")
    public R newsPage(Integer pageNum,Integer pageSize,@RequestParam(required = false,defaultValue = "1") Integer type,String belong) {
        if(pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        Page<News> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Page<News> news = iNewsService.getNewsByPage(page,type,belong);
        return R.ok(news);
    }


    @ApiOperation("设置地市警种DaaS总览")
    @RequestMapping(value = "/{belong}/fiveBigResource",method = RequestMethod.POST)
    public R saveOrUpdateFiveBigResource(@PathVariable String belong, @RequestBody DaasOverview daasOverview){
        StringBuilder labelBuilder = new StringBuilder(belong);
        labelBuilder.append("DaaS总览");
        int count = threePartyInterfaceService.count(new QueryWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getLabel,labelBuilder.toString()));
        ThreePartyInterface threePartyInterface = new ThreePartyInterface();
        String data = JSON.toJSONString(R.ok(daasOverview));
        threePartyInterface.setData(data);
        if(count==0){
            threePartyInterface.setLabel(labelBuilder.toString());
            threePartyInterface.setId(UUIDUtil.getUUID());
            threePartyInterface.setCreateTime(new Date());
            threePartyInterface.insert();
        }else {
            threePartyInterface.update(new UpdateWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getLabel,labelBuilder.toString()));
        }
        return R.ok(daasOverview);
    }

    @ApiOperation("设置地市警种IaaS总览")
    @RequestMapping(value = "/{belong}/iaasOverview",method = RequestMethod.POST)
    public R saveOrUpdateIaasOverview(@PathVariable String belong, @RequestBody IaasOverview iaasOverview){
        StringBuilder labelBuilder = new StringBuilder(belong);
        labelBuilder.append("IaaS总览");
        calculatevCpuAllocationRate(iaasOverview.getPoliceCloud());
        calculateGpuAllocationRate(iaasOverview.getPoliceCloud());
        calculateMemoryAllocationRate(iaasOverview.getPoliceCloud());
        calculateStorageAllocationRate(iaasOverview.getPoliceCloud());
        String data = JSON.toJSONString(R.ok(iaasOverview));
        ThreePartyInterface threePartyInterface = new ThreePartyInterface();
        threePartyInterface.setData(data);
        int count = threePartyInterfaceService.count(new QueryWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getLabel,labelBuilder.toString()));
        if(count==0){
            threePartyInterface.setLabel(labelBuilder.toString());
            threePartyInterface.setId(UUIDUtil.getUUID());
            threePartyInterface.setCreateTime(new Date());
            threePartyInterface.insert();
        }else {
            threePartyInterface.update(new UpdateWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getLabel,labelBuilder.toString()));
        }
        return R.ok(iaasOverview);
    }


    private void calculatevCpuAllocationRate(PoliceCloud policeCloud){
        if(policeCloud.getVCpuTotal().intValue()==0 || policeCloud.getVCpuTotal() == null){
            policeCloud.setVCpuAllocationRate(BigDecimal.valueOf(0));
        }else {
            policeCloud.setVCpuAllocationRate(BigDecimalUtil.div4(policeCloud.getVCpuAllocation().doubleValue(),policeCloud.getVCpuTotal().doubleValue()));
        }
    }

    private void calculateGpuAllocationRate(PoliceCloud policeCloud){
        if(policeCloud.getGpuTotal().intValue()==0 || policeCloud.getGpuTotal() == null){
            policeCloud.setGpuAllocationRate(BigDecimal.valueOf(0));
        }else {
            policeCloud.setGpuAllocationRate(BigDecimalUtil.div4(policeCloud.getGpuAllocation().doubleValue(),policeCloud.getGpuTotal().doubleValue()));
        }
    }

    private void calculateMemoryAllocationRate(PoliceCloud policeCloud){
        if(policeCloud.getMemoryTotal().compareTo(BigDecimal.valueOf(0))==0 || policeCloud.getMemoryTotal() == null){
            policeCloud.setMemoryAllocationRate(BigDecimal.valueOf(0));
        }else {
            policeCloud.setMemoryAllocationRate(BigDecimalUtil.div4(policeCloud.getMemoryAllocation().doubleValue(),policeCloud.getMemoryTotal().doubleValue()));
        }
    }

    private void calculateStorageAllocationRate(PoliceCloud policeCloud){
        if(policeCloud.getStorageTotal().compareTo(BigDecimal.valueOf(0))==0 || policeCloud.getStorageTotal() == null){
            policeCloud.setStorageAllocationRate(BigDecimal.valueOf(0));
        }else {
            policeCloud.setStorageAllocationRate(BigDecimalUtil.div4(policeCloud.getStorageAllocation().doubleValue(),policeCloud.getStorageTotal().doubleValue()));
        }
    }


    /**
     * 案例分页接口
     * @return
     */
    @ApiOperation("获取案例列表")
    @RequestMapping("/cases")
    public R casesPage() {
        List<Case> list = iCaseService.list(new QueryWrapper<>());
        return R.ok(list);
    }

    /**
     * 轮播图分页接口
     * @return
     */
    @ApiOperation("获取轮播图列表")
    @RequestMapping("/carousels")
    public R carouselsPage(@RequestParam(required = false,defaultValue = "1")Integer type,String belong) {

        List<Carousel> list = iCarouselService.getIndexCarousel(type,belong);
        return R.ok(list);
    }

    /**
     * 试点应用分页接口
     * @return
     */
    @ApiOperation("获取试点应用列表")
    @RequestMapping("/applys")
    public R applysPage() {

        List<Apply> list = iApplyService.list(new QueryWrapper<>());
        return R.ok(list);
    }

    /**
     * 警种上云分页接口
     * @return
     */
    @ApiOperation("获取警种上云列表")
    @RequestMapping("/policeClasses")
    public R policeClassesPage() {
        List<PoliceClass> list = iPoliceClassService.list(new QueryWrapper<>());
        return R.ok(list);
    }

    /**
     * 新闻详情
     * @param id
     * @return News
     */
    @ApiOperation("新闻详情")
    @RequestMapping("/news/{id}")
    public R newsInfo(@PathVariable String id) {
        News news = iNewsService.getById(id);
        iNewsService.updateViewCountById(news.getId());
        return R.ok(news);
    }


    /**
     * 轮播详情
     * @param id
     * @return News
     */
    @ApiOperation("轮播详情")
    @RequestMapping("/cr/{id}")
    public R Crnfo(@PathVariable String id) {
        Carousel carousel = iCarouselService.getById(id);
        return R.ok(carousel);
    }

    /**
     * 大事记首页列表
     * @return
     */
    @ApiOperation("获取大事记列表")
    @RequestMapping("/events")
    public R event() {
        List<Event> events = iEventService.list(
                new QueryWrapper<Event>()
                        .orderByDesc("HAPPEN_TIME"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        for(Event event : events) {
            event.setShowDate(simpleDateFormat.format(event.getHappenTime()));
        }
        if(events.size() >= 2) {
            return R.ok(events.subList(0,2));
        }
        return R.ok(events);
    }

    @RequestMapping("/eventList")
    public R eventList() {
        List<Event> events = iEventService.list(new QueryWrapper<Event>().orderByDesc("HAPPEN_TIME"));
        return R.ok(events);
    }

    /**
     * iaas paas 二级页面内容
     * @param serviceId
     * @return
     */
    @ApiOperation("获取二级页面内容")
    @RequestMapping("/subPage/{serviceId}")
    public R subPage(@PathVariable String serviceId,@RequestParam(required = false) String name) {
        Map<String, Object> result = Maps.newHashMap();
        if(name != null) {
            if("iaas".equals(name)) {
                Iaas iaas = iIaasService.getById(serviceId);
                result.put("service", iaas);
            }
            if("paas".equals(name)) {
                Paas paas = iPaasService.getById(serviceId);
                result.put("service", paas);
            }
        }
        Subpage subpage = iSubpageService.getOne(new QueryWrapper<Subpage>()
                .eq("SERVICE_ID",serviceId));
        result.put("subPage", subpage);
        return R.ok(result);
    }

}
