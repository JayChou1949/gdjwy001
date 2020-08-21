package com.hirisun.cloud.model.shopping.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.FlowDetailVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("申请信息表")
@Data
public class ApplicationInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("申请信息表")
    private String id;

    @ApiModelProperty("开发公司")
    private String company;

    @ApiModelProperty("使用人")
    private String userName;

    @ApiModelProperty("使用人电话")
    private String userPhone;

    @ApiModelProperty("申请单号")
    private String orderNumber;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("系统所属警种")
    private String policeCategory;

    @ApiModelProperty("申请人姓名")
    private String creatorName;

    @ApiModelProperty("申请人电话")
    private String creatorPhone;

    @ApiModelProperty("申请时间")
    private Date applicationTime;

    @ApiModelProperty("申请时间")
    private String applicationTimeStr;

    @ApiModelProperty("申请人单位")
    private String creatorUnit;

    @ApiModelProperty("建设方式 0:自建 1:第三方建设")
    private String buildMode;

    @ApiModelProperty("申请说明")
    private String explanation;

    @ApiModelProperty("上报方式")
    private String applyType;

    @ApiModelProperty("建设单位")
    private String jsUnit;

    @ApiModelProperty("建设负责人")
    private String jsPrincipal;

    @ApiModelProperty("建设负责人电话")
    private String jsPrincipalPhone;

    @ApiModelProperty("建设经办人")
    private String jsManager;

    @ApiModelProperty("建设经办人电话")
    private String jsManagerPhone;

    @ApiModelProperty("承建单位")
    private String cjUnit;

    @ApiModelProperty("承建公司组织机构代码")
    private String cjOrgCode;

    @ApiModelProperty("承建负责人")
    private String cjPrincipal;

    @ApiModelProperty("承建负责人电话")
    private String cjPrincipalPhone;

    @ApiModelProperty("承建负责人身份证")
    private String cjPrincipalIdcard;

    @ApiModelProperty("承建办理人")
    private String cjHandler;

    @ApiModelProperty("承建办理人电话")
    private String cjHandlerPhone;

    @ApiModelProperty("政府机构名称")
    private String govUnit;

    @ApiModelProperty("政府机构代码")
    private String govOrgCode;

    @ApiModelProperty("政府项目负责人")
    private String govPrincipal;

    @ApiModelProperty("政府项目负责人职务")
    private String govPrincipalPostType;

    @ApiModelProperty("政府项目负责人电话")
    private String govPrincipalPhone;

    @ApiModelProperty("政府项目负责人身份证")
    private String govPrincipalIdcard;

    @ApiModelProperty("国家专项")
    private String nationalSpecialProject;

    @ApiModelProperty("服务申请来源 0：由应用申请发起 1：非应用申请发起")
    private String serviceSource;

    @ApiModelProperty("承建单位输入类型 0:选择输入 1:手动输入")
    private String cjInputType;

    @ApiModelProperty("应用名称输入类型 0:选择输入 1:手动输入")
    private String appNameInputType;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("申请人")
    private String creator;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("服务类型id")
    private String serviceTypeId;

    @ApiModelProperty("资源类型名称")
    private String serviceTypeName;

    @ApiModelProperty("表单编码(服务编码)")
    private String formNum;

    @ApiModelProperty("资源类型 1:IAAS 2:DAAS 3:PAAS 4:SAAS")
    private Long resourceType;

    @ApiModelProperty("流程 id")
    private String flowStepId;

    @ApiModelProperty("流程 id备份,用于加办,转发")
    private String flowStepIdBak;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("厂商")
    private String vendor;

    @ApiModelProperty("是否草稿 0:否 1:是")
    private String draft;

    @ApiModelProperty("是否新流程")
    private String flowNew;

    @ApiModelProperty("地区")
    private String areaName;

    @ApiModelProperty("是否是地市上报：0:不是 1:是")
    private String areaReport;

    @ApiModelProperty("警种(华为)")
    private String hwPoliceCategory;

    @ApiModelProperty("创建日期")
    private Date createTime;

    @ApiModelProperty("修改日期")
    private Date modifiedTime;

    @ApiModelProperty("大数据集群账号")
    private String clusterAccount;

    @ApiModelProperty("使用资源的虚拟机IP")
    private String vmIp;

    @ApiModelProperty("组件信息(申请)")
    private List<Map<String,Object>> serverList;

    @ApiModelProperty("文件信息")
    private List<FilesVo> fileList;
    
    @ApiModelProperty("服务模板文件")
    private List<FilesVo> tempList;

    @ApiModelProperty("组件总数")
    private Integer totalNum;

    @ApiModelProperty("审核记录(包含实施记录)")
    private List<AppReviewInfoVo> reviewList;
    
    @ApiModelProperty("流程id")
    private String workFlowId;

    @ApiModelProperty("流程版本")
    private Integer flowVersion;
   
    @ApiModelProperty("创建人")
    private UserVO user;

    @ApiModelProperty("是否可审核")
    private boolean canReview = false;

    @ApiModelProperty("是否可以加办")
    private boolean canAdd = false;

    @ApiModelProperty("可以审核该字段不为空")
    private String reviewFlag;

    @ApiModelProperty("是否可实施")
    private boolean canImpl = false;

    @ApiModelProperty("是否可删除")
    private boolean canDelete = false;

    @ApiModelProperty("是否可修改")
    private boolean canEdit = false;

    @ApiModelProperty("是否可反馈")
    private boolean canFeedback = false;
    
    @ApiModelProperty("是否可转发")
    private boolean canTrans = false;

    @ApiModelProperty("是否可回退")
    private boolean canFall = false;
    
    @ApiModelProperty("办理进度  1:申请 2:申请单位审批 3:服务台复核 4:科信审批 5:业务办理 6:反馈")
    private int progressNo = 1;

    @ApiModelProperty("实施服务器信息(详情)")
    private List<Map<String,Object>> implServerList;

    @ApiModelProperty("实施结果(最后一条)")
    private AppReviewInfoVo impl;

    @ApiModelProperty("流程详情")
    private FlowDetailVo flowDetail;

    @ApiModelProperty("当前环节处理人")
    private String processingPerson;

    @ApiModelProperty("服务描述")
    private String description;
    
    @ApiModelProperty("服务申请说明")
    private String instructions;
    
    //前端需要
    private String jsUnitId;
    private String cjUnitId;
    private boolean canAdviser;

    @ApiModelProperty("用于标记实体类型,如果是SaaS应用,值为saas.在填写表单的应用名称时使用")
    private String entityType;

    @ApiModelProperty("大数据办审核时间")
    private Date bigDataAuditTime;

    @ApiModelProperty("业务办理时间")
    private Date businessHandlingTime;

    @ApiModelProperty("申请说明Saas")
    private String explanationSaas;

    @ApiModelProperty("实例id")
    private String instanceId;
    
}
