package com.upd.hwcloud.controller.backend.system.ncov;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.ThreePartyInterface;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.ncov.NcovExcelSheetOneVo;
import com.upd.hwcloud.bean.vo.ncov.NcovExcelSheetTwoVo;
import com.upd.hwcloud.common.utils.easypoi.NcovExcelImportUtil;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IThreePartyInterfaceService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author wuc
 * @date 2020/2/24
 */
@Api(description = "疫情数据管理接口")
@RestController
@RequestMapping("/ncov-management")
public class NcovManagerController {




    @Autowired
    private IFilesService filesService;

    @Autowired
    private IThreePartyInterfaceService threePartyInterfaceService;

    @Value("${ncov.important.access}")
    private String importantAccessStr;

    private static  final ImmutableMap<String,String> shortNameMapping = ImmutableMap.<String, String>builder()
            .put("中国民用航空广东安全监督管理局","民航安监局").put("市场监督管理局","市场监管局")
            .put("文化和旅游厅","文旅厅").put("省住房城乡建设厅","住建厅").put("人力资源保障厅","人社厅")
            .build();

    /**
     * 上传疫情数据源
     * @param
     * @return
     */
    @ApiOperation("数据源文件上传")
    @RequestMapping(value = "/dataUpload",method = RequestMethod.POST)
    public R dataUpload(@ApiParam(name = "file",required = true) MultipartFile file) throws IOException {
       // List<MultipartFile> files = request.getFiles("file");
        if(file != null){
            String originalFilename = file.getOriginalFilename();
            String[] spt = originalFilename.split("[.]");
            if(spt.length!=2){
                return R.error("文件名非法");
            }
            if(!StringUtils.equals("xlsx",spt[1])){
                return R.error("请上传xlsx格式文件");
            }
            Files f = filesService.uploadNcovDataFile(file);
            System.out.println("==start parse==");
            R r = parseExcel();
            System.out.println("==end==" + r);
            return R.ok(f);
        }else {
            return R.error("请选择 一个 文件上传");
        }

    }

    /**
     *
     * 疫情集群文件上传 clustertUpload
     * @param
     * @param
     * @return
     */
    @ApiOperation("疫情集群文件上传")
    @RequestMapping(value = "/cluster/upload",method = RequestMethod.POST)
    public R clustertUpload(@ApiParam(name = "file",required = true) MultipartFile file) throws IOException {

        if(file != null){
            String originalFilename = file.getOriginalFilename();
            String[] spt = originalFilename.split("[.]");
            if(spt.length!=2){
                return R.error("文件名非法");
            }
            if(!StringUtils.equals("xlsx",spt[1])){
                return R.error("请上传xlsx格式文件");
            }
            Files f = filesService.uploadClusterDataFile(file);
            return R.ok(f);
        }else {
            return R.error("请选择 一个 文件上传");
        }

    }


    /**
     *
     * 疫情虚拟机文件上传
     * @param
     * @param
     * @return
     */
    @ApiOperation("疫情虚拟机文件上传")
    @RequestMapping(value = "/ecs/upload",method = RequestMethod.POST)
    public R ecsUpload(@ApiParam(name = "file",required = true) MultipartFile file) throws IOException {

        if(file != null){
            String originalFilename = file.getOriginalFilename();
            String[] spt = originalFilename.split("[.]");
            if(spt.length!=2){
                return R.error("文件名非法");
            }
            if(!StringUtils.equals("xlsx",spt[1])){
                return R.error("请上传xlsx格式文件");
            }
            Files f = filesService.uploadEcsDataFile(file);
            return R.ok(f);
        }else {
            return R.error("请选择 一个 文件上传");
        }

    }

    @ApiOperation("防疫大数据文件上传")
    @RequestMapping(value = "/ncovDataArea/{module}/upload",method = RequestMethod.POST)
    public R ncovDataAreaUpload(@PathVariable String module,@ApiParam(name = "file",required = true) MultipartFile file) throws IOException {
        if(file != null){
            String originalFilename = file.getOriginalFilename();
            String[] spt = originalFilename.split("[.]");
            if(spt.length!=2){
                return R.error("文件名非法");
            }
            Files f = filesService.ncovDataAreaUpload(file,module);
            return R.ok(f);
        }else {
            return R.error("请选择一个文件上传");
        }
    }

    /**
     *
     * 上传质量分析报告 reportUpload
     * @param
     * @param title
     * @return
     */
    @ApiOperation("质量报告文件上传")
    @RequestMapping(value = "/report/{title}/upload",method = RequestMethod.POST)
    public R reportUpload(@ApiParam(name = "file",required = true) MultipartFile file, @PathVariable String title) throws IOException {

        if(file != null){
            String originalFilename = file.getOriginalFilename();
            String[] spt = originalFilename.split("[.]");
            if(spt.length!=2){
                return R.error("文件名非法");
            }
            Files f = filesService.uploadNcovReport(file,title);
            return R.ok(f);
        }else {
            return R.error("请选择 一个 文件上传");
        }
    }


    @ApiOperation("根据Excel统计分析疫情数据")
    @RequestMapping(value = "/ncov/parseExcel",method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public R parseExcel(){
        List<NcovExcelSheetOneVo> sheetOneVoList = NcovExcelImportUtil.getSheetOneData();
        List<NcovExcelSheetTwoVo> sheetTwoVoList = NcovExcelImportUtil.getSheetTwoData();

        saveNcovImportOverview(sheetOneVoList);
        saveNcovImportDepartment(sheetOneVoList);
        saveNcovImportRecentSevenDaysChange(sheetTwoVoList);
        saveNcovImportImportantData(sheetOneVoList,sheetTwoVoList);
        return R.ok();
    }

    @ApiOperation("疫情集群Excel解析")
    @RequestMapping(value = "/ncov/cluster/parseExcel",method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public R parseClusterExcel() {

        return R.ok();
    }

    /**
     * 概览信息解析并存入三方表（ncovImportOverView）
     * @param sheetOneVoList
     */
    private void saveNcovImportOverview(List<NcovExcelSheetOneVo> sheetOneVoList){
        long total = sheetOneVoList.stream().mapToLong(NcovExcelSheetOneVo::getDataTotalNum).sum();
        long yesterdayUp = sheetOneVoList.stream().mapToLong(NcovExcelSheetOneVo::getDataUpOfYesterday).sum();
        long tableNum = sheetOneVoList.size();
        long departmentNum = sheetOneVoList.stream().map(NcovExcelSheetOneVo::getDepartmentOfData).distinct().count();
        Map<String,Long> overviewMap = Maps.newHashMapWithExpectedSize(7);
        overviewMap.put("total",total);
        overviewMap.put("yesterdayDataUp",yesterdayUp);
        overviewMap.put("tableNum",tableNum);
        overviewMap.put("departmentNum",departmentNum);
        ThreePartyInterface threePartyInterface = new ThreePartyInterface();
        threePartyInterface.setId("ncovImportOverview");
        threePartyInterface.setLabel("ncovImportOverview");
        threePartyInterface.setData(JSON.toJSONString(R.ok(overviewMap)));
        threePartyInterface.setCreateTime(new Date());
        threePartyInterfaceService.saveOrUpdateData(threePartyInterface);
    }


    /**
     * 解析保存疫情近七天数据变化
     * @param sheetTwoVoList
     */
    private void saveNcovImportRecentSevenDaysChange(List<NcovExcelSheetTwoVo> sheetTwoVoList){
        long seven = sheetTwoVoList.stream().mapToLong(NcovExcelSheetTwoVo::getSeven).sum();

        long six = sheetTwoVoList.stream().mapToLong(NcovExcelSheetTwoVo::getSix).sum();

        long five = sheetTwoVoList.stream().mapToLong(NcovExcelSheetTwoVo::getFive).sum();

        long four = sheetTwoVoList.stream().mapToLong(NcovExcelSheetTwoVo::getFour).sum();

        long three = sheetTwoVoList.stream().mapToLong(NcovExcelSheetTwoVo::getThree).sum();

        long two = sheetTwoVoList.stream().mapToLong(NcovExcelSheetTwoVo::getTwo).sum();

        long one = sheetTwoVoList.stream().mapToLong(NcovExcelSheetTwoVo::getOne).sum();


        Map<String,Long> resMap = Maps.newLinkedHashMap();
        LocalDate now = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd");
        resMap.put(now.minus(7, ChronoUnit.DAYS).format(fmt),seven);
        resMap.put(now.minus(6, ChronoUnit.DAYS).format(fmt),six);
        resMap.put(now.minus(5, ChronoUnit.DAYS).format(fmt),five);
        resMap.put(now.minus(4, ChronoUnit.DAYS).format(fmt),four);
        resMap.put(now.minus(3, ChronoUnit.DAYS).format(fmt),three);
        resMap.put(now.minus(2, ChronoUnit.DAYS).format(fmt),two);
        resMap.put(now.minus(1, ChronoUnit.DAYS).format(fmt),one);
        System.out.println("XX"+resMap);
        ThreePartyInterface threePartyInterface = new ThreePartyInterface();
        threePartyInterface.setId("ncovImportRecentAll");
        threePartyInterface.setLabel("ncovImportRecentAll");
        System.out.println("R.ok"+R.ok(resMap));
        System.out.println("R,ok json"+JSON.toJSONString(R.ok(resMap)));
        threePartyInterface.setData(JSON.toJSONString(R.ok(resMap)));
        threePartyInterface.setCreateTime(new Date());
        threePartyInterfaceService.saveOrUpdateData(threePartyInterface);

    }

    /**
     * 解析保存疫情接入单位数据情况
     * @param sheetOneVoList
     */
    private void saveNcovImportDepartment(List<NcovExcelSheetOneVo> sheetOneVoList){
        Map<String,Long> departmentMap = sheetOneVoList.stream().collect(Collectors.groupingBy(NcovExcelSheetOneVo::getDepartmentOfData,Collectors.summingLong(NcovExcelSheetOneVo::getDataTotalNum)));
        System.out.println("before"+departmentMap);
        System.out.println("b.size"+departmentMap.size());
        shortNameMapping.forEach((k,v)->{
            departmentMap.put(v,departmentMap.remove(k));
        });
        System.out.println("after"+departmentMap);
        System.out.println("a.size"+departmentMap.size());
        ThreePartyInterface threePartyInterface = new ThreePartyInterface();
        threePartyInterface.setId("ncovImportDepartment");
        threePartyInterface.setLabel("ncovImportDepartment");
        threePartyInterface.setData(JSON.toJSONString(R.ok(departmentMap)));
        threePartyInterface.setCreateTime(new Date());
        threePartyInterfaceService.saveOrUpdateData(threePartyInterface);
    }


    /**
     * 解析保存重点接入数据
     * @param sheetOneVoList
     * @param sheetTwoVoList
     */
    private void saveNcovImportImportantData(List<NcovExcelSheetOneVo> sheetOneVoList,List<NcovExcelSheetTwoVo> sheetTwoVoList){

        List<String> importantAccess = Splitter.on(",").splitToList(importantAccessStr);
        System.out.println(" pz "+importantAccess);
        List<NcovExcelSheetOneVo> importantDataOfSheetOne = sheetOneVoList.stream().filter(item->importantAccess.contains(item.getTableCnName())).collect(Collectors.toList());
        System.out.println("one size: "+importantDataOfSheetOne.size());

        List<NcovExcelSheetTwoVo> importantDataOfSheetTwo = sheetTwoVoList.stream().filter(item->importantAccess.contains(item.getTableCnName())).collect(Collectors.toList());
        System.out.println("two size:"+importantDataOfSheetTwo.size());

        List<Map<String,Object>> leftSideMapList = Lists.newArrayList();

        importantDataOfSheetOne.forEach(data->{
            Map<String,Object> dataMap =Maps.newHashMap();
            dataMap.put("name",data.getTableCnName());
            dataMap.put("total",data.getDataTotalNum());
            dataMap.put("yesterdayUp",data.getDataUpOfYesterday());
            leftSideMapList.add(dataMap);
        });

        LocalDate now = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd");

        List<Map<String,Object>> rightSideMapList = Lists.newArrayList();
        importantDataOfSheetTwo.forEach(data->{
            Map<String,Object> dataMap = Maps.newLinkedHashMap();
            dataMap.put("name",data.getTableCnName());
            dataMap.put(now.minus(7, ChronoUnit.DAYS).format(fmt),data.getSeven());
            dataMap.put(now.minus(6, ChronoUnit.DAYS).format(fmt),data.getSix());
            dataMap.put(now.minus(5, ChronoUnit.DAYS).format(fmt),data.getFive());
            dataMap.put(now.minus(4, ChronoUnit.DAYS).format(fmt),data.getFour());
            dataMap.put(now.minus(3, ChronoUnit.DAYS).format(fmt),data.getThree());
            dataMap.put(now.minus(2, ChronoUnit.DAYS).format(fmt),data.getTwo());
            dataMap.put(now.minus(1, ChronoUnit.DAYS).format(fmt),data.getOne());
            System.out.println("XXX"+dataMap);
            //dataMap.entrySet().stream().sorted(Comparator.comparing(e->e.getKey()));
            rightSideMapList.add(dataMap);
        });

        Map<String,Object> resMap = Maps.newHashMap();
        resMap.put("leftSide",leftSideMapList);
        resMap.put("rightSide",rightSideMapList);
        System.out.println("   imporant: "+ JSON.toJSONString(resMap));
        ThreePartyInterface threePartyInterface = new ThreePartyInterface();
        threePartyInterface.setId("ncovImportImportantData");
        threePartyInterface.setLabel("ncovImportImportantData");
        threePartyInterface.setData(JSON.toJSONString(R.ok(resMap)));
        threePartyInterface.setCreateTime(new Date());
        threePartyInterfaceService.saveOrUpdateData(threePartyInterface);

    }
}
