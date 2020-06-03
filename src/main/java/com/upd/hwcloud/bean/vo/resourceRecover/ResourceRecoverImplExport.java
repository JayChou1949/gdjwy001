package com.upd.hwcloud.bean.vo.resourceRecover;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yyc
 * @date 2020/5/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRecoverImplExport {

    /**
     * 缩配类型
     */
    @Excel(name = "缩配类型")
    private String shrinkType;


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
     * 实际缩配CPU(核)
     */
    @Excel(name = "实际缩配CPU（核）")
    private String actualShrinkCpu;

    /**
     * 实际缩配内存(GB)
     */
    @Excel(name = "实际缩配内存（GB）")
    private String actualShrinkMemory;

    /**
     * 实际缩配磁盘(GB)
     */
    @Excel(name = "实际缩配磁盘（GB）")
    private String actualShrinkDisk;

    @Excel(name = "处理结果")
    private String processResult;


}
