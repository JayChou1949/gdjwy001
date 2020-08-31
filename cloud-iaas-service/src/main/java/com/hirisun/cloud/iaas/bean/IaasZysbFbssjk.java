package com.hirisun.cloud.iaas.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * IAAS资源上报分布式并行数据库
 * </p>
 *
 * @author yyc
 * @since 2020-05-09
 */
@TableName("TB_IAAS_ZYSB_FBSSJK")
public class IaasZysbFbssjk extends Model<IaasZysbFbssjk> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 说明
     */
         @TableField("REMARK")
    private String remark;

        /**
     * 申请信息 id
     */
         @TableField("MASTER_ID")
    private String masterId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 应用ID
     */
         @TableField("PROJECT_ID")
    private String projectId;

        /**
     * CPU 核
     */
         @TableField("CPU")
    private Integer cpu;

        /**
     * 内存 GB
     */
         @TableField("MEMORYS")
    private Double memorys;

        /**
     * 存储 TB
     */
         @TableField("STORAGES")
    private Double storages;


    public String getId() {
        return id;
    }

    public IaasZysbFbssjk setId(String id) {
        this.id = id;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public IaasZysbFbssjk setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public IaasZysbFbssjk setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public IaasZysbFbssjk setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public Integer getCpu() {
        return cpu;
    }

    public IaasZysbFbssjk setCpu(Integer cpu) {
        this.cpu = cpu;
        return this;
    }

    public Double getMemorys() {
        return memorys;
    }

    public IaasZysbFbssjk setMemorys(Double memorys) {
        this.memorys = memorys;
        return this;
    }

    public Double getStorages() {
        return storages;
    }

    public IaasZysbFbssjk setStorages(Double storages) {
        this.storages = storages;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasZysbFbssjk{" +
        "id=" + id +
        ", remark=" + remark +
        ", masterId=" + masterId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", projectId=" + projectId +
        ", cpu=" + cpu +
        ", memorys=" + memorys +
        ", storages=" + storages +
        "}";
    }
}
