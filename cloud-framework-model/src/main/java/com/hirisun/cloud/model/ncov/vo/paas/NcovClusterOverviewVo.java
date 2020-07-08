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
@ApiModel("数据概览")
public class NcovClusterOverviewVo {

    @Excel(name = "CPU(核)",type = 10)
    @ApiModelProperty(value="CPU(核)")
    private Integer cpu;

    @Excel(name = "内存(GB)",type = 10)
    @ApiModelProperty(value="内存(GB)")
    private Double memory;
}
