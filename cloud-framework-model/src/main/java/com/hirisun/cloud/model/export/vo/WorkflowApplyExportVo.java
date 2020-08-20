package com.hirisun.cloud.model.export.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @ Author wuxiaoxing
 *  台站管理-工单统计-ipds申请统计导出
 */
@Data
public class WorkflowApplyExportVo {

    @Excel(name = "序号")
    private Integer num;

    private String id;

    @Excel(name = "工单号",width = 15)
    private String orderNumber;

    @Excel(name = "申请时间",exportFormat = "yyy-MM-dd HH:mm:ss",width = 20)
    private Date applicationTime;

    @Excel(name = "申请人")
    private String creatorName;

    @Excel(name = "申请人电话",width = 15)
    private String creatorPhone;

    @Excel(name = "服务类型")
    private String serviceType;

    @Excel(name = "资源类型",width = 15)
    private String serviceTypeName;

    @Excel(name = "申请人单位",width = 20)
    private String orgName;

    @Excel(name = "项目名称",width = 20)
    private String projectName;

    @Excel(name = "应用名称",width = 20)
    private String appName;

    @Excel(name = "警种")
    private String policeCategory;

    @Excel(name = "地市")
    private String areaName;


    @Excel(name = "承建单位",width = 20)
    private String cjUnit;

    @Excel(name = "承建单位负责人")
    private String cjPrincipal;

    @Excel(name = "承建单位负责人电话",width = 15)
    private String cjPrincipalPhone;

    @Excel(name = "当前处理环节")
    private String node;

//    @Excel(name = "服务台复核人员")
//    private String recheckPerson;
//
//    @Excel(name = "服务台复核时间",exportFormat = "yyy-MM-dd HH:mm:ss",width = 20)
//    private Date recheckTime;
//
//    @Excel(name = "服务台复核结果")
//    private String recheckResult;
//
//    @Excel(name = "服务台复核意见")
//    private String recheckOpnion;
//
    @Excel(name = "本单位审批人员")
    private String recheckPerson;

    @Excel(name = "本单位审批时间",exportFormat = "yyy-MM-dd HH:mm:ss",width = 20)
    private Date recheckTime;

    @Excel(name = "本单位审批结果")
    private String recheckResult;

    @Excel(name = "本单位审批意见")
    private String recheckOpnion;
//
//    @Excel(name = "业务办理时间",exportFormat = "yyy-MM-dd HH:mm:ss",width = 20)
//    private Date carrayTime;
//
//    @Excel(name = "业务办理结果")
//    private String carrayResult;
//
//    @Excel(name = "业务办理人")
//    private String carrayPerson;


    @Excel(name = "环节名")
    private String modelName0;

    @Excel(name = "审批时间",exportFormat = "yyy-MM-dd HH:mm:ss",width = 20)
    private Date auditTime0;

    @Excel(name = "审批结果")
    private String auditResult0;

    @Excel(name = "审批意见")
    private String auditOpnion0;
    @Excel(name = "审批人员")
    private String auditPerson0;
    @Excel(name = "环节名")
    private String modelName1;

    @Excel(name = "审批时间",exportFormat = "yyy-MM-dd HH:mm:ss",width = 20)
    private Date auditTime1;

    @Excel(name = "审批结果")
    private String auditResult1;

    @Excel(name = "审批意见")
    private String auditOpnion1;

    @Excel(name = "审批人员")
    private String auditPerson1;

    @Excel(name = "环节名")
    private String modelName2;

    @Excel(name = "审批时间",exportFormat = "yyy-MM-dd HH:mm:ss",width = 20)
    private Date auditTime2;

    @Excel(name = "审批结果")
    private String auditResult2;

    @Excel(name = "审批意见")
    private String auditOpnion2;

    @Excel(name = "审批人员")
    private String auditPerson2;

    @Excel(name = "环节名")
    private String modelName3;

    @Excel(name = "审批时间",exportFormat = "yyy-MM-dd HH:mm:ss",width = 20)
    private Date auditTime3;

    @Excel(name = "审批结果")
    private String auditResult3;

    @Excel(name = "审批意见")
    private String auditOpnion3;

    @Excel(name = "审批人员")
    private String auditPerson3;


    @Excel(name = "业务办理时间",exportFormat = "yyy-MM-dd HH:mm:ss",width = 20)
    private Date auditTime4;

    @Excel(name = "业务办理结果")
    private String auditResult4;

    @Excel(name = "业务办理人")
    private String auditPerson4;

}
