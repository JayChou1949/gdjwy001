package com.upd.hwcloud.bean.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangbao
 * @since 2019-10-24
 */
@TableName("TB_SAAS_RECOVER_INFO")
public class SaasRecoverInfo extends Model<SaasRecoverInfo> {

    private static final long serialVersionUID = 1L;

        /**
     * 主键
     */
        @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 回收申请单ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 回收人的姓名
     */
        @Excel(name = "姓名")
         @TableField("RE_NAME")
    private String reName;

        /**
     * 回收人身份证号
     */
        @Excel(name = "身份证号")
         @TableField("RE_IDCARD")
    private String reIdcard;

        /**
     * 所在单位id
     */
         @TableField("RE_ORG_ID")
    private String reOrgId;

        /**
     * 单位名称
     */
        @Excel(name = "工作单位")
         @TableField("RE_ORG_NAME")
    private String reOrgName;

        /**
     * 创建时间
     */
         @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

        /**
     * 修改时间
     */
         @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 是否为导入的数据 0否 1是
     */
    @TableField("IS_IMPORT")
    private String isImport;

    /**
     * 服务数量
     */
    @Excel(name = "当前在用服务")
    @TableField(exist = false)
    private String num;

    /**
     * 当前环节处理人
     */
    @TableField(exist = false)
    private String processingPerson;

    public String getProcessingPerson() {
        return processingPerson;
    }

    public void setProcessingPerson(String processingPerson) {
        this.processingPerson = processingPerson;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public SaasRecoverInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public SaasRecoverInfo setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getReName() {
        return reName;
    }

    public SaasRecoverInfo setReName(String reName) {
        this.reName = reName;
        return this;
    }

    public String getReIdcard() {
        return reIdcard;
    }

    public SaasRecoverInfo setReIdcard(String reIdcard) {
        this.reIdcard = reIdcard;
        return this;
    }

    public String getReOrgId() {
        return reOrgId;
    }

    public SaasRecoverInfo setReOrgId(String reOrgId) {
        this.reOrgId = reOrgId;
        return this;
    }

    public String getReOrgName() {
        return reOrgName;
    }

    public SaasRecoverInfo setReOrgName(String reOrgName) {
        this.reOrgName = reOrgName;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SaasRecoverInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public SaasRecoverInfo setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getIsImport() {
        return isImport;
    }

    public void setIsImport(String isImport) {
        this.isImport = isImport;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SaasRecoverInfo{" +
        "id=" + id +
        ", appInfoId=" + appInfoId +
        ", reName=" + reName +
        ", reIdcard=" + reIdcard +
        ", reOrgId=" + reOrgId +
        ", reOrgName=" + reOrgName +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
