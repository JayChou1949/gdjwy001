package com.upd.hwcloud.bean.vo.threeparty.daas;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuc
 * @date 2019/12/19
 */
@Data
public class DaasOverview {

    @ApiModelProperty(value = "地市警种资源总量",example = "479000张表")
    private String countValue;

    @ApiModelProperty(value = "地市警种资源总量(条)",example = "27.87亿条")
    private String sumValue;


    @ApiModelProperty(value = "上报省厅数据量",example = "324000亿条")
    private String toProvinceValue;

    @ApiModelProperty(value = "上报省厅数据量（张）",example = "111张表")
    private String toProvinceCountValue;

    @ApiModelProperty(value = "省厅资源总量（张）",example = "12002张表")
    private String provinceValue;

    @ApiModelProperty(value = "省厅资源总量（条）",example = "31960.67亿条")
    private String provinceSumValue;

    @ApiModelProperty("区县上报")
    private DaasSubOverview area;

    @ApiModelProperty("警种及行业公安共享")
    private DaasSubOverview police;

    @ApiModelProperty("政府部分及企事业单位")
    private DaasSubOverview government;
}
