package com.upd.hwcloud.bean.vo.ncov;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.hwcloud.bean.entity.webManage.News;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author wuc
 * @date 2020/2/19
 */
@Data
public class NcovExcelSheetTwoVo {

    @Excel(name = "数据部门")
    private String departmentOfData;

    @Excel(name = "*采集单位")
    private String unit;

    @Excel(name = "表中文名")
    private String tableCnName;

    @Excel(name = "要素类型")
    private String elementType;

    @Excel(name = "数据总数",type = 10)
    private Long dataTotalNum;

    @JSONField(name = "02-13")
    @Excel(name = "数据一",type = 10)
    private Integer seven;

    @Excel(name = "数据二",type = 10)
    private Integer six;

    @Excel(name = "数据三",type = 10)
    private Integer five;

    @Excel(name = "数据四",type = 10)
    private Integer four;

    @Excel(name = "数据五",type = 10)
    private Integer three;

    @Excel(name = "数据六",type = 10)
    private Integer two;

    @Excel(name = "数据七",type = 10)
    private Integer one;

}
