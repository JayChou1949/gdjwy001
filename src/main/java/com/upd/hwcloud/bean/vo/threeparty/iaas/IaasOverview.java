package com.upd.hwcloud.bean.vo.threeparty.iaas;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 二级门户IaaS概览
 * @author wuc
 * @date 2019/12/19
 */
@Data
public class IaasOverview {
    @ApiModelProperty(value = "物理设备")
    private  PhysicalDevice physicalDevice;

    @ApiModelProperty(value = "服务实例")
    private ServiceInstance serviceInstance;

    @ApiModelProperty(value = "大数据组件")
    private BigDataComponent bigDataComponent;

    @ApiModelProperty("警务云工程")
    private PoliceCloud policeCloud;
}
