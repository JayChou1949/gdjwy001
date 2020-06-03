package com.upd.hwcloud.bean.entity;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @author wb
 * @date 2019/11/9
 */
public class SaasApplicationExport {

    @Excel(name = "序号")
    private Integer id;

    @Excel(name = "工单号",width = 15)
    private String orderNumber;

    @Excel(name = "申请单总数")
    private String num;

    @Excel(name = "申请人")
    private String creatorName;

    @Excel(name = "申请人单位",width = 15)
    private String orgName;

    @Excel(name = "当前处理状态")
    private String stepName;

    @Excel(name = "服务台审核人员")
    private String recheckPerson;

    @Excel(name = "服务台审核时间",exportFormat = "yyy-MM-dd hh:mm:ss",width = 15)
    private Date recheckTime;

    @Excel(name = "服务台审批结果")
    private String recheckResult;

    @Excel(name = "服务台复核意见",width = 30)
    private String recheckOpnion;

    private String ids;

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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
