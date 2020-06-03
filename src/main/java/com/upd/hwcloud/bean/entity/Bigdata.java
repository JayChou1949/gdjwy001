package com.upd.hwcloud.bean.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 大数据库服务目录
 * </p>
 *
 * @author huru
 * @since 2018-12-26
 */
@TableName("TB_BIGDATA")
public class Bigdata extends Model<Bigdata> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    /**
     * 名称
     */
    @Excel(name = "服务名称")
    @TableField("NAME")
    private String name;

    @Excel(name = "发布时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Date modifiedTime;

    /**
     * 状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除
     */
    @TableField("STATUS")
    private Long status;

    /**
     * 创建人
     */
    @TableField("CREATOR")
    private String creator;

    /**
     * 版本号
     */
    @Excel(name = "版本号")
    @TableField("VERSION")
    private String version;

    /**
     * 业务分类
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;

    /**
     * 服务分类
     */
    @TableField("SERVICE_TYPE")
    private String serviceType;

    /**
     * 服务商
     */
    @Excel(name = "服务商")
    @TableField("PROVIDER")
    private String provider;

    /**
     * 标签
     */
    @Excel(name = "标签")
    @TableField("LABEL")
    private String label;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 附件id
     */
    @TableField("FILE_ID")
    private String fileId;
    /**
     * 服务的GUID
     */
    @Excel(name = "服务GUID")
    @TableField("APIG_GUID")
    private String apigGuid;
    /**
     * 服务所在的集群id
     */
    @TableField("APIG_CLUSTER_ID")
    private String apigClusterId;
    /**
     * 挂载时间
     */
    @TableField("UPDATE_AT")
    private Date updateAt;
    /**
     * apiId
     */
    @TableField("APIPRODUCT_ID")
    private String apiproductId;

   @TableField("DOMAIN_ID")
   private String domainId;

   @TableField("PROJECT_ID")
   private String projectId;

    /**
     * 类型,7为daas 其它为paas
     */
    @TableField("CATA_LOG")
    private String cataLog;

    @TableField("SERVICE_MODE")
    private String serviceMode;

    /**
     * 分类
     */
    @Excel(name = "分类")
    @TableField("CATEGORY")
    private String category;

    /**
     * 更新周期
     */
    @Excel(name = "更新周期")
    @TableField("UPDATE_CYCLE")
    private String updateCycle;

    /**
     * 数据资源
     */
    @Excel(name = "数据资源")
    @TableField("DATA_RESOURCE")
    private String dataResource;

    /**
     * 数据来源
     */
    @Excel(name = "数据来源")
    @TableField("DATA_FROM")
    private String dataFrom;

    /**
     * 资源来源系统
     */
    @Excel(name = "资源来源系统")
    @TableField("FROM_SYSTEM")
    private String fromSystem;

    /**
     * 来源网域
     */
    @Excel(name = "来源网域")
    @TableField("FROM_NET")
    private String fromNet;

    /**
     * 采集单位
     */
    @Excel(name = "采集单位")
    @TableField("COLLECTION_UNIT")
    private String collectionUnit;

    /**
     * 说明
     */
    @Excel(name = "说明")
    @TableField("EXPLANATION")
    private String explanation;


    @Excel(name = "API服务")
    @TableField(exist = false)
    private String apiService;

    /**
     * 文件后缀
     */
    @TableField("SUFFIX")
    private String suffix;

    /**
     * 地区
     */
    @TableField("AREA_NAME")
    private String areaName;

    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @TableField(exist = false)
    private boolean canAddShoppingCart = true;


    public String getId() {
        return id;
    }

    public Bigdata setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Bigdata setName(String name) {
        this.name = name;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Bigdata setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public Bigdata setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public Long getStatus() {
        return status;
    }

    public Bigdata setStatus(Long status) {
        this.status = status;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Bigdata setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Bigdata setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getBusinessType() {
        return businessType;
    }

    public Bigdata setBusinessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    public String getServiceType() {
        return serviceType;
    }

    public Bigdata setServiceType(String serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public Bigdata setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Bigdata setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Bigdata setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getFileId() {
        return fileId;
    }

    public Bigdata setFileId(String fileId) {
        this.fileId = fileId;
        return this;
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

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
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

    public String getCataLog() {
        return cataLog;
    }

    public void setCataLog(String cataLog) {
        this.cataLog = cataLog;
    }

    public String getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(String serviceMode) {
        this.serviceMode = serviceMode;
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

    public String getApiService() {
        return "APIMode".equals(serviceMode) ? "是" : "否";
    }

    public void setApiService(String apiService) {
        this.apiService = apiService;
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

    public boolean isCanAddShoppingCart() {
        return canAddShoppingCart;
    }

    public void setCanAddShoppingCart(boolean canAddShoppingCart) {
        this.canAddShoppingCart = canAddShoppingCart;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Bigdata{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", status=" + status +
                ", creator='" + creator + '\'' +
                ", version='" + version + '\'' +
                ", businessType='" + businessType + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", provider='" + provider + '\'' +
                ", label='" + label + '\'' +
                ", remark='" + remark + '\'' +
                ", fileId='" + fileId + '\'' +
                ", apigGuid='" + apigGuid + '\'' +
                ", apigClusterId='" + apigClusterId + '\'' +
                ", updateAt=" + updateAt +
                ", apiproductId='" + apiproductId + '\'' +
                '}';
    }
}
