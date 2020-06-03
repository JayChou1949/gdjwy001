package com.upd.hwcloud.bean.vo.resourceReport.export;

import com.upd.hwcloud.bean.entity.report.ReportFusionAccess;
import com.upd.hwcloud.bean.entity.report.ReportIaas;
import com.upd.hwcloud.bean.entity.report.ReportPaas;

import java.util.Date;
import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yyc
 * @date 2020/5/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportExport {

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
    private List<ReportIaas> iaasList;

    @ExcelCollection(name = "PaaS资源",orderNum = "7")
    private List<ReportPaas> paasList;

    @ExcelCollection(name = "FusionAccess",orderNum = "7")
    private List<ReportFusionAccess> fusionAccessList;





}
