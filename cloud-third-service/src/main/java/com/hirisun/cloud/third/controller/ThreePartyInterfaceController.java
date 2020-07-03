package com.hirisun.cloud.third.controller;


import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.hirisun.cloud.common.util.OkHttpUtils;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.dto.*;
import com.hirisun.cloud.model.ncov.vo.daas.NcovExcelSheetOneVo;
import com.hirisun.cloud.model.ncov.vo.daas.NcovExcelSheetTwoVo;
import com.hirisun.cloud.model.ncov.vo.daas.NcovOverview;
import com.hirisun.cloud.model.third.dto.DirectUnitOrderDetail;
import com.hirisun.cloud.model.third.dto.DirectUnitStatistics;
import com.hirisun.cloud.model.third.dto.ResourceTotalDTO;
import com.hirisun.cloud.model.third.dto.YqServiceDetail;
import com.hirisun.cloud.third.bean.Files;
import com.hirisun.cloud.third.bean.ThreePartyInterface;
import com.hirisun.cloud.third.service.IFilesService;
import com.hirisun.cloud.third.service.ThreePartyInterfaceService;
import com.hirisun.cloud.third.util.easypoi.NcovExcelImportUtil;
import com.hirisun.cloud.third.util.ncov.MemoryPageUtil;
import io.swagger.annotations.*;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Autowired
    private IFilesService filesService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation("保存第三方接口")
    @PostMapping("/save")
    public ResponseResult save(ThreePartyInterface threePartyInterface) {
        threePartyInterface.insertOrUpdate();
        return QueryResponseResult.success();
    }

    /**
     * 根据第三方资源id查找数据
     * 兼容前端代码，开放post、getMapping
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据第三方资源id查找数据", notes = "{"
            + "resourceTotal:资源目录总数;        "
            + "zonglan:daas总览;      "
            + "zlCount10 :daas总览-近10日每日增量趋势图;       "
            + "fiveBigResource:数据接入（数据概况）;        "
            + "fiveBigResource01:原始库数据;        "
            + "fiveBigResource02:资源库数据;        "
            + "fiveBigResource03:主题库数据;        "
            + "fiveBigResource04:知识库数据;        "
            + "fiveBigResource05:业务库数据;"
            + "fiveBigResource06:业务要素索引库（接口）;       "
            + "resourceRelationCount:知识图谱库;     "
            + "dataGovern:数据治理;"
            + "resourceDataSource02:地市上报;"
            + "resourceDataSource01:警种及行业公安共享;"
            + "resourceDataSource04:公安部下发;"
            + "resourceDataSource03:政府部门及企事业单位共享;"
            + "resourceDataSource05:外省共享;"
            + "dataCheckCount:数据对账;"
            + "dbConfig:数据库监控;"
            + "resourceSensitivity:资源分级分类;"
            + "sjcjgk:数据处理概况;"
            + "sjzlgl:数据质量管理;"
            + "sjxygk:数据血缘管理;"
            + "resourceTotal:资源目录总数;"
            + "dataSensitivity:数据分级分类;"
            + "dataOperManage:数据运维管理;"
            + "dataCount:近7天每日增量趋势图;"
            + "resourceSourceCount:各网域资源/数据共享占比;"
            + "dataCount07:近7天JZ数据每日增量总数;"
            + "resourceSensitivity:资源分级分类;"
            + "dataCount08:近7天WA数据每日增量总数;"
            + "cityDataCountList:每个地市的总数据量;"
            + "cityDataCountTable:每个地市的总表数;"
            + "yesterdayCount:每个地市的昨日增量数;"
            + "cityDataCountSum:已汇聚地市数据总量;"
            + "cityDataCountSumTable:已汇聚资源数量;"
            + "cityDataIncrementDay7:本周数据增量;"
            + "cityDataIncrementDay1:昨日数据增量;"
            + "dataGroupCountJz:公安网共享;"
            + "dataGroupCountBm:政府部门间共享(含企事业单位共享);"
            + "findPartSend:公安部下发;"
            + "departmentCount:外省共享;"
            + "sjclqst:近30天数据处理趋势;"
            + "resourceScale:近7天数据标准化资源数/数据量;"
            + "cityDataCountNew:数据元;"
            + "resourceClassCount:各资源分级分类概况;"
            + "queryCountGroupBySectionClass:分类概况;"
            + "zyzbqst:近7天异常更新率/缺业务代码资源占比趋势;"
            + "zygxycl:近7天资源更新异常率TOP10;"
            + "gxyczyssjz:近7天更新异常资源所属警种TOP10;"
            + "yszdkzl:要素字段空值率资源TOP10;"
            + "zyssjzpjkzl:资源所属警种平均空值率TOP10;"
            + "yszdzhglzy:要素字段值合格率资源LAST10;"
            + "zyssjzzdzhgl:资源所属警种字段值合格率LAST10;"
            + "qywdmzy:缺业务代码资源TOP10;"
            + "zyssjzqywdm:资源所属警种缺业务代码TOP10;"
            + "dataCount3001:原始库近30天每日增量趋势图;"
            + "attrCategory:筛选条件;"
            + "dataTag:常用标签;"
            + "apimodel:模型超市按模型上线时间排行;"
            + "apimodelHot:模型超市按模型热度排行;"
            + "modeljuesai:模型超市按决赛取;"
            + "resourceCategory:获取资源分类;"
            + "ncovImportOverview:疫情数据接入概览(/ncov/import/overview);"
            + "ncovImportRecentAll:疫情数据接入-最近七天(/ncov/import/recentAll);"
            + "ncovImportDepartment:疫情数据接入-单位数据(/ncov/import/department);"
            + "ncovImportOverview:疫情数据接入概览(/ncov/import/importantData);"
            + "ncovImportImportantData:疫情数据接入-重点数据(/ncov/import/elementOverview);"

            + "}")
    @ApiResponses(//相应参数说明
            @ApiResponse(code = 200, message = "success", response = ThreePartyInterface.class)
    )
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public Object getById(@ApiParam(name = "id", value = "第三方数据id", required = true) @PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", 201);
            map.put("data", new ArrayList<>());
            return map;
        }
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getById(id);
        if (threePartyInterface == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", 200);
            map.put("data", new ArrayList<>());
            return map;
        }
        logger.info("threePartyInterface:{}", threePartyInterface);
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
            + "cxjs:查询检索服务总数;      "
            + "bkdy:布控订阅服务总数;       "
            + "mxfx:模型分析服务总数;        "
            + "sjts:数据推送服务总数;        "
            + "xtdytop:近7天系统调用top10;        "
            + "xtdytopAll:系统调用Top10;        "
            + "fwfwtop:近7天服务访问top10;        "
            + "fwfwtopAll:服务访问top10;        "
            + "zxgztop:最新挂载服务top10;       "
            + "yyfwtop:应用调用服务Top10;     "
            + "jzdytop5:警种调用Top5;      "
            + "dsdytop5:地市调用Top5;       "
            + "hDyhFwFwTop:高频订阅高频访问服务Top10;     "
            + "lDyhFwFwTop:低频订阅高频访问服务Top10;      "
            + "hDylFwXtTop:高频订阅低频访问系统Top10;       "
            + "ztqs7day:近7天服务请求总体趋势;     "
            + "hsqs7day:近7天服务响应耗时趋势;      "
            + "yqServiceTop10:疫情数据服务Top10;       "
            + "yqServiceInfo:疫情数据服务二级页面;     "
            + "yqServiceLast7:疫情服务近7天调用趋势;      "

            + "}")
    @ApiResponses(//相应参数说明
            @ApiResponse(code = 200, message = "success", response = ThreePartyInterface.class)
    )
    @RequestMapping(value = "/getByLabel/{label}", method = {RequestMethod.GET, RequestMethod.POST})
    public Object getByLabel(@ApiParam(name = "label", value = "第三方数据标识", required = true) @PathVariable String label) {
        if (StringUtils.isEmpty(label)) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", 201);
            map.put("data", new ArrayList<>());
            return map;
        }
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", label));
        if (threePartyInterface == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", 200);
            map.put("data", new ArrayList<>());
            return map;
        }
        logger.info("threePartyInterface:{}", threePartyInterface);
        return JSONObject.parseObject(threePartyInterface.getData());
    }

    @ApiOperation("地市警种DaaS接入数据接口（手动配置）")
    @GetMapping(value = "/{belong}/fiveBigResource")
    @ApiImplicitParam(name = "belong", value = "警种/地市", dataType = "string", paramType = "path", example = "梅州")
    public JSONObject areaOrPoliceFiveBigResource(@PathVariable String belong) {
        StringBuilder labelBuilder = new StringBuilder(belong);
        labelBuilder.append("DaaS总览");
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(new QueryWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getLabel, labelBuilder.toString()));
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("地市警种IaaS总览数据接口（手动配置）")
    @GetMapping(value = "/{belong}/iaasOverview")
    @ApiImplicitParam(name = "belong", value = "警种/地市", dataType = "string", paramType = "path", example = "梅州")
    public JSONObject areaOrPoliceIaaS(@PathVariable String belong) {
        StringBuilder labelBuilder = new StringBuilder(belong);
        labelBuilder.append("IaaS总览");
        ThreePartyInterface iaasOverview = threePartyInterfaceService.getOne(new QueryWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getLabel, labelBuilder.toString()));
        return JSONObject.parseObject(iaasOverview.getData());
    }

    @ApiOperation("数据库异常清单")
    @GetMapping("/dbConfigList")
    public Object dbConfigList(@RequestParam(required = false, defaultValue = "1") String page
            , @RequestParam(required = false, defaultValue = "10") String limit) {
        String url = "http://15.40.3.71:8000/services/serviceInvoke/queryDataService/foreign/dataCount/dbConfigList?page=" + page + "&limit=" + limit;
        threePartyInterfaceService.getOrUpdateData(url, "dbConfigList", "数据库异常清单");
        ThreePartyInterface dbConfigList = threePartyInterfaceService.getById("dbConfigList");
        if (dbConfigList != null) {
            return JSONObject.parseObject(dbConfigList.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("资源异常清单")
    @GetMapping("/dataCheckList")
    public Object dataCheckList(@RequestParam(required = false, defaultValue = "1") String page
            , @RequestParam(required = false, defaultValue = "10") String limit) {
        String url = "http://15.40.3.71:8000/services/serviceInvoke/queryDataService/foreign/dataCount/dataCheckList?page=" + page + "&limit=" + limit;
        threePartyInterfaceService.getOrUpdateData(url, "dataCheckList", "数据库异常清单");
        ThreePartyInterface dbConfigList = threePartyInterfaceService.getById("dataCheckList");
        if (dbConfigList != null) {
            return JSONObject.parseObject(dbConfigList.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("获取实时数据24条")
    @GetMapping(value = "/getRealTimeData24")
    public Object getRealTimeData24() {
        List<ThreePartyInterface> listIN = threePartyInterfaceService.list(new QueryWrapper<ThreePartyInterface>().eq("LABEL", "实时数据IN").orderByAsc("CREATE_TIME"));
        List<ThreePartyInterface> listOUT = threePartyInterfaceService.list(new QueryWrapper<ThreePartyInterface>().eq("LABEL", "实时数据OUT").orderByAsc("CREATE_TIME"));
        List<Map<String, Object>> mapsIn = parseData(listIN);
        List<Map<String, Object>> mapsOut = parseData(listOUT);
        Map<String, Object> inOut = new HashMap<>();
        inOut.put("inList", mapsIn);
        inOut.put("outList", mapsOut);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("data", inOut);
        return res;
    }

    /**
     * 获取实时数据，包括实时数据in和out
     */
    @ApiOperation("获取实时数据In")
    @GetMapping("/getRealTimeDataIn")
    public JSONObject getRealTimeData() {
        ThreePartyInterface one = threePartyInterfaceService.getOne(new QueryWrapper<ThreePartyInterface>()
                .eq("LABEL", "实时数据IN").orderByDesc("CREATE_TIME"));
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        JSONObject jsonObject = JSONObject.parseObject(one.getData());
        jsonObject.getJSONObject("data").put("time", fmt.format(one.getCreateTime()));
        return jsonObject;
    }

    /**
     * 待合并
     */
    @ApiOperation("获取实时数据Out")
    @GetMapping("/getRealTimeDataOut")
    public JSONObject getRealTimeDataOut() {
        ThreePartyInterface one = threePartyInterfaceService.getOne(new QueryWrapper<ThreePartyInterface>()
                .eq("LABEL", "实时数据OUT").orderByDesc("CREATE_TIME"));
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        JSONObject jsonObject = JSONObject.parseObject(one.getData());
        jsonObject.getJSONObject("data").put("time", fmt.format(one.getCreateTime()));
        return jsonObject;
    }

    @ApiOperation("系统调用导出/未实现接口")
    @GetMapping("/xtdytop/export")
    public void xtdytopExport(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "starTime", required = false) String starTime,
                              @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "starTime1", required = false) String starTime1, @RequestParam(value = "endTime1", required = false) String endTime1,
                              @RequestParam(value = "area", required = false) String area, @RequestParam(value = "category", required = false) String category) throws Exception {

    }

    @ApiOperation("大数据工程资源情况(全部)")
    @GetMapping(value = "/dsjgc")
    public Object dsjgc() {
        ThreePartyInterface fiveBigResourceT1 = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "大数据工程资源情况(一期)"));
        ThreePartyInterface fiveBigResourceT2 = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "大数据工程资源情况"));
        if (fiveBigResourceT1 != null && fiveBigResourceT2 != null) {
            ResourceTotalDTO t1 = JSONObject.parseObject(fiveBigResourceT1.getData(), ResourceTotalDTO.class);
            ResourceTotalDTO t2 = JSONObject.parseObject(fiveBigResourceT2.getData(), ResourceTotalDTO.class);
            ResourceTotalDTO.ResourceTotalDataDTO t1Data = t1.getData();
            ResourceTotalDTO.ResourceTotalDataDTO t2Data = t2.getData();
            if (t1Data == null && t2Data != null) {
                return t2;
            }
            if (t2Data == null && t1Data != null) {
                return t1;
            }
            if (t1Data != null && t2Data != null) {
                ResourceTotalDTO.ResourceTotalDataDTO total = new ResourceTotalDTO.ResourceTotalDataDTO();
                total.setStorageTotal(t1Data.getStorageTotal() + t2Data.getStorageTotal());
                total.setServiceNum(t1Data.getServiceNum() + t2Data.getServiceNum());
                total.setNode(t1Data.getNode() + t2Data.getNode());
                total.setTenant(t1Data.getTenant() + t2Data.getTenant());
                ResourceTotalDTO totalDTO = new ResourceTotalDTO();
                totalDTO.setCode(200);
                totalDTO.setData(total);
                return totalDTO;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("搜索模型")
    @GetMapping("/searchModel")
    public Object searchModel(@RequestParam(required = false) String currPage
            , @RequestParam(required = false) String pageSize, @RequestParam(required = false) String searchModelName
            , @RequestParam(required = false) String date, @RequestParam(required = false) String searchUnit) {
        Map<String, String> map = new HashMap<>();
        map.put("currPage", currPage == null ? "1" : currPage);
        map.put("pageSize", pageSize == null ? "10" : pageSize);
        map.put("searchModelName", searchModelName == null ? "" : searchModelName);
        map.put("date", date == null ? "" : date);
        map.put("searchUnit", searchUnit == null ? "" : searchUnit);

        threePartyInterfaceService.postOrUpdateData("http://68.26.21.46:8080/hz/open/api/model/page"
                , "searchModel", "条件搜索模型", map);
        ThreePartyInterface searchModel = threePartyInterfaceService.getById("searchModel");
        if (searchModel != null) {
            return JSONObject.parseObject(searchModel.getData());
        }
        return new JSONObject();
    }

    //五大库二级页面接口
    @ApiOperation("五大库二级页面类型")
    @GetMapping("/wdkSubPageType")
    public Object wdkSubPageType(String resourceFirstClass) throws Exception {
        String url = "http://15.40.3.71:8000/services/serviceInvoke/queryDataService/foreign/statisticsSecond/getSecondClass?resourceFirstClass=" + resourceFirstClass;
        Response response = OkHttpUtils.get(url, null);
        String res = response.body().string();
        response.close();
        return JSONObject.parseObject(res);
    }

    @ApiOperation("五大库二级页面详情")
    @GetMapping("/wdkSubPageInfo")
    public Object wdkSubPageInfo(String resourceFirstClass, String resourceSecondClass) throws Exception {
        String url = "http://15.40.3.71:8000/services/serviceInvoke/queryDataService/foreign/statisticsSecond/getSecondClassCount?resourceFirstClass=" + resourceFirstClass + "&resourceSecondClass=" + resourceSecondClass;
        Response response = OkHttpUtils.get(url, null);
        String res = response.body().string();
        response.close();
        return JSONObject.parseObject(res);
    }

    /**
     * 省直单位总览
     *
     * @return
     */
    @ApiOperation("省直单位总览")
    @RequestMapping(value = "/unitOverview", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult unitOverview() {
        try {
            CovOverview covOverview = threePartyInterfaceService.unitOverview();
            return QueryResponseResult.success(covOverview);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }
    @ApiOperation("疫情云桌面二级页面发放情况")
    @RequestMapping("/epidemicDesktopTable")
    public ResponseResult epidemicDesktopTable() {
        try {
            List<EpidemicDesktop> epidemicDesktops = threePartyInterfaceService.epidemicExcl();
            EpidemicDeskIssue epidemicDeskIssue = threePartyInterfaceService.epidemicDeskIssue(epidemicDesktops);
            return QueryResponseResult.success(epidemicDeskIssue);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }

    /**
     * 疫情云桌面表格数据总览
     *
     * @return
     */
    @ApiOperation("疫情云桌面表格数据总览")
    @RequestMapping("/epidemicDesktopNum")
    public ResponseResult epidemicDesktopNum() {
        String res = stringRedisTemplate.opsForValue().get(NcovKey.EPIC_DESKTOP);
        if(org.apache.commons.lang3.StringUtils.isNotBlank(res)){
            DeskTopNum deskTopNum = JSON.parseObject(res,DeskTopNum.class);
            return QueryResponseResult.success(deskTopNum);
        }
        try {
            List<EpidemicDesktop> epidemicDesktops = threePartyInterfaceService.epidemicExcl();
            DeskTopNum deskTopNum = threePartyInterfaceService.epidemicExclNum(epidemicDesktops);
            stringRedisTemplate.opsForValue().set(NcovKey.EPIC_DESKTOP,JSON.toJSONString(deskTopNum),5, TimeUnit.HOURS);
            return QueryResponseResult.success(deskTopNum);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }

    /**
     * 疫情云桌面表格数据
     *
     * @return
     */
    @ApiOperation("疫情云桌面表格数据")
    @RequestMapping("/epidemicDesktop")
    public ResponseResult epidemicDesktop(@RequestParam("pageSize") long pageSize, @RequestParam("pageNum") long pageNum) {
        try {
            List<EpidemicDesktop> epidemicDesktops = threePartyInterfaceService.epidemicExcl();
            Page<EpidemicDesktop> page = MemoryPageUtil.page(epidemicDesktops, pageSize, pageNum);
            return QueryResponseResult.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }

    }

    /**
     * 省厅警种总览
     *
     * @return
     */
    @ApiOperation("省厅警种总览")
    @RequestMapping(value = "/policeData", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult policeData() {
        try {
            CovOverview covOverview = threePartyInterfaceService.policeData();
            return QueryResponseResult.success(covOverview);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }

    /**
     * 地市总览
     *
     * @return
     */
    @ApiOperation("地市总览")
    @RequestMapping(value = "/areaData", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult<CovOverview> areaData() {
        try {
            CovOverview covOverview = threePartyInterfaceService.areaData();
            return QueryResponseResult.success(covOverview);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }

    /**
     * 省直各单位订购及调用统计
     *
     * @return
     */
    @ApiOperation("省直各单位订购及调用统计")
    @RequestMapping(value = "/unitOverviewLevel2", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult unitOverviewLevel2() {
        try {
            List<CovOverviewLevel2> covOverviewLevel2list = threePartyInterfaceService.unitOverviewLevel2();
            return QueryResponseResult.success(covOverviewLevel2list);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }

    /**
     * 各省直单位调用详情
     *
     * @param unitName 单位名称
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation("各省直单位调用详情")
    @RequestMapping(value = "/unitStatistic", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult unitStatistic(@RequestParam(name = "unitName") String unitName,
                                        @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                                        @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        try {
            IPage<DirectUnitStatistics> directUnitStatistics = threePartyInterfaceService.unitStatistic(unitName, pageNo, pageSize);
            return QueryResponseResult.success(directUnitStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }

    /**
     * 各省直单位订阅详情
     *
     * @param unitName 省直单位名称
     * @return
     */
    @ApiOperation("各省直单位订阅详情")
    @RequestMapping(value = "/unitOrderDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult unitOrderDetail(@RequestParam(name = "unitName") String unitName,
                                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                                          @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        try {
            IPage<DirectUnitOrderDetail> directUnitOrderDetail = threePartyInterfaceService.unitOrderDetail(unitName, pageNo, pageSize);
            return QueryResponseResult.success(directUnitOrderDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }
    /**
     * 疫情云桌面表格上传
     *
     * @param request
     * @return
     */
    @ApiOperation("疫情云桌面表格上传")
    @RequestMapping("/updataUnitFile")
    @ResponseBody
    public ResponseResult updataUnitFile(MultipartRequest request) {
        List<MultipartFile> files = request.getFiles("file");
        for (MultipartFile file : files) {
            try {
                threePartyInterfaceService.updataFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                return QueryResponseResult.fail();
            }
        }
        return QueryResponseResult.success(files.get(0).getOriginalFilename());
    }
    /**
     * 疫情云桌面表格下载
     *
     * @param response
     * @return
     */
    @ApiOperation("疫情云桌面表格下载")
    @RequestMapping("/downDataFile")
    @ResponseBody
    public ResponseResult downDataFile(HttpServletResponse response) {
        try {
            threePartyInterfaceService.downdataFile(response);
        } catch (IOException e) {
            e.printStackTrace();
            return QueryResponseResult.success();
        }
        return QueryResponseResult.success();
    }

    /**
     * 省直单位表格上传
     *
     * @param request
     * @return
     */
    @ApiOperation("省直单位表格上传")
    @RequestMapping(value = "/uploadUnitFile", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult uploadUnitFile(MultipartRequest request) {
        List<MultipartFile> files = request.getFiles("file");
        for (MultipartFile file : files) {
            try {
                threePartyInterfaceService.uploadFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                return QueryResponseResult.fail();
            }
        }
        return QueryResponseResult.success(files.get(0).getOriginalFilename());
    }

    /**
     * 省直单位表格下载
     *
     * @param response
     * @return
     */
    @ApiOperation("省直单位表格下载")
    @RequestMapping(value = "/downloadUnitFile", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult downloadUnitFile(HttpServletResponse response) {
        try {
            threePartyInterfaceService.downloadFile(response);
        } catch (IOException e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
        return QueryResponseResult.success();
    }

    /**
     * 各警种订购及调用统计
     *
     * @return
     */
    @ApiOperation("各警种订购及调用统计")
    @RequestMapping(value = "/policeDataLevel2", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult policeDataLevel2() {
        try {
            List<CovOverviewLevel2> covOverviewLevel2s = threePartyInterfaceService.policeDataLevel2();
            //调用总次数降序排列
            List<CovOverviewLevel2> collect = covOverviewLevel2s.stream().sorted(Comparator.comparingInt(CovOverviewLevel2::getDyCount).reversed()).collect(Collectors.toList());
            return QueryResponseResult.success(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }

    /**
     * 各地市订购及调用统计
     *
     * @return
     */
    @ApiOperation("各地市订购及调用统计")
    @RequestMapping(value = "/areaDataLevel2", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult areaDataLevel2() {
        try {
            List<CovOverviewLevel2> covOverviewLevel2s = threePartyInterfaceService.areaDataLevel2();
            //调用总次数降序排列
            List<CovOverviewLevel2> collect = covOverviewLevel2s.stream().sorted(Comparator.comparingInt(CovOverviewLevel2::getDyCount).reversed()).collect(Collectors.toList());
            return QueryResponseResult.success(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }

    /**
     * 各警种调用详情
     *
     * @param policeName 警种名称
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation("各警种调用详情")
    @RequestMapping(value = "/policeStatistic", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult policeStatistic(@RequestParam(name = "policeName") String policeName,
                                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                                          @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        try {
            IPage<CovStatistic> covStatistics = threePartyInterfaceService.policeStatistic(policeName, pageNo, pageSize);
            return QueryResponseResult.success(covStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }

    /**
     * 各地市调用详情
     *
     * @param areaName 地市名称
     * @return
     */
    @ApiOperation("各地市调用详情")
    @RequestMapping(value = "/areaStatistic", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult areaStatistic(@RequestParam(name = "areaName") String areaName,
                                        @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                                        @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        try {
            IPage<CovStatistic> covStatistics = threePartyInterfaceService.areaStatistic(areaName, pageNo, pageSize);
            return QueryResponseResult.success(covStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }

    /**
     * 各警种订阅详情
     *
     * @param policeName 警种名称
     * @return
     */
    @ApiOperation("各警种订阅详情")
    @RequestMapping(value = "/policeOrderDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult policeOrderDetail(@RequestParam(name = "policeName") String policeName,
                                            @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                                            @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        try {
            IPage<CovOrderDetail> covStatistics = threePartyInterfaceService.policeOrderDetail(policeName, pageNo, pageSize);
            return QueryResponseResult.success(covStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }

    /**
     * 各地市订阅详情
     *
     * @param areaName 地市名称
     * @return
     */
    @ApiOperation("各地市订阅详情")
    @RequestMapping(value = "/areaOrderDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult areaOrderDetail(@RequestParam(name = "areaName") String areaName,
                                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                                          @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        try {
            IPage<CovOrderDetail> covStatistics = threePartyInterfaceService.areaOrderDetail(areaName, pageNo, pageSize);
            return QueryResponseResult.success(covStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail();
        }
    }


    /**
     * 数据处理
     */
    private List<Map<String, Object>> parseData(List<ThreePartyInterface> list) {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        List<Map<String, Object>> maps = new ArrayList<>();
        list.forEach(item -> {
            JSONObject jsonObject = JSONObject.parseObject(item.getData()).getJSONObject("data");
            Map<String, Object> map = new HashMap<>();
            map.put("value", jsonObject.getString("value"));
            map.put("ext2", jsonObject.getString("ext2"));
            map.put("time", fmt.format(item.getCreateTime()));
            maps.add(map);
        });
        return maps;
    }

    //---------------------------疫情部分--------------------------------

    @ApiOperation("疫情数据接入-要素部门")
    @RequestMapping("/ncov/import/departmentOfElement")
    @ResponseBody
    public ResponseResult ncovImportDepartmentOfElementC(String type) {

        return QueryResponseResult.success(ncovImportDepartmentOfElement(type));
    }


    @ApiOperation("疫情-部门要素目录分页")
    @RequestMapping("/ncov/import/departmentOfElement/detailPage")
    @ResponseBody
    public ResponseResult ncovImportDepartmentOfElementPage(@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "1") int pageNum, String type, String dept, String name) {
        List<NcovExcelSheetOneVo> fullInfoList = getFullNcovExcelInfo();
        dealCarType(fullInfoList);
        List<Files> attachmentList = getAllAttachment(fullInfoList);
        List<NcovExcelSheetOneVo> attachmentFirstList = getAttachmentFirstSort(fullInfoList, attachmentList);
        Page<NcovExcelSheetOneVo> page = MemoryPageUtil.ncovPageByDeptTypeAndName(attachmentFirstList, pageSize, pageNum, dept, type, name);
        return QueryResponseResult.success(page);


    }

    @ApiOperation("疫情数据接入-分页")
    @RequestMapping("ncov/import/page")
    @ResponseBody
    public ResponseResult ncovImportPage(@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "1") int pageNum, String name) {

        List<NcovExcelSheetOneVo> fullInfoList = getFullNcovExcelInfo();
        List<Files> attachmentList = getAllAttachment(fullInfoList);

        List<NcovExcelSheetOneVo> attachmentFirstList = getAttachmentFirstSort(fullInfoList, attachmentList);
        Page<NcovExcelSheetOneVo> page = MemoryPageUtil.ncovPageByName(attachmentFirstList, pageSize, pageNum, name);
        return QueryResponseResult.success(page);

    }
    /**
     * 要素下部门
     *
     * @param type
     * @return
     */
    private List<NcovOverview> ncovImportDepartmentOfElement(String type) {
        List<NcovExcelSheetOneVo> fullInfoList = getFullNcovExcelInfo();
        dealCarType(fullInfoList);
        List<NcovExcelSheetOneVo> typeList = fullInfoList.stream().filter(item -> org.apache.commons.lang3.StringUtils.equals(type, item.getElementType())).collect(Collectors.toList());

        Map<String, Long> tableNum = typeList.stream().collect(Collectors.groupingBy(NcovExcelSheetOneVo::getDepartmentOfData, Collectors.counting()));

        Map<String, Long> all = typeList.stream().collect(Collectors.groupingBy(NcovExcelSheetOneVo::getDepartmentOfData, Collectors.summingLong(NcovExcelSheetOneVo::getDataTotalNum)));

        Map<String, Long> yesterday = typeList.stream().collect(Collectors.groupingBy(NcovExcelSheetOneVo::getDepartmentOfData, Collectors.summingLong(NcovExcelSheetOneVo::getDataUpOfYesterday)));

        List<NcovOverview> overviewList = dealGroupMapDataOfNcov(all, yesterday, tableNum);


        return overviewList;
    }

    /**
     * 合并SheetTwo的元素类型到SheetOne
     *
     * @return
     */
    private List<NcovExcelSheetOneVo> getFullNcovExcelInfo() {
        List<NcovExcelSheetOneVo> sheetOneVoList = NcovExcelImportUtil.getSheetOneData();

        List<NcovExcelSheetTwoVo> sheetTwoVoList = NcovExcelImportUtil.getSheetTwoData();

        sheetOneVoList.forEach(one -> {
            sheetTwoVoList.forEach(two -> {
                if (org.apache.commons.lang3.StringUtils.equals(two.getTableCnName(), one.getTableCnName())) {
                    one.setElementType(two.getElementType());
                }
            });
        });
        return sheetOneVoList;
    }

    private List<Files> getAllAttachment(List<NcovExcelSheetOneVo> sheetOneVoList) {
        List<String> nameList = sheetOneVoList.stream().map(NcovExcelSheetOneVo::getTableCnName).collect(Collectors.toList());
        List<Files> files = filesService.list(new QueryWrapper<Files>().lambda().in(Files::getTitle, nameList));
        return files;

    }
    private List<NcovExcelSheetOneVo> getAttachmentFirstSort(List<NcovExcelSheetOneVo> fullInfoList, List<Files> attachmentList) {
        fullInfoList.forEach(sheetOneVo -> {
            attachmentList.forEach(attachment -> {
                if (org.apache.commons.lang3.StringUtils.equals(attachment.getTitle(), sheetOneVo.getTableCnName())) {
                    sheetOneVo.setAttachmentUrl(attachment.getUrl());
                    sheetOneVo.setAttachmentName(attachment.getOriginaName());
                }
            });
        });
        List<NcovExcelSheetOneVo> attachmentFirstList = fullInfoList.stream().sorted(Comparator.comparing(i -> i.getAttachmentUrl(), Comparator.nullsLast(String::compareTo))).collect(Collectors.toList());
        return attachmentFirstList;
    }
    /**
     * 车辆 处理为 车
     *
     * @param sheetOneVoList
     */
    private void dealCarType(List<NcovExcelSheetOneVo> sheetOneVoList) {
        sheetOneVoList.forEach(item -> {
            if (org.apache.commons.lang3.StringUtils.equals("车辆", item.getElementType())) {
                item.setElementType("车");
            }
        });
    }
    /**
     * 疫情数据分组map整合为一个实体
     *
     * @param all
     * @param yesterday
     * @return
     */
    private List<NcovOverview> dealGroupMapDataOfNcov(Map<String, Long> all, Map<String, Long> yesterday, Map<String, Long> tableNum) {
        List<NcovOverview> overviewList = Lists.newArrayList();
        all.forEach((k, v) -> {
            NcovOverview overview = new NcovOverview();
            overview.setName(k);
            overview.setTotal(v);
            overviewList.add(overview);
        });

        overviewList.forEach(overview -> {
            overview.setYesterdayUp(yesterday.get(overview.getName()));
            overview.setTableNum(tableNum.get(overview.getName()));
        });

        //按接入數降序排列
        List<NcovOverview> sortList = overviewList.parallelStream().sorted(Comparator.comparingLong(NcovOverview::getTotal).reversed()).collect(Collectors.toList());
        return sortList;
    }
}

