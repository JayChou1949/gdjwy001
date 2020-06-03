package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.bean.entity.report.ReportFusionAccess;
import com.upd.hwcloud.bean.entity.report.ReportIaas;
import com.upd.hwcloud.bean.entity.report.ReportPaas;
import com.upd.hwcloud.bean.entity.report.ReportSpecial;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * IAAS资源上报项目信息
 * </p>
 *
 * @author wuc
 * @since 2019-09-09
 */
@TableName("TB_IAAS_ZYSB_XMXX")
public class IaasZysbXmxx extends Model<IaasZysbXmxx> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 项目名称
     */
         @TableField("PROJECT_NAME")
    private String projectName;

        /**
     * 项目说明
     */
         @TableField("PROJECT_DESC")
    private String projectDesc;

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
     * IaaS资源
     */
    @TableField(exist = false)
    private List<ReportIaas> iaasList;

    /**
     * PaaS资源
     */
    @TableField(exist = false)
    private List<ReportPaas> paasList;

    /**
     * 桌面云资源
     */
    @TableField(exist = false)
    private List<ReportFusionAccess> fusionAccessList;


    /**
     * 特殊需求
     */
    @TableField(exist = false)
    private List<ReportSpecial> specialList;

    @TableField(exist = false)
    private List<Files> fileList;

    public String getId() {
        return id;
    }

    public IaasZysbXmxx setId(String id) {
        this.id = id;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public IaasZysbXmxx setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public IaasZysbXmxx setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public IaasZysbXmxx setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasZysbXmxx setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasZysbXmxx setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }


    public List<ReportIaas> getIaasList() {
        return iaasList;
    }

    public void setIaasList(List<ReportIaas> iaasList) {
        this.iaasList = iaasList;
    }

    public List<ReportPaas> getPaasList() {
        return paasList;
    }

    public void setPaasList(List<ReportPaas> paasList) {
        this.paasList = paasList;
    }

    public List<ReportFusionAccess> getFusionAccessList() {
        return fusionAccessList;
    }

    public void setFusionAccessList(List<ReportFusionAccess> fusionAccessList) {
        this.fusionAccessList = fusionAccessList;
    }

    public List<ReportSpecial> getSpecialList() {
        return specialList;
    }

    public void setSpecialList(List<ReportSpecial> specialList) {
        this.specialList = specialList;
    }

    public List<Files> getFileList() {
        return fileList;
    }

    public void setFileList(List<Files> fileList) {
        this.fileList = fileList;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasZysbXmxx{" +
        "id=" + id +
        ", projectName=" + projectName +
        ", projectDesc=" + projectDesc +
        ", masterId=" + masterId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
