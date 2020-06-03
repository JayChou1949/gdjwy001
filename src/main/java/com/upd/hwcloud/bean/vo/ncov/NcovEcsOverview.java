package com.upd.hwcloud.bean.vo.ncov;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author wuc
 * @date 2020/3/5
 */
@Data
public class NcovEcsOverview {

    @Excel(name = "供给总数",type = 10)
    private Integer total;

    @Excel(name = "支持警种",type = 10)
    private Integer supportPolice;

    @Excel(name = "支撑地市",type = 10)
    private Integer supportArea;

    @Excel(name = "支撑应用",type = 10)
    private Integer supportApp;

    @Excel(name = "CPU(核)",type = 10)
    private Integer cpu;

    @Excel(name = "内存(GB)",type = 10)
    private Double memory;

    @Excel(name = "存储(TB)",type = 10)
    private Double storage;
}
