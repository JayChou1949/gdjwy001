package com.hirisun.cloud.model.iaas.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class EcsVO {
    @Excel(name = "服务名称")
    private String name;
    private String nameCn;
    private String type;
    @Excel(name = "vCPU(核)")
    private Double vcpuSize;
    @Excel(name = "vCPU利用率")
    private Double cpuUsage;
    @Excel(name = "内存(G)")
    private Double ramSize;
    @Excel(name = "内存使用率")
    private Double memoryUsage;
    @Excel(name = "存储(T)")
    private Double diskSize;
    @Excel(name = "存储使用率")
    private Double evsDiskUsage;
    private Double cpuUsageAvg;
    private Double diskUsageAvg;
    private Double memoryUsageAvg;
    private Double cpuUsageMax;
    private Double diskUsageMax;
    private Double memoryUsageMax;
    private String tenant;
    private String siteName;
    private String siteType;
    private Integer updateTime;
    private String cloud1;
    private String cloud2;
    private String vmCreateTime;
    private String ipAddress;
    private String comments;
    private String osVersion;
    private String spec;
    private String isDelete;
}
