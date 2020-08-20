package com.hirisun.cloud.order.bean.application;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;

/**
 * <p>
 * 服务申请审核信息表
 * </p>
 *
 * @author wuc
 * @since 2018-12-04
 */
@TableName("TB_APP_REVIEW_INFO")
public class AppReviewInfo extends Model<AppReviewInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 审批人
     */
         @TableField("CREATOR")
    private String creator;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 结果 0:驳回 1:通过
     */
         @TableField("RESULT")
    private String result;

    @TableField("REMARK")
    private String remark;

        /**
     * 环节名
     */
         @TableField("STEP_NAME")
    private String stepName;

        /**
     * 服务信息id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    /**
     * 类型 1：审核 2：实施;4 回退，5参与人,6加办
     */
    @TableField("R_TYPE")
     private String rType;

    /**
     * 环节 id
     */
    @TableField("FLOW_STEP_ID")
    private String flowStepId;

    @TableField(exist = false)
    private UserVO user;

    @TableField(exist = false)
    private List<FilesVo> fileList;

    /**
     * 是否关系型数据库新增账号流程
     */
    @TableField(exist = false)
    private int  rdbAddAccount;

    @TableField(exist = false)
    private int resourceRecoveredAgree;

    public String getId() {
        return id;
    }

    public AppReviewInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public AppReviewInfo setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AppReviewInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public AppReviewInfo setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getResult() {
        return result;
    }

    public AppReviewInfo setResult(String result) {
        this.result = result;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AppReviewInfo setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public AppReviewInfo setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }

    public String getFlowStepId() {
        return flowStepId;
    }

    public void setFlowStepId(String flowStepId) {
        this.flowStepId = flowStepId;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public List<FilesVo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FilesVo> fileList) {
        this.fileList = fileList;
    }

    public int getRdbAddAccount() {
        return rdbAddAccount;
    }

    public int getResourceRecoveredAgree() {
        return resourceRecoveredAgree;
    }

    public void setResourceRecoveredAgree(int resourceRecoveredAgree) {
        this.resourceRecoveredAgree = resourceRecoveredAgree;
    }

    public void setRdbAddAccount(int rdbAddAccount) {
        this.rdbAddAccount = rdbAddAccount;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AppReviewInfo{" +
        "id=" + id +
        ", creator=" + creator +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", result=" + result +
        ", remark=" + remark +
        ", stepName=" + stepName +
        ", appInfoId=" + appInfoId +
        "}";
    }
}
