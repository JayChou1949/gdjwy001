package com.hirisun.cloud.model.third.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class SaasUseTotal {

    @Excel(name = "序号")
    private Integer id;

    @Excel(name = "姓名")
    private String creatorName;

    @Excel(name = "身份证号码")
    private String creator;

    @Excel(name = "工作单位")
    private String orgName;

    @Excel(name = "职务")
    private String postType;

    @Excel(name = "手机号码")
    private String mobileWork;

    @Excel(name = "终端IP地址")
    private String ip;

    @Excel(name = "所属地区")
    private String areas;

    @Excel(name = "所属警种")
    private String policeCategory;

    @Excel(name = "二级管理单位（地市/警种）")
    private String areasPoliceCategory;

    @Excel(name = "申请服务")
    private String serviceName;

    @Excel(name = "服务是否有使用权限")
    private String status;

    @Excel(name = "申请该服务次数",type = 10)
    private Integer applyServiceCount;

    private String applyType;

    private String recoverFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getMobileWork() {
        return mobileWork;
    }

    public void setMobileWork(String mobileWork) {
        this.mobileWork = mobileWork;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAreasPoliceCategory() {
        return areasPoliceCategory;
    }

    public void setAreasPoliceCategory(String areasPoliceCategory) {
        this.areasPoliceCategory = areasPoliceCategory;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

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

    public Integer getApplyServiceCount() {
        return applyServiceCount;
    }

    public void setApplyServiceCount(Integer applyServiceCount) {
        this.applyServiceCount = applyServiceCount;
    }

    public String getRecoverFlag() {
        return recoverFlag;
    }

    public void setRecoverFlag(String recoverFlag) {
        this.recoverFlag = recoverFlag;
    }

}
