package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务注册表
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */

public class Register<T extends Register> extends Model<T> {

    @TableId(value = "ID",type = IdType.UUID)
    protected String id;

        /**
     * 服务名称
     */
         @TableField("NAME")
         @ApiModelProperty(value = "服务名称")
    protected String name;

    /**
     * 项目名称
     */
    @TableField("PROJECT_NAME")
    @ApiModelProperty(value = "项目名称")
    protected String projectName;


        /**
     * 状态 ：1建设中；0 建设完成
     */
         @TableField("BUILD_STATUS")
         @ApiModelProperty(value = "服务状态")
    protected Long buildStatus;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    protected Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    protected Date modifiedTime;

        /**
     * 申请人
     */
         @TableField("CREATOR")
    protected String creator;
    /**
     * 流程id
     */
    @TableField("WORK_FLOW_ID")
    protected String workFlowId;

    /**
     * 流程版本
     */
    @TableField("WORK_FLOW_VERSION")
    protected Integer workFlowVersion;

    /**
     * 状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回
     */
    @TableField("STATUS")
    protected String status;
    /**
     * 申请人姓名
     */
    @TableField("CREATOR_NAME")
    protected String creatorName;

    /**
     * 申请单号
     */
    @TableField("ORDER_NUMBER")
    protected String orderNumber;
        /**
     * 所属分类
     */
         @TableField("SUB_TYPE")
         @ApiModelProperty(value = "所属分类")
    protected String subType;

        /**
     * 服务描述
     */ @ApiModelProperty(value = "服务描述")
         @TableField("DESCRIPTION")
    protected String description;

        /**
     * 建设单位
     */
         @TableField("JS_UNIT")
    protected String jsUnit;

        /**
     * 建设负责人
     */
         @TableField("JS_PRINCIPAL")
    protected String jsPrincipal;

        /**
     * 建设负责人电话
     */
         @TableField("JS_PRINCIPAL_PHONE")
    protected String jsPrincipalPhone;

        /**
     * 建设经办人
     */
         @TableField("JS_MANAGER")
    protected String jsManager;

        /**
     * 建设经办人电话
     */
         @TableField("JS_MANAGER_PHONE")
    protected String jsManagerPhone;

        /**
     * 承建单位
     */
         @TableField("CJ_UNIT")
    protected String cjUnit;

        /**
     * 承建负责人
     */
         @TableField("CJ_PRINCIPAL")
    protected String cjPrincipal;

        /**
     * 承建负责人电话
     */
         @TableField("CJ_PRINCIPAL_PHONE")
    protected String cjPrincipalPhone;

        /**
     * 承建办理人
     */
         @TableField("CJ_HANDLER")
    protected String cjHandler;

        /**
     * 承建办理人电话
     */
         @TableField("CJ_HANDLER_PHONE")
    protected String cjHandlerPhone;

        /**
     * 承建负责人身份证
     */
         @TableField("CJ_PRINCIPAL_IDCARD")
    protected String cjPrincipalIdcard;

        /**
     * 所属警种
     */ @ApiModelProperty(value = "所属警种")
         @TableField("POLICE_CATEGORY")
    protected String policeCategory;

        /**
     * 承建公司组织机构代码
     */
         @TableField("CJ_ORG_CODE")
    protected String cjOrgCode;

        /**
     * 承建单位输入类型 0:选择输入 1:手动输入
     */
         @TableField("CJ_INPUT_TYPE")
    protected String cjInputType;

        /**
     * 建设方式 0:自建 1:第三方建设
     */ @ApiModelProperty(value = "建设方式")
         @TableField("BUILD_MODE")
    protected String buildMode;

        /**
     * 所属地区
     */ @ApiModelProperty(value = "所属地区")
         @TableField("AREA")
    protected String area;

        /**
     * 系统地址
     */ @ApiModelProperty(value = "系统地址")
         @TableField("URL")
    protected String url;

        /**
     * 是否可申请
     */ @ApiModelProperty(value = "是否可申请")
         @TableField("CAN_APPLICATION")
    protected String canApplication;

        /**
     * 服务LOGO
     */ @ApiModelProperty(value = "服务LOGO")
         @TableField("IMAGE")
    protected String image;

        /**
     * 负责人
     */
         @TableField("RES_PERSON")
    protected String resPerson;

        /**
     * 负责人单位
     */
         @TableField("RES_ORG")
    protected String resOrg;

        /**
     * 权限说明
     */ @ApiModelProperty(value = "权限说明")
         @TableField("PERMISSION_INS")
    protected String permissionIns;

        /**
     * 建设周期
     */ @ApiModelProperty(value = "建设周期")
         @TableField("JS_CIRCLE")
    protected String jsCircle;

        /**
     * 生命周期
     */ @ApiModelProperty(value = "生命周期")
         @TableField("SM_CIRCLE")
    protected String smCircle;

        /**
     * 上线时间
     */ @ApiModelProperty(value = "上线时间")
         @TableField("ON_DATE")
    protected Date onDate;
    /**
     * 上报方式
     */ @ApiModelProperty(value = "上报方式 0-地区 1-警种 2-政府机构")
    @TableField("APPLY_TYPE")
     protected String applyType;
        /**
     * 建设负责人身份证
     */
         @TableField("JS_PRINCIPAL_IDCARD")
    protected String jsPrincipalIdcard;

    @TableField(exist = false)
    protected List<Files> fileList;
    /**
     * 审核记录(包含实施记录)
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected List<AppReviewInfo> reviewList;
    /**
     * 创建人
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected User user;

    /**
     * 是否可审核
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canReview = false;

    /**
     * 是否可以加办
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canAdd = false;

    /**
     * 是否可删除
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canDelete = false;

    /**
     * 是否可修改
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canEdit = false;

    /**
     * 是否可转发
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canTrans = false;

    /**
     * 是否可回退
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canFall = false;
    /**
     * 当前环节处理人
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected String processingPerson;
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canAdviser;

    @TableField (exist = false)
    protected String instanceId;


    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getId() {
        return id;
    }

    public Register setId(String id) {
        this.id = id;
        return this;
    }

    public List<Files> getFileList() {
        return fileList;
    }

    public void setFileList(List<Files> fileList) {
        this.fileList = fileList;
    }

    public List<AppReviewInfo> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<AppReviewInfo> reviewList) {
        this.reviewList = reviewList;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public boolean getCanAdviser() {
        return canAdviser;
    }

    public void setCanAdviser(boolean canAdviser) {
        this.canAdviser = canAdviser;
    }

    public String getName() {
        return name;
    }

    public Register setName(String name) {
        this.name = name;
        return this;
    }

    public Long getBuildStatus() {
        return buildStatus;
    }

    public Register setBuildStatus(Long buildStatus) {
        this.buildStatus = buildStatus;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Register setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public Register setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Register setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getSubType() {
        return subType;
    }

    public Register setSubType(String subType) {
        this.subType = subType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Register setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getJsUnit() {
        return jsUnit;
    }

    public Register setJsUnit(String jsUnit) {
        this.jsUnit = jsUnit;
        return this;
    }

    public String getJsPrincipal() {
        return jsPrincipal;
    }

    public Register setJsPrincipal(String jsPrincipal) {
        this.jsPrincipal = jsPrincipal;
        return this;
    }

    public String getJsPrincipalPhone() {
        return jsPrincipalPhone;
    }

    public Register setJsPrincipalPhone(String jsPrincipalPhone) {
        this.jsPrincipalPhone = jsPrincipalPhone;
        return this;
    }

    public String getJsManager() {
        return jsManager;
    }

    public Register setJsManager(String jsManager) {
        this.jsManager = jsManager;
        return this;
    }

    public String getJsManagerPhone() {
        return jsManagerPhone;
    }

    public Register setJsManagerPhone(String jsManagerPhone) {
        this.jsManagerPhone = jsManagerPhone;
        return this;
    }

    public String getCjUnit() {
        return cjUnit;
    }

    public Register setCjUnit(String cjUnit) {
        this.cjUnit = cjUnit;
        return this;
    }

    public String getCjPrincipal() {
        return cjPrincipal;
    }

    public Register setCjPrincipal(String cjPrincipal) {
        this.cjPrincipal = cjPrincipal;
        return this;
    }

    public String getCjPrincipalPhone() {
        return cjPrincipalPhone;
    }

    public Register setCjPrincipalPhone(String cjPrincipalPhone) {
        this.cjPrincipalPhone = cjPrincipalPhone;
        return this;
    }

    public String getCjHandler() {
        return cjHandler;
    }

    public Register setCjHandler(String cjHandler) {
        this.cjHandler = cjHandler;
        return this;
    }

    public String getCjHandlerPhone() {
        return cjHandlerPhone;
    }

    public Register setCjHandlerPhone(String cjHandlerPhone) {
        this.cjHandlerPhone = cjHandlerPhone;
        return this;
    }

    public String getCjPrincipalIdcard() {
        return cjPrincipalIdcard;
    }

    public Register setCjPrincipalIdcard(String cjPrincipalIdcard) {
        this.cjPrincipalIdcard = cjPrincipalIdcard;
        return this;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public Register setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
        return this;
    }

    public String getCjOrgCode() {
        return cjOrgCode;
    }

    public Register setCjOrgCode(String cjOrgCode) {
        this.cjOrgCode = cjOrgCode;
        return this;
    }

    public String getCjInputType() {
        return cjInputType;
    }

    public Register setCjInputType(String cjInputType) {
        this.cjInputType = cjInputType;
        return this;
    }

    public String getBuildMode() {
        return buildMode;
    }

    public Register setBuildMode(String buildMode) {
        this.buildMode = buildMode;
        return this;
    }

    public String getArea() {
        return area;
    }

    public Register setArea(String area) {
        this.area = area;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Register setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getCanApplication() {
        return canApplication;
    }

    public Register setCanApplication(String canApplication) {
        this.canApplication = canApplication;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Register setImage(String image) {
        this.image = image;
        return this;
    }

    public String getResPerson() {
        return resPerson;
    }

    public Register setResPerson(String resPerson) {
        this.resPerson = resPerson;
        return this;
    }

    public String getResOrg() {
        return resOrg;
    }

    public Register setResOrg(String resOrg) {
        this.resOrg = resOrg;
        return this;
    }

    public String getPermissionIns() {
        return permissionIns;
    }

    public Register setPermissionIns(String permissionIns) {
        this.permissionIns = permissionIns;
        return this;
    }

    public String getJsCircle() {
        return jsCircle;
    }

    public Register setJsCircle(String jsCircle) {
        this.jsCircle = jsCircle;
        return this;
    }

    public String getSmCircle() {
        return smCircle;
    }

    public Register setSmCircle(String smCircle) {
        this.smCircle = smCircle;
        return this;
    }

    public Date getOnDate() {
        return onDate;
    }

    public Register setOnDate(Date onDate) {
        this.onDate = onDate;
        return this;
    }

    public String getJsPrincipalIdcard() {
        return jsPrincipalIdcard;
    }

    public Register setJsPrincipalIdcard(String jsPrincipalIdcard) {
        this.jsPrincipalIdcard = jsPrincipalIdcard;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SaasRegister{" +
        "id=" + id +
        ", name=" + name +
        ", buildStatus=" + buildStatus +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", creator=" + creator +
        ", subType=" + subType +
        ", description=" + description +
        ", jsUnit=" + jsUnit +
        ", jsPrincipal=" + jsPrincipal +
        ", jsPrincipalPhone=" + jsPrincipalPhone +
        ", jsManager=" + jsManager +
        ", jsManagerPhone=" + jsManagerPhone +
        ", cjUnit=" + cjUnit +
        ", cjPrincipal=" + cjPrincipal +
        ", cjPrincipalPhone=" + cjPrincipalPhone +
        ", cjHandler=" + cjHandler +
        ", cjHandlerPhone=" + cjHandlerPhone +
        ", cjPrincipalIdcard=" + cjPrincipalIdcard +
        ", policeCategory=" + policeCategory +
        ", cjOrgCode=" + cjOrgCode +
        ", cjInputType=" + cjInputType +
        ", buildMode=" + buildMode +
        ", area=" + area +
        ", url=" + url +
        ", canApplication=" + canApplication +
        ", image=" + image +
        ", resPerson=" + resPerson +
        ", resOrg=" + resOrg +
        ", permissionIns=" + permissionIns +
        ", jsCircle=" + jsCircle +
        ", smCircle=" + smCircle +
        ", onDate=" + onDate +
        ", jsPrincipalIdcard=" + jsPrincipalIdcard +
        "}";
    }
}
