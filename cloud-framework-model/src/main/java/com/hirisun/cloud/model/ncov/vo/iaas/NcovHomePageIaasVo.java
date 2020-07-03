package com.hirisun.cloud.model.ncov.vo.iaas;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("疫情-IaaS(ECS)")
public class NcovHomePageIaasVo {

    @Excel(name = "供给总数",type = 10)
    @ApiModelProperty(value="供给总数")
    private Integer total;

    @Excel(name = "支持警种",type = 10)
    @ApiModelProperty(value="支持警种")
    private Integer supportPolice;

    @Excel(name = "支撑地市",type = 10)
    @ApiModelProperty(value="支撑地市")
    private Integer supportArea;

    @Excel(name = "支撑应用",type = 10)
    @ApiModelProperty(value="支撑应用")
    private Integer supportApp;

    @Excel(name = "CPU(核)",type = 10)
    @ApiModelProperty(value="CPU(核)")
    private Integer cpu;

    @Excel(name = "内存(GB)",type = 10)
    @ApiModelProperty(value="内存(GB)")
    private Double memory;

    @Excel(name = "存储(TB)",type = 10)
    @ApiModelProperty(value="存储(TB)")
    private Double storage;
}
