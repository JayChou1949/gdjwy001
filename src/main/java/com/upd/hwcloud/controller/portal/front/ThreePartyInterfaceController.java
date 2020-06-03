package com.upd.hwcloud.controller.portal.front;


import cn.afterturn.easypoi.excel.entity.ExportParams;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.dto.*;
import com.upd.hwcloud.bean.dto.cov.*;
import com.upd.hwcloud.bean.entity.Bigdata;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.ThreePartyInterface;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.ncov.NcovExcelSheetOneVo;
import com.upd.hwcloud.bean.vo.ncov.NcovExcelSheetTwoVo;
import com.upd.hwcloud.bean.vo.ncov.NcovOverview;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.common.utils.OkHttpUtils;
import com.upd.hwcloud.common.utils.easypoi.ExportMoreView;
import com.upd.hwcloud.common.utils.easypoi.ExportView;
import com.upd.hwcloud.common.utils.easypoi.NcovExcelImportUtil;
import com.upd.hwcloud.common.utils.ncov.MemoryPageUtil;
import com.upd.hwcloud.service.IBigdataService;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IThreePartyInterfaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 第三方接口表 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-12-03
 */
@Api(description = "第三方接口")
@Controller
@RequestMapping("/api/threePartyInterface")
public class ThreePartyInterfaceController {

    @Autowired
    IThreePartyInterfaceService threePartyInterfaceService;
    @Autowired
    IBigdataService bigdataService;

    @Autowired
    private IFilesService filesService;

    @Value("${ncov.important.access}")
    private String importantAccessStr;

    private static final ImmutableMap<String, String> shortNameMapping = ImmutableMap.<String, String>builder()
            .put("中国民用航空广东安全监督管理局", "民航安监局").put("市场监督管理局", "市场监管局")
            .put("文化和旅游厅", "文旅厅").put("省住房城乡建设厅", "住建厅").put("人力资源保障厅", "人社厅")
            .build();


    @ApiOperation("保存第三方接口")
    @RequestMapping("/save")
    @ResponseBody
    public R save(ThreePartyInterface threePartyInterface) {
        threePartyInterface.insertOrUpdate();
        return R.ok();
    }

//    @RequestMapping("/test")
//    public void test() {
//        Integer value = 2000;
//        Integer ext2 = 23116;
//        for(int i=0; i<30; i++) {
//            JSONObject jsonObject = new JSONObject();
//            JSONObject jsonObject1 = new JSONObject();
//            jsonObject1.put("value",String.valueOf(value));
//            jsonObject1.put("ext1","2000条");
//            jsonObject1.put("ext2",String.valueOf(ext2));
//            jsonObject1.put("ext3","2.31万条");
//            jsonObject.put("msg","操作成功");
//            jsonObject.put("code",0);
//            jsonObject.put("data",jsonObject1);
//            value += 772;
//            ext2 += 6542;
//
//            ThreePartyInterface threePartyInterface = new ThreePartyInterface();
//            threePartyInterface.setId(UUIDUtil.getUUID());
//            threePartyInterface.setLabel("实时数据OUT");
//            threePartyInterface.setData(jsonObject.toString());
//            threePartyInterface.insert();
//        }
//    }


    @ApiOperation("疫情数据接入概览")
    @RequestMapping("/ncov/import/overview")
    @ResponseBody
    public JSONObject ncovImportOverview() {
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getById("ncovImportOverview");
        return JSONObject.parseObject(threePartyInterface.getData());

    }

    @ApiOperation("疫情数据接入-最近七天")
    @RequestMapping("/ncov/import/recentAll")
    @ResponseBody
    public JSONObject ncovImportRecentAll() {
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getById("ncovImportRecentAll");
        System.out.println(JSONObject.parseObject(threePartyInterface.getData()));
        System.out.println("String" + threePartyInterface.getData());
        return JSONObject.parseObject(threePartyInterface.getData(), Feature.OrderedField);

    }

    @ApiOperation("疫情数据接入-单位数据")
    @RequestMapping("/ncov/import/department")
    @ResponseBody
    public JSONObject ncovImportDepartment() {
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getById("ncovImportDepartment");
        return JSONObject.parseObject(threePartyInterface.getData());
    }

    @ApiOperation("疫情数据接入-重点数据")
    @RequestMapping("/ncov/import/importantData")
    @ResponseBody
    public JSONObject ncovImporImportantData() {
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getById("ncovImportImportantData");
        return JSONObject.parseObject(threePartyInterface.getData(), Feature.OrderedField);

    }

    @ApiOperation("疫情数据接入-要素总览")
    @RequestMapping("/ncov/import/elementOverview")
    @ResponseBody
    public R ncovImportElementOverview() {

        return R.ok(ncovImportElementTypeOverview());
    }

    @ApiOperation("疫情数据接入-要素部门")
    @RequestMapping("/ncov/import/departmentOfElement")
    @ResponseBody
    public R ncovImportDepartmentOfElementC(String type) {

        return R.ok(ncovImportDepartmentOfElement(type));
    }


    @ApiOperation("疫情-部门要素目录分页")
    @RequestMapping("/ncov/import/departmentOfElement/detailPage")
    @ResponseBody
    public R ncovImportDepartmentOfElementPage(@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "1") int pageNum, String type, String dept, String name) {
        List<NcovExcelSheetOneVo> fullInfoList = getFullNcovExcelInfo();
        dealCarType(fullInfoList);
        List<Files> attachmentList = getAllAttachment(fullInfoList);
        List<NcovExcelSheetOneVo> attachmentFirstList = getAttachmentFirstSort(fullInfoList, attachmentList);
        Page<NcovExcelSheetOneVo> page = MemoryPageUtil.ncovPageByDeptTypeAndName(attachmentFirstList, pageSize, pageNum, dept, type, name);
        return R.ok(page);


    }

    @ApiOperation("疫情数据接入-分页")
    @RequestMapping("ncov/import/page")
    @ResponseBody
    public R ncovImportPage(@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "1") int pageNum, String name) {

        List<NcovExcelSheetOneVo> fullInfoList = getFullNcovExcelInfo();
        List<Files> attachmentList = getAllAttachment(fullInfoList);

        List<NcovExcelSheetOneVo> attachmentFirstList = getAttachmentFirstSort(fullInfoList, attachmentList);
        Page<NcovExcelSheetOneVo> page = MemoryPageUtil.ncovPageByName(attachmentFirstList, pageSize, pageNum, name);
        return R.ok(page);

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
                if (StringUtils.equals(two.getTableCnName(), one.getTableCnName())) {
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
                if (StringUtils.equals(attachment.getTitle(), sheetOneVo.getTableCnName())) {
                    sheetOneVo.setAttachmentUrl(attachment.getUrl());
                    sheetOneVo.setAttachmentName(attachment.getOriginaName());
                }
            });
        });
        List<NcovExcelSheetOneVo> attachmentFirstList = fullInfoList.stream().sorted(Comparator.comparing(i -> i.getAttachmentUrl(), Comparator.nullsLast(String::compareTo))).collect(Collectors.toList());
        return attachmentFirstList;
    }


    /**
     * 解析要素类型概览
     */
    private List<NcovOverview> ncovImportElementTypeOverview() {


        List<NcovExcelSheetOneVo> fullInfoList = getFullNcovExcelInfo();
        dealCarType(fullInfoList);

        Map<String, Long> tableNum = fullInfoList.stream().collect(Collectors.groupingBy(NcovExcelSheetOneVo::getElementType, Collectors.counting()));

        Map<String, Long> allNum = fullInfoList.stream().collect(Collectors.groupingBy(NcovExcelSheetOneVo::getElementType, Collectors.summingLong(NcovExcelSheetOneVo::getDataTotalNum)));

        Map<String, Long> upNum = fullInfoList.stream().collect(Collectors.groupingBy(NcovExcelSheetOneVo::getElementType, Collectors.summingLong(NcovExcelSheetOneVo::getDataUpOfYesterday)));


        return dealGroupMapDataOfNcov(allNum, upNum, tableNum);

    }

    /**
     * 车辆 处理为 车
     *
     * @param sheetOneVoList
     */
    private void dealCarType(List<NcovExcelSheetOneVo> sheetOneVoList) {
        sheetOneVoList.forEach(item -> {
            if (StringUtils.equals("车辆", item.getElementType())) {
                item.setElementType("车");
            }
        });
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
        List<NcovExcelSheetOneVo> typeList = fullInfoList.stream().filter(item -> StringUtils.equals(type, item.getElementType())).collect(Collectors.toList());

        Map<String, Long> tableNum = typeList.stream().collect(Collectors.groupingBy(NcovExcelSheetOneVo::getDepartmentOfData, Collectors.counting()));

        Map<String, Long> all = typeList.stream().collect(Collectors.groupingBy(NcovExcelSheetOneVo::getDepartmentOfData, Collectors.summingLong(NcovExcelSheetOneVo::getDataTotalNum)));

        Map<String, Long> yesterday = typeList.stream().collect(Collectors.groupingBy(NcovExcelSheetOneVo::getDepartmentOfData, Collectors.summingLong(NcovExcelSheetOneVo::getDataUpOfYesterday)));

        List<NcovOverview> overviewList = dealGroupMapDataOfNcov(all, yesterday, tableNum);


        return overviewList;
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


    @ApiOperation("daas总览")
    @RequestMapping("/zonglan")
    @ResponseBody
    public JSONObject zonglan() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("zonglan");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("daas总览-近10日每日增量趋势图")
    @RequestMapping("/zlCount10")
    @ResponseBody
    public JSONObject zlCount10() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("zlCount10");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    //首页daas 美亚接口
    @ApiOperation("数据接入（数据概况）")
    @RequestMapping("/fiveBigResource")
    @ResponseBody
    public JSONObject fiveBigResource() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("fiveBigResource");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("地市警种DaaS接入数据接口（手动配置）")
    @RequestMapping(value = "/{belong}/fiveBigResource", method = RequestMethod.GET)
    @ApiImplicitParam(name = "belong", value = "警种/地市", dataType = "string", paramType = "path", example = "梅州")
    @ResponseBody
    public JSONObject areaOrPoliceFiveBigResource(@PathVariable String belong) {
        StringBuilder labelBuilder = new StringBuilder(belong);
        labelBuilder.append("DaaS总览");
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(new QueryWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getLabel, labelBuilder.toString()));
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("地市警种IaaS总览数据接口（手动配置）")
    @RequestMapping(value = "/{belong}/iaasOverview", method = RequestMethod.GET)
    @ApiImplicitParam(name = "belong", value = "警种/地市", dataType = "string", paramType = "path", example = "梅州")
    @ResponseBody
    public JSONObject areaOrPoliceIaaS(@PathVariable String belong) {
        StringBuilder labelBuilder = new StringBuilder(belong);
        labelBuilder.append("IaaS总览");
        ThreePartyInterface iaasOverview = threePartyInterfaceService.getOne(new QueryWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getLabel, labelBuilder.toString()));
        return JSONObject.parseObject(iaasOverview.getData());
    }


    @ApiOperation("原始库数据")
    @RequestMapping("/fiveBigResource01")
    @ResponseBody
    public JSONObject fiveBigResource01() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("fiveBigResource01");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("资源库数据")
    @RequestMapping("/fiveBigResource02")
    @ResponseBody
    public JSONObject fiveBigResource02() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("fiveBigResource02");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("主题库数据")
    @RequestMapping("/fiveBigResource03")
    @ResponseBody
    public JSONObject fiveBigResource03() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("fiveBigResource03");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("知识库数据")
    @RequestMapping("/fiveBigResource04")
    @ResponseBody
    public JSONObject fiveBigResource04() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("fiveBigResource04");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("业务库数据")
    @RequestMapping("/fiveBigResource05")
    @ResponseBody
    public JSONObject fiveBigResource05() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("fiveBigResource05");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("业务要素索引库（接口）")
    @RequestMapping("/fiveBigResource06")
    @ResponseBody
    public JSONObject fiveBigResource06() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("fiveBigResource06");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("知识图谱库")
    @RequestMapping("/resourceRelationCount")
    @ResponseBody
    public JSONObject resourceRelationCount() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("resourceRelationCount");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("数据治理")
    @RequestMapping("/dataGovern")
    @ResponseBody
    public JSONObject dataGovern() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("dataGovern");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("地市上报")
    @RequestMapping("/resourceDataSource02")
    @ResponseBody
    public JSONObject resourceDataSource02() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("resourceDataSource02");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("警种及行业公安共享")
    @RequestMapping("/resourceDataSource01")
    @ResponseBody
    public JSONObject resourceDataSource01() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("resourceDataSource01");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("公安部下发")
    @RequestMapping("/resourceDataSource04")
    @ResponseBody
    public JSONObject resourceDataSource04() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("resourceDataSource04");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("政府部门及企事业单位共享")
    @RequestMapping("/resourceDataSource03")
    @ResponseBody
    public JSONObject resourceDataSource03() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("resourceDataSource03");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("外省共享")
    @RequestMapping("/resourceDataSource05")
    @ResponseBody
    public JSONObject resourceDataSource05() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("resourceDataSource05");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("数据对账")
    @RequestMapping("/dataCheckCount")
    @ResponseBody
    public JSONObject dataCheckCount() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("dataCheckCount");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("数据库监控")
    @RequestMapping("/dbConfig")
    @ResponseBody
    public JSONObject dbConfig() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("dbConfig");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("资源分级分类")
    @RequestMapping("/resourceSensitivity")
    @ResponseBody
    public JSONObject resourceSensitivity() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("resourceSensitivity");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("数据处理概况")
    @RequestMapping("/sjcjgk")
    @ResponseBody
    public JSONObject sjcjgk() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("sjcjgk");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("数据质量管理")
    @RequestMapping("/sjzlgl")
    @ResponseBody
    public JSONObject sjzlgl() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("sjzlgl");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("数据血缘管理")
    @RequestMapping("/sjxygk")
    @ResponseBody
    public JSONObject sjxygk() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("sjxygk");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("资源目录总数")
    @RequestMapping("/resourceTotal")
    @ResponseBody
    public JSONObject resourceTotal() {
        ThreePartyInterface resourceTotal = threePartyInterfaceService.getById("resourceTotal");
        return JSONObject.parseObject(resourceTotal.getData());
    }

    @ApiOperation("数据分级分类")
    @RequestMapping("/dataSensitivity")
    @ResponseBody
    public JSONObject dataSensitivity() {
        ThreePartyInterface dataSensitivity = threePartyInterfaceService.getById("dataSensitivity");
        return JSONObject.parseObject(dataSensitivity.getData());
    }

    @ApiOperation("数据运维管理")
    @RequestMapping("/dataOperManage")
    @ResponseBody
    public JSONObject dataOperManage() {
        ThreePartyInterface dataOperManage = threePartyInterfaceService.getById("dataOperManage");
        return JSONObject.parseObject(dataOperManage.getData());
    }


    //二级页面美亚接口

    @ApiOperation("近7天每日增量趋势图")
    @RequestMapping("/dataCount")
    @ResponseBody
    public JSONObject dataCount() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("dataCount");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("各网域资源/数据共享占比")
    @RequestMapping("/resourceSourceCount")
    @ResponseBody
    public JSONObject resourceSourceCount() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("resourceSourceCount");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("近7天JZ数据每日增量总数")
    @RequestMapping("/dataCount07")
    @ResponseBody
    public JSONObject dataCount07() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("dataCount07");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("近7天WA数据每日增量总数")
    @RequestMapping("/dataCount08")
    @ResponseBody
    public JSONObject dataCount08() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("dataCount08");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("每个地市的总数据量")
    @RequestMapping("/cityDataCountList")
    @ResponseBody
    public JSONObject cityDataCountList() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("cityDataCountList");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("每个地市的总表数")
    @RequestMapping("/cityDataCountTable")
    @ResponseBody
    public JSONObject cityDataCountTable() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("cityDataCountTable");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("每个地市的昨日增量数")
    @RequestMapping("/yesterdayCount")
    @ResponseBody
    public JSONObject yesterdayCount() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("yesterdayCount");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("已汇聚地市数据总量")
    @RequestMapping("/cityDataCountSum")
    @ResponseBody
    public JSONObject cityDataCountSum() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("cityDataCountSum");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("已汇聚资源数量")
    @RequestMapping("/cityDataCountSumTable")
    @ResponseBody
    public JSONObject cityDataCountSumTable() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("cityDataCountSumTable");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("本周数据增量")
    @RequestMapping("/cityDataIncrementDay7")
    @ResponseBody
    public JSONObject cityDataIncrementDay7() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("cityDataIncrementDay7");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("昨日数据增量")
    @RequestMapping("/cityDataIncrementDay1")
    @ResponseBody
    public JSONObject cityDataIncrementDay1() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("cityDataIncrementDay1");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("公安网共享")
    @RequestMapping("/dataGroupCountJz")
    @ResponseBody
    public JSONObject dataGroupCountJz() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("dataGroupCountJz");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("政府部门间共享(含企事业单位共享)")
    @RequestMapping("/dataGroupCountBm")
    @ResponseBody
    public JSONObject dataGroupCountBm() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("dataGroupCountBm");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("公安部下发")
    @RequestMapping("/findPartSend")
    @ResponseBody
    public JSONObject findPartSend() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("findPartSend");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("外省共享")
    @RequestMapping("/departmentCount")
    @ResponseBody
    public JSONObject departmentCount() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("departmentCount");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("近30天数据处理趋势")
    @RequestMapping("/sjclqst")
    @ResponseBody
    public JSONObject sjclqst() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("sjclqst");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("近7天数据标准化资源数/数据量")
    @RequestMapping("/resourceScale")
    @ResponseBody
    public JSONObject resourceScale() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("resourceScale");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("数据元")
    @RequestMapping("/cityDataCountNew")
    @ResponseBody
    public JSONObject cityDataCountNew() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("cityDataCountNew");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("资源分级分类概况")
    @RequestMapping("/resourceClassCount")
    @ResponseBody
    public JSONObject resourceClassCount() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("resourceClassCount");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("分类概况")
    @RequestMapping("/queryCountGroupBySectionClass")
    @ResponseBody
    public JSONObject queryCountGroupBySectionClass() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("queryCountGroupBySectionClass");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("近7天异常更新率/缺业务代码资源占比趋势")
    @RequestMapping("/zyzbqst")
    @ResponseBody
    public JSONObject zyzbqst() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("zyzbqst");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("近7天资源更新异常率TOP10")
    @RequestMapping("/zygxycl")
    @ResponseBody
    public JSONObject zygxycl() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("zygxycl");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("近7天更新异常资源所属警种TOP10")
    @RequestMapping("/gxyczyssjz")
    @ResponseBody
    public JSONObject gxyczyssjz() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("gxyczyssjz");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("要素字段空值率资源TOP10")
    @RequestMapping("/yszdkzl")
    @ResponseBody
    public JSONObject yszdkzl() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("yszdkzl");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("资源所属警种平均空值率TOP10")
    @RequestMapping("/zyssjzpjkzl")
    @ResponseBody
    public JSONObject zyssjzpjkzl() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("zyssjzpjkzl");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("要素字段值合格率资源LAST10")
    @RequestMapping("/yszdzhglzy")
    @ResponseBody
    public JSONObject yszdzhglzy() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("yszdzhglzy");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("资源所属警种字段值合格率LAST10")
    @RequestMapping("/zyssjzzdzhgl")
    @ResponseBody
    public JSONObject zyssjzzdzhgl() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("zyssjzzdzhgl");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("缺业务代码资源TOP10")
    @RequestMapping("/qywdmzy")
    @ResponseBody
    public JSONObject qywdmzy() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("qywdmzy");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("资源所属警种缺业务代码TOP10")
    @RequestMapping("/zyssjzqywdm")
    @ResponseBody
    public JSONObject zyssjzqywdm() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("zyssjzqywdm");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("原始库近30天每日增量趋势图")
    @RequestMapping("/dataCount3001")
    @ResponseBody
    public JSONObject dataCount3001() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("dataCount3001");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("数据库异常清单")
    @RequestMapping("/dbConfigList")
    @ResponseBody
    public Object dbConfigList(@RequestParam(required = false, defaultValue = "1") String page
            , @RequestParam(required = false, defaultValue = "10") String limit) {
        //http://68.26.20.21/services/serviceInvoke/queryDataService/foreign/dataCount/dbConfigList?page=1&limit=10
        String url = "http://15.40.3.71:8000/services/serviceInvoke/queryDataService/foreign/dataCount/dbConfigList?page=" + page + "&limit=" + limit;
        threePartyInterfaceService.getOrUpdateData(url, "dbConfigList", "数据库异常清单");
        ThreePartyInterface dbConfigList = threePartyInterfaceService.getById("dbConfigList");
        if (dbConfigList != null) {
            return JSONObject.parseObject(dbConfigList.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("资源异常清单")
    @RequestMapping("/dataCheckList")
    @ResponseBody
    public Object dataCheckList(@RequestParam(required = false, defaultValue = "1") String page
            , @RequestParam(required = false, defaultValue = "10") String limit) {
        //http://68.26.20.21/services/serviceInvoke/queryDataService/foreign/dataCount/dbConfigList?page=1&limit=10
        String url = "http://15.40.3.71:8000/services/serviceInvoke/queryDataService/foreign/dataCount/dataCheckList?page=" + page + "&limit=" + limit;
        threePartyInterfaceService.getOrUpdateData(url, "dataCheckList", "数据库异常清单");
        ThreePartyInterface dbConfigList = threePartyInterfaceService.getById("dataCheckList");
        if (dbConfigList != null) {
            return JSONObject.parseObject(dbConfigList.getData());
        }
        return new JSONObject();
    }

    //数据目录板块
    @ApiOperation("筛选条件")
    @RequestMapping("/attrCategory")
    @ResponseBody
    public JSONObject attrCategory() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("attrCategory");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("常用标签")
    @RequestMapping("/dataTag")
    @ResponseBody
    public JSONObject dataTag() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("dataTag");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("获取资源分类")
    @RequestMapping("/resourceCategory")
    @ResponseBody
    public JSONObject resourceCategory() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("resourceCategory");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("数据目录列表")
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject pageQuery(@RequestBody JSONObject jsonObject) {
        String jsonString = jsonObject.getJSONObject("dataJson").toJSONString();
        jsonObject.remove("dataJson");
        jsonObject.put("dataJson", jsonString);
        threePartyInterfaceService.postJsonUpdateData("http://15.40.3.71:8000/services/serviceInvoke/queryDataService/foreign/dataResource/action/pageQuery",
                "pageQuery", "数据目录列表", jsonObject.toString());
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("pageQuery");
        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("数据表结构")
    @RequestMapping(value = "/sourceStandardFieldMappingList", method = RequestMethod.GET)
    @ResponseBody
    public Object sourceStandardFieldMappingList(String id) {
        //http://68.26.20.21/services/serviceInvoke/queryDataService/foreign/dataCount/dbConfigList?page=1&limit=10
        String url = "http://15.40.3.71:8000/services/serviceInvoke/queryDataService/foreign/dataResource/action/sourceStandardFieldMappingList?resourceId=" + id;
        threePartyInterfaceService.getOrUpdateData(url, "sourceStandardFieldMappingList", "数据表结构");
        ThreePartyInterface dbConfigList = threePartyInterfaceService.getById("sourceStandardFieldMappingList");
        if (dbConfigList != null) {
            return JSONObject.parseObject(dbConfigList.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("获取实时数据24条")
    @RequestMapping(value = "/getRealTimeData24", method = RequestMethod.GET)
    @ResponseBody
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

    @ApiOperation("获取实时数据In")
    @RequestMapping("/getRealTimeDataIn")
    @ResponseBody
    public JSONObject getRealTimeDataIn() {
        ThreePartyInterface one = threePartyInterfaceService.getOne(new QueryWrapper<ThreePartyInterface>()
                .eq("LABEL", "实时数据IN").orderByDesc("CREATE_TIME"));
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        JSONObject jsonObject = JSONObject.parseObject(one.getData());
        jsonObject.getJSONObject("data").put("time", fmt.format(one.getCreateTime()));
        return jsonObject;
//        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("getRealTimeDataIn");
//        return JSONObject.parseObject(fiveBigResource.getData());
    }

    @ApiOperation("获取实时数据Out")
    @RequestMapping("/getRealTimeDataOut")
    @ResponseBody
    public JSONObject getRealTimeDataOut() {
        ThreePartyInterface one = threePartyInterfaceService.getOne(new QueryWrapper<ThreePartyInterface>()
                .eq("LABEL", "实时数据OUT").orderByDesc("CREATE_TIME"));
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        JSONObject jsonObject = JSONObject.parseObject(one.getData());
        jsonObject.getJSONObject("data").put("time", fmt.format(one.getCreateTime()));
        return jsonObject;
//        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("getRealTimeDataOut");
//        return JSONObject.parseObject(fiveBigResource.getData());
    }

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

    //后台配置 华为接口
    @ApiOperation("服务总量")
    @RequestMapping("/fwzl")
    @ResponseBody
    public JSONObject fwzl() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "服务总量"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        return new JSONObject();
    }

    @ApiOperation("查询检索服务总数")
    @RequestMapping("/cxjs")
    @ResponseBody
    public JSONObject cxjs() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "查询检索服务总数"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        return new JSONObject();
    }

    @ApiOperation("布控订阅服务总数")
    @RequestMapping("/bkdy")
    @ResponseBody
    public JSONObject bkdy() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "布控订阅服务总数"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        return new JSONObject();
    }

    @ApiOperation("模型分析服务总数")
    @RequestMapping("/mxfx")
    @ResponseBody
    public JSONObject mxfx() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "模型分析服务总数"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        return new JSONObject();
    }

    @ApiOperation("数据推送服务总数")
    @RequestMapping("/sjts")
    @ResponseBody
    public JSONObject sjts() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "数据推送服务总数"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        return new JSONObject();
    }

    @ApiOperation("近7天系统调用top10")
    @RequestMapping("/xtdytop")
    @ResponseBody
    public JSONObject xtdytop() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "近7天系统调用top10"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        return new JSONObject();
    }

    @ApiOperation("系统调用导出")
    @RequestMapping("/xtdytop/export")
    public void xtdytopExport(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "starTime", required = false) String starTime,
                              @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "starTime1", required = false) String starTime1, @RequestParam(value = "endTime1", required = false) String endTime1,
                              @RequestParam(value = "area", required = false) String area, @RequestParam(value = "category", required = false) String category) throws Exception {
        List<XtdyExport> normalData = threePartyInterfaceService.xtdyTop(true, starTime, endTime);
        ExportView normalDataView = new ExportView.Builder()
                .exportParams(new ExportParams(null, "应用调用服务详情"))
                .cls(XtdyExport.class)
                .dataList(normalData)
                .create();

        List<XtdyExport> abnormalData = threePartyInterfaceService.xtdyTop(false, starTime, endTime);
        ExportView abnormalDataView = new ExportView.Builder()
                .exportParams(new ExportParams(null, "不规范名称数据"))
                .cls(XtdyExport.class)
                .dataList(abnormalData)
                .create();

        List<InstanceExport> instanceData = threePartyInterfaceService.daasInstance(starTime1, endTime1, area, category);
        ExportView instanceDataView = new ExportView.Builder()
                .exportParams(new ExportParams(null, "应用订阅服务"))
                .cls(InstanceExport.class)
                .dataList(instanceData)
                .create();

        List<BigdataExport> serviceData = threePartyInterfaceService.serviceMessage();
        ExportView serviceDataView = new ExportView.Builder()
                .exportParams(new ExportParams(null, "服务清单"))
                .cls(BigdataExport.class)
                .dataList(serviceData)
                .create();

        List<ExportView> moreViewList = Lists.newArrayList();
        moreViewList.add(normalDataView);
        moreViewList.add(abnormalDataView);
        moreViewList.add(instanceDataView);
        moreViewList.add(serviceDataView);
        ExportMoreView moreView = new ExportMoreView();
        moreView.setMoreViewList(moreViewList);
        ExcelUtil.exportMoreView(request, response, "DaaS服务详情信息表", moreView);
    }

    @ApiOperation("系统调用top10(全量)")
    @RequestMapping("/xtdytopAll")
    @ResponseBody
    public JSONObject xtdytopAll() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "系统调用Top10"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("近7天服务访问top10")
    @RequestMapping("/fwfwtop")
    @ResponseBody
    public JSONObject fwfwtop() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "近7天服务访问top10"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("服务访问导出")
    @RequestMapping("/fwfwtop/export")
    public void fwfwtopExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<DaasFwTopDTO> datas = threePartyInterfaceService.fwfwTop10();
        HSSFWorkbook excle = ExcelUtil.createExcel(datas, DaasFwTopDTO.class, "服务被调用");
        ExcelUtil.export(request, response, "服务被调用信息表", excle);
    }

    @ApiOperation("服务访问top10(全量)")
    @RequestMapping("/fwfwtopAll")
    @ResponseBody
    public JSONObject fwfwtopAll() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "服务访问top10"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("最新挂载服务top10")
    @RequestMapping("/zxgztop")
    @ResponseBody
    public JSONObject zxgztop() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "最新挂载服务top10"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        return new JSONObject();
    }

    @ApiOperation("最新挂载服务导出")
    @RequestMapping("/zxgztop/export")
    public void zxgztopExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<DaasFwTopDTO> datas = threePartyInterfaceService.zxgzTop10();
        HSSFWorkbook excle = ExcelUtil.createExcel(datas, DaasFwTopDTO.class, "最新挂载服务");
        ExcelUtil.export(request, response, "最新挂载服务信息表", excle);
    }

    @ApiOperation("应用调用服务Top10")
    @RequestMapping("/yyfwtop")
    @ResponseBody
    public JSONObject yyfwtop() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "应用调用服务Top10"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("警种调用Top5")
    @RequestMapping("/jzdytop5")
    @ResponseBody
    public JSONObject jzdytop5() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "警种调用Top5"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("地市调用Top5")
    @RequestMapping("/dsdytop5")
    @ResponseBody
    public JSONObject dsdytop5() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "地市调用Top5"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("高频订阅高频访问服务Top10")
    @RequestMapping("/hDyhFwFwTop")
    @ResponseBody
    public JSONObject hDyhFwFwTop() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "高频订阅高频访问服务Top10"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("低频订阅高频访问服务Top10")
    @RequestMapping("/lDyhFwFwTop")
    @ResponseBody
    public JSONObject lDyhFwFwTop() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "低频订阅高频访问服务Top10"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("高频订阅低频访问系统Top10")
    @RequestMapping("/hDylFwXtTop")
    @ResponseBody
    public JSONObject hDylFwXtTop() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "高频订阅低频访问系统Top10"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());
        }
        return new JSONObject();
    }

    @ApiOperation("近7天服务请求总体趋势")
    @RequestMapping("/ztqs7day")
    @ResponseBody
    public JSONObject ztqs7day() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "近7天服务请求总体趋势"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        return null;
    }

    @ApiOperation("近7天服务响应耗时趋势")
    @RequestMapping("/hsqs7day")
    @ResponseBody
    public JSONObject hsqs7day() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "近7天服务响应耗时趋势"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        return null;
    }

    //首页iaas top5low5配置接口
    @ApiOperation("全网资源Top5")
    @RequestMapping("/qwtop5")
    @ResponseBody
    public Object qwtop5() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "全网资源Top5"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("全网资源Bot5")
    @RequestMapping("/qwbop5")
    @ResponseBody
    public Object qwbop5() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "全网资源Bot5"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("警种资源Top5")
    @RequestMapping("/jztop5")
    @ResponseBody
    public Object jztop5() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "警种资源Top5"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("警种资源Bot5")
    @RequestMapping("/jzbop5")
    @ResponseBody
    public Object jzbop5() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "警种资源Bot5"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("地市资源情况Top5")
    @RequestMapping("/dqtop5")
    @ResponseBody
    public Object dqtop5() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "地市资源情况Top5"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("地市资源情况Bot5")
    @RequestMapping("/dqbop5")
    @ResponseBody
    public Object dqbop5() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "地市资源情况Bot5"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    //后台配置 daas数据

    @ApiOperation("daas数据总量")
    @RequestMapping(value = "/total", method = RequestMethod.GET)
    @ResponseBody
    public Object total() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "daas数据总量"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("daas数据治理")
    @RequestMapping(value = "/govern", method = RequestMethod.GET)
    @ResponseBody
    public Object govern() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "daas数据治理"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("daas数据质量")
    @RequestMapping(value = "/quality", method = RequestMethod.GET)
    @ResponseBody
    public Object quality() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "daas数据质量"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("业务要素索引库")
    @RequestMapping(value = "/ywyssyk", method = RequestMethod.GET)
    @ResponseBody
    public Object ywyssyk() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "业务要素索引库"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("知识图谱库")
    @RequestMapping(value = "/zstpk", method = RequestMethod.GET)
    @ResponseBody
    public Object zstpk() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "知识图谱库"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("标签库")
    @RequestMapping(value = "/bqk", method = RequestMethod.GET)
    @ResponseBody
    public Object bqk() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "标签库"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    //iaas配置
    @ApiOperation("大数据工程资源情况(二期)")
    @RequestMapping(value = "/dsjgc/t2", method = RequestMethod.GET)
    @ResponseBody
    public Object dsjgcT2() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "大数据工程资源情况"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("大数据工程资源情况(一期)")
    @RequestMapping(value = "/dsjgc/t1", method = RequestMethod.GET)
    @ResponseBody
    public Object dsjgcT1() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "大数据工程资源情况(一期)"));
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("大数据工程资源情况(全部)")
    @RequestMapping(value = "/dsjgc", method = RequestMethod.GET)
    @ResponseBody
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

    //模型超市接口
    @ApiOperation("模型超市按模型上线时间排行")
    @RequestMapping("/apimodel")
    @ResponseBody
    public Object apimodel() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("apimodel");
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("模型超市按模型热度排行")
    @RequestMapping("/apimodelHot")
    @ResponseBody
    public Object apimodelHot() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("apimodelHot");
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    @ApiOperation("搜索模型")
    @RequestMapping("/searchModel")
    @ResponseBody
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

    //决赛模型接口
    @ApiOperation("模型超市按决赛取")
    @RequestMapping("/modeljuesai")
    @ResponseBody
    public Object modeljuesai() {
        ThreePartyInterface fiveBigResource = threePartyInterfaceService.getById("modeljuesai");
        if (fiveBigResource != null) {
            return JSONObject.parseObject(fiveBigResource.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new ArrayList<>());
        return map;
    }

    //五大库二级页面接口
    @ApiOperation("五大库二级页面类型")
    @RequestMapping("/wdkSubPageType")
    @ResponseBody
    public Object wdkSubPageType(String resourceFirstClass) throws Exception {
        String url = "http://15.40.3.71:8000/services/serviceInvoke/queryDataService/foreign/statisticsSecond/getSecondClass?resourceFirstClass=" + resourceFirstClass;
        String res = OkHttpUtils.get(url, null).body().string();
        return JSONObject.parseObject(res);
    }

    @ApiOperation("五大库二级页面详情")
    @RequestMapping("/wdkSubPageInfo")
    @ResponseBody
    public Object wdkSubPageInfo(String resourceFirstClass, String resourceSecondClass) throws Exception {
        String url = "http://15.40.3.71:8000/services/serviceInvoke/queryDataService/foreign/statisticsSecond/getSecondClassCount?resourceFirstClass=" + resourceFirstClass + "&resourceSecondClass=" + resourceSecondClass;
        String res = OkHttpUtils.get(url, null).body().string();
        return JSONObject.parseObject(res);
    }

    /********************************************************
     *********************** PAAS服务市场 *********************
     *********************************************************/

    @ApiOperation("PAAS首页概况")
    @RequestMapping("/paasGk")
    @ResponseBody
    public Object paasGk() {
        ThreePartyInterface data = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "PAAS首页概况"));
        if (data != null) {
            return JSONObject.parseObject(data.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new JSONObject());
        return map;
    }

    @ApiOperation("PAAS服务订阅统计")
    @RequestMapping("/paasFwdytj")
    @ResponseBody
    public Object paasFwdytj() {
        ThreePartyInterface data = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "PAAS服务订阅统计"));
        if (data != null) {
            return JSONObject.parseObject(data.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new JSONObject());
        return map;
    }

    @ApiOperation("PAAS订阅Top10")
    @RequestMapping("/paasDyTop10")
    @ResponseBody
    public Object paasDyTop10() {
        ThreePartyInterface data = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "PAAS订阅Top10"));
        if (data != null) {
            return JSONObject.parseObject(data.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new JSONObject());
        return map;
    }

    @ApiOperation("PAAS支持警种导出")
    @RequestMapping("/paasZcjz/export")
    public void paasZcjzExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TopJzDTO> datas = threePartyInterfaceService.paasZcjzExport();
        HSSFWorkbook excle = ExcelUtil.createExcel(datas, TopJzDTO.class, "支撑警种");
        ExcelUtil.export(request, response, "支撑警种信息表", excle);
    }

    @ApiOperation("PAAS服务订阅导出")
    @RequestMapping("/paasFwDy/export")
    public void paasFwDyExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TopBdyDTO> datas = threePartyInterfaceService.paasFwDyExport();
        HSSFWorkbook excle = ExcelUtil.createExcel(datas, TopBdyDTO.class, "服务被订阅");
        ExcelUtil.export(request, response, "服务被订阅信息表", excle);
    }

    @ApiOperation("PAAS系统订阅导出")
    @RequestMapping("/paasXtSub/export")
    public void paasXtSubExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TopDyDTO> datas = threePartyInterfaceService.paasXtSubExport();
        HSSFWorkbook excle = ExcelUtil.createExcel(datas, TopDyDTO.class, "应用订阅");
        ExcelUtil.export(request, response, "应用订阅信息表", excle);
    }

    @ApiOperation("PAAS服务近十日访问趋势")
    @RequestMapping("/paasFwfwqs")
    @ResponseBody
    public Object paasFwfwqs() {
        ThreePartyInterface data = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "PAAS服务近十日访问趋势"));
        if (data != null) {
            return JSONObject.parseObject(data.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new JSONObject());
        return map;
    }

    @ApiOperation("PAAS警种地市调用Top5")
    @RequestMapping("/paasJzdsTop5")
    @ResponseBody
    public Object paasJzdsTop5() {
        ThreePartyInterface data = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "PAAS警种地市调用Top5"));
        if (data != null) {
            return JSONObject.parseObject(data.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new JSONObject());
        return map;
    }

    @ApiOperation("PAAS服务系统Top10")
    @RequestMapping("/paasXtfwTop10")
    @ResponseBody
    public Object paasXtfwTop10() {
        ThreePartyInterface data = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "PAAS服务系统Top10"));
        if (data != null) {
            return JSONObject.parseObject(data.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new JSONObject());
        return map;
    }

    @ApiOperation("PAAS服务访问导出")
    @RequestMapping("/paasFwfw/export")
    public void paasFwfwExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Top10DTO> datas = threePartyInterfaceService.paasFwfwExport();
        HSSFWorkbook excle = ExcelUtil.createExcel(datas, Top10DTO.class, "服务被调用");
        ExcelUtil.export(request, response, "服务被调用信息表", excle);
    }

    @ApiOperation("PAAS系统调用导出")
    @RequestMapping("/paasXtdy/export")
    public void paasXtdyExport(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "starTime", required = false) String starTime,
                               @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "starTime1", required = false) String starTime1, @RequestParam(value = "endTime1", required = false) String endTime1,
                               @RequestParam(value = "area", required = false) String area, @RequestParam(value = "category", required = false) String category) throws Exception {
        List<XtdyExport> normalData = threePartyInterfaceService.paasXtdyExport(true, starTime, endTime);
        ExportView normalDataView = new ExportView.Builder()
                .exportParams(new ExportParams(null, "应用调用服务详情"))
                .cls(XtdyExport.class)
                .dataList(normalData)
                .create();

        List<XtdyExport> abnormalData = threePartyInterfaceService.paasXtdyExport(false, starTime, endTime);
        ExportView abnormalDataView = new ExportView.Builder()
                .exportParams(new ExportParams(null, "不规范名称数据"))
                .cls(XtdyExport.class)
                .dataList(abnormalData)
                .create();

        List<InstanceExport> instanceData = threePartyInterfaceService.paasInstance(starTime1, endTime1, area, category);
        ExportView instanceDataView = new ExportView.Builder()
                .exportParams(new ExportParams(null, "应用订阅服务"))
                .cls(InstanceExport.class)
                .dataList(instanceData)
                .create();

        List<Bigdata> serviceData = bigdataService.list(new QueryWrapper<Bigdata>().lambda()
                .in(Bigdata::getCataLog, 8, 9, 10).orderByAsc(Bigdata::getCreateTime));
        ExportView serviceDataView = new ExportView.Builder()
                .exportParams(new ExportParams(null, "服务清单"))
                .cls(Bigdata.class)
                .dataList(serviceData)
                .create();

        List<ExportView> moreViewList = Lists.newArrayList();
        moreViewList.add(normalDataView);
        moreViewList.add(abnormalDataView);
        moreViewList.add(instanceDataView);
        moreViewList.add(serviceDataView);
        ExportMoreView moreView = new ExportMoreView();
        moreView.setMoreViewList(moreViewList);
        ExcelUtil.exportMoreView(request, response, "PaaS服务详情信息表", moreView);
    }

    @ApiOperation("PAAS近7天时延统计")
    @RequestMapping("/paasSytj")
    @ResponseBody
    public Object paasSytj() {
        ThreePartyInterface data = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "PAAS近7天时延统计"));
        if (data != null) {
            return JSONObject.parseObject(data.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new JSONObject());
        return map;
    }

    @ApiOperation("PAAS近7天异常率统计")
    @RequestMapping("/paasYctj")
    @ResponseBody
    public Object paasYctj() {
        ThreePartyInterface data = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "PAAS近7天异常率统计"));
        if (data != null) {
            return JSONObject.parseObject(data.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new JSONObject());
        return map;
    }

    @ApiOperation("PAAS时延异常TOP10")
    @RequestMapping("/paasSyYcTop10")
    @ResponseBody
    public Object paasSyYcTop10() {
        ThreePartyInterface data = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "PAAS时延异常TOP10"));
        if (data != null) {
            return JSONObject.parseObject(data.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new JSONObject());
        return map;
    }

    @ApiOperation("PAAS服务访问时延导出")
    @RequestMapping("/paasDelay/export")
    public void paasDelayExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<DelayExport> datas = threePartyInterfaceService.paasDelay();
        datas.forEach(delayExport -> delayExport.setDelay(delayExport.getDelay()));
        HSSFWorkbook excle = ExcelUtil.createExcel(datas, DelayExport.class, "服务访问时延");
        ExcelUtil.export(request, response, "服务访问时延信息表", excle);
    }

    @ApiOperation("PAAS服务访问异常导出")
    @RequestMapping("/paasAbnormal/export")
    public void paasAbnormalExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<AbnoramlExport> datas = threePartyInterfaceService.paasAbnormal();
        datas.forEach(abnoramlExport -> abnoramlExport.setAbnormalRate(abnoramlExport.getAbnormalRate()));
        HSSFWorkbook excle = ExcelUtil.createExcel(datas, AbnoramlExport.class, "服务访问异常");
        ExcelUtil.export(request, response, "服务访问异常信息表", excle);
    }

    /********************************************************
     *********************** IAAS *********************
     *********************************************************/

    @ApiOperation("IAAS扩/缩统计")
    @RequestMapping("/needAddAndSubVmList")
    @ResponseBody
    public Object needAddAndSubVmList(@RequestParam(required = true) String name) {

        if ("打私".equals(name)) {
            name = "海关缉私";
        }
        ThreePartyInterface data = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", name + "扩/缩统计"));
        if (data != null) {
            return JSONObject.parseObject(data.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new JSONObject());
        return map;
    }

    @ApiOperation("IAAS扩/缩概况")
    @RequestMapping("/vmAddAndSubOverview")
    @ResponseBody
    public Object vmAddAndSubOverview() {
        ThreePartyInterface data = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "vmAddAndSubOverview"));
        if (data != null) {
            return JSONObject.parseObject(data.getData());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", new JSONObject());
        return map;
    }

    /**
     * 疫情服务top10
     *
     * @return
     */
    @ApiOperation("疫情服务top10")
    @RequestMapping("/yqServiceTop10")
    @ResponseBody
    public JSONObject yqServiceTop10() {
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "疫情数据服务Top10"));
        JSONObject data = null;
        if (threePartyInterface != null) {
            data = JSONObject.parseObject(threePartyInterface.getData());
        }
        return data;
    }

    /**
     * 疫情数据服务二级页面
     *
     * @return
     */
    @ApiOperation("疫情数据服务二级页面")
    @RequestMapping("/yqServiceInfo")
    @ResponseBody
    public JSONObject yqServiceInfo() {
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "疫情数据服务二级页面"));
        JSONObject data = null;
        if (threePartyInterface != null) {
            data = JSONObject.parseObject(threePartyInterface.getData());
        }
        return data;
    }

    /**
     * 疫情服务近7天调用趋势
     *
     * @return
     */
    @ApiOperation("疫情服务近7天调用趋势")
    @RequestMapping("/yqServiceLast7")
    @ResponseBody
    public JSONObject yqServiceLast7() {
        ThreePartyInterface threePartyInterface = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "疫情服务近7天调用趋势"));
        JSONObject data = null;
        if (threePartyInterface != null) {
            data = JSONObject.parseObject(threePartyInterface.getData());
        }
        return data;
    }

    /**
     * 疫情服务订阅详情
     * label  传值：疫情专项
     *
     * @return
     */
    @ApiOperation("疫情服务订阅详情")
    @RequestMapping("/yqServiceDetail")
    @ResponseBody
    public R yqServiceDetail(@RequestParam(name = "serviceName", required = false) String serviceName,
                             @RequestParam(name = "appName", required = false) String appName,
                             @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                             @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        List<YqServiceDetail> yqServiceDetail = bigdataService.yqServiceDetail("疫情专项", serviceName, appName, pageNo, pageSize);
        IPage<YqServiceDetail> page = new Page<>(pageNo, pageSize);
        page.setSize(pageSize);
        page.setCurrent(pageNo);
        page.setTotal(bigdataService.yqdy("疫情专项", serviceName, appName));
        page.setRecords(yqServiceDetail);
        return R.ok(page);
    }

    /**
     * 疫情服务地市订阅统计
     *
     * @return
     */
    @ApiOperation("疫情服务地市订阅统计")
    @RequestMapping("/areaOrder")
    @ResponseBody
    public R areaOrder() {
        List<YqStatistics> yqStatistics = bigdataService.areaOrder("疫情专项");
        return R.ok(yqStatistics);
    }

    /**
     * 疫情服务警种订阅统计
     *
     * @return
     */
    @ApiOperation("疫情服务警种订阅统计")
    @RequestMapping("/policeOrder")
    @ResponseBody
    public R policeOrder() {
        List<YqStatistics> yqStatistics = bigdataService.policeOrder("疫情专项");
        return R.ok(yqStatistics);
    }

    /**
     * 服务被订阅TOP10
     *
     * @return
     */
    @ApiOperation("服务被订阅TOP10")
    @RequestMapping("/serviceByOrder")
    @ResponseBody
    public R serviceByOrder() {
        List<YqStatistics> yqStatistics = bigdataService.serviceByOrder("疫情专项");
        return R.ok(yqStatistics);
    }

    /**
     * 应用订阅TOP10
     *
     * @return
     */
    @ApiOperation("应用订阅TOP10")
    @RequestMapping("/appOrder")
    @ResponseBody
    public R appOrder() {
        List<YqStatistics> yqStatistics = bigdataService.appOrder("疫情专项");
        return R.ok(yqStatistics);
    }

    /**
     * 省直单位总览
     *
     * @return
     */
    @ApiOperation("省直单位总览")
    @RequestMapping("/unitOverview")
    @ResponseBody
    public R unitOverview() {
        try {
            CovOverview covOverview = threePartyInterfaceService.unitOverview();
            return R.ok(covOverview);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    @ApiOperation("疫情云桌面二级页面发放情况")
    @RequestMapping("/epidemicDesktopTable")
    @ResponseBody
    public R epidemicDesktopTable() {
        try {
            List<EpidemicDesktop> epidemicDesktops = threePartyInterfaceService.epidemicExcl();
            EpidemicDeskIssue epidemicDeskIssue = threePartyInterfaceService.epidemicDeskIssue(epidemicDesktops);
            return R.ok(epidemicDeskIssue);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 疫情云桌面表格数据总览
     *
     * @return
     */
    @ApiOperation("疫情云桌面表格数据总览")
    @RequestMapping("/epidemicDesktopNum")
    @ResponseBody
    public R epidemicDesktopNum() {
        try {
            List<EpidemicDesktop> epidemicDesktops = threePartyInterfaceService.epidemicExcl();
            DeskTopNum deskTopNum = threePartyInterfaceService.epidemicExclNum(epidemicDesktops);
            return R.ok(deskTopNum);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 疫情云桌面表格数据
     *
     * @return
     */
    @ApiOperation("疫情云桌面表格数据")
    @RequestMapping("/epidemicDesktop")
    @ResponseBody
    public R epidemicDesktop(@RequestParam("pageSize") long pageSize, @RequestParam("pageNum") long pageNum) {
        try {
            List<EpidemicDesktop> epidemicDesktops = threePartyInterfaceService.epidemicExcl();
            Page<EpidemicDesktop> page = MemoryPageUtil.page(epidemicDesktops, pageSize, pageNum);
            return R.ok(page);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

    }


    /**
     * 省直各单位订购及调用统计
     *
     * @return
     */
    @ApiOperation("省直各单位订购及调用统计")
    @RequestMapping("/unitOverviewLevel2")
    @ResponseBody
    public R unitOverviewLevel2() {
        try {
            List<CovOverviewLevel2> covOverviewLevel2list = threePartyInterfaceService.unitOverviewLevel2();
            return R.ok(covOverviewLevel2list);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
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
    @RequestMapping("/unitStatistic")
    @ResponseBody
    public R unitStatistic(@RequestParam(name = "unitName") String unitName,
                           @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                           @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        try {
            IPage<DirectUnitStatistics> directUnitStatistics = threePartyInterfaceService.unitStatistic(unitName, pageNo, pageSize);
            return R.ok(directUnitStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 各省直单位订阅详情
     *
     * @param unitName 省直单位名称
     * @return
     */
    @ApiOperation("各省直单位订阅详情")
    @RequestMapping("/unitOrderDetail")
    @ResponseBody
    public R unitOrderDetail(@RequestParam(name = "unitName") String unitName,
                             @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                             @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        try {
            IPage<DirectUnitOrderDetail> directUnitOrderDetail = threePartyInterfaceService.unitOrderDetail(unitName, pageNo, pageSize);
            return R.ok(directUnitOrderDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
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
    public R updataUnitFile(MultipartRequest request) {
        List<MultipartFile> files = request.getFiles("file");
        for (MultipartFile file : files) {
            try {
                threePartyInterfaceService.updataFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                return R.error();
            }
        }
        return R.ok(files.get(0).getOriginalFilename());
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
    public R downDataFile(HttpServletResponse response) {
        try {
            threePartyInterfaceService.downdataFile(response);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
        return R.ok();
    }

    /**
     * 省直单位表格上传
     *
     * @param request
     * @return
     */
    @ApiOperation("省直单位表格上传")
    @RequestMapping("/uploadUnitFile")
    @ResponseBody
    public R uploadUnitFile(MultipartRequest request) {
        List<MultipartFile> files = request.getFiles("file");
        for (MultipartFile file : files) {
            try {
                threePartyInterfaceService.uploadFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                return R.error();
            }
        }
        return R.ok(files.get(0).getOriginalFilename());
    }

    /**
     * 省直单位表格下载
     *
     * @param response
     * @return
     */
    @ApiOperation("省直单位表格下载")
    @RequestMapping("/downloadUnitFile")
    @ResponseBody
    public R downloadUnitFile(HttpServletResponse response) {
        try {
            threePartyInterfaceService.downloadFile(response);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
        return R.ok();
    }

    /**
     * 省厅警种总览
     *
     * @return
     */
    @ApiOperation("省厅警种总览")
    @RequestMapping("/policeData")
    @ResponseBody
    public R policeData() {
        try {
            CovOverview covOverview = threePartyInterfaceService.policeData();
            return R.ok(covOverview);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 地市总览
     *
     * @return
     */
    @ApiOperation("地市总览")
    @RequestMapping("/areaData")
    @ResponseBody
    public R areaData() {
        try {
            CovOverview covOverview = threePartyInterfaceService.areaData();
            return R.ok(covOverview);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 各警种订购及调用统计
     *
     * @return
     */
    @ApiOperation("各警种订购及调用统计")
    @RequestMapping("/policeDataLevel2")
    @ResponseBody
    public R policeDataLevel2() {
        try {
            List<CovOverviewLevel2> covOverviewLevel2s = threePartyInterfaceService.policeDataLevel2();
            //调用总次数降序排列
            List<CovOverviewLevel2> collect = covOverviewLevel2s.stream().sorted(Comparator.comparingInt(CovOverviewLevel2::getDyCount).reversed()).collect(Collectors.toList());
            return R.ok(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 各地市订购及调用统计
     *
     * @return
     */
    @ApiOperation("各地市订购及调用统计")
    @RequestMapping("/areaDataLevel2")
    @ResponseBody
    public R areaDataLevel2() {
        try {
            List<CovOverviewLevel2> covOverviewLevel2s = threePartyInterfaceService.areaDataLevel2();
            //调用总次数降序排列
            List<CovOverviewLevel2> collect = covOverviewLevel2s.stream().sorted(Comparator.comparingInt(CovOverviewLevel2::getDyCount).reversed()).collect(Collectors.toList());
            return R.ok(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
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
    @RequestMapping("/policeStatistic")
    @ResponseBody
    public R policeStatistic(@RequestParam(name = "policeName") String policeName,
                             @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                             @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        try {
            IPage<CovStatistic> covStatistics = threePartyInterfaceService.policeStatistic(policeName, pageNo, pageSize);
            return R.ok(covStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 各地市调用详情
     *
     * @param areaName 地市名称
     * @return
     */
    @ApiOperation("各地市调用详情")
    @RequestMapping("/areaStatistic")
    @ResponseBody
    public R areaStatistic(@RequestParam(name = "areaName") String areaName,
                           @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                           @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        try {
            IPage<CovStatistic> covStatistics = threePartyInterfaceService.areaStatistic(areaName, pageNo, pageSize);
            return R.ok(covStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 各警种订阅详情
     *
     * @param policeName 警种名称
     * @return
     */
    @ApiOperation("各警种订阅详情")
    @RequestMapping("/policeOrderDetail")
    @ResponseBody
    public R policeOrderDetail(@RequestParam(name = "policeName") String policeName,
                               @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                               @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        try {
            IPage<CovOrderDetail> covStatistics = threePartyInterfaceService.policeOrderDetail(policeName, pageNo, pageSize);
            return R.ok(covStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 各地市订阅详情
     *
     * @param areaName 地市名称
     * @return
     */
    @ApiOperation("各地市订阅详情")
    @RequestMapping("/areaOrderDetail")
    @ResponseBody
    public R areaOrderDetail(@RequestParam(name = "areaName") String areaName,
                             @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                             @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize) {
        try {
            IPage<CovOrderDetail> covStatistics = threePartyInterfaceService.areaOrderDetail(areaName, pageNo, pageSize);
            return R.ok(covStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
}

