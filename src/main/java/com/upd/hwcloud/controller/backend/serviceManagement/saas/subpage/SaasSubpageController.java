package com.upd.hwcloud.controller.backend.serviceManagement.saas.subpage;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.UserType;
import com.upd.hwcloud.bean.dto.DaasServiceOverview;
import com.upd.hwcloud.bean.dto.InnerServiceOverview;
import com.upd.hwcloud.bean.dto.ServiceOverview;
import com.upd.hwcloud.bean.dto.VmGeneralExport;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.SaasSubpage;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.controller.backend.serviceManagement.BaseSubpageController;
import com.upd.hwcloud.service.ISaasService;
import com.upd.hwcloud.service.ISaasSubpageService;
import com.upd.hwcloud.service.dm.ICloudVmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * SAAS二级页面内容 前端控制器
 * </p>
 */
@Api(description = "saas二级页面")
@RestController
@RequestMapping("/saasSubpage")
public class SaasSubpageController extends BaseSubpageController<ISaasSubpageService, SaasSubpage> {

    @Autowired
    private ISaasSubpageService saasSubpageService;
    @Autowired
    private ISaasService saasService;
    @Autowired
    private ICloudVmService cloudVmService;

    private static final int MAX_ROW = 10;

    @ApiOperation("是否有权限查看服务资源")
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
    public R permission(@LoginUser User user, @PathVariable String id) {
        boolean permission = false;
        Saas saas = saasService.getById(id);
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
        return R.ok(permission);
    }

    @ApiOperation("DAAS层服务数据")
    @RequestMapping(value = "/daas/{name}", method = RequestMethod.GET)
    public R daas(@PathVariable String name) {
        List<DaasServiceOverview> data = saasSubpageService.daas(name);
        int count = data == null ? 0 : data.size();
        if (data != null && data.size() > MAX_ROW) {
            data = data.subList(0, MAX_ROW);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("count", count);
        result.put("list", data);
        return R.ok(result);
    }

    @ApiOperation("DAAS层服务数据 - 导出")
    @RequestMapping(value = "/daas/export/{name}", method = RequestMethod.GET)
    public void daasExport(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable String name) throws Exception {
        List<DaasServiceOverview> data = saasSubpageService.daas(name);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"DaaS层服务数据"), DaasServiceOverview.class, data);
        ExcelUtil.export(request, response, "DaaS层服务数据表", workbook);
    }

    @ApiOperation("PAAS层服务数据 - 服务市场")
    @RequestMapping(value = "/paas/{name}", method = RequestMethod.GET)
    public R paas(@PathVariable String name) {
        List<ServiceOverview> data = saasSubpageService.paas(name);
        int count = data == null ? 0 : data.size();
        if (data != null && data.size() > MAX_ROW) {
            data = data.subList(0, MAX_ROW);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("count", count);
        result.put("list", data);
        return R.ok(result);
    }

    @ApiOperation("PAAS层服务数据 - 服务市场 - 导出")
    @RequestMapping(value = "/paas/export/{name}", method = RequestMethod.GET)
    public void paasExport(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable String name) throws Exception {
        List<ServiceOverview> data = saasSubpageService.paas(name);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"服务市场"), ServiceOverview.class, data);
        ExcelUtil.export(request, response, "PAAS层服务数据表", workbook);
    }

    @ApiOperation("PAAS层服务数据 - 其它服务")
    @RequestMapping(value = "/paas/other/{name}", method = RequestMethod.GET)
    public R paasOther(@PathVariable String name) {
        List<InnerServiceOverview> data = saasSubpageService.paasOther(name);
        int count = data == null ? 0 : data.size();
        if (data != null && data.size() > MAX_ROW) {
            data = data.subList(0, MAX_ROW);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("count", count);
        result.put("list", data);
        return R.ok(result);
    }

    @ApiOperation("PAAS层服务数据 - 其它服务 - 导出")
    @RequestMapping(value = "/paas/other/export/{name}", method = RequestMethod.GET)
    public void paasOtherExport(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable String name) throws Exception {
        List<InnerServiceOverview> data = saasSubpageService.paasOther(name);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"其它服务"), InnerServiceOverview.class, data);
        ExcelUtil.export(request, response, "PAAS层服务数据表", workbook);
    }

    @ApiOperation("IAAS层服务数据")
    @RequestMapping(value = "/iaas/{name}", method = RequestMethod.GET)
    @ResponseBody
    public R iaas(@PathVariable String name){
        List<VmGeneralExport> data = cloudVmService.exportByAppName(name);
        int count = data == null ? 0 : data.size();
        if (data != null && data.size() > MAX_ROW) {
            data = data.subList(0, MAX_ROW);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("count", count);
        result.put("list", data);
        return R.ok(result);
    }

    @ApiOperation("IAAS层服务数据导出")
    @RequestMapping(value = "/iaas/export/{name}", method = RequestMethod.GET)
    @ResponseBody
    public void iaasExport(@PathVariable String name, HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<VmGeneralExport> exportList = cloudVmService.exportByAppName(name);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"IaaS服务资源"), VmGeneralExport.class, exportList);
        ExcelUtil.export(request, response, "IaaS服务资源", workbook);
    }

}

