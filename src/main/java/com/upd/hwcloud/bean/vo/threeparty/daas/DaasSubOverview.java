package com.upd.hwcloud.bean.vo.threeparty.daas;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuc
 * @date 2019/12/19
 */

@Data
public class DaasSubOverview {
    /**
     *入库资源数
     */
    @ApiModelProperty(value = "入库资源数",example = "134张表")
    private String count;

    /**
     *入库数据量
     */
    @ApiModelProperty(value = "入库数据量",example = "100亿条")
    private String sum;

    /**
     *资源数昨日增量
     */
    @ApiModelProperty(value = "资源数昨日增量",example = "100张表")
    private String countIncrement;

    /**
     *数据量昨日增量
     */
    @ApiModelProperty(value = "数据量昨日增量",example = "10亿条")
    private String sumIncrement;
}
