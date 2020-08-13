package com.hirisun.cloud.model.daas.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author wuc
 * @date 2019/12/6
 */
@Data
public class BigDataOfSaasVO {
    @Excel(name = "服务名称")
    private String serviceName;

    /**
     * 厂商
     */
    @Excel(name = "服务厂商")
    private String provider;

    @Excel(name = "所属类别")
    private String subType;
}
