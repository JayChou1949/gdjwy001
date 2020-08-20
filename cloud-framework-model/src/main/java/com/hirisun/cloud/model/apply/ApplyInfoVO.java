package com.hirisun.cloud.model.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 申请信息表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-08
 */
@Data
@ApiModel(value="ApplyInfo视图对象", description="申请信息表")
public class ApplyInfoVO implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "开发公司")
    private String company;

    @ApiModelProperty(value = "使用人")
    private String userName;

    @ApiModelProperty(value = "使用人电话")
    private String userPhone;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "申请人")
    private String creator;

    @ApiModelProperty(value = "状态 0:购物车 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施未完成")
    private String status;

    @ApiModelProperty(value = "服务类型id")
    private String serviceTypeId;

    @ApiModelProperty(value = "资源类型 1:IAAS 2:DAAS 3:PAAS 4:saas应用 5saas服务")
    private Integer resourceType;

    private Date createTime;

    private Date modifiedTime;

    @ApiModelProperty(value = "申请人姓名")
    private String creatorName;

    @ApiModelProperty(value = "资源类型名称")
    private String serviceTypeName;

    @ApiModelProperty(value = "表单编码(服务编码)")
    private String formNum;

    private String orderNumber;

    private String account;

    @ApiModelProperty(value = "申请说明")
    private String explanation;

    @ApiModelProperty(value = "厂商")
    private String vendor;

    @ApiModelProperty(value = "建设单位")
    private String buildUnit;

    @ApiModelProperty(value = "建设单位负责人")
    private String buildPrincipal;

    @ApiModelProperty(value = "建设单位负责人电话")
    private String buildPrincipalPhone;

    @ApiModelProperty(value = "建设单位经办人")
    private String buildManager;

    @ApiModelProperty(value = "建设单位经办人电话")
    private String buildManagerPhone;

    @ApiModelProperty(value = "承建单位")
    private String contractUnit;

    @ApiModelProperty(value = "承建单位负责人")
    private String contractPrincipal;

    @ApiModelProperty(value = "承建单位负责人电话")
    private String contractPrincipalPhone;

    @ApiModelProperty(value = "承建单位办理人")
    private String contractHandler;

    @ApiModelProperty(value = "承建单位办理人电话")
    private String contractHandlerPhone;

    @ApiModelProperty(value = "承建单位负责人身份证")
    private String contractPrincipalIdcard;

    @ApiModelProperty(value = "警种")
    private String policeCategory;

    private Date applyTime;

    @ApiModelProperty(value = "申请人单位")
    private String creatorUnit;

    @ApiModelProperty(value = "申请人电话")
    private String creatorPhone;

    @ApiModelProperty(value = "统一社会信用代码")
    private String contractOrgCode;

    @ApiModelProperty(value = "承建单位输入类型 0:选择输入 1:手动输入")
    private String contractInputType;

    @ApiModelProperty(value = "建设方式 0:自建 1:第三方建设")
    private String buildMode;

    @ApiModelProperty(value = "是否草稿 0:否 1:是")
    private String draft;

    @ApiModelProperty(value = "应用名称输入类型 0:选择输入 1:手动输入")
    private String appNameInputType;

    @ApiModelProperty(value = "地区")
    private String areaName;

    @ApiModelProperty(value = "警种(华为)")
    private String hwPoliceCategory;

    @ApiModelProperty(value = "集群类型 公安网集群：0   技网集群:1")
    private Integer clusterType;

    @ApiModelProperty(value = "政府机构名称")
    private String govUnit;

    @ApiModelProperty(value = "政府机构代码")
    private String govOrgCode;

    @ApiModelProperty(value = "政府项目负责人")
    private String govPrincipal;

    @ApiModelProperty(value = "政府项目负责人职务")
    private String govPrincipalPostType;

    @ApiModelProperty(value = "政府项目负责人电话")
    private String govPrincipalPhone;

    @ApiModelProperty(value = "政府项目负责人身份证")
    private String govPrincipalIdcard;

    @ApiModelProperty(value = "上报方式 0-地区 1-警种 2-政府机构")
    private String applyType;

    @ApiModelProperty(value = "国家专项")
    private String nationalSpecialProject;

    @ApiModelProperty(value = "服务申请来源 0：由应用申请发起 1：非应用申请发起")
    private String serviceSource;


    @Override
    public String toString() {
        return "ApplyInfo{" +
        "id=" + id +
        ", company=" + company +
        ", userName=" + userName +
        ", userPhone=" + userPhone +
        ", projectName=" + projectName +
        ", appName=" + appName +
        ", remark=" + remark +
        ", creator=" + creator +
        ", status=" + status +
        ", serviceTypeId=" + serviceTypeId +
        ", resourceType=" + resourceType +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", creatorName=" + creatorName +
        ", serviceTypeName=" + serviceTypeName +
        ", formNum=" + formNum +
        ", orderNumber=" + orderNumber +
        ", account=" + account +
        ", explanation=" + explanation +
        ", vendor=" + vendor +
        ", buildUnit=" + buildUnit +
        ", buildPrincipal=" + buildPrincipal +
        ", buildPrincipalPhone=" + buildPrincipalPhone +
        ", buildManager=" + buildManager +
        ", buildManagerPhone=" + buildManagerPhone +
        ", contractUnit=" + contractUnit +
        ", contractPrincipal=" + contractPrincipal +
        ", contractPrincipalPhone=" + contractPrincipalPhone +
        ", contractHandler=" + contractHandler +
        ", contractHandlerPhone=" + contractHandlerPhone +
        ", contractPrincipalIdcard=" + contractPrincipalIdcard +
        ", policeCategory=" + policeCategory +
        ", applyTime=" + applyTime +
        ", creatorUnit=" + creatorUnit +
        ", creatorPhone=" + creatorPhone +
        ", contractOrgCode=" + contractOrgCode +
        ", contractInputType=" + contractInputType +
        ", buildMode=" + buildMode +
        ", draft=" + draft +
        ", appNameInputType=" + appNameInputType +
        ", areaName=" + areaName +
        ", hwPoliceCategory=" + hwPoliceCategory +
        ", clusterType=" + clusterType +
        ", govUnit=" + govUnit +
        ", govOrgCode=" + govOrgCode +
        ", govPrincipal=" + govPrincipal +
        ", govPrincipalPostType=" + govPrincipalPostType +
        ", govPrincipalPhone=" + govPrincipalPhone +
        ", govPrincipalIdcard=" + govPrincipalIdcard +
        ", applyType=" + applyType +
        ", nationalSpecialProject=" + nationalSpecialProject +
        ", serviceSource=" + serviceSource +
        "}";
    }
}
