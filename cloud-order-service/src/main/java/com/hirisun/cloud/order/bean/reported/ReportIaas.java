package com.hirisun.cloud.order.bean.reported;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-04
 */
@TableName("TB_REPORT_IAAS")
@ApiModel(value="ReportIaas对象", description="")
public class ReportIaas implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "UUID")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "资源名称")
    @TableField("RESOURCE_NAME")
    private String resourceName;

    @ApiModelProperty(value = "CPU 核")
    @TableField("CPU")
    private BigDecimal cpu;

    @ApiModelProperty(value = "内存 GB")
    @TableField("MEMORY")
    private BigDecimal memory;

    @ApiModelProperty(value = "存储 TB")
    @TableField("DISK")
    private BigDecimal disk;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "应用信息ID")
    @TableField("PROJECT_ID")
    private String projectId;

    @ApiModelProperty(value = "订单ID")
    @TableField("APP_INFO_ID")
    private String appInfoId;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFIED_TIME")
    private LocalDateTime modifiedTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public BigDecimal getCpu() {
        return cpu;
    }

    public void setCpu(BigDecimal cpu) {
        this.cpu = cpu;
    }

    public BigDecimal getMemory() {
        return memory;
    }

    public void setMemory(BigDecimal memory) {
        this.memory = memory;
    }

    public BigDecimal getDisk() {
        return disk;
    }

    public void setDisk(BigDecimal disk) {
        this.disk = disk;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public void setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String toString() {
        return "ReportIaas{" +
        "id=" + id +
        ", resourceName=" + resourceName +
        ", cpu=" + cpu +
        ", memory=" + memory +
        ", disk=" + disk +
        ", remark=" + remark +
        ", projectId=" + projectId +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
