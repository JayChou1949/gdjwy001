package com.upd.hwcloud.bean.vo.threeparty.iaas;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 物理设备
 * @author wuc
 * @date 2019/12/19
 */
@Data
public class PhysicalDevice {

    @ApiModelProperty(value = "服务器")
    private int server;

    @ApiModelProperty(value = "网络设备")
    private int network;

    @ApiModelProperty(value = "存储设备")
    private BigDecimal storage;

}
