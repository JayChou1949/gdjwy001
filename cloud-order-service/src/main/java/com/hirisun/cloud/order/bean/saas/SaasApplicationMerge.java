package com.hirisun.cloud.order.bean.saas;

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
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;

/**
 * <p>
 * SaaS资源申请合并信息表
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
 */
@TableName("TB_SAAS_APPLICATION_MERGE")
public class SaasApplicationMerge extends Model<SaasApplicationMerge> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 申请人
     */
         @TableField("CREATOR")
    private String creator;

        /**
     * 申请人姓名
     */
         @TableField("CREATOR_NAME")
    private String creatorName;

        /**
     * 单位id
     */
         @TableField("ORG_ID")
    private String orgId;

        /**
     * 单位名称
     */
         @TableField("ORG_NAME")
    private String orgName;

        /**
     * 职务
     */
         @TableField("POST_TYPE")
    private String postType;

        /**
     * 手机号
     */
         @TableField("MOBILE_WORK")
    private String mobileWork;

        /**
     * 申请时间
     */
         @TableField("APPLICATION_TIME")
    private Date applicationTime;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 
     */
         @TableField("STATUS")
    private String status;

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
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * 申请说明
     */
    @TableField("EXPLANATION")
    private String explanation;

    /**
     * 地区
     */
    @TableField("AREAS")
    private String areas;

    /**
     * 系统所属警种
     */
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @TableField("RECHECK_TIME")
    private Date recheckTime;

    @TableField("BIG_DATA_TIME")
    private Date bigDataTime;

    @TableField("CARRY_TIME")
    private Date carryTime;

    /**
     * 是否为导入的数据 0否 1是
     */
    @TableField("IS_IMPORT")
    private String isImport;

    @TableField(exist = false)
    private List<FilesVo> fileList;

    @TableField(exist = false)
    private List<SaasApplication> applicationList;

    /**
     * 审核记录(包含实施记录)
     */
    @TableField(exist = false)
    private List<AppReviewInfoVo> reviewList;
    /**
     * 创建人
     */
    @TableField(exist = false)
    private UserVO user;

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

    @TableField(exist = false)
    private boolean canAdviser;

    /**
     * 是否可实施
     */
    @TableField(exist = false)
    private boolean canImpl = false;

    /**
     * 是否可撤销合并
     */
    @TableField(exist = false)
    private boolean canUnmerge = false;

    /**
     * 申请单数量
     */
    @TableField(exist = false)
    private int num = 0;

    /**
     * 实施信息
     */
    @TableField(exist = false)
    private AppReviewInfoVo impl;

    /**
     * 当前环节处理人
     */
    @TableField(exist = false)
    private String processingPerson;

    @TableField(exist = false)
    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getProcessingPerson() {
        return processingPerson;
    }

    public void setProcessingPerson(String processingPerson) {
        this.processingPerson = processingPerson;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setImpl(AppReviewInfoVo impl) {
        this.impl = impl;
    }

    public AppReviewInfoVo getImpl() {
        return impl;
    }

    public List<FilesVo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FilesVo> fileList) {
        this.fileList = fileList;
    }

    public List<SaasApplication> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<SaasApplication> applicationList) {
        this.applicationList = applicationList;
    }

    public String getId() {
        return id;
    }

    public SaasApplicationMerge setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public SaasApplicationMerge setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public SaasApplicationMerge setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public SaasApplicationMerge setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public SaasApplicationMerge setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public String getPostType() {
        return postType;
    }

    public SaasApplicationMerge setPostType(String postType) {
        this.postType = postType;
        return this;
    }

    public String getMobileWork() {
        return mobileWork;
    }

    public SaasApplicationMerge setMobileWork(String mobileWork) {
        this.mobileWork = mobileWork;
        return this;
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public SaasApplicationMerge setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SaasApplicationMerge setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public SaasApplicationMerge setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SaasApplicationMerge setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public Date getRecheckTime() {
        return recheckTime;
    }

    public void setRecheckTime(Date recheckTime) {
        this.recheckTime = recheckTime;
    }

    public Date getBigDataTime() {
        return bigDataTime;
    }

    public void setBigDataTime(Date bigDataTime) {
        this.bigDataTime = bigDataTime;
    }

    public Date getCarryTime() {
        return carryTime;
    }

    public void setCarryTime(Date carryTime) {
        this.carryTime = carryTime;
    }

    public String getIsImport() {
        return isImport;
    }

    public void setIsImport(String isImport) {
        this.isImport = isImport;
    }

    public List<AppReviewInfoVo> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<AppReviewInfoVo> reviewList) {
        this.reviewList = reviewList;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public boolean isCanReview() {
        return canReview;
    }

    public void setCanReview(boolean canReview) {
        this.canReview = canReview;
    }

    public boolean isCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
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

    public boolean isCanTrans() {
        return canTrans;
    }

    public void setCanTrans(boolean canTrans) {
        this.canTrans = canTrans;
    }

    public boolean isCanFall() {
        return canFall;
    }

    public void setCanFall(boolean canFall) {
        this.canFall = canFall;
    }

    public boolean isCanAdviser() {
        return canAdviser;
    }

    public void setCanAdviser(boolean canAdviser) {
        this.canAdviser = canAdviser;
    }

    public boolean isCanImpl() {
        return canImpl;
    }

    public void setCanImpl(boolean canImpl) {
        this.canImpl = canImpl;
    }

    public boolean isCanUnmerge() {
        return canUnmerge;
    }

    public void setCanUnmerge(boolean canUnmerge) {
        this.canUnmerge = canUnmerge;
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
        return "SaasApplicationMerge{" +
        "id=" + id +
        ", creator=" + creator +
        ", creatorName=" + creatorName +
        ", orgId=" + orgId +
        ", orgName=" + orgName +
        ", postType=" + postType +
        ", mobileWork=" + mobileWork +
        ", applicationTime=" + applicationTime +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", status=" + status +
        "}";
    }
}
