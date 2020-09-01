package com.hirisun.cloud.system.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hirisun.cloud.model.service.AppReviewInfoVo;

/**
 * 资源回收申请信息
 */
@TableName("TB_RESOURCE_RECOVER_APP_INFO")
public class ResourceRecoverAppInfo implements Serializable {

	private static final long serialVersionUID = 3768746595846131924L;

		/**
     * UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 发起回收人
     */
         @TableField("CREATOR")
    private String creator;

        /**
     * 发起回收人申请号
     */
         @TableField("CREATOR_ID_CARD")
    private String creatorIdCard;

        /**
     * 发起回收人电话
     */
         @TableField("CREATOR_PHONE")
    private String creatorPhone;

        /**
     * 被回收人
     */
         @TableField("RECOVERED_PERSON")
    private String recoveredPerson;

        /**
     * 被回收人电话
     */
         @TableField("RECOVERED_PERSON_PHONE")
    private String recoveredPersonPhone;

    /**
     * 被回收人身份证
     */
    @TableField("RECOVERED_PERSON_ID_CARD")
    private String recoveredPersonIdCard;

        /**
     * 订单号
     */
         @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * 拟缩虚拟机数目
     */
    @TableField(exist = false)
     private String num;

        /**
     * 订单状态
     */
         @TableField("STATUS")
    private String status;

        /**
     * 创建时间
     */
        @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

        /**
     * 修改时间
     */
        @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 被回收人同意回收 0 不同意 1同意
     */
    @TableField("RECOVERED_AGREE")
    private Integer recoveredAgree;


    @TableField(exist = false)
    private List<Files> fileList;

    /**
     * 流程ID
     */
    @TableField(exist = false)
         private String workFlowId;
    /**
     * 流程版本号
     */
    @TableField(exist = false)
         private Integer workFlowVersion;

    @TableField(exist = false)
    private List<ResourceRecover> serverList;

    @TableField(exist = false)
    private List<ResourceRecoverImpl> implServerList;

    @TableField(exist = false)
    private List<AppReviewInfoVo> reviewList;

    /**
     * 实施结果(最后一条)
     */
    @TableField(exist = false)
    private AppReviewInfoVo impl;



    /**
     * 是否可审核
     */
    @TableField(exist = false)
    private boolean canReview = false;

    /**
     * 是否可删除
     */
    @TableField(exist = false)
    private boolean canDelete = false;

    /**
     * 是否可修改
     */
    @TableField(exist = false)
    private boolean canEdit = false;

    /**
     * 是否可实施
     */
    @TableField(exist = false)
    private boolean canImpl = false;

    /**
     * 当前环节处理人
     */
    @TableField(exist = false)
    private String processingPerson;

    @TableField(exist = false)
    private String instanceId;





    public String getId() {
        return id;
    }

    public ResourceRecoverAppInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public ResourceRecoverAppInfo setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getCreatorIdCard() {
        return creatorIdCard;
    }

    public ResourceRecoverAppInfo setCreatorIdCard(String creatorIdCard) {
        this.creatorIdCard = creatorIdCard;
        return this;
    }

    public String getCreatorPhone() {
        return creatorPhone;
    }

    public ResourceRecoverAppInfo setCreatorPhone(String creatorPhone) {
        this.creatorPhone = creatorPhone;
        return this;
    }

    public String getRecoveredPerson() {
        return recoveredPerson;
    }

    public ResourceRecoverAppInfo setRecoveredPerson(String recoveredPerson) {
        this.recoveredPerson = recoveredPerson;
        return this;
    }

    public String getRecoveredPersonPhone() {
        return recoveredPersonPhone;
    }

    public ResourceRecoverAppInfo setRecoveredPersonPhone(String recoveredPersonPhone) {
        this.recoveredPersonPhone = recoveredPersonPhone;
        return this;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public ResourceRecoverAppInfo setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ResourceRecoverAppInfo setStatus(String status) {
        this.status = status;
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

    public Integer getRecoveredAgree() {
        return recoveredAgree;
    }

    public void setRecoveredAgree(Integer recoveredAgree) {
        this.recoveredAgree = recoveredAgree;
    }

    public List<Files> getFileList() {
        return fileList;
    }

    public void setFileList(List<Files> fileList) {
        this.fileList = fileList;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public List<ResourceRecover> getServerList() {
        return serverList;
    }

    public void setServerList(List<ResourceRecover> serverList) {
        this.serverList = serverList;
    }

    public List<ResourceRecoverImpl> getImplServerList() {
        return implServerList;
    }

    public void setImplServerList(List<ResourceRecoverImpl> implServerList) {
        this.implServerList = implServerList;
    }

    public List<AppReviewInfoVo> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<AppReviewInfoVo> reviewList) {
        this.reviewList = reviewList;
    }

    public Integer getWorkFlowVersion() {
        return workFlowVersion;
    }

    public void setWorkFlowVersion(Integer workFlowVersion) {
        this.workFlowVersion = workFlowVersion;
    }

    public String getRecoveredPersonIdCard() {
        return recoveredPersonIdCard;
    }

    public void setRecoveredPersonIdCard(String recoveredPersonIdCard) {
        this.recoveredPersonIdCard = recoveredPersonIdCard;
    }

    public AppReviewInfoVo getImpl() {
        return impl;
    }

    public void setImpl(AppReviewInfoVo impl) {
        this.impl = impl;
    }

    public boolean isCanReview() {
        return canReview;
    }

    public void setCanReview(boolean canReview) {
        this.canReview = canReview;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getProcessingPerson() {
        return processingPerson;
    }

    public void setProcessingPerson(String processingPerson) {
        this.processingPerson = processingPerson;
    }

    public boolean isCanImpl() {
        return canImpl;
    }

    public void setCanImpl(boolean canImpl) {
        this.canImpl = canImpl;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

}
