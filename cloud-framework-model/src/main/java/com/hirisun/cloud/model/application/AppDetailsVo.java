package com.hirisun.cloud.model.application;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @ Author：xqp
 * @ Description：
 * @ Date：Created in 11:25 2020/7/2
 */
@Data
public class AppDetailsVo {

    @Excel(name = "序号",orderNum = "1")
    private Integer id;

    @Excel(name = "应用名称",orderNum = "2",width = 50)
    private String serviceName;

    @Excel(name = "访问总数",orderNum = "3",width = 8)
    private Integer viewCount;

    @Excel(name = "应用开通次数",orderNum = "4",width = 8)
    private Integer openingNumber;

    @Excel(name = "权限开通时间",orderNum = "5",width = 25)
    private String openingTime;

    @Excel(name = "权限被回收次数",orderNum = "6",width = 8)
    private Integer recyclingNumber;

    @Excel(name = "最后回收时间",orderNum = "7",width = 25)
    private String recyclingTime;
}
