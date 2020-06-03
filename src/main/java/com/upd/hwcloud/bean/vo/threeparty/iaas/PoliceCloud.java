package com.upd.hwcloud.bean.vo.threeparty.iaas;

import com.upd.hwcloud.common.utils.BigDecimalUtil;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 警务云工程
 * @author wuc
 * @date 2019/12/19
 */
@Accessors(chain = true)
@Data
public class PoliceCloud {

    @ApiModelProperty("vCpu分配量(核)")
    private Integer vCpuAllocation;

    @ApiModelProperty("vCpu总量(核)")
    private Integer vCpuTotal;

    @ApiModelProperty("vCpu分配率")
    private BigDecimal vCpuAllocationRate;

    @ApiModelProperty("vCpu使用率")
    private BigDecimal vCpuUsage;

    @ApiModelProperty("gpu分配量")
    private Integer gpuAllocation;

    @ApiModelProperty("gpu总量")
    private Integer gpuTotal;

    @ApiModelProperty("gpu分配率")
    private BigDecimal gpuAllocationRate;

    @ApiModelProperty("gpu使用率")
    private BigDecimal gpuUsage;

    @ApiModelProperty("memory分配量(GB)")
    private BigDecimal memoryAllocation;

    @ApiModelProperty("memory总量(GB)")
    private BigDecimal memoryTotal;

    @ApiModelProperty("memory分配率")
    private BigDecimal memoryAllocationRate;

    @ApiModelProperty("memory使用率")
    private BigDecimal memoryUsage;

    @ApiModelProperty("storage分配量(TB)")
    private BigDecimal storageAllocation;

    @ApiModelProperty("storage总量(TB)")
    private BigDecimal storageTotal;

    @ApiModelProperty("storage分配率")
    private BigDecimal storageAllocationRate;

    @ApiModelProperty("storage使用率")
    private BigDecimal storageUsage;


}
