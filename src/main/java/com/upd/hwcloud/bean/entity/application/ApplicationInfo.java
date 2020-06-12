package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.dto.FlowDetail;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.common.utils.annotation.ExcelExplain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 申请信息表
 * </p>
 *
 * @param <S> 用户申请的服务器信息
 * @param <I> 实施的服务器信息
 *
 * @author wuc
 * @since 2018-11-30
 */
@TableName("TB_APPLICATION_INFO")
public class ApplicationInfo<S, I> extends Model<ApplicationInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    /**
     * 开发公司
     */
    @TableField("COMPANY")
    private String company;

    /**
     * 使用人
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 使用人电话
     */
    @TableField("USER_PHONE")
    private String userPhone;

    /**
     * 申请单号
     */
    @ExcelExplain(head = "申请单号")
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * 应用名称
     */
    @ExcelExplain(head = "应用名称")
    @TableField("APP_NAME")
    private String appName;

    /**
     * 项目名称
     */
    @ExcelExplain(head = "项目名称")
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 系统所属警种
     */
    @ExcelExplain(head = "所属警种")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    /**
     * 申请人姓名
     */
    @ExcelExplain(head = "申请人")
    @TableField("CREATOR_NAME")
    private String creatorName;

    /**
     * 申请人电话
     */
    @ExcelExplain(head = "申请人电话")
    @TableField("CREATOR_PHONE")
    private String creatorPhone;

    /**
     * 申请时间
     */
    @TableField("APPLICATION_TIME")
    private Date applicationTime;

    /**
     * 申请时间
     */
    @ExcelExplain(head = "申请时间")
    @TableField(exist = false)
    private String applicationTimeStr;

    /**
     * 申请人单位
     */
    @ExcelExplain(head = "申请单位")
    @TableField("CREATOR_UNIT")
    private String creatorUnit;

    /**
     * 建设方式 0:自建 1:第三方建设
     */
    @ExcelExplain(head = "建设方式 0:自建 1:第三方建设")
    @TableField("BUILD_MODE")
    private String buildMode;

    /**
     * 申请说明
     */
    @ExcelExplain(head = "申请说明")
    @TableField("EXPLANATION")
    private String explanation;

    /**
     * 上报方式
     */ @ApiModelProperty(value = "上报方式 0-地区 1-警种 2-政府机构")
    @TableField("APPLY_TYPE")
    private String applyType;

    /**
     * 建设单位
     */
    @ExcelExplain(head = "建设单位")
    @TableField("JS_UNIT")
    private String jsUnit;

    /**
     * 建设负责人
     */
    @ExcelExplain(head = "建设单位负责人")
    @TableField("JS_PRINCIPAL")
    private String jsPrincipal;

    /**
     * 建设负责人电话
     */
    @ExcelExplain(head = "建设单位负责人电话")
    @TableField("JS_PRINCIPAL_PHONE")
    private String jsPrincipalPhone;

    /**
     * 建设经办人
     */
    @ExcelExplain(head = "建设单位经办人")
    @TableField("JS_MANAGER")
    private String jsManager;

    /**
     * 建设经办人电话
     */
    @ExcelExplain(head = "建设单位经办人电话")
    @TableField("JS_MANAGER_PHONE")
    private String jsManagerPhone;

    /**
     * 承建单位
     */
    @ExcelExplain(head = "承建单位")
    @TableField("CJ_UNIT")
    private String cjUnit;

    /**
     * 承建公司组织机构代码
     */
    @ExcelExplain(head = "统一社会信用代码")
    @TableField("CJ_ORG_CODE")
    private String cjOrgCode;

    /**
     * 承建负责人
     */
    @ExcelExplain(head = "承建单位负责人")
    @TableField("CJ_PRINCIPAL")
    private String cjPrincipal;

    /**
     * 承建负责人电话
     */
    @ExcelExplain(head = "承建单位负责人电话")
    @TableField("CJ_PRINCIPAL_PHONE")
    private String cjPrincipalPhone;

    /**
     * 承建负责人身份证
     */
    @ExcelExplain(head = "承建单位负责人身份证")
    @TableField("CJ_PRINCIPAL_IDCARD")
    private String cjPrincipalIdcard;

    /**
     * 承建办理人
     */
    @ExcelExplain(head = "承建单位办理人")
    @TableField("CJ_HANDLER")
    private String cjHandler;

    /**
     * 承建办理人电话
     */
    @ExcelExplain(head = "承建单位办理人电话")
    @TableField("CJ_HANDLER_PHONE")
    private String cjHandlerPhone;

    @ExcelExplain(head = "政府机构名称")
    @TableField("GOV_UNIT")
    private String govUnit;

    @ExcelExplain(head = "政府机构代码")
    @TableField("GOV_ORG_CODE")
    private String govOrgCode;

    @ExcelExplain(head = "政府项目负责人")
    @TableField("GOV_PRINCIPAL")
    private String govPrincipal;

    @ExcelExplain(head = "政府项目负责人职务")
    @TableField("GOV_PRINCIPAL_POST_TYPE")
    private String govPrincipalPostType;

    @ExcelExplain(head = "政府项目负责人电话")
    @TableField("GOV_PRINCIPAL_PHONE")
    private String govPrincipalPhone;

    @ExcelExplain(head = "政府项目负责人身份证")
    @TableField("GOV_PRINCIPAL_IDCARD")
    private String govPrincipalIdcard;

    /**
     * 国家专项
     */
    @TableField("NATIONAL_SPECIAL_PROJECT")
    private String nationalSpecialProject;

    /**
     * 承建单位输入类型 0:选择输入 1:手动输入
     */
    @TableField("CJ_INPUT_TYPE")
    private String cjInputType;

    /**
     * 应用名称输入类型 0:选择输入 1:手动输入
     */
    @TableField("APP_NAME_INPUT_TYPE")
    private String appNameInputType;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 申请人
     */
    @TableField("CREATOR")
    private String creator;

    /**
     * 状态
     * {@link ApplicationInfoStatus}
     */
    @TableField("STATUS")
    private String status;

    /**
     * 服务类型id
     */
    @TableField("SERVICE_TYPE_ID")
    private String serviceTypeId;

    /**
     * 资源类型名称
     */
    @TableField("SERVICE_TYPE_NAME")
    private String serviceTypeName;

    /**
     * 表单编码(服务编码)
     */
    @TableField("FORM_NUM")
    private String formNum;

    /**
     * 资源类型 1:IAAS 2:DAAS 3:PAAS 4:SAAS
     */
    @TableField("RESOURCE_TYPE")
    private Long resourceType;

    /**
     * 流程 id
     */
    @TableField("FLOW_STEP_ID")
    private String flowStepId;

    /**
     * 流程 id备份,用于加办,转发
     */
    @TableField("FLOW_STEP_ID_BAK")
    private String flowStepIdBak;

    /**
     * 账号
     */
    @TableField("ACCOUNT")
    private String account;

    /**
     * 厂商
     */
    @TableField("VENDOR")
    private String vendor;

    /**
     * 是否草稿 0:否 1:是
     */
    @TableField("DRAFT")
    private String draft;

    /**
     * 是否新流程
     */
    @TableField("FLOW_NEW")
    private String flowNew;

    /**
     * 地区
     */
    @TableField("AREA_NAME")
    private String areaName;

    /**
     * 警种(华为)
     */
    @TableField("HW_POLICE_CATEGORY")
    private String hwPoliceCategory;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 组件信息(申请)
     */
    @TableField(exist = false)
    private List<S> serverList;

    @TableField(exist = false)
    private List<Files> fileList;
    /**
     * 服务模板文件
     */
    @TableField(exist = false)
    private List<Files> tempList;

    /**
     * 组件总数
     */
    @TableField(exist = false)
    private Integer totalNum;

    /**
     * 审核记录(包含实施记录)
     */
    @TableField(exist = false)
    private List<AppReviewInfo> reviewList;
    @TableField(exist = false)
    private String workFlowId;

    @TableField(exist = false)
    private Integer flowVersion;
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
     * 可以审核该字段不为空
     */
    @TableField(exist = false)
    private String reviewFlag;

    /**
     * 是否可实施
     */
    @TableField(exist = false)
    private boolean canImpl = false;

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
     * 是否可反馈
     */
    @TableField(exist = false)
    private boolean canFeedback = false;
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
     * 办理进度  1:申请 2:申请单位审批 3:服务台复核 4:科信审批 5:业务办理 6:反馈
     */
    @TableField(exist = false)
    private int progressNo = 1;

    /**
     * 实施服务器信息(详情)
     */
    @TableField(exist = false)
    private List<I> implServerList;

    /**
     * 实施结果(最后一条)
     */
    @TableField(exist = false)
    private AppReviewInfo impl;

    /**
     * 流程详情
     */
    @TableField(exist = false)
    private FlowDetail flowDetail;

    /**
     * 当前环节处理人
     */
    @TableField(exist = false)
    private String processingPerson;

    /**
     * 服务描述
     */
    @TableField(exist = false)
    private String description;
    /**
     * 服务申请说明
     */
    @TableField(exist = false)
    private String instructions;
    //前端需要
    @TableField(exist = false)
    private String jsUnitId;
    @TableField(exist = false)
    private String cjUnitId;
    @TableField(exist = false)
    private boolean canAdviser;

    /**
     * 用于标记实体类型,如果是SaaS应用,值为"saas".
     * 在填写表单的应用名称时使用
     */
    @TableField(exist = false)
    private String entityType;

    /**
     * 大数据办审核时间
     */
    @TableField(exist = false)
    private Date bigDataAuditTime;

    /**
     * 业务办理时间
     */
    @TableField(exist = false)
    private Date businessHandlingTime;

    public Date getBigDataAuditTime() {
        return bigDataAuditTime;
    }

    @TableField(exist = false)
    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public ApplicationInfo setBigDataAuditTime(Date bigDataAuditTime) {
        this.bigDataAuditTime = bigDataAuditTime;
        return this;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public Date getBusinessHandlingTime() {
        return businessHandlingTime;
    }

    public ApplicationInfo setBusinessHandlingTime(Date businessHandlingTime) {
        this.businessHandlingTime = businessHandlingTime;
        return this;
    }

    public Integer getFlowVersion() {
        return flowVersion;
    }

    public void setFlowVersion(Integer flowVersion) {
        this.flowVersion = flowVersion;
    }

    public String getApplicationTimeStr() {
        return applicationTimeStr;
    }

    public void setApplicationTimeStr(String applicationTimeStr) {
        this.applicationTimeStr = applicationTimeStr;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getJsUnitId() {
        return jsUnitId;
    }

    public List<Files> getTempList() {
        return tempList;
    }

    public void setTempList(List<Files> tempList) {
        this.tempList = tempList;
    }

    public void setJsUnitId(String jsUnitId) {
        this.jsUnitId = jsUnitId;
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

    public String getCjUnitId() {
        return cjUnitId;
    }

    public void setCjUnitId(String cjUnitId) {
        this.cjUnitId = cjUnitId;
    }

    public String getId() {
        return id;
    }

    public ApplicationInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public ApplicationInfo setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getFlowNew() {
        return flowNew;
    }

    public void setFlowNew(String flowNew) {
        this.flowNew = flowNew;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public ApplicationInfo setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        return this;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getProjectName() {
        return projectName;
    }

    public ApplicationInfo setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public ApplicationInfo setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getAppNameInputType() {
        return appNameInputType;
    }

    public void setAppNameInputType(String appNameInputType) {
        this.appNameInputType = appNameInputType;
    }

    public String getRemark() {
        return remark;
    }

    public ApplicationInfo setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public ApplicationInfo setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getStatus() {
        return status;
    }

    public ApplicationInfo setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public String getFormNum() {
        return formNum;
    }

    public void setFormNum(String formNum) {
        this.formNum = formNum;
    }

    public ApplicationInfo setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
        return this;
    }

    public Long getResourceType() {
        return resourceType;
    }

    public ApplicationInfo setResourceType(Long resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public String getFlowStepId() {
        return flowStepId;
    }

    public void setFlowStepId(String flowStepId) {
        this.flowStepId = flowStepId;
    }

    public String getFlowStepIdBak() {
        return flowStepIdBak;
    }

    public void setFlowStepIdBak(String flowStepIdBak) {
        this.flowStepIdBak = flowStepIdBak;
    }

    public boolean isCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    public boolean isCanFeedback() {
        return canFeedback;
    }

    public void setCanFeedback(boolean canFeedback) {
        this.canFeedback = canFeedback;
    }

    public int getProgressNo() {
        return progressNo;
    }

    public void setProgressNo(int progressNo) {
        this.progressNo = progressNo;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getJsUnit() {
        return jsUnit;
    }

    public void setJsUnit(String jsUnit) {
        this.jsUnit = jsUnit;
    }

    public String getJsPrincipal() {
        return jsPrincipal;
    }

    public void setJsPrincipal(String jsPrincipal) {
        this.jsPrincipal = jsPrincipal;
    }

    public String getJsPrincipalPhone() {
        return jsPrincipalPhone;
    }

    public void setJsPrincipalPhone(String jsPrincipalPhone) {
        this.jsPrincipalPhone = jsPrincipalPhone;
    }

    public String getJsManager() {
        return jsManager;
    }

    public void setJsManager(String jsManager) {
        this.jsManager = jsManager;
    }

    public String getJsManagerPhone() {
        return jsManagerPhone;
    }

    public void setJsManagerPhone(String jsManagerPhone) {
        this.jsManagerPhone = jsManagerPhone;
    }

    public String getCjUnit() {
        return cjUnit;
    }

    public void setCjUnit(String cjUnit) {
        this.cjUnit = cjUnit;
    }

    public String getCjPrincipal() {
        return cjPrincipal;
    }

    public void setCjPrincipal(String cjPrincipal) {
        this.cjPrincipal = cjPrincipal;
    }

    public String getCjPrincipalPhone() {
        return cjPrincipalPhone;
    }

    public void setCjPrincipalPhone(String cjPrincipalPhone) {
        this.cjPrincipalPhone = cjPrincipalPhone;
    }

    public String getCjHandler() {
        return cjHandler;
    }

    public void setCjHandler(String cjHandler) {
        this.cjHandler = cjHandler;
    }

    public String getCjHandlerPhone() {
        return cjHandlerPhone;
    }

    public void setCjHandlerPhone(String cjHandlerPhone) {
        this.cjHandlerPhone = cjHandlerPhone;
    }

    public String getCjPrincipalIdcard() {
        return cjPrincipalIdcard;
    }

    public void setCjPrincipalIdcard(String cjPrincipalIdcard) {
        this.cjPrincipalIdcard = cjPrincipalIdcard;
    }

    public String getCjInputType() {
        return cjInputType;
    }

    public void setCjInputType(String cjInputType) {
        this.cjInputType = cjInputType;
    }

    public String getCjOrgCode() {
        return cjOrgCode;
    }

    public void setCjOrgCode(String cjOrgCode) {
        this.cjOrgCode = cjOrgCode;
    }

    public String getGovUnit() {
        return govUnit;
    }

    public void setGovUnit(String govUnit) {
        this.govUnit = govUnit;
    }

    public String getGovOrgCode() {
        return govOrgCode;
    }

    public void setGovOrgCode(String govOrgCode) {
        this.govOrgCode = govOrgCode;
    }

    public String getGovPrincipal() {
        return govPrincipal;
    }

    public void setGovPrincipal(String govPrincipal) {
        this.govPrincipal = govPrincipal;
    }

    public String getGovPrincipalPostType() {
        return govPrincipalPostType;
    }

    public void setGovPrincipalPostType(String govPrincipalPostType) {
        this.govPrincipalPostType = govPrincipalPostType;
    }

    public String getGovPrincipalPhone() {
        return govPrincipalPhone;
    }

    public void setGovPrincipalPhone(String govPrincipalPhone) {
        this.govPrincipalPhone = govPrincipalPhone;
    }

    public String getGovPrincipalIdcard() {
        return govPrincipalIdcard;
    }

    public void setGovPrincipalIdcard(String govPrincipalIdcard) {
        this.govPrincipalIdcard = govPrincipalIdcard;
    }

    public String getNationalSpecialProject() {
        return nationalSpecialProject;
    }

    public void setNationalSpecialProject(String nationalSpecialProject) {
        this.nationalSpecialProject = nationalSpecialProject;
    }

    public String getBuildMode() {
        return buildMode;
    }

    public void setBuildMode(String buildMode) {
        this.buildMode = buildMode;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getCreatorUnit() {
        return creatorUnit;
    }

    public void setCreatorUnit(String creatorUnit) {
        this.creatorUnit = creatorUnit;
    }

    public String getCreatorPhone() {
        return creatorPhone;
    }

    public void setCreatorPhone(String creatorPhone) {
        this.creatorPhone = creatorPhone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ApplicationInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ApplicationInfo setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public List<S> getServerList() {
        return serverList;
    }

    public void setServerList(List<S> serverList) {
        this.serverList = serverList;
    }

    public List<Files> getFileList() {
        return fileList;
    }

    public void setFileList(List<Files> fileList) {
        this.fileList = fileList;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AppReviewInfo> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<AppReviewInfo> reviewList) {
        this.reviewList = reviewList;
    }

    public AppReviewInfo getImpl() {
        return impl;
    }

    public void setImpl(AppReviewInfo impl) {
        this.impl = impl;
    }

    public List<I> getImplServerList() {
        return implServerList;
    }

    public void setImplServerList(List<I> implServerList) {
        this.implServerList = implServerList;
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

    public String getReviewFlag() {
        return reviewFlag;
    }

    public void setReviewFlag(String reviewFlag) {
        this.reviewFlag = reviewFlag;
    }

    public boolean isCanImpl() {
        return canImpl;
    }

    public void setCanImpl(boolean canImpl) {
        this.canImpl = canImpl;
    }

    public FlowDetail getFlowDetail() {
        return flowDetail;
    }

    public void setFlowDetail(FlowDetail flowDetail) {
        this.flowDetail = flowDetail;
    }

    public String getProcessingPerson() {
        return processingPerson;
    }

    public void setProcessingPerson(String processingPerson) {
        this.processingPerson = processingPerson;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getHwPoliceCategory() {
        return hwPoliceCategory;
    }

    public void setHwPoliceCategory(String hwPoliceCategory) {
        this.hwPoliceCategory = hwPoliceCategory;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ApplicationInfo{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", company=" + company +
                ", userName=" + userName +
                ", userPhone=" + userPhone +
                ", projectName=" + projectName +
                ", appName=" + appName +
                ", remark=" + remark +
                ", creator=" + creator +
                ", status=" + status +
                ", serviceTypeId=" + serviceTypeId +
                ", resourceType=" + resourceType +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                "}";
    }

    public void setCanAdviser(boolean canAdviser) {
        this.canAdviser = canAdviser;
    }

    public boolean getCanAdviser() {
        return canAdviser;
    }
}
