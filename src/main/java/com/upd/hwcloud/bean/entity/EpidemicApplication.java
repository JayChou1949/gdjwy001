package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 疫情资源申请信息表
 * </p>
 *
 * @author wuc
 * @since 2020-02-27
 */
@TableName("TB_EPIDEMIC_APPLICATION")
public class EpidemicApplication extends Model<EpidemicApplication> {

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

        /**
     * 上报方式--按地区 0；按警种 1
     */
         @TableField("APPLY_TYPE")
    private String applyType;

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

        /**
     * 申请说明
     */
         @TableField("EXPLANATION")
    private String explanation;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 申请单号
     */
         @TableField("ORDER_NUMBER")
    private String orderNumber;

        /**
     * 状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 
     */
         @TableField("STATUS")
    private String status;

        /**
     * 终端IP地址
     */
         @TableField("IP")
    private String ip;

        /**
     * 权限回收标志  0:未回收  -1:已回收  99:回收中
     */
         @TableField("RECOVER_FLAG")
    private String recoverFlag;

    @TableField("RECOVER_ID")
    private String recoverId;

        /**
     * 流程ID
     */
         @TableField("WORK_FLOW_ID")
    private String workFlowId;

    /**
     * 流程ID
     */
    @TableField("WORK_FLOW_VERSION")
    private Integer workFlowVersion;


    @TableField(exist = false)
    private List<Files> fileList;


    @TableField(exist = false)
    private List<EpidemicApplicationExt> serviceList;

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

    @TableField(exist = false)
    private boolean canAdviser;

    /**
     * 是否可实施
     */
    @TableField(exist = false)
    private boolean canImpl = false;



    /**
     * 实施信息
     */
    @TableField(exist = false)
    private AppReviewInfo impl;

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

    public EpidemicApplication setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public EpidemicApplication setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public EpidemicApplication setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public EpidemicApplication setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public EpidemicApplication setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public Integer getWorkFlowVersion() {
        return workFlowVersion;
    }

    public void setWorkFlowVersion(Integer workFlowVersion) {
        this.workFlowVersion = workFlowVersion;
    }

    public String getPostType() {
        return postType;
    }

    public EpidemicApplication setPostType(String postType) {
        this.postType = postType;
        return this;
    }

    public String getMobileWork() {
        return mobileWork;
    }

    public EpidemicApplication setMobileWork(String mobileWork) {
        this.mobileWork = mobileWork;
        return this;
    }


    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getApplyType() {
        return applyType;
    }

    public EpidemicApplication setApplyType(String applyType) {
        this.applyType = applyType;
        return this;
    }

    public String getAreas() {
        return areas;
    }

    public EpidemicApplication setAreas(String areas) {
        this.areas = areas;
        return this;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public EpidemicApplication setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
        return this;
    }

    public String getExplanation() {
        return explanation;
    }

    public EpidemicApplication setExplanation(String explanation) {
        this.explanation = explanation;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public EpidemicApplication setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public EpidemicApplication setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public EpidemicApplication setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getRecoverFlag() {
        return recoverFlag;
    }

    public EpidemicApplication setRecoverFlag(String recoverFlag) {
        this.recoverFlag = recoverFlag;
        return this;
    }

    public String getRecoverId() {
        return recoverId;
    }

    public EpidemicApplication setRecoverId(String recoverId) {
        this.recoverId = recoverId;
        return this;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public EpidemicApplication setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
        return this;
    }

    public List<Files> getFileList() {
        return fileList;
    }

    public void setFileList(List<Files> fileList) {
        this.fileList = fileList;
    }

    public List<EpidemicApplicationExt> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<EpidemicApplicationExt> serviceList) {
        this.serviceList = serviceList;
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

    public AppReviewInfo getImpl() {
        return impl;
    }

    public void setImpl(AppReviewInfo impl) {
        this.impl = impl;
    }

    public String getProcessingPerson() {
        return processingPerson;
    }

    public void setProcessingPerson(String processingPerson) {
        this.processingPerson = processingPerson;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EpidemicApplication{" +
                "id='" + id + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", postType='" + postType + '\'' +
                ", mobileWork='" + mobileWork + '\'' +
                ", applicationTime=" + applicationTime +
                ", applyType='" + applyType + '\'' +
                ", areas='" + areas + '\'' +
                ", policeCategory='" + policeCategory + '\'' +
                ", explanation='" + explanation + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", orderNumber='" + orderNumber + '\'' +
                ", status='" + status + '\'' +
                ", ip='" + ip + '\'' +
                ", recoverFlag='" + recoverFlag + '\'' +
                ", recoverId='" + recoverId + '\'' +
                ", workFlowId='" + workFlowId + '\'' +
                ", fileList=" + fileList +
                ", serviceList=" + serviceList +
                ", reviewList=" + reviewList +
                ", user=" + user +
                ", canReview=" + canReview +
                ", canAdd=" + canAdd +
                ", canDelete=" + canDelete +
                ", canEdit=" + canEdit +
                ", canTrans=" + canTrans +
                ", canFall=" + canFall +
                ", canAdviser=" + canAdviser +
                ", canImpl=" + canImpl +
                ", impl=" + impl +
                ", processingPerson='" + processingPerson + '\'' +
                '}';
    }
}
