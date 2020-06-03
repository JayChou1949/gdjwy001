package com.upd.hwcloud.controller.backend.common;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.IaasZYSBapplicationExport;
import com.upd.hwcloud.bean.entity.SaasApplicationExport;
import com.upd.hwcloud.bean.entity.SaasRecoverInfo;
import com.upd.hwcloud.bean.entity.ServicePublishApplicationRegistExport;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.*;
import com.upd.hwcloud.bean.vo.applicationInfoOrder.IPDVo;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.common.utils.easypoi.ExcelStyleUtil;
import com.upd.hwcloud.controller.backend.application.SaasWorkFlowController;
import com.upd.hwcloud.service.ISaasRecoverInfoService;
import com.upd.hwcloud.service.application.*;
import com.upd.hwcloud.service.factory.WorkbenchFactory;
import com.upd.hwcloud.service.workbench.Workbench;

import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zwb on 2019/4/23.
 */
@Api(description = "导出")
@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private IApplicationInfoService applicationInfoService;
    @Autowired
    private IPassTyxxUserService passTyxxUserService;
    @Autowired
    private IPaasRqzyFwqService paasRqzyFwqService;
    @Autowired
    private IIaasYzmyzyUserService iaasYzmyzyUserService;
    @Autowired
    private IIaasTxyfwService iaasTxyfwService;
    @Autowired
    private IIaasTxyfwImplService iaasTxyfwImplService;
    @Autowired
    private ISaasRecoverInfoService saasRecoverInfoService;
    @Autowired
    private WorkbenchFactory workbenchFactory;

    @ApiOperation("导出基本信息excel")
    @RequestMapping("/excel/{id}")
    public void exportExcel(@LoginUser User user, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ApplicationInfo data = applicationInfoService.getById(id);
        data.setApplicationTimeStr(DateFormatUtils.format(data.getApplicationTime(), "yyyy-MM-dd HH:mm:ss"));
        if ("0".equals(data.getBuildMode())) {
            data.setCjUnit(null);
            data.setCjOrgCode(null);
            data.setCjPrincipal(null);
            data.setCjPrincipalPhone(null);
            data.setCjPrincipalIdcard(null);
            data.setCjHandler(null);
            data.setCjHandlerPhone(null);
        }
        HSSFWorkbook excle = ExcelUtil.createExcel(Collections.singletonList(data), ApplicationInfo.class, "基本信息");
        ExcelUtil.export(request,response,"申请基本信息表",excle);

    }

    @ApiOperation("导出统一用户excel")
    @RequestMapping("/tyyh/excel/{id}")
    public void exportTyyhExcel(@LoginUser User user, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<PassTyxxUser> datas = passTyxxUserService.list(new QueryWrapper<PassTyxxUser>().lambda()
                .eq(PassTyxxUser::getAppInfoId, id)
                .orderByAsc(PassTyxxUser::getCreateTime));
        HSSFWorkbook excle = ExcelUtil.createExcel(datas, PassTyxxUser.class, "服务商人员信息");
        ExcelUtil.export(request,response,"服务商人员信息表",excle);

    }

    @ApiOperation("导出PaaS容器服务excel")
    @RequestMapping("/paasrqzy/excel/{id}")
    public void exportPaasrqzyExcel(@LoginUser User user, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<PaasRqzyFwq> datas = paasRqzyFwqService.list(new QueryWrapper<PaasRqzyFwq>().lambda()
                .eq(PaasRqzyFwq::getAppInfoId, id)
                .orderByAsc(PaasRqzyFwq::getCreateTime));
        HSSFWorkbook excle = ExcelUtil.createExcel(datas, PaasRqzyFwq.class, "服务器信息");
        ExcelUtil.export(request,response,"服务器信息表",excle);

    }
    @ApiOperation("桌面云业务办理导出excel")
    @RequestMapping("/zmyywbl/excel/{id}")
    public void exportZmyExcel(@LoginUser User user, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<IaasYzmyzyUser> datas = iaasYzmyzyUserService.list(new QueryWrapper<IaasYzmyzyUser>().lambda()
                .eq(IaasYzmyzyUser::getAppInfoId,id)
        );
        for(IaasYzmyzyUser info : datas){
            String sex = info.getSex();//1男2女
            info.setSex("1".equals(sex)?"男":"女");
            String type = info.getUserType();//1 民警 2 开发人员
            info.setUserType("1".equals(type)?"民警":"开发人员");
        }
        HSSFWorkbook excel = ExcelUtil.createExcel(datas,IaasYzmyzyUser.class,"桌面云人员信息");
        System.err.println(excel.toString());
        ExcelUtil.export(request,response,"桌面云人员信息",excel);
    }

    /**
     * ECS未业务办理时,弹性云服务的实施表中（TB_IAAS_TXYFW_IMPL）不会保存数据，故先需要在申请信息表（TB_IAAS_TXYFW）中查找
     * @param user
     * @param id
     * @param request
     * @param response
     */
    @ApiOperation("弹性云业务办理导出excel")
    @RequestMapping("/txyywbl/excel/{id}")
    public void exportTxyfwExcel(@LoginUser User user,@PathVariable String id,HttpServletRequest request,HttpServletResponse response) throws Exception {
        IaasTxyfwImpl iaasTxyfwImpl = null;
        //申请信息表
        List<IaasTxyfw> txyfwList = iaasTxyfwService.list(new QueryWrapper<IaasTxyfw>().lambda()
            .eq(IaasTxyfw::getAppInfoId,id)
        );
        //实施表
        List<IaasTxyfwImpl> txyfwimplList = iaasTxyfwImplService.list(new QueryWrapper<IaasTxyfwImpl>().lambda()
                .eq(IaasTxyfwImpl::getAppInfoId,id)
        );
        //如果实施表中有数据，则无需拷贝，直接用即可，否则需要将申请信息表中的数据拷贝一下
        if(txyfwimplList.isEmpty()){
            for (IaasTxyfw obj : txyfwList) {
                iaasTxyfwImpl = new IaasTxyfwImpl();
                BeanUtils.copyProperties(obj,iaasTxyfwImpl);
                iaasTxyfwImpl.setId(UUIDUtil.getUUID());
                iaasTxyfwImpl.setAppName(null);
                iaasTxyfwImpl.setServerId(null);
                iaasTxyfwImpl.setServerPort(null);
                iaasTxyfwImpl.setUserName(null);
                iaasTxyfwImpl.setPassword(null);
                iaasTxyfwImpl.setAccessIp(null);
                int num = obj.getNum().intValue();
                while(num>=1){
                    txyfwimplList.add(iaasTxyfwImpl);
                    num--;
                }
            }
            for(IaasTxyfwImpl info : txyfwimplList){
                String isff = info.getIsFf();//1是0否
                info.setIsFf("1".equals(isff)?"是":"否");
            }
        }
        HSSFWorkbook excel = ExcelUtil.createExcel(txyfwimplList,IaasTxyfwImpl.class,"弹性云业务办理导出");
        ExcelUtil.export(request,response,"弹性云业务办理导出",excel);
    }


    @ApiOperation("SAAS资源回收信息导出excel")
    @RequestMapping("/saasRecover/export/{id}")
    public void exportSaas(@LoginUser User user, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<SaasRecoverInfo> data = saasRecoverInfoService.getUserUseService(id);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"SaaS权限回收信息"), SaasRecoverInfo.class, data);
        ExcelUtil.export(request, response, "SaaS权限回收信息表", workbook);
    }
    @ApiOperation("警务云工单需求导出")
    @RequestMapping(value = "/wugdxq/export", method = RequestMethod.GET)
    public void saasRecoverTotal(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(required = false) String areas,
                                 @RequestParam(required = false) String policeCategory,
                                 String submitStartDate, String submitEndDate,
                                 String bigdataStartDate, String bigdataEndDate,
                                 String carryStartDate, String carryEndDate) throws Exception {
       /* if (UserType.TENANT_MANAGER.getCode().equals(user.getType())) {
            areas = user.getTenantArea();
            policeCategory = user.getTenantPoliceCategory();
        }*/
        Map<String, String> params = SaasWorkFlowController.getParamsMap(submitStartDate, submitEndDate, bigdataStartDate, bigdataEndDate, carryStartDate, carryEndDate);
        //新
        Map<String, Object> canshu = new HashMap<>();
        canshu.put("user", user);
        canshu.put("areas", areas);
        canshu.put("policeCategory", policeCategory);
        canshu.put("submitStartDate", submitStartDate);
        canshu.put("submitEndDate", submitEndDate);
        canshu.put("bigdataStartDate", bigdataStartDate);
        canshu.put("bigdataEndDate", bigdataEndDate);
        canshu.put("carryStartDate", carryStartDate);
        canshu.put("carryEndDate", carryEndDate);
//        List<SaasRecoverTotal> data = saasRecoverApplicationService.saasRecoverExport(areas, params);
//        List<PassDaasIaasApplicationExport> data = applicationInfoService.pdiApplicationExport(areas,policeCategory, params);
//        Map<String,Object> pdia = new HashMap<>();
//        pdia.put("title",new ExportParams(null,"IaaSDaaSPaaS申请"));
//        pdia.put("entity",PassDaasIaasApplicationExport.class);
//        pdia.put("data",data);
        List<SaasApplicationExport> data1 = applicationInfoService.saasApplicationExport(areas,policeCategory, params);
        Map<String,Object> sae = new HashMap<>();
        ExportParams exportParams1 = new ExportParams(null, "SaaS申请");
        exportParams1.setStyle(ExcelStyleUtil.class);
        sae.put("title",exportParams1);
        sae.put("entity",SaasApplicationExport.class);
        sae.put("data",data1);
//        List<ServicePublishApplicationRegistExport> data2 = applicationInfoService.servicePublishRegistExport(areas,policeCategory, params);
        List<ServicePublishApplicationRegistExport> data2 = applicationInfoService.getAppRegistList(canshu);
        Map<String,Object> spar = new HashMap<>();
        ExportParams exportParams2 = new ExportParams(null, "应用注册");
        exportParams2.setStyle(ExcelStyleUtil.class);
        spar.put("title",exportParams2);
        spar.put("entity",ServicePublishApplicationRegistExport.class);
        spar.put("data",data2);
//        List<IaasZYSBapplicationExport> data3 = applicationInfoService.iaasZysbAppExport(areas,policeCategory, params);
        List<IaasZYSBapplicationExport> data3 = applicationInfoService.getIaaSZysbList(canshu);
        Map<String,Object> izysbe = new HashMap<>();
        ExportParams exportParams3 = new ExportParams(null, "IaaS资源上报");
        exportParams3.setStyle(ExcelStyleUtil.class);
        izysbe.put("title",exportParams3);
        izysbe.put("entity",IaasZYSBapplicationExport.class);
        izysbe.put("data",data3);

        List<IPDVo> data4 = applicationInfoService.getIPDApplicationInfoOrderList(canshu);
        Map<String,Object> pdia1 = new HashMap<>();
        ExportParams exportParams4 = new ExportParams(null, "IaaSDaaSPaaS申请");
        exportParams4.setStyle(ExcelStyleUtil.class);
        pdia1.put("title",exportParams4);
        pdia1.put("entity",IPDVo.class);
        pdia1.put("data",data4);

        List<Map<String,Object>> datalist = new ArrayList<>();
        datalist.add(pdia1);
        datalist.add(sae);
        datalist.add(spar);
        datalist.add(izysbe);

        Workbook workbook = ExcelExportUtil.exportExcel(datalist, ExcelType.HSSF);

        if(workbook != null){
            ExcelUtil.export(request, response, "工单统计", workbook);
        }
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"IaaSDaaSPaaS申请"), PassDaasIaasApplicationExport.class, data);
//        ExcelUtil.export(request, response, "警务云工单需求导出", workbook);

    }

    @ApiOperation("saas三级页面导出")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path",name = "resourceType",value = "资源类型(Saas,Daas,Paas)",required = true,dataType = "String"),
            @ApiImplicitParam(paramType="path",name = "name",value = "SaaS应用名",required = true,dataType = "String")
    })
    @RequestMapping(value = "saas/application/{resourceType}/{name}/statistics",method = RequestMethod.GET)
    public void saasApplicationStatisticsExport(@PathVariable String name ,@PathVariable String resourceType,HttpServletRequest request, HttpServletResponse response) throws Exception {
        Workbench workbench = workbenchFactory.getWorkbench(resourceType);
        workbench.lvThreePageExport(name,request,response);
    }

}
