package com.upd.hwcloud.bean.dto;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @author wuc
 * @date 2019/10/29
 */
public class SaasRecoverTotal {

    @Excel(name = "工单号")
    private String billNum;

    @Excel(name = "姓名")
    private String creatorName;

    @Excel(name = "身份证号码")
    private String creator;

    @Excel(name = "工作单位")
    private String orgName;
    @Excel(name = "地区")
    private String areas;
    @Excel(name = "警种")
    private String policeCategory;

    @Excel(name = "职务")
    private String post;

    @Excel(name = "手机号码")
    private String phone;

    @Excel(name = "二级/一级管理员提交时间", exportFormat = "yyyy-MM-dd")
    private Date createTime;

    @Excel(name = "服务台复核完成审批时间", exportFormat = "yyyy-MM-dd")
    private Date recheckTime;

    @Excel(name = "大数据办完成审批时间", exportFormat = "yyyy-MM-dd")
    private Date bigDataTime;

    @Excel(name = "服务权限回收时间", exportFormat = "yyyy-MM-dd")
    private Date carryTime;

    @Excel(name = "回收服务")
    private String reServiceName;


    private String applyType;


    public String getCreatorName() {
        return creatorName;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getReServiceName() {
        return reServiceName;
    }

    public void setReServiceName(String reServiceName) {
        this.reServiceName = reServiceName;
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
