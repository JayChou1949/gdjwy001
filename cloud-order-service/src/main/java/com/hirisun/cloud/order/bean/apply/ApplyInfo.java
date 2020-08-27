package com.hirisun.cloud.order.bean.apply;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 申请信息表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-08
 */
@Data
@TableName("T_APPLY_INFO")
@ApiModel(value="ApplyInfo对象", description="申请信息表")
public class ApplyInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "开发公司")
    @TableField("COMPANY")
    private String company;

    @ApiModelProperty(value = "使用人")
    @TableField("USER_NAME")
    private String userName;

    @ApiModelProperty(value = "使用人电话")
    @TableField("USER_PHONE")
    private String userPhone;

    @ApiModelProperty(value = "项目名称")
    @TableField("PROJECT_NAME")
    private String projectName;

    @ApiModelProperty(value = "应用名称")
    @TableField("APP_NAME")
    private String appName;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "申请人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "状态 0:购物车 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施未完成")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "服务类型id")
    @TableField("SERVICE_TYPE_ID")
    private String serviceTypeId;

    @ApiModelProperty(value = "资源类型 1:IAAS 2:DAAS 3:PAAS 4:saas应用 5saas服务")
    @TableField("RESOURCE_TYPE")
    private Integer resourceType;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date modifiedTime;

    @ApiModelProperty(value = "申请人姓名")
    @TableField("CREATOR_NAME")
    private String creatorName;

    @ApiModelProperty(value = "资源类型名称")
    @TableField("SERVICE_TYPE_NAME")
    private String serviceTypeName;

    @ApiModelProperty(value = "表单编码(服务编码)")
    @TableField("FORM_NUM")
    private String formNum;

    @TableField("ORDER_NUMBER")
    private String orderNumber;

    @TableField("ACCOUNT")
    private String account;

    @ApiModelProperty(value = "申请说明")
    @TableField("EXPLANATION")
    private String explanation;

    @ApiModelProperty(value = "厂商")
    @TableField("VENDOR")
    private String vendor;

    @ApiModelProperty(value = "建设单位")
    @TableField("BUILD_UNIT")
    private String buildUnit;

    @ApiModelProperty(value = "建设单位负责人")
    @TableField("BUILD_PRINCIPAL")
    private String buildPrincipal;

    @ApiModelProperty(value = "建设单位负责人电话")
    @TableField("BUILD_PRINCIPAL_PHONE")
    private String buildPrincipalPhone;

    @ApiModelProperty(value = "建设单位经办人")
    @TableField("BUILD_MANAGER")
    private String buildManager;

    @ApiModelProperty(value = "建设单位经办人电话")
    @TableField("BUILD_MANAGER_PHONE")
    private String buildManagerPhone;

    @ApiModelProperty(value = "承建单位")
    @TableField("CONTRACT_UNIT")
    private String contractUnit;

    @ApiModelProperty(value = "承建单位负责人")
    @TableField("CONTRACT_PRINCIPAL")
    private String contractPrincipal;

    @ApiModelProperty(value = "承建单位负责人电话")
    @TableField("CONTRACT_PRINCIPAL_PHONE")
    private String contractPrincipalPhone;

    @ApiModelProperty(value = "承建单位办理人")
    @TableField("CONTRACT_HANDLER")
    private String contractHandler;

    @ApiModelProperty(value = "承建单位办理人电话")
    @TableField("CONTRACT_HANDLER_PHONE")
    private String contractHandlerPhone;

    @ApiModelProperty(value = "承建单位负责人身份证")
    @TableField("CONTRACT_PRINCIPAL_IDCARD")
    private String contractPrincipalIdcard;

    @ApiModelProperty(value = "警种")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @TableField("APPLY_TIME")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date applyTime;

    @ApiModelProperty(value = "申请人单位")
    @TableField("CREATOR_UNIT")
    private String creatorUnit;

    @ApiModelProperty(value = "申请人电话")
    @TableField("CREATOR_PHONE")
    private String creatorPhone;

    @ApiModelProperty(value = "统一社会信用代码")
    @TableField("CONTRACT_ORG_CODE")
    private String contractOrgCode;

    @ApiModelProperty(value = "承建单位输入类型 0:选择输入 1:手动输入")
    @TableField("CONTRACT_INPUT_TYPE")
    private String contractInputType;

    @ApiModelProperty(value = "建设方式 0:自建 1:第三方建设")
    @TableField("BUILD_MODE")
    private String buildMode;

    @ApiModelProperty(value = "是否草稿 0:否 1:是")
    @TableField("DRAFT")
    private String draft;

    @ApiModelProperty(value = "应用名称输入类型 0:选择输入 1:手动输入")
    @TableField("APP_NAME_INPUT_TYPE")
    private String appNameInputType;

    @ApiModelProperty(value = "地区")
    @TableField("AREA_NAME")
    private String areaName;

    @ApiModelProperty(value = "警种(华为)")
    @TableField("HW_POLICE_CATEGORY")
    private String hwPoliceCategory;

    @ApiModelProperty(value = "集群类型 公安网集群：0   技网集群:1")
    @TableField("CLUSTER_TYPE")
    private Integer clusterType;

    @ApiModelProperty(value = "政府机构名称")
    @TableField("GOV_UNIT")
    private String govUnit;

    @ApiModelProperty(value = "政府机构代码")
    @TableField("GOV_ORG_CODE")
    private String govOrgCode;

    @ApiModelProperty(value = "政府项目负责人")
    @TableField("GOV_PRINCIPAL")
    private String govPrincipal;

    @ApiModelProperty(value = "政府项目负责人职务")
    @TableField("GOV_PRINCIPAL_POST_TYPE")
    private String govPrincipalPostType;

    @ApiModelProperty(value = "政府项目负责人电话")
    @TableField("GOV_PRINCIPAL_PHONE")
    private String govPrincipalPhone;

    @ApiModelProperty(value = "政府项目负责人身份证")
    @TableField("GOV_PRINCIPAL_IDCARD")
    private String govPrincipalIdcard;

    @ApiModelProperty(value = "上报方式 0-地区 1-警种 2-政府机构")
    @TableField("APPLY_TYPE")
    private String applyType;

    @ApiModelProperty(value = "国家专项")
    @TableField("NATIONAL_SPECIAL_PROJECT")
    private String nationalSpecialProject;

    @ApiModelProperty(value = "服务申请来源 0：由应用申请发起 1：非应用申请发起")
    @TableField("SERVICE_SOURCE")
    private String serviceSource;

    @ApiModelProperty(value = "大数据集群账号")
    @TableField("CLUSTER_ACCOUNT")
    private String clusterAccount;

    @ApiModelProperty(value = "使用资源的虚拟机IP")
    @TableField("VM_IP")
    private String vmIp;
    
//    @ApiModelProperty(value = "流程 id")
//    @TableField("FLOW_STEP_ID")
//    private String flowStepId;
//
//    @ApiModelProperty(value = "流程 id备份,用于加办,转发")
//    @TableField("FLOW_STEP_ID_BAK")
//    private String flowStepIdBak;
//
//    @ApiModelProperty(value = "是否新流程")
//    @TableField("FLOW_NEW")
//    private String flowNew;
    
    /**
     * 组件信息(申请)
     */
    @TableField(exist = false)
    private List<Map<String,Object>> serverList;

    @TableField(exist = false)
    private List<FilesVo> fileList;
    /**
     * 服务模板文件
     */
    @TableField(exist = false)
    private String tempList;

    /**
     * 组件总数
     */
    @TableField(exist = false)
    private Integer totalNum;

    /**
     * 审核记录(包含实施记录)
     */
//    @TableField(exist = false)
//    private List<AppReviewInfo> reviewList;
    @TableField(exist = false)
    private String workFlowId;

    @TableField(exist = false)
    private Integer flowVersion;
    /**
     * 创建人
     */
    @TableField(exist = false)
    private UserVO user;

    /**
     * 是否可审核
     */
    @TableField(exist = false)
    private boolean canReview = false;

    /**
     * 是否可以加办
     */
    @TableField(exist = false)
    private boolean canAdd = false;

    /**
     * 可以审核该字段不为空
     */
    @TableField(exist = false)
    private String reviewFlag;

    /**
     * 是否可实施
     */
    @TableField(exist = false)
    private boolean canImpl = false;

    /**
     * 是否可删除
     */
    @TableField(exist = false)
    private boolean canDelete = false;

    /**
     * 是否可修改
     */
    @TableField(exist = false)
    private boolean canEdit = false;

    /**
     * 是否可反馈
     */
    @TableField(exist = false)
    private boolean canFeedback = false;
    /**
     * 是否可转发
     */
    @TableField(exist = false)
    private boolean canTrans = false;

    /**
     * 是否可回退
     */
    @TableField(exist = false)
    private boolean canFall = false;
    /**
     * 办理进度  1:申请 2:申请单位审批 3:服务台复核 4:科信审批 5:业务办理 6:反馈
     */
    @TableField(exist = false)
    private int progressNo = 1;

    /**
     * 实施服务器信息(详情)
     */
    @TableField(exist = false)
    private List<Map<String,Object>> implServerList;

    /**
     * 实施结果(最后一条)
     */
//    @TableField(exist = false)
//    private AppReviewInfo impl;

    /**
     * 流程详情
     */
//    @TableField(exist = false)
//    private FlowDetail flowDetail;

    /**
     * 当前环节处理人
     */
    @TableField(exist = false)
    private String processingPerson;

    /**
     * 服务描述
     */
    @TableField(exist = false)
    private String description;
    /**
     * 服务申请说明
     */
    @TableField(exist = false)
    private String instructions;
    //前端需要
    @TableField(exist = false)
    private String jsUnitId;
    @TableField(exist = false)
    private String cjUnitId;
    @TableField(exist = false)
    private boolean canAdviser;

    /**
     * 用于标记实体类型,如果是SaaS应用,值为"saas".
     * 在填写表单的应用名称时使用
     */
    @TableField(exist = false)
    private String entityType;

    /**
     * 大数据办审核时间
     */
    @TableField(exist = false)
    private Date bigDataAuditTime;

    /**
     * 业务办理时间
     */
    @TableField(exist = false)
    private Date businessHandlingTime;

    /**
     * 申请说明Saas
     */
    @TableField(exist = false)
    private String explanationSaas;

    @TableField(exist = false)
    private String instanceId;


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
