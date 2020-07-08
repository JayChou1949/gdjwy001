package com.hirisun.cloud.model.ncov.vo.paas;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuc
 * @date 2020/3/3
 */
@Data
@ApiModel("集群资源分配详情")
public class NcovClusterResourceVo {

    @Excel(name = "部门")
    @ApiModelProperty(value="部门")
    private String department;

    @Excel(name = "应用名")
    @ApiModelProperty(value="应用名")
    private String appName;

    @Excel(name = "集群")
    @ApiModelProperty(value="集群")
    private String clusterName;

    @Excel(name = "CPU(核)",type = 10)
    @ApiModelProperty(value="CPU(核)")
    private Integer cpu;

    @Excel(name = "内存(GB)",type = 10)
    @ApiModelProperty(value="内存(GB)")
    private Double memory;
}
