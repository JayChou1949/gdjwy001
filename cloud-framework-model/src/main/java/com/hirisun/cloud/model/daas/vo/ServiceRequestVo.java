package com.hirisun.cloud.model.daas.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class ServiceRequestVo {

    @Excel(name = "序号",orderNum = "1")
    private Integer id;//序号

    @Excel(name = "服务名称",orderNum = "1",width = 60)
    private String serviceName;//服务名称

    @Excel(name = "地市",orderNum = "2",width = 15)
    private String serviceAreaName;//地市

    @Excel(name = "警种",orderNum = "3",width = 15)
    private String servicePoliceCategory;//警种

    @Excel(name = "应用名称",orderNum = "4",width = 60)
    private String appName;//应用名称

    @Excel(name = "地市",orderNum = "5",width = 15)
    private String appAreaName;//地市

    @Excel(name = "警种",orderNum = "6",width = 15)
    private String appPoliceCategory;//警种

    @Excel(name = "被调用总数(次)",orderNum = "7",width = 15,type = 10)
    private Integer totalReqCount;//被调用总数

    @Excel(name = "时间",orderNum = "8",width = 15)
    private String currentTime;//时间
}
