package com.hirisun.cloud.order.bean.publish;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;

/**
 * 服务发布
 * @author Lenovo
 *
 */
@TableName("T_SERVICE_PUBLISH")
public class ServicePublish implements Serializable {

	private static final long serialVersionUID = 6358249264564773381L;

	@TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 申请人
     */
         @TableField("CREATOR")
    private String creator;

        /**
     * 状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施未完成
     */
         @TableField("STATUS")
    private String status;

        /**
     * 申请人姓名
     */
         @TableField("CREATOR_NAME")
    private String creatorName;

    @TableField("ORDER_NUMBER")
    private String orderNumber;

        /**
     * 服务名称
     */
         @TableField("SERVICE_NAME")
    private String serviceName;

        /**
     * 服务类型 DAAS,PAAS,SAAS
     */
         @TableField("SERVICE_TYPE")
    private String serviceType;

        /**
     * 是否发布APIG,1:是 0:否
     */
         @TableField("IS_PUBLISH_APIG")
    private String isPublishApig;

        /**
     * 是否来源应用,1:是 0:否
     */
         @TableField("IS_FROM_APP")
    private String isFromApp;

        /**
     * 来源应用名称
     */
         @TableField("FROM_APP_NAME")
    private String fromAppName;

        /**
     * 来源应用 ID
     */
         @TableField("FROM_APP_ID")
    private String fromAppId;

        /**
     * 地区
     */
         @TableField("AREA_NAME")
    private String areaName;

        /**
     * 警种
     */
         @TableField("POLICE_CATEGORY")
    private String policeCategory;

        /**
     * 分类
     */
         @TableField("CATEGORY")
    private String category;

        /**
     * 优先显示名称
     */
         @TableField("PRIORITY_NAME")
    private String priorityName;

        /**
     * 服务商
     */
         @TableField("VENDOR")
    private String vendor;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

        /**
     * 服务logo
     */
         @TableField("LOGO_URL")
    private String logoUrl;

    /**
     * 标签
     */
    @TableField("TAG")
    private String tag;

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
     * 创建人
     */
    @TableField(exist = false)
    private UserVO user;

    /**
     * 审核附件
     */
    @TableField(exist = false)
    private List<FilesVo> fileList;

    /**
     * 接口文档附件
     */
    @TableField(exist = false)
    private List<FilesVo> interfaceFileList;

    /**
     * 开发工具包附件
     */
    @TableField(exist = false)
    private List<FilesVo> developmentFileList;

    /**
     * api产品
     */
    @TableField(exist = false)
    private ServicePublishApiProduct apiProduct;

    /**
     * api
     */
    @TableField(exist = false)
    private List<ServicePublishApi> apiList;

    /**
     * 后端服务
     */
    @TableField(exist = false)
    private List<ServicePublishBackend> backendList;

    /**
     * 审核记录(包含实施记录)
     */
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
     * 是否可以加办
     */
    @TableField(exist = false)
    private boolean canAdd = false;

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
     * 当前环节处理人
     */
    @TableField(exist = false)
    private String processingPerson;

    /**
     * 数据来源. 1.页面填写 2.美亚数据
     */
    @TableField("WHERE_FROM")
    private String whereFrom;

    /**
     * 建设方式 0:自建 1:第三方建设
     */
    @TableField("BUILD_MODE")
    private String buildMode;

    /**
     * 建设单位
     */
    @TableField("JS_UNIT")
    private String jsUnit;

    /**
     * 建设负责人
     */
    @TableField("JS_PRINCIPAL")
    private String jsPrincipal;

    /**
     * 建设负责人电话
     */
    @TableField("JS_PRINCIPAL_PHONE")
    private String jsPrincipalPhone;

    /**
     * 建设经办人
     */
    @TableField("JS_MANAGER")
    private String jsManager;

    /**
     * 建设经办人电话
     */
    @TableField("JS_MANAGER_PHONE")
    private String jsManagerPhone;

    /**
     * 承建单位
     */
    @TableField("CJ_UNIT")
    private String cjUnit;

    /**
     * 承建公司组织机构代码
     */
    @TableField("CJ_ORG_CODE")
    private String cjOrgCode;

    /**
     * 承建负责人
     */
    @TableField("CJ_PRINCIPAL")
    private String cjPrincipal;

    /**
     * 承建负责人电话
     */
    @TableField("CJ_PRINCIPAL_PHONE")
    private String cjPrincipalPhone;

    /**
     * 承建负责人身份证
     */
    @TableField("CJ_PRINCIPAL_IDCARD")
    private String cjPrincipalIdcard;

    /**
     * 承建办理人
     */
    @TableField("CJ_HANDLER")
    private String cjHandler;

    /**
     * 承建办理人电话
     */
    @TableField("CJ_HANDLER_PHONE")
    private String cjHandlerPhone;

    /**
     * 承建单位输入类型 0:选择输入 1:手动输入
     */
    @TableField("CJ_INPUT_TYPE")
    private String cjInputType;

    /**
     * 更新周期
     */
    @TableField("UPDATE_CYCLE")
    private String updateCycle;

    /**
     * 数据资源
     */
    @TableField("DATA_RESOURCE")
    private String dataResource;

    /**
     * 数据来源
     */
    @TableField("DATA_FROM")
    private String dataFrom;

    /**
     * 资源来源系统
     */
    @TableField("FROM_SYSTEM")
    private String fromSystem;

    /**
     * 来源网域
     */
    @TableField("FROM_NET")
    private String fromNet;

    /**
     * 采集单位
     */
    @TableField("COLLECTION_UNIT")
    private String collectionUnit;

    @TableField(exist = false)
    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }


    public Integer getWorkFlowVersion() {
        return workFlowVersion;
    }

    public void setWorkFlowVersion(Integer workFlowVersion) {
        this.workFlowVersion = workFlowVersion;
    }

    public String getUpdateCycle() {
        return updateCycle;
    }

    public void setUpdateCycle(String updateCycle) {
        this.updateCycle = updateCycle;
    }

    public String getDataResource() {
        return dataResource;
    }

    public void setDataResource(String dataResource) {
        this.dataResource = dataResource;
    }

    public String getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom;
    }

    public String getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(String fromSystem) {
        this.fromSystem = fromSystem;
    }

    public String getFromNet() {
        return fromNet;
    }

    public void setFromNet(String fromNet) {
        this.fromNet = fromNet;
    }

    public String getCollectionUnit() {
        return collectionUnit;
    }

    public void setCollectionUnit(String collectionUnit) {
        this.collectionUnit = collectionUnit;
    }

    public List<FilesVo> getInterfaceFileList() {
        return interfaceFileList;
    }

    public void setInterfaceFileList(List<FilesVo> interfaceFileList) {
        this.interfaceFileList = interfaceFileList;
    }

    public List<FilesVo> getDevelopmentFileList() {
        return developmentFileList;
    }

    public void setDevelopmentFileList(List<FilesVo> developmentFileList) {
        this.developmentFileList = developmentFileList;
    }

    public String getBuildMode() {
        return buildMode;
    }

    public void setBuildMode(String buildMode) {
        this.buildMode = buildMode;
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

    public String getCjOrgCode() {
        return cjOrgCode;
    }

    public void setCjOrgCode(String cjOrgCode) {
        this.cjOrgCode = cjOrgCode;
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

    public String getCjPrincipalIdcard() {
        return cjPrincipalIdcard;
    }

    public void setCjPrincipalIdcard(String cjPrincipalIdcard) {
        this.cjPrincipalIdcard = cjPrincipalIdcard;
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

    public String getCjInputType() {
        return cjInputType;
    }

    public void setCjInputType(String cjInputType) {
        this.cjInputType = cjInputType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWhereFrom() {
        return whereFrom;
    }

    public void setWhereFrom(String whereFrom) {
        this.whereFrom = whereFrom;
    }

    public String getId() {
        return id;
    }

    public ServicePublish setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ServicePublish setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ServicePublish setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public ServicePublish setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ServicePublish setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public ServicePublish setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public ServicePublish setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ServicePublish setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceType() {
        return serviceType;
    }

    public ServicePublish setServiceType(String serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public String getIsPublishApig() {
        return isPublishApig;
    }

    public ServicePublish setIsPublishApig(String isPublishApig) {
        this.isPublishApig = isPublishApig;
        return this;
    }

    public String getIsFromApp() {
        return isFromApp;
    }

    public ServicePublish setIsFromApp(String isFromApp) {
        this.isFromApp = isFromApp;
        return this;
    }

    public String getFromAppName() {
        return fromAppName;
    }

    public ServicePublish setFromAppName(String fromAppName) {
        this.fromAppName = fromAppName;
        return this;
    }

    public String getFromAppId() {
        return fromAppId;
    }

    public ServicePublish setFromAppId(String fromAppId) {
        this.fromAppId = fromAppId;
        return this;
    }

    public String getAreaName() {
        return areaName;
    }

    public ServicePublish setAreaName(String areaName) {
        this.areaName = areaName;
        return this;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public ServicePublish setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ServicePublish setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public ServicePublish setPriorityName(String priorityName) {
        this.priorityName = priorityName;
        return this;
    }

    public String getVendor() {
        return vendor;
    }

    public ServicePublish setVendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ServicePublish setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public ServicePublish setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
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

    public ServicePublishApiProduct getApiProduct() {
        return apiProduct;
    }

    public void setApiProduct(ServicePublishApiProduct apiProduct) {
        this.apiProduct = apiProduct;
    }

    public List<ServicePublishApi> getApiList() {
        return apiList;
    }

    public void setApiList(List<ServicePublishApi> apiList) {
        this.apiList = apiList;
    }

    public List<ServicePublishBackend> getBackendList() {
        return backendList;
    }

    public void setBackendList(List<ServicePublishBackend> backendList) {
        this.backendList = backendList;
    }

    public List<AppReviewInfoVo> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<AppReviewInfoVo> reviewList) {
        this.reviewList = reviewList;
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

    public boolean isCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    public boolean isCanImpl() {
        return canImpl;
    }

    public void setCanImpl(boolean canImpl) {
        this.canImpl = canImpl;
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

    public String getProcessingPerson() {
        return processingPerson;
    }

    public void setProcessingPerson(String processingPerson) {
        this.processingPerson = processingPerson;
    }

}
