package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * DaaS 表
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
@TableName("TB_DAAS")
public class Daas extends Model<Daas> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private String id;

        /**
     *  服务名称
     */
         @TableField("NAME")
    private String name;

    /**
     * 服务简称
     */
    @TableField("SHORT_NAME")
    private String shortName;

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

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private String subTypeName;

    @TableField(exist = false)
    private Boolean redPoint;
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
    public String getId() {
        return id;
    }

    public Daas setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Daas setName(String name) {
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

    public Daas setSubType(String subType) {
        this.subType = subType;
        return this;
    }

    public Long getSort() {
        return sort;
    }

    public Daas setSort(Long sort) {
        this.sort = sort;
        return this;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public Daas setVersionCode(String versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Daas setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Daas setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Daas setUrl(String url) {
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

    public Daas setImage(String image) {
        this.image = image;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Daas setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getProApplication() {
        return proApplication;
    }

    public Daas setProApplication(String proApplication) {
        this.proApplication = proApplication;
        return this;
    }

    public String getProUse() {
        return proUse;
    }

    public Daas setProUse(String proUse) {
        this.proUse = proUse;
        return this;
    }

    public String getTestForm() {
        return testForm;
    }

    public Daas setTestForm(String testForm) {
        this.testForm = testForm;
        return this;
    }

    public String getTestApplication() {
        return testApplication;
    }

    public Daas setTestApplication(String testApplication) {
        this.testApplication = testApplication;
        return this;
    }

    public String getTestUse() {
        return testUse;
    }

    public Daas setTestUse(String testUse) {
        this.testUse = testUse;
        return this;
    }

    public String getGuide() {
        return guide;
    }

    public Daas setGuide(String guide) {
        this.guide = guide;
        return this;
    }

    public String getGuideIntranet() {
        return guideIntranet;
    }

    public Daas setGuideIntranet(String guideIntranet) {
        this.guideIntranet = guideIntranet;
        return this;
    }

    public String getFirstInterfacePerson() {
        return firstInterfacePerson;
    }

    public Daas setFirstInterfacePerson(String firstInterfacePerson) {
        this.firstInterfacePerson = firstInterfacePerson;
        return this;
    }

    public String getUpInterfacePerson() {
        return upInterfacePerson;
    }

    public Daas setUpInterfacePerson(String upInterfacePerson) {
        this.upInterfacePerson = upInterfacePerson;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Daas setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public Daas setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public Long getStatus() {
        return status;
    }

    public Daas setStatus(Long status) {
        this.status = status;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Daas setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getTop() {
        return top;
    }

    public Daas setTop(String top) {
        this.top = top;
        return this;
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

    public Boolean getRedPoint() {
        return redPoint;
    }

    public void setRedPoint(Boolean redPoint) {
        this.redPoint = redPoint;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Daas{" +
        "id=" + id +
        ", name=" + name +
        ", subType=" + subType +
        ", sort=" + sort +
        ", versionCode=" + versionCode +
        ", userName=" + userName +
        ", password=" + password +
        ", url=" + url +
        ", image=" + image +
        ", description=" + description +
        ", proApplication=" + proApplication +
        ", proUse=" + proUse +
        ", testForm=" + testForm +
        ", testApplication=" + testApplication +
        ", testUse=" + testUse +
        ", guide=" + guide +
        ", guideIntranet=" + guideIntranet +
        ", firstInterfacePerson=" + firstInterfacePerson +
        ", upInterfacePerson=" + upInterfacePerson +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", status=" + status +
        ", creator=" + creator +
        ", top=" + top +
        "}";
    }
}
