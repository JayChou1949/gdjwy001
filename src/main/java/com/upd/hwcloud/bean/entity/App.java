package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 厂商应用
 * </p>
 *
 * @author wuc
 * @since 2019-05-13
 */
@TableName("TB_APP")
public class App extends Model<App> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private String id;

        /**
     * 应用名称
     */
         @TableField("APPLY_NAME")
    private String applyName;

        /**
     * 应用代码
     */
         @TableField("APPLY_CODE")
    private String applyCode;

        /**
     * 应用类别
     */
         @TableField("APPLY_CATEGORY")
    private String applyCategory;

        /**
     * 所属业务警种
     */
         @TableField("POLICE_SPECIES")
    private String policeSpecies;

        /**
     * 所属单位id
     */
         @TableField("ORG_ID")
    private String orgId;

        /**
     * 所属单位名称
     */
         @TableField("ORG_NAME")
    private String orgName;

        /**
     * 排序号
     */
         @TableField("SORT")
    private String sort;

        /**
     * 应用url
     */
         @TableField("APPLY_URL")
    private String applyUrl;

        /**
     * 应用厂商id
     */
         @TableField("MANUFACTURER_ID")
    private String manufacturerId;

        /**
     * 应用厂商名称
     */
         @TableField("MANUFACTURER_NAME")
    private String manufacturerName;

        /**
     * 应用状态
     */
         @TableField("APPLY_STATUS")
    private String applyStatus;

        /**
     * 应用管理员
     */
         @TableField("MANAGER_NAME")
    private String managerName;

        /**
     * 管理员联系电话
     */
         @TableField("MANAGER_PHONE")
    private String managerPhone;

        /**
     * 应用描述
     */
         @TableField("MANAGER_DESCRIBE")
    private String managerDescribe;

    @TableField("API_KEY")
    private String apiKey;

    @TableField("SECRET_KEY")
    private String secretKey;

        /**
     * 应用负责人
     */
         @TableField("FZR")
    private String fzr;

        /**
     * 负责人电话
     */
         @TableField("FZR_PHONE")
    private String fzrPhone;

        /**
     * 应用图标
     */
         @TableField("APPLY_ICON")
    private String applyIcon;

        /**
     * 是否删除 0:使用中，1:已删除
     */
         @TableField("DELETED")
    private String deleted;

        /**
     * 注册时间
     */
         @TableField("REGISTRATION_TIME")
    private String registrationTime;

        /**
     * 注册时间
     */
         @TableField("REGISTRATION_TIME_STR")
    private String registrationTimeStr;


    public String getId() {
        return id;
    }

    public App setId(String id) {
        this.id = id;
        return this;
    }

    public String getApplyName() {
        return applyName;
    }

    public App setApplyName(String applyName) {
        this.applyName = applyName;
        return this;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public App setApplyCode(String applyCode) {
        this.applyCode = applyCode;
        return this;
    }

    public String getApplyCategory() {
        return applyCategory;
    }

    public App setApplyCategory(String applyCategory) {
        this.applyCategory = applyCategory;
        return this;
    }

    public String getPoliceSpecies() {
        return policeSpecies;
    }

    public App setPoliceSpecies(String policeSpecies) {
        this.policeSpecies = policeSpecies;
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public App setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public App setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public String getSort() {
        return sort;
    }

    public App setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public App setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
        return this;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public App setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
        return this;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public App setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
        return this;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public App setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
        return this;
    }

    public String getManagerName() {
        return managerName;
    }

    public App setManagerName(String managerName) {
        this.managerName = managerName;
        return this;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public App setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
        return this;
    }

    public String getManagerDescribe() {
        return managerDescribe;
    }

    public App setManagerDescribe(String managerDescribe) {
        this.managerDescribe = managerDescribe;
        return this;
    }

    public String getApiKey() {
        return apiKey;
    }

    public App setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public App setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public String getFzr() {
        return fzr;
    }

    public App setFzr(String fzr) {
        this.fzr = fzr;
        return this;
    }

    public String getFzrPhone() {
        return fzrPhone;
    }

    public App setFzrPhone(String fzrPhone) {
        this.fzrPhone = fzrPhone;
        return this;
    }

    public String getApplyIcon() {
        return applyIcon;
    }

    public App setApplyIcon(String applyIcon) {
        this.applyIcon = applyIcon;
        return this;
    }

    public String getDeleted() {
        return deleted;
    }

    public App setDeleted(String deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public App setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
        return this;
    }

    public String getRegistrationTimeStr() {
        return registrationTimeStr;
    }

    public App setRegistrationTimeStr(String registrationTimeStr) {
        this.registrationTimeStr = registrationTimeStr;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "App{" +
        "id=" + id +
        ", applyName=" + applyName +
        ", applyCode=" + applyCode +
        ", applyCategory=" + applyCategory +
        ", policeSpecies=" + policeSpecies +
        ", orgId=" + orgId +
        ", orgName=" + orgName +
        ", sort=" + sort +
        ", applyUrl=" + applyUrl +
        ", manufacturerId=" + manufacturerId +
        ", manufacturerName=" + manufacturerName +
        ", applyStatus=" + applyStatus +
        ", managerName=" + managerName +
        ", managerPhone=" + managerPhone +
        ", managerDescribe=" + managerDescribe +
        ", apiKey=" + apiKey +
        ", secretKey=" + secretKey +
        ", fzr=" + fzr +
        ", fzrPhone=" + fzrPhone +
        ", applyIcon=" + applyIcon +
        ", deleted=" + deleted +
        ", registrationTime=" + registrationTime +
        ", registrationTimeStr=" + registrationTimeStr +
        "}";
    }
}
