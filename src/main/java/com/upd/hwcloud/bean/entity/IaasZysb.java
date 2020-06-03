package com.upd.hwcloud.bean.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 申请信息表
 * </p>
 *
 * @author zwb
 * @since 2019-05-22
 */
@TableName("TB_IAAS_ZYSB")
public class IaasZysb extends Model<IaasZysb> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 申请人
     */
         @TableField("CREATOR")
    private String creator;

        /**
     * 状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 
     */
         @TableField("STATUS")
    private String status;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;
    /**
     * 上报方式
     */ @ApiModelProperty(value = "上报方式 0-地区 1-警种")
    @TableField("APPLY_TYPE")
    private String applyType;
        /**
     * 流程id
     */
         @TableField("WORK_FLOW_ID")
    private String workFlowId;

    /**
     * 流程版本
     */
    @TableField("WORK_FLOW_VERSION")
    private Integer workFlowVersion;

    /**
     * 申请单号
     */
    @Excel(name = "工单号")
    @TableField("ORDER_NUMBER")
    private String orderNumber;

        /**
     * 申请人姓名
     */
        @Excel(name = "申请人")
         @TableField("CREATOR_NAME")
    private String creatorName;

        /**
     * 申请说明
     */
         @TableField("EXPLANATION")
    private String explanation;

        /**
     * 系统所属警种
     */
         @TableField("POLICE_CATEGORY")
    private String policeCategory;

        /**
     * 申请人单位
     */
        @Excel(name = "申请单位")
         @TableField("CREATOR_UNIT")
    private String creatorUnit;

    /**
     * 申请时间
     */
    @Excel(name = "申请时间", exportFormat = "yyyy-MM-dd")
    @TableField("APPLICATION_TIME")
    private Date applicationTime;

        /**
     * 申请人电话
     */
         @TableField("CREATOR_PHONE")
    private String creatorPhone;

        /**
     * 地区
     */
         @TableField("AREAS")
    private String areas;

        /**
     * 开始时间
     */
        @Excel(name = "预计使用时间", exportFormat = "yyyy-MM-dd")
         @TableField("START_DATE")
    private Date startDate;

    @TableField(exist = false)
    private List<IaasZysbXmxx> projectList;

    /**
     * 审核记录(包含实施记录)
     */
    @TableField(exist = false)
    private List<AppReviewInfo> reviewList;
    /**
     * 创建人
     */
    @TableField(exist = false)
    private User user;

    /**
     * 是否可审核
     */
    @TableField(exist = false)
    private boolean canReview = false;

    /**
     * 是否可以加办
     */
    @TableField(exist = false)
    private boolean canAdd = false;
    
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
     * 是否可转发
     */
    @TableField(exist = false)
    private boolean canTrans = false;

    /**
     * 是否可回退
     */
    @TableField(exist = false)
    private boolean canFall = false;
    /**
     * 当前环节处理人
     */
    @TableField(exist = false)
    private String processingPerson;
    @TableField(exist = false)
    private boolean canAdviser;

    @TableField(exist = false)
    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public List<AppReviewInfo> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<AppReviewInfo> reviewList) {
        this.reviewList = reviewList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getCanAdviser() {
        return canAdviser;
    }

    public void setCanAdviser(boolean canAdviser) {
        this.canAdviser = canAdviser;
    }

    public boolean getCanReview() {
        return canReview;
    }

    public void setCanReview(boolean canReview) {
        this.canReview = canReview;
    }

    public boolean getCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public boolean getCanTrans() {
        return canTrans;
    }

    public void setCanTrans(boolean canTrans) {
        this.canTrans = canTrans;
    }

    public boolean getCanFall() {
        return canFall;
    }

    public void setCanFall(boolean canFall) {
        this.canFall = canFall;
    }

    public String getProcessingPerson() {
        return processingPerson;
    }

    public void setProcessingPerson(String processingPerson) {
        this.processingPerson = processingPerson;
    }

    public String getId() {
        return id;
    }

    public IaasZysb setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public List<IaasZysbXmxx> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<IaasZysbXmxx> projectList) {
        this.projectList = projectList;
    }

    public IaasZysb setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public IaasZysb setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasZysb setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasZysb setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }



    public String getCreatorName() {
        return creatorName;
    }

    public IaasZysb setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public IaasZysb setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public String getExplanation() {
        return explanation;
    }

    public IaasZysb setExplanation(String explanation) {
        this.explanation = explanation;
        return this;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public IaasZysb setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
        return this;
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public IaasZysb setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
        return this;
    }

    public String getCreatorUnit() {
        return creatorUnit;
    }

    public IaasZysb setCreatorUnit(String creatorUnit) {
        this.creatorUnit = creatorUnit;
        return this;
    }

    public String getCreatorPhone() {
        return creatorPhone;
    }

    public IaasZysb setCreatorPhone(String creatorPhone) {
        this.creatorPhone = creatorPhone;
        return this;
    }

    public String getAreas() {
        return areas;
    }

    public IaasZysb setAreas(String areas) {
        this.areas = areas;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public IaasZysb setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public Integer getWorkFlowVersion() {
        return workFlowVersion;
    }

    public void setWorkFlowVersion(Integer workFlowVersion) {
        this.workFlowVersion = workFlowVersion;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasZysb{" +
        "id=" + id +
        ", creator=" + creator +
        ", status=" + status +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", workFlowId=" + workFlowId +
        ", creatorName=" + creatorName +
        ", orderNumber=" + orderNumber +
        ", explanation=" + explanation +
        ", policeCategory=" + policeCategory +
        ", applicationTime=" + applicationTime +
        ", creatorUnit=" + creatorUnit +
        ", creatorPhone=" + creatorPhone +
        ", areas=" + areas +
        ", startDate=" + startDate +
        "}";
    }
}
