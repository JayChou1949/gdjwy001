package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangbao
 * @since 2019-10-24
 */
@TableName("TB_SAAS_RECOVER_APPLICATION")
public class SaasRecoverApplication extends Model<SaasRecoverApplication> {

    private static final long serialVersionUID = 1L;

        /**
     * 主键
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 身份证号
     */
         @TableField("CREATOR")
    private String creator;

        /**
     * 创建人姓名
     */
         @TableField("CREATOR_NAME")
    private String creatorName;

        /**
     * 组织机构ID
     */
         @TableField("ORG_ID")
    private String orgId;

        /**
     * 组织机构名称
     */
         @TableField("ORG_NAME")
    private String orgName;

        /**
     * 职位
     */
         @TableField("POST")
    private String post;

        /**
     * 电话号码
     */
         @TableField("PHONE")
    private String phone;

        /**
     * 申请时间
     */
         @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

        /**
     * 修改时间
     */
         @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 申请单号
     */
         @TableField("BILL_NUM")
    private String billNum;

        /**
     * 状态
     */
         @TableField("STATUS")
    private String status;

        /**
     * 服务台复核处理时间
     */
         @TableField("RECHECK_TIME")
    private Date recheckTime;

        /**
     * 大数据办审批处理时间
     */
         @TableField("BIG_DATA_TIME")
    private Date bigDataTime;

        /**
     * 业务办理处理时间
     */
         @TableField("CARRY_TIME")
    private Date carryTime;

    /**
     * 是否为导入的数据 0否 1是
     */
    @TableField("IS_IMPORT")
    private String isImport;

    /**
     * 申请人信息
     */
    private User user;

    @TableField(exist = false)
    private String workFlowId;


    @TableField(exist = false)
    private Integer workFlowVersion;

    /**
     * 回收信息
     */
    @TableField(exist = false)
    private List<User> recoverInfoList;
    /**
     * 审批信息
     * @return
     */
    @TableField(exist = false)
    private List<AppReviewInfo> reviewList;

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
     * 文件上传
     */
    @TableField(exist = false)
    private List<Files> fileList;


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

    public boolean isCanImpl() {
        return canImpl;
    }

    public void setCanImpl(boolean canImpl) {
        this.canImpl = canImpl;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public AppReviewInfo getImpl() {
        return impl;
    }

    public void setImpl(AppReviewInfo impl) {
        this.impl = impl;
    }

    public Integer getWorkFlowVersion() {
        return workFlowVersion;
    }

    public void setWorkFlowVersion(Integer workFlowVersion) {
        this.workFlowVersion = workFlowVersion;
    }

    @TableField(exist = false)
    private boolean canAdviser;

    /**
     * 是否可实施
     */
    @TableField(exist = false)
    private boolean canImpl = false;

    /**
     * 申请单数量
     */
    @TableField(exist = false)
    private int num = 0;

    /**
     * 实施信息
     */
    @TableField(exist = false)
    private AppReviewInfo impl;


    /**
     * 地区
     */
    @TableField("AREAS")
    private String areas;



    /**
     * 回收详情
     * @return
     */
    @TableField(exist = false)
    private List<SaasRecoverInfo> recoverInfos;

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

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    /**

     * 警种
     */
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    /**
     * 申请方式--按地区 0；按警种 1
     */
    @TableField("APPLY_TYPE")
    private String applyType;
    /**
     * 服务台添加新的用户
     */
    @TableField(exist = false)
    private List<User> addUserlist;


    public List<SaasRecoverInfo> getRecoverInfos() {
        return recoverInfos;
    }

    public void setRecoverInfos(List<SaasRecoverInfo> recoverInfos) {
        this.recoverInfos = recoverInfos;
    }

    public List<AppReviewInfo> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<AppReviewInfo> reviewList) {
        this.reviewList = reviewList;
    }

    public List<User> getRecoverInfoList() {
        return recoverInfoList;
    }

    public void setRecoverInfoList(List<User> recoverInfoList) {
        this.recoverInfoList = recoverInfoList;
    }

    public String getId() {
        return id;
    }

    public SaasRecoverApplication setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public SaasRecoverApplication setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public SaasRecoverApplication setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public SaasRecoverApplication setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public SaasRecoverApplication setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public String getPost() {
        return post;
    }

    public SaasRecoverApplication setPost(String post) {
        this.post = post;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public SaasRecoverApplication setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SaasRecoverApplication setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public SaasRecoverApplication setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getBillNum() {
        return billNum;
    }

    public SaasRecoverApplication setBillNum(String billNum) {
        this.billNum = billNum;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SaasRecoverApplication setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getRecheckTime() {
        return recheckTime;
    }

    public SaasRecoverApplication setRecheckTime(Date recheckTime) {
        this.recheckTime = recheckTime;
        return this;
    }

    public Date getBigDataTime() {
        return bigDataTime;
    }

    public SaasRecoverApplication setBigDataTime(Date bigDataTime) {
        this.bigDataTime = bigDataTime;
        return this;
    }

    public Date getCarryTime() {
        return carryTime;
    }

    public SaasRecoverApplication setCarryTime(Date carryTime) {
        this.carryTime = carryTime;
        return this;
    }

    public String getIsImport() {
        return isImport;
    }

    public void setIsImport(String isImport) {
        this.isImport = isImport;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public boolean isCanReview() {
        return canReview;
    }

    public void setCanReview(boolean canReview) {
        this.canReview = canReview;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }


    public boolean isCanAdviser() {
        return canAdviser;
    }

    public void setCanAdviser(boolean canAdviser) {
        this.canAdviser = canAdviser;
    }
    public List<Files> getFileList() {
        return fileList;
    }

    public void setFileList(List<Files> fileList) {
        this.fileList = fileList;
    }
    public List<User> getAddUserlist() {
        return addUserlist;
    }

    public void setAddUserlist(List<User> addUserlist) {
        this.addUserlist = addUserlist;
    }
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SaasRecoverApplication{" +
        "id=" + id +
        ", creator=" + creator +
        ", creatorName=" + creatorName +
        ", orgId=" + orgId +
        ", orgName=" + orgName +
        ", post=" + post +
        ", phone=" + phone +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", billNum=" + billNum +
        ", status=" + status +
        ", recheckTime=" + recheckTime +
        ", bigDataTime=" + bigDataTime +
        ", carryTime=" + carryTime +
        "}";
    }



}
