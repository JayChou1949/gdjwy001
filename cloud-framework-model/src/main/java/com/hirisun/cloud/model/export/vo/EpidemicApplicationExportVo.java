package com.hirisun.cloud.model.export.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @author wb   疫情应用工单导出
 * @date 2019/11/9
 */
@Data
public class EpidemicApplicationExportVo {

    @Excel(name = "序号")
    private Integer id;

    @Excel(name = "工单号",width = 15)
    private String orderNumber;

    @Excel(name = "申请单总数")
    private String num;

    @Excel(name = "申请人")
    private String creatorName;

    @Excel(name = "申请人单位",width = 15)
    private String orgName;

    @Excel(name = "当前环节")
    private String stepName;

    @Excel(name = "服务台审核人员")
    private String recheckPerson;

    @Excel(name = "服务台审核时间",exportFormat = "yyy-MM-dd hh:mm:ss",width = 15)
    private Date recheckTime;

    @Excel(name = "服务台审批结果")
    private String recheckResult;

    @Excel(name = "服务台复核意见",width = 30)
    private String recheckOpnion;

    private String ids;


}
