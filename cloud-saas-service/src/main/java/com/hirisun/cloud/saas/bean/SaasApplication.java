package com.hirisun.cloud.saas.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.service.AppReviewInfoVo;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * <p>
 * SaaS资源申请原始信息表
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
 */
@TableName("TB_SAAS_APPLICATION")
public class SaasApplication extends Model<SaasApplication> {

    private static final long serialVersionUID = 1L;

    @Excel(name = "序号")
    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    /**
     * 申请人姓名
     */
    @Excel(name = "姓名")
    @TableField("CREATOR_NAME")
    private String creatorName;

        /**
     * 申请人
     */
        @Excel(name = "身份证号码")
         @TableField("CREATOR")
    private String creator;

        /**
     * 单位id
     */
         @TableField("ORG_ID")
    private String orgId;

        /**
     * 单位名称
     */
        @Excel(name = "工作单位")
         @TableField("ORG_NAME")
    private String orgName;

        /**
     * 职务
     */
        @Excel(name = "职务")
         @TableField("POST_TYPE")
    private String postType;

        /**
     * 手机号
     */
        @Excel(name = "手机号码")
         @TableField("MOBILE_WORK")
    private String mobileWork;

    /**
     * 终端IP地址
     */
    @Excel(name = "终端IP地址")
    @TableField("IP")
    private String ip;

    /**
     * 申请服务数量
     */
    @Excel(name = "申请服务数量")
    @TableField(exist = false)
    private int num = 0;

    /**
     * 服务列表
     */
    @TableField(exist = false)
    private List<Map<String,Object>> serviceList;

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

    @TableField("MERGE_ID")
    private String mergeId;

    /**
     * 权限回收标志  0:未回收  -1:已回收  99:回收中
     */
    @TableField("RECOVER_FLAG")
    private String recoverFlag;

    /**
     * 是否为导入的数据 0否 1是
     */
    @TableField("IS_IMPORT")
    private String isImport;

    /**
     * 回收申请单id
     */
    @TableField("RECOVER_ID")
    private String recoverId;

    /**
     * 是否为特殊应用申请 0:否 1:可视化建模平台 2:广东公安数据接入平台
     */
    @TableField("VISIBLE")
    private String visible;

    /**
     * 统一用户id
     */
    @TableField("USERID")
    private String userId;

    @TableField(exist = false)
    private List<FilesVo> fileList;

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
     * 是否可提交
     */
    @TableField(exist = false)
    private boolean canSubmit = false;

    /**
     * 审核记录(包含实施记录)
     */
    @TableField(exist = false)
    private List<ApplyReviewRecordVO> reviewList;

    /**
     * 实施信息
     */
    @TableField(exist = false)
    private ApplyReviewRecordVO impl;

    /**
     * saas申请用用统计
     */
    @TableField(exist = false)
    private String total;
    /**
     * 服务ID
     */
    @TableField(exist = false)
    private String serviceId;
    /**
     * 服务名称
     */
    @TableField(exist = false)
    private String serviceName;

    /**
     *服务应用名称（地市应用 警种应用 通用应用）
     * @return
     */
    @TableField(exist = false)
    private String applicationName;

    /**
     * 可视化资源列表
     */
    @TableField(exist = false)
    private List<SaasAppExtResource> resourceList;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ApplyReviewRecordVO> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ApplyReviewRecordVO> reviewList) {
        this.reviewList = reviewList;
    }

    public ApplyReviewRecordVO getImpl() {
        return impl;
    }

    public void setImpl(ApplyReviewRecordVO impl) {
        this.impl = impl;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public boolean isCanSubmit() {
        return canSubmit;
    }

    public void setCanSubmit(boolean canSubmit) {
        this.canSubmit = canSubmit;
    }

    public List<FilesVo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FilesVo> fileList) {
        this.fileList = fileList;
    }

    public List<Map<String,Object>> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Map<String,Object>> serviceList) {
        this.serviceList = serviceList;
    }

    public String getId() {
        return id;
    }

    public SaasApplication setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public SaasApplication setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public SaasApplication setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public SaasApplication setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getOrgName() {
        return orgName;
    }

    public SaasApplication setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public String getPostType() {
        return postType;
    }

    public SaasApplication setPostType(String postType) {
        this.postType = postType;
        return this;
    }

    public String getMobileWork() {
        return mobileWork;
    }

    public SaasApplication setMobileWork(String mobileWork) {
        this.mobileWork = mobileWork;
        return this;
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public SaasApplication setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
        return this;
    }

    public String getApplyType() {
        return applyType;
    }

    public SaasApplication setApplyType(String applyType) {
        this.applyType = applyType;
        return this;
    }

    public String getAreas() {
        return areas;
    }

    public SaasApplication setAreas(String areas) {
        this.areas = areas;
        return this;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public SaasApplication setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
        return this;
    }

    public String getExplanation() {
        return explanation;
    }

    public SaasApplication setExplanation(String explanation) {
        this.explanation = explanation;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SaasApplication setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public SaasApplication setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public SaasApplication setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SaasApplication setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMergeId() {
        return mergeId;
    }

    public SaasApplication setMergeId(String mergeId) {
        this.mergeId = mergeId;
        return this;
    }

    public String getRecoverFlag() {
        return recoverFlag;
    }

    public void setRecoverFlag(String recoverFlag) {
        this.recoverFlag = recoverFlag;
    }

    public String getIsImport() {
        return isImport;
    }

    public void setIsImport(String isImport) {
        this.isImport = isImport;
    }

    public String getRecoverId() {
        return recoverId;
    }

    public void setRecoverId(String recoverId) {
        this.recoverId = recoverId;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getIp() {
        return ip;
    }

    public SaasApplication setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public List<SaasAppExtResource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<SaasAppExtResource> resourceList) {
        this.resourceList = resourceList;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SaasApplication{" +
        "id=" + id +
        ", creator=" + creator +
        ", creatorName=" + creatorName +
        ", orgId=" + orgId +
        ", orgName=" + orgName +
        ", postType=" + postType +
        ", mobileWork=" + mobileWork +
        ", applicationTime=" + applicationTime +
        ", applyType=" + applyType +
        ", areas=" + areas +
        ", policeCategory=" + policeCategory +
        ", explanation=" + explanation +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", orderNumber=" + orderNumber +
        ", status=" + status +
        ", mergeId=" + mergeId +
        ", ip=" + ip +
                ", userId=" + userId +
        "}";
    }
}
