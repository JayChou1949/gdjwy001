package com.hirisun.cloud.saas.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.contains.UserType;
import com.hirisun.cloud.common.util.ExcelUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.daas.vo.DaasServiceOverview;
import com.hirisun.cloud.model.daas.vo.InnerServiceOverview;
import com.hirisun.cloud.model.daas.vo.ServiceOverview;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.saas.bean.Saas;
import com.hirisun.cloud.saas.bean.SaasSubpage;
import com.hirisun.cloud.saas.bean.SaasSubpageConf;
import com.hirisun.cloud.saas.service.SaasService;
import com.hirisun.cloud.saas.service.SaasSubpageConfService;
import com.hirisun.cloud.saas.service.SaasSubpageService;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags ="后台-服务管理-(软件服务 Saas)-二级页面")
@RequestMapping("/saas/subpage")
@RestController
public class SaasSubpageConfigController {
	
	@Autowired
    private SaasSubpageService saasSubpageConfigService;
	@Autowired
	private SaasSubpageConfService saasSubpageConfService;
	@Autowired
	private SaasService saasConfigService;
	
	private static final int MAX_ROW = 10;

    @ApiOperation("新增")
    @PutMapping(value = "/create")
    @ResponseBody
    public ResponseResult create(@LoginUser UserVO user, @RequestBody SaasSubpage saas) {
    	saasSubpageConfigService.saveSaasPage(user,saas);
        return ResponseResult.success();
    }


    @ApiOperation("修改二级页面配置信息")
    @PutMapping(value = "/edit")
    @ResponseBody
    public ResponseResult edit(@LoginUser UserVO user, @RequestBody SaasSubpage saas) {
    	saasSubpageConfigService.updateIaasPage(user,saas);
        return ResponseResult.success();
    }

    @ApiOperation("服务配置详情")
    @ApiImplicitParam(name="iaasId", value="服务id", required = true, dataType="String")
    @GetMapping(value = "/detail")
    @ResponseBody
    public ResponseResult detail(@LoginUser UserVO user, @RequestParam(required = true) String iaasId) {
    	SaasSubpage iaas = saasSubpageConfigService.getDetail(iaasId);
        return QueryResponseResult.success(iaas);
    }
    
    @ApiImplicitParam(name="saasId", value="服务id", required = true, dataType="String")
    @PutMapping(value = "/conf/save")
    public ResponseResult save(@RequestParam(required = true) String saasId, @RequestBody SaasSubpageConf conf) {
        saasSubpageConfService.save(saasId, conf);
        return ResponseResult.success();
    }

    @ApiImplicitParam(name="saasId", value="服务id", required = true, dataType="String")
    @GetMapping(value = "/conf/detail")
    public ResponseResult detail(@RequestParam(required = true) String saasId) {
        SaasSubpageConf conf = saasSubpageConfService.getOne(new QueryWrapper<SaasSubpageConf>().lambda()
                .eq(SaasSubpageConf::getMasterId, saasId));
        return QueryResponseResult.success(conf);
    }
    
    @ApiOperation("是否有权限查看服务资源")
    @ApiImplicitParam(name="saasId", value="服务id", required = true, dataType="String")
    @GetMapping(value = "/permission")
    public ResponseResult permission(@LoginUser UserVO user,@RequestParam(required = true)String id) {
        boolean permission = false;
        Saas saas = saasConfigService.getSaasConfigById(id);
        if (saas != null) {
            boolean isCreator = Objects.equals(user.getIdcard(), saas.getCreator());
            boolean isManager = Objects.equals(UserType.MANAGER.getCode(), user.getType());
            if (isCreator || isManager) {
                permission = true;
            }
            if (!permission) {
                boolean isTenantManager = Objects.equals(UserType.TENANT_MANAGER.getCode(), user.getType());
                boolean enable = Objects.equals(saas.getAreaName(), user.getTenantArea()) || Objects.equals(saas.getPoliceCategory(), user.getTenantPoliceCategory());
                if (isTenantManager && enable) {
                    permission = true;
                }
            }
        }
        return QueryResponseResult.success(permission);
    }

    @ApiOperation("DAAS层服务数据")
    @ApiImplicitParam(name="name", value="名称", required = true, dataType="String")
    @GetMapping(value = "/daas")
    public ResponseResult daas(@RequestParam(required = true)String name) {
        List<DaasServiceOverview> data = saasSubpageConfigService.daas(name);
        int count = data == null ? 0 : data.size();
        if (data != null && data.size() > MAX_ROW) {
            data = data.subList(0, MAX_ROW);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("count", count);
        result.put("list", data);
        return QueryResponseResult.success(result);
    }

    @ApiOperation("DAAS层服务数据 - 导出")
    @ApiImplicitParam(name="name", value="名称", required = true, dataType="String")
    @GetMapping(value = "/daas/export")
    public void daasExport(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(required = true) String name) throws Exception {
        List<DaasServiceOverview> data = saasSubpageConfigService.daas(name);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"DaaS层服务数据"), DaasServiceOverview.class, data);
        ExcelUtil.export(request, response, "DaaS层服务数据表", workbook);
    }

    @ApiOperation("PAAS层服务数据 - 服务市场")
    @ApiImplicitParam(name="name", value="名称", required = true, dataType="String")
    @GetMapping(value = "/paas")
    public ResponseResult paas(@RequestParam(required = true) String name) {
        List<ServiceOverview> data = saasSubpageConfigService.paas(name);
        int count = data == null ? 0 : data.size();
        if (data != null && data.size() > MAX_ROW) {
            data = data.subList(0, MAX_ROW);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("count", count);
        result.put("list", data);
        return QueryResponseResult.success(result);
    }

    @ApiOperation("PAAS层服务数据 - 服务市场 - 导出")
    @ApiImplicitParam(name="name", value="名称", required = true, dataType="String")
    @GetMapping(value = "/paas/export")
    public void paasExport(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(required = true) String name) throws Exception {
        List<ServiceOverview> data = saasSubpageConfigService.paas(name);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"服务市场"), ServiceOverview.class, data);
        ExcelUtil.export(request, response, "PAAS层服务数据表", workbook);
    }

    @ApiOperation("PAAS层服务数据 - 其它服务")
    @ApiImplicitParam(name="name", value="名称", required = true, dataType="String")
    @GetMapping(value = "/paas/other")
    public ResponseResult paasOther(@RequestParam(required = true) String name) {
        List<InnerServiceOverview> data = saasSubpageConfigService.paasOther(name);
        int count = data == null ? 0 : data.size();
        if (data != null && data.size() > MAX_ROW) {
            data = data.subList(0, MAX_ROW);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("count", count);
        result.put("list", data);
        return QueryResponseResult.success(result);
    }

    @ApiOperation("PAAS层服务数据 - 其它服务 - 导出")
    @ApiImplicitParam(name="name", value="名称", required = true, dataType="String")
    @GetMapping(value = "/paas/other/export")
    public void paasOtherExport(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(required = true) String name) throws Exception {
        List<InnerServiceOverview> data = saasSubpageConfigService.paasOther(name);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"其它服务"), InnerServiceOverview.class, data);
        ExcelUtil.export(request, response, "PAAS层服务数据表", workbook);
    }

//    @ApiOperation("IAAS层服务数据")
//    @GetMapping(value = "/iaas")
//    @ResponseBody
//    public R iaas(String name){
//        List<VmGeneralExport> data = cloudVmService.exportByAppName(name);
//        int count = data == null ? 0 : data.size();
//        if (data != null && data.size() > MAX_ROW) {
//            data = data.subList(0, MAX_ROW);
//        }
//        Map<String, Object> result = Maps.newHashMap();
//        result.put("count", count);
//        result.put("list", data);
//        return R.ok(result);
//    }
//
//    @ApiOperation("IAAS层服务数据导出")
//    @GetMapping(value = "/iaas/export")
//    @ResponseBody
//    public void iaasExport(String name, HttpServletRequest request, HttpServletResponse response) throws Exception{
//        List<VmGeneralExport> exportList = cloudVmService.exportByAppName(name);
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"IaaS服务资源"), VmGeneralExport.class, exportList);
//        ExcelUtil.export(request, response, "IaaS服务资源", workbook);
//    }
    
    
	
}
