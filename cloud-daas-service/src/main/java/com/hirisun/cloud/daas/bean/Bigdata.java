package com.hirisun.cloud.daas.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 大数据库服务目录
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-02
 */
@TableName("TB_BIGDATA")
@ApiModel(value="Bigdata对象", description="大数据库服务目录")
public class Bigdata implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFIED_TIME")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除")
    @TableField("STATUS")
    private Long status;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "版本号")
    @TableField("VERSION")
    private String version;

    @ApiModelProperty(value = "业务分类")
    @TableField("BUSINESS_TYPE")
    private String businessType;

    @ApiModelProperty(value = "服务分类")
    @TableField("SERVICE_TYPE")
    private String serviceType;

    @ApiModelProperty(value = "服务商")
    @TableField("PROVIDER")
    private String provider;

    @ApiModelProperty(value = "标签")
    @TableField("LABEL")
    private String label;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "附件id")
    @TableField("FILE_ID")
    private String fileId;

    @ApiModelProperty(value = "服务的guid")
    @TableField("APIG_GUID")
    private String apigGuid;

    @ApiModelProperty(value = "APIG集群ID")
    @TableField("APIG_CLUSTER_ID")
    private String apigClusterId;

    @ApiModelProperty(value = "服务更新时间")
    @TableField("UPDATE_AT")
    private LocalDateTime updateAt;

    @ApiModelProperty(value = "API产品ID")
    @TableField("APIPRODUCT_ID")
    private String apiproductId;

    @ApiModelProperty(value = "租户ID")
    @TableField("DOMAIN_ID")
    private String domainId;

    @ApiModelProperty(value = "项目ID")
    @TableField("PROJECT_ID")
    private String projectId;

    @TableField("CATA_LOG")
    private Integer cataLog;

    @ApiModelProperty(value = "更新周期")
    @TableField("UPDATE_CYCLE")
    private String updateCycle;

    @ApiModelProperty(value = "数据资源")
    @TableField("DATA_RESOURCE")
    private String dataResource;

    @ApiModelProperty(value = "数据来源")
    @TableField("DATA_FROM")
    private String dataFrom;

    @ApiModelProperty(value = "资源来源系统")
    @TableField("FROM_SYSTEM")
    private String fromSystem;

    @ApiModelProperty(value = "来源网域")
    @TableField("FROM_NET")
    private String fromNet;

    @ApiModelProperty(value = "采集单位")
    @TableField("COLLECTION_UNIT")
    private String collectionUnit;

    @ApiModelProperty(value = "说明")
    @TableField("EXPLANATION")
    private String explanation;

    @ApiModelProperty(value = "分类")
    @TableField("CATEGORY")
    private String category;

    @TableField("SERVICE_MODE")
    private String serviceMode;

    @ApiModelProperty(value = "地区")
    @TableField("AREA_NAME")
    private String areaName;

    @ApiModelProperty(value = "系统所属警种")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @TableField("SUFFIX")
    private String suffix;

    @ApiModelProperty(value = "来源地区名称")
    @TableField("CITY_CODE_NAME")
    private String cityCodeName;

    @ApiModelProperty(value = "资源状态名称")
    @TableField("RESOURCE_STATUS_NAME")
    private String resourceStatusName;

    @ApiModelProperty(value = "DAAS服务类型 1：数据服务 2：数据资源")
    @TableField("DATA_TYPE")
    private String dataType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getApigGuid() {
        return apigGuid;
    }

    public void setApigGuid(String apigGuid) {
        this.apigGuid = apigGuid;
    }

    public String getApigClusterId() {
        return apigClusterId;
    }

    public void setApigClusterId(String apigClusterId) {
        this.apigClusterId = apigClusterId;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getApiproductId() {
        return apiproductId;
    }

    public void setApiproductId(String apiproductId) {
        this.apiproductId = apiproductId;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getCataLog() {
        return cataLog;
    }

    public void setCataLog(Integer cataLog) {
        this.cataLog = cataLog;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(String serviceMode) {
        this.serviceMode = serviceMode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getCityCodeName() {
        return cityCodeName;
    }

    public void setCityCodeName(String cityCodeName) {
        this.cityCodeName = cityCodeName;
    }

    public String getResourceStatusName() {
        return resourceStatusName;
    }

    public void setResourceStatusName(String resourceStatusName) {
        this.resourceStatusName = resourceStatusName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "Bigdata{" +
        "id=" + id +
        ", name=" + name +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", status=" + status +
        ", creator=" + creator +
        ", version=" + version +
        ", businessType=" + businessType +
        ", serviceType=" + serviceType +
        ", provider=" + provider +
        ", label=" + label +
        ", remark=" + remark +
        ", fileId=" + fileId +
        ", apigGuid=" + apigGuid +
        ", apigClusterId=" + apigClusterId +
        ", updateAt=" + updateAt +
        ", apiproductId=" + apiproductId +
        ", domainId=" + domainId +
        ", projectId=" + projectId +
        ", cataLog=" + cataLog +
        ", updateCycle=" + updateCycle +
        ", dataResource=" + dataResource +
        ", dataFrom=" + dataFrom +
        ", fromSystem=" + fromSystem +
        ", fromNet=" + fromNet +
        ", collectionUnit=" + collectionUnit +
        ", explanation=" + explanation +
        ", category=" + category +
        ", serviceMode=" + serviceMode +
        ", areaName=" + areaName +
        ", policeCategory=" + policeCategory +
        ", suffix=" + suffix +
        ", cityCodeName=" + cityCodeName +
        ", resourceStatusName=" + resourceStatusName +
        ", dataType=" + dataType +
        "}";
    }
}
