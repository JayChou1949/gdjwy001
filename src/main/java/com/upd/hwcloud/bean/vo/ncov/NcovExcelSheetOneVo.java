package com.upd.hwcloud.bean.vo.ncov;

import com.upd.hwcloud.bean.entity.Files;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author wuc
 * @date 2020/2/19
 */
@Data
public class NcovExcelSheetOneVo {

    @Excel(name = "数据部门")
    private String departmentOfData;

    @Excel(name = "*采集单位")
    private String unit;

    @Excel(name = "表中文名")
    private String tableCnName;

    private String elementType;

    @Excel(name = "数据总数",type = 10)
    private Long dataTotalNum;

    @Excel(name = "昨日增量",type = 10)
    private Integer dataUpOfYesterday;


    private String attachmentName;

    private String attachmentUrl;

}
