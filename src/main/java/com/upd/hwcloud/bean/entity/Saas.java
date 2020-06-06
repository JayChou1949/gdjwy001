package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * SaaS 表
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
@TableName("TB_SAAS")
public class Saas extends Model<Saas> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private String id;

        /**
     *  服务名称
     */
         @TableField("NAME")
    private String name;
    /**
     * 关联流程ID
     */
    @TableField("WORK_FLOW_ID")
    private String workFlowId;
    /**
     * 服务简称
     */
    @TableField("SHORT_NAME")
    private String shortName;
    /**
     * 申请说明
     */
    @TableField("INSTRUCTIONS")
    private String instructions;
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
        /**
     * 子类
     */
         @TableField("SUB_TYPE")
    private String subType;

        /**
     * 排序
     */
        @TableField(value = "SORT",fill = FieldFill.INSERT)
    private Long sort;

        /**
     * 版本号
     */
         @TableField("VERSION_CODE")
    private String versionCode;

        /**
     * 用户名
     */
         @TableField("USER_NAME")
    private String userName;

        /**
     * 密码
     */
         @TableField("PASSWORD")
    private String password;

        /**
     * 链接
     */
         @TableField("URL")
    private String url;

    /**
     * 二级页面 URL
     */
    @TableField("SUB_PAGE_URL")
    private String subPageUrl;

        /**
     * 展示图
     */
         @TableField("IMAGE")
    private String image;



    /**
     * 通过nginx获取地址
     */
    @TableField("REAL_IMAGE")
    private String realImage;

        /**
     * 描述
     */
         @TableField("DESCRIPTION")
    private String description;

        /**
     * 生产环境资源申请方式
     */
         @TableField("PRO_APPLICATION")
    private String proApplication;

        /**
     * 生产环境资源使用方式
     */
         @TableField("PRO_USE")
    private String proUse;

        /**
     * 测试环境存在形式
     */
         @TableField("TEST_FORM")
    private String testForm;

        /**
     * 测试环境资源申请方式
     */
         @TableField("TEST_APPLICATION")
    private String testApplication;

        /**
     * 测试环境资源使用方式
     */
         @TableField("TEST_USE")
    private String testUse;

        /**
     * 使用/开发指南
     */
         @TableField("GUIDE")
    private String guide;

        /**
     * 公安网 使用/开发指南位置
     */
         @TableField("GUIDE_INTRANET")
    private String guideIntranet;

        /**
     * 运维服务第一接口人
     */
         @TableField("FIRST_INTERFACE_PERSON")
    private String firstInterfacePerson;

        /**
     * 运维服务上升接口人
     */
         @TableField("UP_INTERFACE_PERSON")
    private String upInterfacePerson;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除
     */
         @TableField("STATUS")
    private Long status;

        /**
     * 创建人
     */
         @TableField("CREATOR")
    private String creator;

    /**
     * 是否大图  是否大图 0:否 1:是
     */
    @TableField("TOP")
    private String top;

    /**
     * 建设进展 0.已完成 1.建设中
     */
    @TableField("BUILD_STATUS")
    private Long buildStatus;

    /**
     * 二级页面查看权限 0.完全开放 1登录可看
     */
    @TableField("SUB_PAGE_PERMISSION")
    private Long subPagePermission;

    /**
     * 是否首页展示 0:否 1:是
     */
    @TableField("HOME")
    private String home;

    /**
     * 是否能申请 0:否 1:是
     */
    @TableField("CAN_APPLICATION")
    private String canApplication;

    /**
     * 是否有开发文档 0:否 1:是
     */
    @TableField("HAS_DOC")
    private String hasDoc;

    /**
     * 是否加密应用 0:否 1:是
     */
    @TableField("SECRET")
    private String secret;

    /**
     * 申请表单编码
     */
    @TableField("FORM_NUM")
    private String formNum;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private String subTypeName;

    /**
     * 标签描述
     */
    @TableField("TAG_DESC")
    private String tagDesc;

    /**
     * 接入系统数量
     */
    @TableField("AP_NUM")
    private String apNum;

    /**
     * 上报方式 0-地区 1-警种 2-政府机构
     */
    @TableField("APPLY_TYPE")
    private String applyType;

    /**
     * 地区
     */
    @TableField("AREA_NAME")
    private String areaName;

    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @TableField("REGIST_ID")
    private String registId;

    /**
     * 浏览量
     */
    @TableField("VIEW_COUNT")
    private Long viewCount;


    /**
     * API网关GUID
     */
    @TableField("SERVICE_GUID")
    private String serviceGuid;


    /**
     * 是否为saas服务 0应用 1为服务
     */
    @TableField("SERVICE_FLAG")
    private Integer serviceFlag;


    /**
     * 是否是试点应用：0-非试点,1-2019,2-2020,...(目前只有2019试点，以后扩展按该规则)
     */
    @TableField("PILOT_APP")
    private Integer pilotApp;

    /**
     * 进入系统点击量
     */
    @TableField("SYS_VIEW")
    private Long sysView;


    /**
     * 测试应用 0：非测试应用 1：测试应用 类别为通用应用时生效
     */
    @TableField("TEST")
    private Boolean test;

    /**
     * SAAS/应用所属专项
     */
    @TableField("PROJECT")
    private String project;

    @TableField(exist = false)
    private boolean canAddShoppingCart = true;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public Long getSysView() {
        return sysView;
    }

    public void setSysView(Long sysView) {
        this.sysView = sysView;
    }

    public Integer getPilotApp() {
        return pilotApp;
    }

    public void setPilotApp(Integer pilotApp) {
        this.pilotApp = pilotApp;
    }

    public Integer getServiceFlag() {
        return serviceFlag;
    }

    public void setServiceFlag(Integer serviceFlag) {
        this.serviceFlag = serviceFlag;
    }

    public String getServiceGuid() {
        return serviceGuid;
    }

    public void setServiceGuid(String serviceGuid) {
        this.serviceGuid = serviceGuid;
    }

    public String getId() {
        return id;
    }

    public Saas setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Saas setName(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSubType() {
        return subType;
    }

    public Saas setSubType(String subType) {
        this.subType = subType;
        return this;
    }

    public String getRealImage() {
        return realImage;
    }

    public void setRealImage(String realImage) {
        this.realImage = realImage;
    }

    public Long getSort() {
        return sort;
    }

    public Saas setSort(Long sort) {
        this.sort = sort;
        return this;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public Saas setVersionCode(String versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Saas setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public boolean isCanAddShoppingCart() {
        return canAddShoppingCart;
    }

    public void setCanAddShoppingCart(boolean canAddShoppingCart) {
        this.canAddShoppingCart = canAddShoppingCart;
    }

    public String getPassword() {
        return password;
    }

    public Saas setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Saas setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getSubPageUrl() {
        return subPageUrl;
    }

    public void setSubPageUrl(String subPageUrl) {
        this.subPageUrl = subPageUrl;
    }

    public String getImage() {
        return image;
    }

    public Saas setImage(String image) {
        this.image = image;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Saas setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getProApplication() {
        return proApplication;
    }

    public Saas setProApplication(String proApplication) {
        this.proApplication = proApplication;
        return this;
    }

    public String getProUse() {
        return proUse;
    }

    public Saas setProUse(String proUse) {
        this.proUse = proUse;
        return this;
    }

    public String getTestForm() {
        return testForm;
    }

    public Saas setTestForm(String testForm) {
        this.testForm = testForm;
        return this;
    }

    public String getTestApplication() {
        return testApplication;
    }

    public Saas setTestApplication(String testApplication) {
        this.testApplication = testApplication;
        return this;
    }

    public String getTestUse() {
        return testUse;
    }

    public Saas setTestUse(String testUse) {
        this.testUse = testUse;
        return this;
    }

    public String getGuide() {
        return guide;
    }

    public Saas setGuide(String guide) {
        this.guide = guide;
        return this;
    }

    public String getGuideIntranet() {
        return guideIntranet;
    }

    public Saas setGuideIntranet(String guideIntranet) {
        this.guideIntranet = guideIntranet;
        return this;
    }

    public String getFirstInterfacePerson() {
        return firstInterfacePerson;
    }

    public Saas setFirstInterfacePerson(String firstInterfacePerson) {
        this.firstInterfacePerson = firstInterfacePerson;
        return this;
    }

    public String getUpInterfacePerson() {
        return upInterfacePerson;
    }

    public Saas setUpInterfacePerson(String upInterfacePerson) {
        this.upInterfacePerson = upInterfacePerson;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Saas setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public Saas setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public Long getStatus() {
        return status;
    }

    public Saas setStatus(Long status) {
        this.status = status;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Saas setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getTop() {
        return top;
    }

    public Saas setTop(String top) {
        this.top = top;
        return this;
    }

    public String getFormNum() {
        return formNum;
    }

    public void setFormNum(String formNum) {
        this.formNum = formNum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public Long getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(Long buildStatus) {
        this.buildStatus = buildStatus;
    }

    public Long getSubPagePermission() {
        return subPagePermission;
    }

    public void setSubPagePermission(Long subPagePermission) {
        this.subPagePermission = subPagePermission;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getCanApplication() {
        return canApplication;
    }

    public void setCanApplication(String canApplication) {
        this.canApplication = canApplication;
    }

    public String getHasDoc() {
        return hasDoc;
    }

    public void setHasDoc(String hasDoc) {
        this.hasDoc = hasDoc;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    @Override
    public String toString() {
        return "Saas{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", workFlowId='" + workFlowId + '\'' +
                ", shortName='" + shortName + '\'' +
                ", instructions='" + instructions + '\'' +
                ", subType='" + subType + '\'' +
                ", sort=" + sort +
                ", versionCode='" + versionCode + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", subPageUrl='" + subPageUrl + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", proApplication='" + proApplication + '\'' +
                ", proUse='" + proUse + '\'' +
                ", testForm='" + testForm + '\'' +
                ", testApplication='" + testApplication + '\'' +
                ", testUse='" + testUse + '\'' +
                ", guide='" + guide + '\'' +
                ", guideIntranet='" + guideIntranet + '\'' +
                ", firstInterfacePerson='" + firstInterfacePerson + '\'' +
                ", upInterfacePerson='" + upInterfacePerson + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", status=" + status +
                ", creator='" + creator + '\'' +
                ", top='" + top + '\'' +
                ", buildStatus=" + buildStatus +
                ", subPagePermission=" + subPagePermission +
                ", home='" + home + '\'' +
                ", canApplication='" + canApplication + '\'' +
                ", hasDoc='" + hasDoc + '\'' +
                ", secret='" + secret + '\'' +
                ", formNum='" + formNum + '\'' +
                ", user=" + user +
                ", subTypeName='" + subTypeName + '\'' +
                ", tagDesc='" + tagDesc + '\'' +
                ", apNum='" + apNum + '\'' +
                ", applyType='" + applyType + '\'' +
                ", areaName='" + areaName + '\'' +
                ", policeCategory='" + policeCategory + '\'' +
                ", registId='" + registId + '\'' +
                ", viewCount=" + viewCount +
                ", serviceGuid='" + serviceGuid + '\'' +
                ", serviceFlag=" + serviceFlag +
                ", pilotApp=" + pilotApp +
                ", sysView=" + sysView +
                ", test=" + test +
                '}';
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }


    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    public String getApNum() {
        return apNum;
    }

    public void setApNum(String apNum) {
        this.apNum = apNum;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public String getRegistId() {
        return registId;
    }

    public void setRegistId(String registId) {
        this.registId = registId;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
}
