package com.hirisun.cloud.daas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.util.ExcelUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.daas.bean.DaasResource;
import com.hirisun.cloud.daas.service.DaasResourceService;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(description = "大数据目录管理接口")
@RestController
@RequestMapping("/daas/resource")
public class DaasResourceController {

	@Autowired
	private DaasResourceService daasResourceService;
	
	@ApiOperation("分页查询大数据目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", dataType="String"),
            @ApiImplicitParam(name="name", value="服务名", dataType="String"),
            @ApiImplicitParam(name="dataFrom", value="数据来源", dataType="String"),
            @ApiImplicitParam(name="collectionUnit", value="采集单位", dataType="String"),
            @ApiImplicitParam(name="category", value="所属分类", dataType="String")
    })
    @GetMapping(value = "/page")
    public ResponseResult page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String name,
                  @RequestParam(required = false) String dataFrom,
                  @RequestParam(required = false) String collectionUnit,
                  @RequestParam(required = false) String category) {
		
        Page<DaasResource> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page = daasResourceService.getPage(page, name, dataFrom, collectionUnit, category);
        return QueryResponseResult.success(page);
    }
	
	@ApiOperation("获取大数据目录详情")
    @GetMapping(value = "/detail")
    public ResponseResult detail(@PathVariable String id) {
		DaasResource daasResource = daasResourceService.getDaasResourceById(id);
		return QueryResponseResult.success(daasResource);
    }

    @ApiOperation("修改大数据目录")
    @PostMapping(value = "/edit")
    public ResponseResult edit(@RequestBody DaasResource daasResource) {
        
    	daasResourceService.edit(daasResource);
        return ResponseResult.success();
    }

    @ApiOperation("保存一键申请页面表格列配置")
    @PostMapping(value = "/save/column/config")
    public ResponseResult saveColumnConfig(@RequestBody List<String> config) {
    	daasResourceService.saveColumnConfig(config);
    	return ResponseResult.success();
    }

    @ApiOperation("重置一键申请页面表格列配置")
    @GetMapping(value = "/reset/column/config")
    public ResponseResult resetColumnConfig() {
    	daasResourceService.resetColumnConfig();
    	return ResponseResult.success();
    }

    @ApiOperation("导出服务列表")
    @GetMapping(value = "/export")
    public void export(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) String dataFrom,
                       @RequestParam(required = false) String collectionUnit,
                       @RequestParam(required = false) String category) throws Exception {
        List<DaasResource> data = daasResourceService.getList(name, dataFrom, collectionUnit, category);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"DaaS服务"), DaasResource.class, data);
        ExcelUtil.export(request, response, "DaaS服务表", workbook);
    }

    /**
     * 根据服务ID查询服务详情
     * @param serviceId
     * @return
     */
    @GetMapping(value = "/service/detail")
    public ResponseResult getServiceDetail(String serviceId){
    	DaasResource daasResource = daasResourceService.getServiceByServiceId(serviceId);
        return QueryResponseResult.success(daasResource);
    }
    
    @ApiOperation("获取一键申请页面表格列配置")
    @RequestMapping(value = "/get/columnConfig", method = RequestMethod.GET)
    public ResponseResult getColumnConfig() {
    	List columnConfig = daasResourceService.getColumnConfig();
        return QueryResponseResult.success(columnConfig);
    }
	
}
