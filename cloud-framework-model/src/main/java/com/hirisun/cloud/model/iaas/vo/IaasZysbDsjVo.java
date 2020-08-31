package com.hirisun.cloud.model.iaas.vo;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("IAAS资源上报大数据")
@Data
public class IaasZysbDsjVo implements Serializable {

	private static final long serialVersionUID = 2372578086771665351L;

	@ApiModelProperty("id")
    private String id;

    @Excel(name = "需求组件")
    @ApiModelProperty("组件名称")
    private String resourceName;

    @ApiModelProperty("说明")
    private String remark;

    @ApiModelProperty("申请信息 id")
    private String masterId;

    @ApiModelProperty("创建日期")
    private Date createTime;

    @ApiModelProperty("修改日期")
    private Date modifiedTime;

    @ApiModelProperty("应用ID")
    private String projectId;

    @Excel(name = "CPU(核)", type = 10)
    @ApiModelProperty("CPU(核)")
    private Double cpu;

    @Excel(name = "内存(GB)", type = 10)
    @ApiModelProperty("内存(GB)")
    private Double memorys;

    @Excel(name = "存储(TB)", type = 10)
    @ApiModelProperty("存储(TB)")
    private Double storages;

}
