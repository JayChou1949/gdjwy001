package com.upd.hwcloud.bean.entity;

import java.util.Date;

/**
 * @Description: 申请基本详情
 * @author: yyc
 * @date: 2018-10-13 14:44
 **/
public class ApplicationBaseInfo {
    /**申请单位*/
    private String applyDepartFullName;
    /**申请人*/
    private String applyPerson;
    /**申请时间*/
    private Date applyDate;
    /**手机*/
    private String telphoneNumber;
    /**办公电话*/
    private String officePhone;
    /**公安网邮箱*/
    private String policeNetEmail;
    /**系统名称*/
    private String projectName;
    /**开发公司*/
    private String developmentCompany;
    /**项目负责人*/
    private String projectPrincipal;
    /**联系电话*/
    private String principalPhone;
    /**申请说明*/
    private String applyExplain;
    /**附*/
    private String files;

//    /**设备组*/
//    private List<ApplicationDevDetail> devList;

//
//    public List<ApplicationDevDetail> getDevList() {
//        return devList;
//    }
//
//    public void setDevList(List<ApplicationDevDetail> devList) {
//        this.devList = devList;
//    }


    public String getApplyDepartFullName() {
        return applyDepartFullName;
    }

    public void setApplyDepartFullName(String applyDepartFullName) {
        this.applyDepartFullName = applyDepartFullName;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getTelphoneNumber() {
        return telphoneNumber;
    }

    public void setTelphoneNumber(String telphoneNumber) {
        this.telphoneNumber = telphoneNumber;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getPoliceNetEmail() {
        return policeNetEmail;
    }

    public void setPoliceNetEmail(String policeNetEmail) {
        this.policeNetEmail = policeNetEmail;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDevelopmentCompany() {
        return developmentCompany;
    }

    public void setDevelopmentCompany(String developmentCompany) {
        this.developmentCompany = developmentCompany;
    }

    public String getProjectPrincipal() {
        return projectPrincipal;
    }

    public void setProjectPrincipal(String projectPrincipal) {
        this.projectPrincipal = projectPrincipal;
    }

    public String getPrincipalPhone() {
        return principalPhone;
    }

    public void setPrincipalPhone(String principalPhone) {
        this.principalPhone = principalPhone;
    }

    public String getApplyExplain() {
        return applyExplain;
    }

    public void setApplyExplain(String applyExplain) {
        this.applyExplain = applyExplain;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "ApplicationBaseInfo{" +
                "applyDepartFullName='" + applyDepartFullName + '\'' +
                ", applyPerson='" + applyPerson + '\'' +
                ", applyDate=" + applyDate +
                ", telphoneNumber='" + telphoneNumber + '\'' +
                ", officePhone='" + officePhone + '\'' +
                ", policeNetEmail='" + policeNetEmail + '\'' +
                ", projectName='" + projectName + '\'' +
                ", developmentCompany='" + developmentCompany + '\'' +
                ", projectPrincipal='" + projectPrincipal + '\'' +
                ", principalPhone='" + principalPhone + '\'' +
                ", applyExplain='" + applyExplain + '\'' +
                ", files='" + files + '\'' +
//                ", devList=" + devList +
                '}';
    }
}
