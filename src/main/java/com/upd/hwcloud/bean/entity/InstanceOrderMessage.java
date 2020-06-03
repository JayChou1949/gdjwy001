package com.upd.hwcloud.bean.entity;

/**
 * <p>
 *  警员订购实例信息
 * </p>
 */

public class InstanceOrderMessage {

    //  实例GUID
    private String instanceId;
    //  实例名
    private String alias;
    //  地区
    private String areaName;
    //  警种
    private String policeGategory;
    //  实例创建时间
    private String createdAt;
    //  实例更新时间
    private String updatedAt;
    //  身份证号
    private String idCard;
    //  姓名
    private String name;
    //  警号
    private String policeNumber;
    //  职务
    private String postType;
    //  单位
    private String orgName;
    //  用户创建时间
    private String createTime;
    //  项目ID
    private String projectId;
    //  AppKey
    private String appKey;
    //  App秘钥
    private String appSecret;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoliceNumber() {
        return policeNumber;
    }

    public void setPoliceNumber(String policeNumber) {
        this.policeNumber = policeNumber;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPoliceGategory() {
        return policeGategory;
    }

    public void setPoliceGategory(String policeGategory) {
        this.policeGategory = policeGategory;
    }

    @Override
    public String toString() {
        return "InstanceOrderMessage{" +
                "instanceId='" + instanceId + '\'' +
                ", alias='" + alias + '\'' +
                ", areaName='" + areaName + '\'' +
                ", policeGategory='" + policeGategory + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", idCard='" + idCard + '\'' +
                ", name='" + name + '\'' +
                ", policeNumber='" + policeNumber + '\'' +
                ", postType='" + postType + '\'' +
                ", orgName='" + orgName + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
