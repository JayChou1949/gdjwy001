package com.hirisun.cloud.model.export.vo;


import java.util.Date;
import java.util.List;

import com.hirisun.cloud.model.iaas.vo.IaasReportVo;
import com.hirisun.cloud.model.pass.vo.PaasReportVo;
import com.hirisun.cloud.model.system.ReportFusionAccessVo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportExportVo {

    /**
     * 申请单号
     */
    private String id;

    @Excel(name = "工单号")
    private String orderNumber;

    @Excel(name = "申请人")
    private String creatorName;

    @Excel(name = "申请单位")
    private String creatorUnit;

    @Excel(name = "申请时间", exportFormat = "yyyy-MM-dd")
    private Date applicationTime;

    @Excel(name = "预计使用时间", exportFormat = "yyyy-MM-dd")
    private Date startDate;

    @ExcelCollection(name = "IaaS资源",orderNum = "6")
    private List<IaasReportVo> iaasList;

    @ExcelCollection(name = "PaaS资源",orderNum = "7")
    private List<PaasReportVo> paasList;

    @ExcelCollection(name = "FusionAccess",orderNum = "7")
    private List<ReportFusionAccessVo> fusionAccessList;

}
