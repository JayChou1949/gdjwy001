package com.upd.hwcloud.bean.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @Description: 虚拟机导出通用DTO
 * @author: yyc
 * @date: 2019-08-26 15:20
 **/
public class VmGeneralExport {

    @Excel(name = "服务名称")
    private String nameCn;

    @Excel(name = "资源类型")
    private String type;

    @Excel(name = "vCPU(核)")
    private Double vcpuSize;

    @Excel(name = "内存(G)")
    private Double ramSize;

    @Excel(name = "存储(T)")
    private Double diskSize;

    @Excel(name = "vCPU利用率(%)")
    private Double cpuUsage;

    @Excel(name = "内存使用率(%)")
    private Double memoryUsage;

    @Excel(name = "存储使用率(%)")
    private Double evsDiskUsage;

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getVcpuSize() {
        return vcpuSize;
    }

    public void setVcpuSize(Double vcpuSize) {
        this.vcpuSize = vcpuSize;
    }

    public Double getRamSize() {
        return ramSize;
    }

    public void setRamSize(Double ramSize) {
        this.ramSize = ramSize;
    }

    public Double getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(Double diskSize) {
        this.diskSize = diskSize;
    }

    public Double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(Double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public Double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public Double getEvsDiskUsage() {
        return evsDiskUsage;
    }

    public void setEvsDiskUsage(Double evsDiskUsage) {
        this.evsDiskUsage = evsDiskUsage;
    }
}
