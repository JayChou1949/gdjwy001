package com.hirisun.cloud.model.saas.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

public class SaasTotalDTO {

    @Excel(name = "序号")
    private Integer id;

    @Excel(name = "工单号")
    private String orderNumber;

    @Excel(name = "申请人数",type = 10)
    private Integer applyNum;

    @Excel(name = "所属地区")
    private String areas;

    @Excel(name = "所属警种")
    private String policeCategory;

    @Excel(name = "二级管理单位（地市/警种）")
    private String areasPoliceCategory;

    @Excel(name = "二级管理员提交一级管理员时间", exportFormat = "yyyy-MM-dd")
    private Date recheckTime;

    @Excel(name = "申请账号数量",type = 10)
    private Integer num;

    @Excel(name = "大数据办完成审批时间", exportFormat = "yyyy-MM-dd")
    private Date bigDataTime;

    @Excel(name = "审批账号数量",type = 10)
    private Integer approveNum;

    @Excel(name = "账号开通时间", exportFormat = "yyyy-MM-dd")
    private Date carryTime;

    @Excel(name = "账号开通数量",type = 10)
    private Integer carryNum;


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

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public String getAreasPoliceCategory() {
        return areasPoliceCategory;
    }

    public void setAreasPoliceCategory(String areasPoliceCategory) {
        this.areasPoliceCategory = areasPoliceCategory;
    }

    public Date getRecheckTime() {
        return recheckTime;
    }

    public void setRecheckTime(Date recheckTime) {
        this.recheckTime = recheckTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getBigDataTime() {
        return bigDataTime;
    }

    public void setBigDataTime(Date bigDataTime) {
        this.bigDataTime = bigDataTime;
    }

    public Integer getApproveNum() {
        return approveNum;
    }

    public void setApproveNum(Integer approveNum) {
        this.approveNum = approveNum;
    }

    public Date getCarryTime() {
        return carryTime;
    }

    public void setCarryTime(Date carryTime) {
        this.carryTime = carryTime;
    }

    public Integer getCarryNum() {
        return carryNum;
    }

    public void setCarryNum(Integer carryNum) {
        this.carryNum = carryNum;
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
