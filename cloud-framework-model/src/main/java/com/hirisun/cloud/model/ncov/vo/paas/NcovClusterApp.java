package com.hirisun.cloud.model.ncov.vo.paas;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class NcovClusterApp {

    @Excel(name = "应用名")
    private String appName;

    @Excel(name = "CPU总数(核)",type = 10)
    private Integer cpu;

    @Excel(name = "内存总数(GB)",type = 10)
    private Double memory;

    @Excel(name = "CPUDAY1",type = 10)
    private Double cpuDay1;

    @Excel(name = "CPUDAY2",type = 10)
    private Double cpuDay2;

    @Excel(name = "CPUDAY3",type = 10)
    private Double cpuDay3;

    @Excel(name = "CPUDAY4",type = 10)
    private Double cpuDay4;

    @Excel(name = "CPUDAY5",type = 10)
    private Double cpuDay5;

    @Excel(name = "CPUDAY6",type = 10)
    private Double cpuDay6;

    @Excel(name = "CPUDAY7",type = 10)
    private Double cpuDay7;

    @Excel(name = "MEMDAY1",type = 10)
    private Double memDay1;

    @Excel(name = "MEMDAY2",type = 10)
    private Double memDay2;

    @Excel(name = "MEMDAY3",type = 10)
    private Double  memDay3;

    @Excel(name = "MEMDAY4",type = 10)
    private Double memDay4;

    @Excel(name = "MEMDAY5",type = 10)
    private Double memDay5;

    @Excel(name = "MEMDAY6",type = 10)
    private Double memDay6;

    @Excel(name = "MEMDAY7",type = 10)
    private Double memDay7;


}
