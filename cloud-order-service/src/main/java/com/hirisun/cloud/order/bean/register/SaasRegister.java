package com.hirisun.cloud.order.bean.register;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * SAAS服务注册表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-02
 */
@Data
@TableName("TB_SAAS_REGISTER")
@ApiModel(value="SaasRegister对象", description="SAAS服务注册表")
public class SaasRegister extends Register<SaasRegister> implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "服务名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "状态 ：1建设中；0 建设完成")
    @TableField("BUILD_STATUS")
    private Integer buildStatus;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("MODIFIED_TIME")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "申请人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "所属分类")
    @TableField("SUB_TYPE")
    private String subType;

    @ApiModelProperty(value = "服务描述")
    @TableField("DESCRIPTION")
    private String description;

    @ApiModelProperty(value = "建设单位")
    @TableField("JS_UNIT")
    private String jsUnit;

    @ApiModelProperty(value = "建设负责人")
    @TableField("JS_PRINCIPAL")
    private String jsPrincipal;

    @ApiModelProperty(value = "建设负责人电话")
    @TableField("JS_PRINCIPAL_PHONE")
    private String jsPrincipalPhone;

    @ApiModelProperty(value = "建设经办人")
    @TableField("JS_MANAGER")
    private String jsManager;

    @ApiModelProperty(value = "建设经办人电话")
    @TableField("JS_MANAGER_PHONE")
    private String jsManagerPhone;

    @ApiModelProperty(value = "承建单位")
    @TableField("CJ_UNIT")
    private String cjUnit;

    @ApiModelProperty(value = "承建负责人")
    @TableField("CJ_PRINCIPAL")
    private String cjPrincipal;

    @ApiModelProperty(value = "承建负责人电话")
    @TableField("CJ_PRINCIPAL_PHONE")
    private String cjPrincipalPhone;

    @ApiModelProperty(value = "承建办理人")
    @TableField("CJ_HANDLER")
    private String cjHandler;

    @ApiModelProperty(value = "承建办理人电话")
    @TableField("CJ_HANDLER_PHONE")
    private String cjHandlerPhone;

    @ApiModelProperty(value = "承建负责人身份证")
    @TableField("CJ_PRINCIPAL_IDCARD")
    private String cjPrincipalIdcard;

    @ApiModelProperty(value = "所属警种")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @ApiModelProperty(value = "承建公司组织机构代码")
    @TableField("CJ_ORG_CODE")
    private String cjOrgCode;

    @ApiModelProperty(value = "承建单位输入类型 0:选择输入 1:手动输入")
    @TableField("CJ_INPUT_TYPE")
    private String cjInputType;

    @ApiModelProperty(value = "建设方式 0:自建 1:第三方建设")
    @TableField("BUILD_MODE")
    private String buildMode;

    @ApiModelProperty(value = "所属地区")
    @TableField("AREA")
    private String area;

    @ApiModelProperty(value = "系统地址")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "是否可申请")
    @TableField("CAN_APPLICATION")
    private String canApplication;

    @ApiModelProperty(value = "服务LOGO")
    @TableField("IMAGE")
    private String image;

    @ApiModelProperty(value = "负责人")
    @TableField("RES_PERSON")
    private String resPerson;

    @ApiModelProperty(value = "负责人单位")
    @TableField("RES_ORG")
    private String resOrg;

    @ApiModelProperty(value = "权限说明")
    @TableField("PERMISSION_INS")
    private String permissionIns;

    @ApiModelProperty(value = "建设周期")
    @TableField("JS_CIRCLE")
    private String jsCircle;

    @ApiModelProperty(value = "生命周期")
    @TableField("SM_CIRCLE")
    private String smCircle;

    @ApiModelProperty(value = "上线时间")
    @TableField("ON_DATE")
    private LocalDateTime onDate;

    @ApiModelProperty(value = "建设负责人身份证")
    @TableField("JS_PRINCIPAL_IDCARD")
    private String jsPrincipalIdcard;

    @ApiModelProperty(value = "申请人姓名")
    @TableField("CREATOR_NAME")
    private String creatorName;

    @ApiModelProperty(value = "状态 0:购物车 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施未完成")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "申请单号")
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    @ApiModelProperty(value = "流程id")
    @TableField("WORK_FLOW_ID")
    private String workFlowId;

    @ApiModelProperty(value = "上报方式--按地区 0；按警种 1")
    @TableField("APPLY_TYPE")
    private String applyType;

    @ApiModelProperty(value = "是否加密应用 0:否 1:是")
    @TableField("SECRET")
    private Integer secret;

    @ApiModelProperty(value = "政府单位名称")
    @TableField("GOV_UNIT")
    private String govUnit;

    @ApiModelProperty(value = "政府单位机构代码")
    @TableField("GOV_ORG_CODE")
    private String govOrgCode;

    @ApiModelProperty(value = "政府单位负责人")
    @TableField("GOV_PRINCIPAL")
    private String govPrincipal;

    @ApiModelProperty(value = "政府单位负责人电话")
    @TableField("GOV_PRINCIPAL_PHONE")
    private String govPrincipalPhone;

    @ApiModelProperty(value = "政府单位负责人身份证")
    @TableField("GOV_PRINCIPAL_IDCARD")
    private String govPrincipalIdcard;

    @ApiModelProperty(value = "政府单位负责人职务")
    @TableField("GOV_PRINCIPAL_POST_TYPE")
    private String govPrincipalPostType;

    @ApiModelProperty(value = "版本")
    @TableField("WORK_FLOW_VERSION")
    private Integer workFlowVersion;

    @ApiModelProperty(value = "项目名称")
    @TableField("PROJECT_NAME")
    private String projectName;

    @ApiModelProperty(value = "服务编码")
    @TableField("SERVICE_CODE")
    private String serviceCode;

    @Override
    public String toString() {
        return "SaasRegister{" +
        "id=" + id +
        ", name=" + name +
        ", buildStatus=" + buildStatus +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", creator=" + creator +
        ", subType=" + subType +
        ", description=" + description +
        ", jsUnit=" + jsUnit +
        ", jsPrincipal=" + jsPrincipal +
        ", jsPrincipalPhone=" + jsPrincipalPhone +
        ", jsManager=" + jsManager +
        ", jsManagerPhone=" + jsManagerPhone +
        ", cjUnit=" + cjUnit +
        ", cjPrincipal=" + cjPrincipal +
        ", cjPrincipalPhone=" + cjPrincipalPhone +
        ", cjHandler=" + cjHandler +
        ", cjHandlerPhone=" + cjHandlerPhone +
        ", cjPrincipalIdcard=" + cjPrincipalIdcard +
        ", policeCategory=" + policeCategory +
        ", cjOrgCode=" + cjOrgCode +
        ", cjInputType=" + cjInputType +
        ", buildMode=" + buildMode +
        ", area=" + area +
        ", url=" + url +
        ", canApplication=" + canApplication +
        ", image=" + image +
        ", resPerson=" + resPerson +
        ", resOrg=" + resOrg +
        ", permissionIns=" + permissionIns +
        ", jsCircle=" + jsCircle +
        ", smCircle=" + smCircle +
        ", onDate=" + onDate +
        ", jsPrincipalIdcard=" + jsPrincipalIdcard +
        ", creatorName=" + creatorName +
        ", status=" + status +
        ", orderNumber=" + orderNumber +
        ", workFlowId=" + workFlowId +
        ", applyType=" + applyType +
        ", secret=" + secret +
        ", govUnit=" + govUnit +
        ", govOrgCode=" + govOrgCode +
        ", govPrincipal=" + govPrincipal +
        ", govPrincipalPhone=" + govPrincipalPhone +
        ", govPrincipalIdcard=" + govPrincipalIdcard +
        ", govPrincipalPostType=" + govPrincipalPostType +
        ", workFlowVersion=" + workFlowVersion +
        ", projectName=" + projectName +
        ", serviceCode=" + serviceCode +
        "}";
    }
}
