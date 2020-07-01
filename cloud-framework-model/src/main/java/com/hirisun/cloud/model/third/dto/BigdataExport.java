package com.hirisun.cloud.model.third.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

/**
 * <p>
 * 大数据库服务目录
 * </p>
 *
 * @author huru
 * @since 2018-12-26
 */
public class BigdataExport {

    /**
     * 名称
     */
    @Excel(name = "服务名称")
    private String name;

    @Excel(name = "发布时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除
     */
    private Long status;

    /**
     * 版本号
     */
    @Excel(name = "版本号")
    private String version;

    /**
     * 服务商
     */
    @Excel(name = "服务商")
    private String provider;

    /**
     * 标签
     */
    @Excel(name = "标签")
    private String label;
    /**
     * 服务的GUID
     */
    @Excel(name = "服务GUID")
    private String apigGuid;

    /**
     * 分类
     */
    @Excel(name = "分类")
    private String category;

    /**
     * 更新周期
     */
    @Excel(name = "更新周期")
    private String updateCycle;

    /**
     * 数据资源
     */
    @Excel(name = "数据资源")
    private String dataResource;

    /**
     * 数据来源
     */
    @Excel(name = "数据来源")
    private String dataFrom;

    /**
     * 资源来源系统
     */
    @Excel(name = "资源来源系统")
    private String fromSystem;

    /**
     * 来源网域
     */
    @Excel(name = "来源网域")
    private String fromNet;

    /**
     * 采集单位
     */
    @Excel(name = "采集单位")
    private String collectionUnit;

    /**
     * 说明
     */
    @Excel(name = "说明")
    private String explanation;

    @Excel(name = "API服务")
    private String serviceMode;

    @Excel(name = "服务状态")
    private String statusName;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getStatusName() {
        if (status==0L){
            return "审核";
        }else if (status==1L){
            return "待上线";
        }else if (status==2L){
            return "上线";
        }else if (status==3L){
            return "驳回";
        }else if (status==4L){
            return "删除";
        }else {
            return "未知";
        }
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getServiceMode() {
        return "APIMode".equals(serviceMode) ? "是" : "否";
    }

    public void setServiceMode(String apiService) {
        this.serviceMode = serviceMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getApigGuid() {
        return apigGuid;
    }

    public void setApigGuid(String apigGuid) {
        this.apigGuid = apigGuid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUpdateCycle() {
        return updateCycle;
    }

    public void setUpdateCycle(String updateCycle) {
        this.updateCycle = updateCycle;
    }

    public String getDataResource() {
        return dataResource;
    }

    public void setDataResource(String dataResource) {
        this.dataResource = dataResource;
    }

    public String getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom;
    }

    public String getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(String fromSystem) {
        this.fromSystem = fromSystem;
    }

    public String getFromNet() {
        return fromNet;
    }

    public void setFromNet(String fromNet) {
        this.fromNet = fromNet;
    }

    public String getCollectionUnit() {
        return collectionUnit;
    }

    public void setCollectionUnit(String collectionUnit) {
        this.collectionUnit = collectionUnit;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }


}
