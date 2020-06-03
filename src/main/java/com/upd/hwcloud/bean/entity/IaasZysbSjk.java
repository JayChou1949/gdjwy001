package com.upd.hwcloud.bean.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * IAAS资源上报数据库
 * </p>
 *
 * @author wuc
 * @since 2019-09-09
 */
@TableName("TB_IAAS_ZYSB_SJK")
public class IaasZysbSjk extends Model<IaasZysbSjk> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 数据库版本
     */
        @Excel(name = "数据库版本")
         @TableField("VERSION")
    private String version;

        /**
     * 备注
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
     * 存储
     */
        @Excel(name = "硬盘(TB)", type = 10)
         @TableField("STORAGES")
    private Double storages;


    public String getId() {
        return id;
    }

    public IaasZysbSjk setId(String id) {
        this.id = id;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public IaasZysbSjk setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public IaasZysbSjk setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public IaasZysbSjk setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasZysbSjk setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasZysbSjk setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public IaasZysbSjk setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public Double getStorages() {
        return storages;
    }

    public IaasZysbSjk setStorages(Double storages) {
        this.storages = storages;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasZysbSjk{" +
        "id=" + id +
        ", version=" + version +
        ", remark=" + remark +
        ", masterId=" + masterId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", projectId=" + projectId +
        ", storages=" + storages +
        "}";
    }
}
