package com.upd.hwcloud.bean.vo.ncov;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author wuc
 * @date 2020/3/3
 */
@Data
public class NcovClusterResource {

    @Excel(name = "部门")
    private String department;

    @Excel(name = "应用名")
    private String appName;

    @Excel(name = "集群")
    private String clusterName;

    @Excel(name = "CPU(核)",type = 10)
    private Integer cpu;

    @Excel(name = "内存(GB)",type = 10)
    private Double memory;
}
