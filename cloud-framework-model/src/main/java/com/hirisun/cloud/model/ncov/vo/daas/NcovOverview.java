package com.hirisun.cloud.model.ncov.vo.daas;

import lombok.Data;

/**
 * @author wuc
 * @date 2020/2/23
 */
@Data
public class NcovOverview {

    private String name;

    //总量
    private Long total;

    //昨日新增
    private Long yesterdayUp;

    //表数
    private Long tableNum;
}
