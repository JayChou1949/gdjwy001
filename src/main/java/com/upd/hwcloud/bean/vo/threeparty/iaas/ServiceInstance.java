package com.upd.hwcloud.bean.vo.threeparty.iaas;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 服务实例
 * @author wuc
 * @date 2019/12/19
 */
@Data
public class ServiceInstance {

    @ApiModelProperty("弹性云服务器")
    private int ecs;

    @ApiModelProperty("裸金属服务器")
    private int bms;

    @ApiModelProperty("桌面云")
    private int cloudDesktop;

    @ApiModelProperty("云硬盘")
    private BigDecimal evs;

    @ApiModelProperty("弹性伸缩")
    private int  as;

    @ApiModelProperty("弹性文件服务")
    private BigDecimal sfs;

    @ApiModelProperty("对象存储")
    private BigDecimal obs;

    @ApiModelProperty("弹性负载均衡")
    private int elb;


}
