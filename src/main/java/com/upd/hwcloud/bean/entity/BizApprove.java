package com.upd.hwcloud.bean.entity;

import java.util.Date;

/**
 * @Description: 建议
 * @author: yyc
 * @date: 2018-10-16 13:23
 **/
public class BizApprove {
    private String id;
    private String approvePerson;
    private String approveResult;
    private String approveOpinion;
    private Date approveDate;

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApprovePerson() {
        return approvePerson;
    }

    public void setApprovePerson(String approvePerson) {
        this.approvePerson = approvePerson;
    }

    public String getApproveResult() {
        return approveResult;
    }

    public void setApproveResult(String approveResult) {
        this.approveResult = approveResult;
    }

    public String getApproveOpinion() {
        return approveOpinion;
    }

    public void setApproveOpinion(String approveOpinion) {
        this.approveOpinion = approveOpinion;
    }

    @Override
    public String toString() {
        return "BizApprove{" +
                "id='" + id + '\'' +
                ", approvePerson='" + approvePerson + '\'' +
                ", approveResult='" + approveResult + '\'' +
                ", approveOpinion='" + approveOpinion + '\'' +
                '}';
    }
}
