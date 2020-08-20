package com.hirisun.cloud.model.export.vo;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("paas-daas-iaas 应用导出")
public class PassDaasIaasApplicationExportVo {


    @Excel(name = "序号")
    @ApiModelProperty("序号")
    private Integer id;

    @Excel(name = "工单号")
    @ApiModelProperty("工单号")
    private String orderNumber;

    @Excel(name = "申请人")
    @ApiModelProperty("申请人")
    private String creatorName;

    @Excel(name = "服务类型")
    @ApiModelProperty("服务类型")
    private String resourceType;

    @Excel(name = "资源类型")
    @ApiModelProperty("资源类型")
    private String serviceTypeName;

    @Excel(name = "申请人单位")
    @ApiModelProperty("申请人单位")
    private String orgName;

    @Excel(name = "项目名称")
    @ApiModelProperty("项目名称")
    private String projectName;

    @Excel(name = "应用名称")
    @ApiModelProperty("应用名称")
    private String appName;

    @Excel(name = "当前处理环节")
    @ApiModelProperty("当前处理环节")
    private String node;

    @Excel(name = "服务台复核人员")
    @ApiModelProperty("服务台复核人员")
    private String recheckPerson;

    @Excel(name = "服务台复核时间",exportFormat = "yyy-MM-dd HH:mm:ss")
    @ApiModelProperty("服务台复核时间")
    private Date recheckTime;

    @Excel(name = "服务台复核结果")
    @ApiModelProperty("服务台复核结果")
    private String recheckResult;

    @Excel(name = "服务台复核意见")
    @ApiModelProperty("服务台复核意见")
    private String recheckOpnion;

    @Excel(name = "大数据办审核人员")
    @ApiModelProperty("大数据办审核人员")
    private String bigdataPerson;

    @Excel(name = "大数据办复核时间",exportFormat = "yyy-MM-dd HH:mm:ss")
    @ApiModelProperty("大数据办复核时间")
    private Date bigdataTime;

    @Excel(name = "大数据办审批结果")
    @ApiModelProperty("大数据办审批结果")
    private String bigdataResult;

    @Excel(name = "大数据办复核意见")
    @ApiModelProperty("大数据办复核意见")
    private String bigdataOpnion;

    @Excel(name = "业务办理时间",exportFormat = "yyy-MM-dd HH:mm:ss")
    @ApiModelProperty("业务办理时间")
    private Date carrayTime;

    @Excel(name = "业务办理结果")
    @ApiModelProperty("业务办理结果")
    private String carrayResult;

    @Excel(name = "业务办理人")
    @ApiModelProperty("业务办理人")
    private String carrayPerson;

    private String haderPerson;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
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

    public String getBigdataPerson() {
        return bigdataPerson;
    }

    public void setBigdataPerson(String bigdataPerson) {
        this.bigdataPerson = bigdataPerson;
    }

    public Date getBigdataTime() {
        return bigdataTime;
    }

    public void setBigdataTime(Date bigdataTime) {
        this.bigdataTime = bigdataTime;
    }

    public String getBigdataResult() {
        return bigdataResult;
    }

    public void setBigdataResult(String bigdataResult) {
        this.bigdataResult = bigdataResult;
    }

    public String getBigdataOpnion() {
        return bigdataOpnion;
    }

    public void setBigdataOpnion(String bigdataOpnion) {
        this.bigdataOpnion = bigdataOpnion;
    }

    public Date getCarrayTime() {
        return carrayTime;
    }

    public void setCarrayTime(Date carrayTime) {
        this.carrayTime = carrayTime;
    }

    public String getCarrayResult() {
        return carrayResult;
    }

    public void setCarrayResult(String carrayResult) {
        this.carrayResult = carrayResult;
    }

    public String getCarrayPerson() {
        return carrayPerson;
    }

    public void setCarrayPerson(String carrayPerson) {
        this.carrayPerson = carrayPerson;
    }

    public String getHaderPerson() {
        return haderPerson;
    }

    public void setHaderPerson(String haderPerson) {
        this.haderPerson = haderPerson;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
