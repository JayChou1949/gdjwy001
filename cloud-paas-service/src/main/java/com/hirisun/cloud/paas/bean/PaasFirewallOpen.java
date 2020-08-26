package com.hirisun.cloud.paas.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 防火墙开通基本信息
 */
@TableName("TB_PAAS_FIREWALL_OPEN")
public class PaasFirewallOpen extends Model<PaasFirewallOpen> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 业务类型 0:正式 1:测试
     */
         @TableField("BUSINESS_TYPE")
    private Integer businessType;

        /**
     * 开通时段 0:长期 1;正式
     */
         @TableField("OPENING_HOURS")
    private Integer openingHours;


    /**
     * 开始时间
     */
    @TableField("START_TIME")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField("END_TIME")
    private Date endTime;



        /**
     * 开发公司
     */
         @TableField("DEVELOPMENT_COMPANY")
    private String developmentCompany;

        /**
     * 项目负责人
     */
         @TableField("PROJECT_MANAGER")
    private String projectManager;

        /**
     * 项目负责人电话
     */
         @TableField("PROJECT_MANAGER_PHONE")
    private String projectManagerPhone;

        /**
     * 技术负责人
     */
         @TableField("TECHNICAL_DIRECTOR")
    private String technicalDirector;

        /**
     * 技术负责人电话
     */
         @TableField("TECHNICAL_DIRECTOR_PHONE")
    private String technicalDirectorPhone;

        /**
     * 说明
     */
         @TableField("EXPLANATION")
    private String explanation;

        /**
     * 申请ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 购物车ID
     */
         @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

         @TableField(exist = false)
         private List<PaasFirewallOpenExt> extList;

        /**
     * 创建时间
     */
         @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

        /**
     * 修改时间
     */
         @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 源地址所属单位
     */
    @TableField("SOURCE_ORG")
    private String sourceOrg;

    /**
     * 源地址所属应用地址
     */
    @TableField("SOURCE_APP")
    private String sourceApp;

    /**
     * 目的地址所属单位
     */
    @TableField("DESTINATION_ORG")
    private String destinationOrg;

    /**
     * 目的地址所属应用/系统
     */
    @TableField("DESTINATION_APP")
    private String destinationApp;


    public String getId() {
        return id;
    }

    public PaasFirewallOpen setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public PaasFirewallOpen setBusinessType(Integer businessType) {
        this.businessType = businessType;
        return this;
    }


    public Integer getOpeningHours() {
        return openingHours;
    }

    public PaasFirewallOpen setOpeningHours(Integer openingHours) {
        this.openingHours = openingHours;
        return this;
    }

    public String getDevelopmentCompany() {
        return developmentCompany;
    }

    public PaasFirewallOpen setDevelopmentCompany(String developmentCompany) {
        this.developmentCompany = developmentCompany;
        return this;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public PaasFirewallOpen setProjectManager(String projectManager) {
        this.projectManager = projectManager;
        return this;
    }

    public String getProjectManagerPhone() {
        return projectManagerPhone;
    }

    public PaasFirewallOpen setProjectManagerPhone(String projectManagerPhone) {
        this.projectManagerPhone = projectManagerPhone;
        return this;
    }

    public String getTechnicalDirector() {
        return technicalDirector;
    }

    public PaasFirewallOpen setTechnicalDirector(String technicalDirector) {
        this.technicalDirector = technicalDirector;
        return this;
    }

    public String getTechnicalDirectorPhone() {
        return technicalDirectorPhone;
    }

    public PaasFirewallOpen setTechnicalDirectorPhone(String technicalDirectorPhone) {
        this.technicalDirectorPhone = technicalDirectorPhone;
        return this;
    }

    public String getExplanation() {
        return explanation;
    }

    public PaasFirewallOpen setExplanation(String explanation) {
        this.explanation = explanation;
        return this;
    }

    public List<PaasFirewallOpenExt> getExtList() {
        return extList;
    }

    public void setExtList(List<PaasFirewallOpenExt> extList) {
        this.extList = extList;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasFirewallOpen setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public PaasFirewallOpen setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getSourceOrg() {
        return sourceOrg;
    }

    public void setSourceOrg(String sourceOrg) {
        this.sourceOrg = sourceOrg;
    }

    public String getSourceApp() {
        return sourceApp;
    }

    public void setSourceApp(String sourceApp) {
        this.sourceApp = sourceApp;
    }

    public String getDestinationOrg() {
        return destinationOrg;
    }

    public void setDestinationOrg(String destinationOrg) {
        this.destinationOrg = destinationOrg;
    }

    public String getDestinationApp() {
        return destinationApp;
    }

    public void setDestinationApp(String destinationApp) {
        this.destinationApp = destinationApp;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasFirewallOpen{" +
        "id=" + id +
        ", businessType=" + businessType +
        ", openingHours=" + openingHours +
        ", developmentCompany=" + developmentCompany +
        ", projectManager=" + projectManager +
        ", projectManagerPhone=" + projectManagerPhone +
        ", technicalDirector=" + technicalDirector +
        ", technicalDirectorPhone=" + technicalDirectorPhone +
        ", explanation=" + explanation +
        ", appInfoId=" + appInfoId +
        ", shoppingCartId=" + shoppingCartId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
