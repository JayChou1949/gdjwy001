package com.hirisun.cloud.model.third.dto;

import java.util.Date;

/**
 * @Description: 流程Dto
 * @author: yyc
 * @date: 2018-10-13 14:56
 **/
public class FlowDto {

    /**人*/
    private String handlePerson;
    /**默认人*/
    private String defaultHandlePerson;
    /**时间*/
    private Date handleDate;
    /**结果*/
    private String modelStatus;

    public String getHandlePerson() {
        return handlePerson;
    }

    public void setHandlePerson(String handlePerson) {
        this.handlePerson = handlePerson;
    }

    public String getDefaultHandlePerson() {
        return defaultHandlePerson;
    }

    public void setDefaultHandlePerson(String defaultHandlePerson) {
        this.defaultHandlePerson = defaultHandlePerson;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public String getModelStatus() {
        return modelStatus;
    }

    public void setModelStatus(String modelStatus) {
        this.modelStatus = modelStatus;
    }

    @Override
    public String toString() {
        return "FlowDto{" +
                "handlePerson='" + handlePerson + '\'' +
                ", defaultHandlePerson='" + defaultHandlePerson + '\'' +
                ", handleDate=" + handleDate +
                ", modelStatus='" + modelStatus + '\'' +
                '}';
    }
}
