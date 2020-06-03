package com.upd.hwcloud.controller.backend.application;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.dto.XtdyExport;
import com.upd.hwcloud.bean.dto.cov.*;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.common.utils.easypoi.ExportMoreView;
import com.upd.hwcloud.common.utils.easypoi.ExportView;
import com.upd.hwcloud.service.application.NcovDataAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author junglefisher
 * @date 2020/5/5 10:46
 */
@Api(description = "疫情专区数据接口")
@RestController
@RequestMapping("/api/ncovDataArea")
public class NcovDataAreaController {
    @Resource
    private NcovDataAreaService ncovDataAreaService;

    @ApiOperation(value = "数据共享")
    @RequestMapping(value = "/dataSharing",method = RequestMethod.GET)
    public R dataSharing(){
        try {
            DataSharing dataSharing = ncovDataAreaService.dataSharingOverview();
            return R.ok(dataSharing);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    @ApiOperation(value = "单位下载排名导出")
    @RequestMapping(value = "/unitDownloadExport",method = RequestMethod.GET)
    public void unitDownloadExport(HttpServletRequest request, HttpServletResponse response){
        try {
            List<UnitDownloadExport> data = ncovDataAreaService.unitDownloadExport();
            ExportView normalDataView = new ExportView.Builder()
                    .exportParams(new ExportParams(null, "单位下载排名"))
                    .cls(UnitDownloadExport.class)
                    .dataList(data)
                    .create();
            List<ExportView> moreViewList = Lists.newArrayList();
            moreViewList.add(normalDataView);
            ExportMoreView moreView = new ExportMoreView();
            moreView.setMoreViewList(moreViewList);
            ExcelUtil.exportMoreView(request, response, "数据共享排名", moreView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "高频使用资源排名导出")
    @RequestMapping(value = "/highFrequencyUseExport",method = RequestMethod.GET)
    public void highFrequencyUseExport(HttpServletRequest request, HttpServletResponse response){
        try {
            List<HighFrequencyUseExport> data = ncovDataAreaService.highFrequencyUseExport();
            ExportView normalDataView = new ExportView.Builder()
                    .exportParams(new ExportParams(null, "高频使用资源排名"))
                    .cls(HighFrequencyUseExport.class)
                    .dataList(data)
                    .create();
            List<ExportView> moreViewList = Lists.newArrayList();
            moreViewList.add(normalDataView);
            ExportMoreView moreView = new ExportMoreView();
            moreView.setMoreViewList(moreViewList);
            ExcelUtil.exportMoreView(request, response, "数据共享排名", moreView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "数据建模")
    @RequestMapping(value ="/dataModeling",method = RequestMethod.GET)
    public R dataModeling(){
        try {
            DataModeling dataModeling = ncovDataAreaService.dataModelingOverview();
            return R.ok(dataModeling);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    @ApiOperation(value = "单位建模排名导出")
    @RequestMapping(value = "/unitModelingExport",method = RequestMethod.GET)
    public void unitModelingExport(HttpServletRequest request, HttpServletResponse response){
        try {
            List<UnitModelingExport> data = ncovDataAreaService.unitModelingExport();
            ExportView normalDataView = new ExportView.Builder()
                    .exportParams(new ExportParams(null, "单位建模排名"))
                    .cls(UnitModelingExport.class)
                    .dataList(data)
                    .create();
            List<ExportView> moreViewList = Lists.newArrayList();
            moreViewList.add(normalDataView);
            ExportMoreView moreView = new ExportMoreView();
            moreView.setMoreViewList(moreViewList);
            ExcelUtil.exportMoreView(request, response, "数据建模排名", moreView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "公共模型建设单位排名导出")
    @RequestMapping(value = "/publicModelConstructionUnitExport",method = RequestMethod.GET)
    public void publicModelConstructionUnitExport(HttpServletRequest request, HttpServletResponse response){
        try {
            List<UnitModelingExport> data = ncovDataAreaService.publicModelConstructionUnitExport();
            ExportView normalDataView = new ExportView.Builder()
                    .exportParams(new ExportParams(null, "公共模型建设单位排名"))
                    .cls(UnitModelingExport.class)
                    .dataList(data)
                    .create();
            List<ExportView> moreViewList = Lists.newArrayList();
            moreViewList.add(normalDataView);
            ExportMoreView moreView = new ExportMoreView();
            moreView.setMoreViewList(moreViewList);
            ExcelUtil.exportMoreView(request, response, "数据建模排名", moreView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "模型热度排名导出")
    @RequestMapping(value = "/modelPopularityExport",method = RequestMethod.GET)
    public void modelPopularityExport(HttpServletRequest request, HttpServletResponse response){
        try {
            List<ModelPopularityExport> data = ncovDataAreaService.modelPopularityExport();
            ExportView normalDataView = new ExportView.Builder()
                    .exportParams(new ExportParams(null, "模型热度排名"))
                    .cls(ModelPopularityExport.class)
                    .dataList(data)
                    .create();
            List<ExportView> moreViewList = Lists.newArrayList();
            moreViewList.add(normalDataView);
            ExportMoreView moreView = new ExportMoreView();
            moreView.setMoreViewList(moreViewList);
            ExcelUtil.exportMoreView(request, response, "数据建模排名", moreView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "数据治理")
    @RequestMapping(value ="/dataGovernance",method = RequestMethod.GET)
    public R dataGovernance(@RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                            @RequestParam(name = "size", required = false, defaultValue = "7") Integer pageSize){
        try {
            DataGovernance dataGovernance = ncovDataAreaService.dataGovernance();
            return R.ok(dataGovernance);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    @ApiOperation(value = "数据治理二级-根据采集单位查询表名")
    @RequestMapping(value ="/getTableNameByUnit",method = RequestMethod.GET)
    public R getTableNameByUnit(@RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                            @RequestParam(name = "size", required = false, defaultValue = "30") Integer pageSize,
                            @RequestParam(name = "unitName") String unitName){
        try {
            List<NameAndFileName> nameAndFileNames = ncovDataAreaService.getTableNameByUnit(unitName);
            IPage<NameAndFileName> dataGovernancePage = new Page<>(pageNo, pageSize);
            int before = pageSize * (pageNo - 1);
            if (before > nameAndFileNames.size()) {
                dataGovernancePage.setRecords(new ArrayList<>());
                dataGovernancePage.setTotal(nameAndFileNames.size());
                return R.ok(dataGovernancePage);
            }
            int after = pageSize * pageNo;
            if (after > nameAndFileNames.size()) {
                after = nameAndFileNames.size();
            }
            List<NameAndFileName> details = nameAndFileNames.subList(before, after);
            dataGovernancePage.setTotal(nameAndFileNames.size());
            dataGovernancePage.setRecords(details);
            return R.ok(dataGovernancePage);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 数据治理表格下载
     *
     * @param response
     * @return
     */
    @ApiOperation("数据治理表格下载")
    @RequestMapping(value = "/downloadUnitFile",method = RequestMethod.GET)
    public R dataGovernanceTableDownload(HttpServletResponse response,@RequestParam(name = "tableName") String tableName){
        try {
            ncovDataAreaService.downloadFile(response,tableName);
            return R.ok();
        } catch (FileNotFoundException e){
            return R.error("未找到此质量分析报告!");
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
    }

    @ApiOperation(value = "数据接入")
    @RequestMapping(value ="/dataAccess",method = RequestMethod.GET)
    public R dataAccess(){
        try {
            DataAccess dataAccess = ncovDataAreaService.dataAccessOverview();
            return R.ok(dataAccess);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    @ApiOperation(value = "数据接入-数据类型二级")
    @RequestMapping(value ="/dataTypeLevel2",method = RequestMethod.GET)
    public R dataTypeLevel2(@RequestParam(name = "type") String type,
                            @RequestParam(name = "from") String from){
        try {
            Map<String, Map<String,Long>> dataTypeLevel2 = ncovDataAreaService.dataTypeLevel2(type,from);
            return R.ok(dataTypeLevel2);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    @ApiOperation(value = "数据接入-数据三级")
    @RequestMapping(value ="/dataAccessLevel3",method = RequestMethod.GET)
    public R dataAccessLevel3(@RequestParam(name = "unitName") String unitName,
                              @RequestParam(name = "from") String from,
                              @RequestParam(name = "type") String type,
                              @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                              @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize){
        try {
            List<DataAccessLevel3> dataAccessLevel3 = ncovDataAreaService.dataAccessLevel3(unitName,from,type);
            IPage<DataAccessLevel3> dataAccessLevel3Page = new Page<>(pageNo, pageSize);
            int before = pageSize * (pageNo - 1);
            if (before > dataAccessLevel3.size()) {
                dataAccessLevel3Page.setRecords(new ArrayList<>());
                dataAccessLevel3Page.setTotal(dataAccessLevel3.size());
                return R.ok(dataAccessLevel3Page);
            }
            int after = pageSize * pageNo;
            if (after > dataAccessLevel3.size()) {
                after = dataAccessLevel3.size();
            }
            List<DataAccessLevel3> details = dataAccessLevel3.subList(before, after);
            dataAccessLevel3Page.setTotal(dataAccessLevel3.size());
            dataAccessLevel3Page.setRecords(details);
            return R.ok(dataAccessLevel3Page);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    @ApiOperation(value = "数据服务")
    @RequestMapping(value ="/dataService",method = RequestMethod.GET)
    public R dataService(){
        try {
            DataService dataService = ncovDataAreaService.dataService();
            return R.ok(dataService);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    @ApiOperation(value = "数据列表及相关数据")
    @RequestMapping(value ="/dataList",method = RequestMethod.GET)
    public R dataList(@RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                      @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize){
        try {
            List<ServiceData> serviceData = ncovDataAreaService.dataList();
            IPage<ServiceData> serviceDataPage = new Page<>(pageNo, pageSize);
            int before = pageSize * (pageNo - 1);
            if (before > serviceData.size()) {
                serviceDataPage.setRecords(new ArrayList<>());
                serviceDataPage.setTotal(serviceData.size());
                return R.ok(serviceDataPage);
            }
            int after = pageSize * pageNo;
            if (after > serviceData.size()) {
                after = serviceData.size();
            }
            List<ServiceData> details = serviceData.subList(before, after);
            serviceDataPage.setTotal(serviceData.size());
            serviceDataPage.setRecords(details);
            return R.ok(serviceDataPage);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    @ApiOperation(value = "首页")
    @RequestMapping(value ="/homePage",method = RequestMethod.GET)
    public R homePage(){
        try {
            HomePageData homePage = ncovDataAreaService.homePage();
            return R.ok(homePage);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    @ApiOperation(value = "省直/地市单位防疫情况")
    @RequestMapping(value ="/preventionSituation",method = RequestMethod.GET)
    public R preventionSituation(@RequestParam(name = "from") String from,
                                 @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                                 @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize){
        try {
            List<PreventionSituation> preventionSituations = ncovDataAreaService.preventionSituation(from);
            IPage<PreventionSituation> preventionSituationPage = new Page<>(pageNo, pageSize);
            int before = pageSize * (pageNo - 1);
            if (before > preventionSituations.size()) {
                preventionSituationPage.setRecords(new ArrayList<>());
                preventionSituationPage.setTotal(preventionSituations.size());
                return R.ok(preventionSituationPage);
            }
            int after = pageSize * pageNo;
            if (after > preventionSituations.size()) {
                after = preventionSituations.size();
            }
            List<PreventionSituation> details = preventionSituations.subList(before, after);
            preventionSituationPage.setTotal(preventionSituations.size());
            preventionSituationPage.setRecords(details);
            return R.ok(preventionSituationPage);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 表格上传
     *
     * @param request
     * @param type 1:数据接入 2.数据治理 3.数据服务 4.数据建模 5.数据共享
     * @return
     */
    @ApiOperation("模板表格上传")
    @RequestMapping("/uploadModelFile")
    @ResponseBody
    public R uploadModelFile(MultipartRequest request,
                            @RequestParam("type") Integer type) {
        String name;
        switch (type){
            case 1:
                name = "数据接入情况.xlsx";
                break;
            case 2:
                name = "质量分析情况清单.xls";
                break;
            case 3:
                name = "地市疫情防控数据.xlsx";
                break;
            case 4:
                name = "数据建模情况V1.xlsx";
                break;
            case 5:
                name = "数据共享情况V1.xlsx";
                break;
            default:
                return R.error("类型错误!");
        }
        List<MultipartFile> files = request.getFiles("file");
        for (MultipartFile file : files) {
            try {
                ncovDataAreaService.uploadFile(file,name);
            } catch (IOException e) {
                e.printStackTrace();
                return R.error();
            }
        }
        return R.ok(files.get(0).getOriginalFilename());
    }
}
