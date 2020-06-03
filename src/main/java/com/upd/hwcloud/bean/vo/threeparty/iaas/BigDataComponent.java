package com.upd.hwcloud.bean.vo.threeparty.iaas;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大数据组件
 * @author wuc
 * @date 2019/12/19
 */
@Data
public class BigDataComponent {

    @ApiModelProperty(value = "服务数")
    private int serviceNum;

    @ApiModelProperty(value = "vCpu数(核)")
    private int vCpu;

    @ApiModelProperty(value = "内存(GB)")
    private BigDecimal memory;

    @ApiModelProperty(value = "存储(TB)")
    private BigDecimal storage;

}
