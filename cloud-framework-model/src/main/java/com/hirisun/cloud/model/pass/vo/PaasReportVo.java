package com.hirisun.cloud.model.pass.vo;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("paas资源Vo")
public class PaasReportVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;

    @Excel(name = "资源名称")
    @ApiModelProperty("资源名称")
    private String resourceName;

    @ApiModelProperty("CPU 核")
    @Excel(name = "CPU(核)",type = 10)
    private Double cpu;

    @ApiModelProperty("内存 GB")
    @Excel(name = "内存(GB)",type = 10)
    private Double memory;

    @ApiModelProperty("存储 TB")
    @Excel(name = "存储(TB)",type = 10)
    private Double disk;
    
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("应用信息ID")
    private String projectId;

    @ApiModelProperty("订单ID")
    private String appInfoId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date modifiedTime;

}
