package com.upd.hwcloud.bean.vo.ncov;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author wuc
 * @date 2020/2/27
 */
@Data
public class NcovIaasVo {


    @Excel(name = "应用")
    private String appName;

    @Excel(name = "弹性云服务器数量")
    private Integer ecsNum;

    @Excel(name = "CPU(核)")
    private Integer cpuNum;

    @Excel(name = "内存(GB)")
    private Long memoryNum;

    @Excel(name = "磁盘(TB)")
    private Double storageNum;

    @Excel(name = "CPU利用率(%)")
    private Double vcpuUsage;

    @Excel(name = "内存利用率(%)")
    private Double memeoryUsage;

    @Excel(name = "磁盘利用率(%)")
    private Double evsDiskUsage;

}
