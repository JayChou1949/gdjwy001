package com.hirisun.cloud.model.system;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 资源回收导入实体
 */
@Data
public class ResourceRecoverImportVo {

    /**
     * 缩配时间
     */
    @Excel(name = "缩配时间",format = "yyyy-MM-dd HH:mm")
    private Date shrinkDate;

    /**
     * 缩配类型
     */
    @Excel(name = "缩配类型")
    private String shrinkType;

    /**
     * 可用分区
     */
    @Excel(name = "可用分区名称")
    private String availablePartition;

    /**
     * 警种
     */
    @Excel(name = "警种")
    private String policeCategory;

    /**
     * 厂商
     */

    @Excel(name = "厂商")
    private String manufacturer;

    /**
     * 使用人
     */
    @Excel(name = "使用人")
    private String instanceUser;

    /**
     * 申请人
     */
    @Excel(name = "申请人")
    private String applicant;

    /**
     * 申请人电话
     */
    @Excel(name = "申请人电话")
    private String applicantPhone;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    private String projectName;

    /**
     * 应用名称
     */
   @Excel(name = "应用名称")
    private String applicationName;

    /**
     * 实例名称
     */
    @Excel(name = "实例名称")
    private String instanceName;

    /**
     * 私有IP地址
     */
    @Excel(name = "私有IP地址")
    private String privateIpAddress;

    /**
     * 实例创建时间
     */
    @Excel(name = "创建时间",format = "yyyy-MM-dd")
    private Date instanceCreatedDate;
    /**
     * 操作系统
     */
    @Excel(name = "操作系统")
    private String os;

    /**
     * 规格
     */
   @Excel(name = "规格")
    private String specification;

    /**
     * 云硬盘
     */
    @Excel(name = "云硬盘(GB)")
    private String cloudHardDisk;

    /**
     * 缩配前CPU(核)
     */
    @Excel(name = "缩配前CPU（核）")
    private String beforeShrinkCpu;

    /**
     * 缩配前内存(GB)
     */
    @Excel(name = "缩配前内存（GB）")
    private String beforeShrinkMemory;

    /**
     * 缩配前磁盘(GB)
     */
   @Excel(name = "缩配前磁盘（GB）")
    private String beforeShrinkDisk;

    /**
     * 缩配后CPU(核)
     */
    @Excel(name = "缩配后CPU（核）")
    private String afterShrinkCpu;

    /**
     * 缩配后内存(GB)
     */
    @Excel(name = "缩配后内存（GB）")
    private String afterShrinkMemory;

    /**
     * 缩配后磁盘(GB)
     */
    @Excel(name = "缩配后磁盘（GB）")
    private String afterShrinkDisk;

    /**
     * CPU使用率平均值(%)
     */
    @Excel(name = "CPU使用率平均值（%）")
    private String cpuAvgRatio;

    /**
     * CPU使用率最大值(%)
     */
    @Excel(name = "CPU使用率最大值（%）")
    private String cpuMaxRatio;

    /**
     * 内存使用率平均值(%)
     */
    @Excel(name = "内存使用率平均值（%）")
    private String memoryAvgRatio;

    /**
     * 内存使用率最大值(%)
     */
    @Excel(name = "内存使用率最大值（%）")
    private String memoryMaxRatio;

    /**
     * 云磁盘使用率平均值(%)
     */
    @Excel(name = "云硬盘使用率平均值（%）")
    private String cloudDiskAvgRatio;

    /**
     * 云磁盘使用率最大值(%)
     */
    @Excel(name = "云硬盘使用率最大值（%）")
    private String cloudDiskMaxRatio;

    @Excel(name = "申请人单位")
    private String applicantOrgName;

}
