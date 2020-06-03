package com.upd.hwcloud.bean.entity;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author wb
 * @date 2019/11/10
 */
@Data
public class IaasZYSBapplicationExport {
    @Excel(name = "序号")
    private Integer id;

    private String masterId;

    @Excel(name = "工单号",width = 15)
    private String orderNumber;

    @Excel(name = "申请人")
    private String creatorName;

    @Excel(name = "申请人单位",width = 20)
    private String creatorUnit;

    @Excel(name = "项目信息一",width = 15)
    private String pro1;

    @Excel(name = "项目信息二",width = 15)
    private String pro2;

    @Excel(name = "项目信息三",width = 15)
    private String pro3;

    @Excel(name = "项目信息四",width = 15)
    private String pro4;

    @Excel(name = "项目信息五",width = 15)
    private String pro5;

    @Excel(name = "项目信息六",width = 15)
    private String pro6;

    @Excel(name = "当前处理状态")
    private String stepName;

    @Excel(name = "服务台审核人员")
    private String recheckPerson;

    @Excel(name = "服务台复核时间",exportFormat = "yyy-MM-dd hh:mm:ss",width = 20)
    private Date recheckTime;

    @Excel(name = "服务台审批结果")
    private String recheckResult;

    @Excel(name = "服务台复核意见",width = 30)
    private String recheckOpnion;
}
