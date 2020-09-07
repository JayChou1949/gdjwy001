package com.hirisun.cloud.daas.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

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
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFIED_TIME")
    private Date modifiedTime;

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
    private Date updateAt;

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