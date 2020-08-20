package com.hirisun.cloud.order.bean.publish;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @author wb
 * @date 2019/11/9
 */
public class ServicePublishApplicationRegistExport {
    @Excel(name = "序号")
    private Integer id;

    @Excel(name = "工单号",width = 15)
    private String orderNumber;

    @Excel(name = "申请人")
    private String creatorName;

    @Excel(name = "申请人单位",width = 20)
    private String orgName;

    @Excel(name = "申请时间",exportFormat = "yyy-MM-dd hh:mm:ss",width = 15)
    private Date createTime;

    @Excel(name = "服务名称",width = 15)
    private String appName;

    @Excel(name = "是否为政府机构应用",width = 15)
    private String isgov;

    @Excel(name = "所属地区")
    private String area;

    @Excel(name = "所属警种")
    private String policeCategory;

    @Excel(name = "处理状态")
    private String stepName;

    @Excel(name = "服务台审核人员")
    private String recheckPerson;

    @Excel(name = "服务台审核时间",exportFormat = "yyy-MM-dd hh:mm:ss",width = 15)
    private Date recheckTime;

    @Excel(name = "服务台审批结果")
    private String recheckResult;

    @Excel(name = "服务台复核意见",width = 30)
    private String recheckOpnion;

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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIsgov() {
        return isgov;
    }

    public void setIsgov(String isgov) {
        this.isgov = isgov;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getRecheckPerson() {
        return recheckPerson;
    }

    public void setRecheckPerson(String recheckPerson) {
        this.recheckPerson = recheckPerson;
    }

    public Date getRecheckTime() {
        return recheckTime;
    }

    public void setRecheckTime(Date recheckTime) {
        this.recheckTime = recheckTime;
    }

    public String getRecheckResult() {
        return recheckResult;
    }

    public void setRecheckResult(String recheckResult) {
        this.recheckResult = recheckResult;
    }

    public String getRecheckOpnion() {
        return recheckOpnion;
    }

    public void setRecheckOpnion(String recheckOpnion) {
        this.recheckOpnion = recheckOpnion;
    }
}
