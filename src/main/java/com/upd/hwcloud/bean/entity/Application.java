package com.upd.hwcloud.bean.entity;

import java.util.Date;

/**
 * @Description: 申请类
 * @author: yyc
 * @date: 2018-10-12 11:59
 **/
public class Application {

    /**申请单号*/
    private String bizId;
    /**申请人*/
    private String applyPerson;
    /**项目名称*/
    private String bizType;
    /**申请时间*/
    private Date applyDate;
    /**处理状态*/
    private String bizStatus;
    /**备注*/
    private String remark;
//    /**基本详情*/
//    private ApplicationBaseInfo baseInfo;


    @Override
    public String toString() {
        return "Application{" +
                "bizId='" + bizId + '\'' +
                ", applyPerson='" + applyPerson + '\'' +
                ", bizType='" + bizType + '\'' +
                ", applyDate=" + applyDate +
                ", bizStatus='" + bizStatus + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }


    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(String bizStatus) {
        this.bizStatus = bizStatus;
    }

    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
