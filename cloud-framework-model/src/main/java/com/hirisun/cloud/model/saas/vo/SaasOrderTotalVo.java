package com.hirisun.cloud.model.saas.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

public class SaasOrderTotalVo {

    @Excel(name = "序号")
    private Integer id;

    @Excel(name = "工单号")
    private String orderNumber;

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

    @Excel(name = "民警提交二级管理员时间", exportFormat = "yyyy-MM-dd")
    private Date createTime;

    @Excel(name = "二级管理员提交一级管理员时间", exportFormat = "yyyy-MM-dd")
    private Date recheckTime;

    @Excel(name = "大数据办完成审批时间", exportFormat = "yyyy-MM-dd")
    private Date bigDataTime;

    @Excel(name = "服务权限开通时间", exportFormat = "yyyy-MM-dd")
    private Date carryTime;

    @Excel(name = "申请服务")
    private String serviceName;


    private String applyType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getRecheckTime() {
        return recheckTime;
    }

    public void setRecheckTime(Date recheckTime) {
        this.recheckTime = recheckTime;
    }

    public Date getBigDataTime() {
        return bigDataTime;
    }

    public void setBigDataTime(Date bigDataTime) {
        this.bigDataTime = bigDataTime;
    }

    public Date getCarryTime() {
        return carryTime;
    }

    public void setCarryTime(Date carryTime) {
        this.carryTime = carryTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

}
