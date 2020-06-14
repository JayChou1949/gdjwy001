package com.upd.hwcloud.controller.backend.serviceManagement.daas;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Bigdata;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.service.IBigdataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 大数据库服务目录 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-12-26
 */
@Api(description = "大数据目录管理接口")
@RestController
@RequestMapping("/bigdata")
public class BigdataController {

    @Autowired
    private IBigdataService bigdataService;

    @ApiOperation("分页查询大数据目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
            @ApiImplicitParam(name="name", value="服务名", paramType="query", dataType="String"),
            @ApiImplicitParam(name="dataFrom", value="数据来源", paramType="query", dataType="String"),
            @ApiImplicitParam(name="collectionUnit", value="采集单位", paramType="query", dataType="String"),
            @ApiImplicitParam(name="category", value="所属分类", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public R page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String name,
                  @RequestParam(required = false) String dataFrom,
                  @RequestParam(required = false) String collectionUnit,
                  @RequestParam(required = false) String category) {
        Page<Bigdata> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page = bigdataService.getPage(page, name, dataFrom, collectionUnit, category);
        return R.ok(page);
    }

    @ApiOperation("获取大数据目录详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R detail(@PathVariable String id) {
        Bigdata bigdata = bigdataService.getById(id);
        return R.ok(bigdata);
    }

    @ApiOperation("修改大数据目录")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public R edit(@RequestBody Bigdata bigdata) {
        LambdaUpdateWrapper<Bigdata> uw = new UpdateWrapper<Bigdata>().lambda()
                .eq(Bigdata::getId, bigdata.getId())
                .set(Bigdata::getUpdateCycle, bigdata.getUpdateCycle())
                .set(Bigdata::getDataResource, bigdata.getDataResource())
                .set(Bigdata::getDataFrom, bigdata.getDataFrom())
                .set(Bigdata::getFromSystem, bigdata.getFromSystem())
                .set(Bigdata::getFromNet, bigdata.getFromNet())
                .set(Bigdata::getCollectionUnit, bigdata.getCollectionUnit())
                .set(Bigdata::getExplanation, bigdata.getExplanation())
                .set(Bigdata::getCategory, bigdata.getCategory())
                .set(Bigdata::getAreaName, bigdata.getAreaName())
                .set(Bigdata::getPoliceCategory, bigdata.getPoliceCategory());
        bigdataService.update(new Bigdata(), uw);
        return R.ok();
    }

    @ApiOperation("保存一键申请页面表格列配置")
    @RequestMapping(value = "/save/columnConfig", method = RequestMethod.POST)
    public R saveColumnConfig(@RequestBody List<String> config) {
        bigdataService.saveColumnConfig(config);
        return R.ok();
    }

    @ApiOperation("重置一键申请页面表格列配置")
    @RequestMapping(value = "/reset/columnConfig", method = RequestMethod.GET)
    public R resetColumnConfig() {
        bigdataService.resetColumnConfig();
        return R.ok();
    }

    @ApiOperation("导出服务列表")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) String dataFrom,
                       @RequestParam(required = false) String collectionUnit,
                       @RequestParam(required = false) String category) throws Exception {
        List<Bigdata> data = bigdataService.getList(name, dataFrom, collectionUnit, category);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"DaaS服务"), Bigdata.class, data);
        ExcelUtil.export(request, response, "DaaS服务表", workbook);
    }

    /**
     * 根据服务ID查询服务详情
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "/getByServiceId/{serviceId}",method = RequestMethod.GET)
    public R getServiceByServiceId(@PathVariable("serviceId") String serviceId){
        try {
            Bigdata bigdata = bigdataService.getServiceByServiceId(serviceId);
            return R.ok(bigdata);
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error();
    }
}

