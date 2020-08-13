package com.hirisun.cloud.model.service.alter.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceAlterVo implements Serializable{

	private static final long serialVersionUID = 2956738365850598990L;

	@ApiModelProperty("发布id")
    private String id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date modifiedTime;

    @ApiModelProperty("申请人")
    private String creator;

    @ApiModelProperty("状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施未完成")
    private String status;

    @ApiModelProperty("申请人姓名")
    private String creatorName;

    @ApiModelProperty("订单号")
    private String orderNumber;

    @ApiModelProperty("服务名称")
    private String serviceName;

    @ApiModelProperty("服务类型 DAAS,PAAS,SAAS")
    private String serviceType;

    @ApiModelProperty("是否发布APIG,1:是 0:否")
    private String isPublishApig;

    @ApiModelProperty("是否来源应用,1:是 0:否")
    private String isFromApp;

    @ApiModelProperty("来源应用名称")
    private String fromAppName;

    @ApiModelProperty("来源应用 ID")
    private String fromAppId;

    @ApiModelProperty("地区")
    private String areaName;

    @ApiModelProperty("警种")
    private String policeCategory;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("优先显示名称")
    private String priorityName;

    @ApiModelProperty("服务商")
    private String vendor;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("服务logo")
    private String logoUrl;

    @ApiModelProperty("标签")
    private String tag;

    @ApiModelProperty("流程id")
    private String workFlowId;

    @ApiModelProperty("流程版本")
    private Integer workFlowVersion;

    @ApiModelProperty("创建人")
    private UserVO user;

    @ApiModelProperty("审核附件")
    private List<FilesVo> fileList;

    @ApiModelProperty("接口文档附件")
    private List<FilesVo> interfaceFileList;

    @ApiModelProperty("开发工具包附件")
    private List<FilesVo> developmentFileList;

    @ApiModelProperty("api产品")
    private ServiceAlterApiProductVo apiProduct;

    @ApiModelProperty("api")
    private List<ServiceAlterApiVo> apiList;
    
    @ApiModelProperty("后端服务")
    private List<ServiceAlterBackendVo> backendList;

    @ApiModelProperty("审核记录(包含实施记录)")
    private List<AppReviewInfoVo> reviewList;

    @ApiModelProperty("实施结果(最后一条)")
    private AppReviewInfoVo impl;

    @ApiModelProperty("是否可审核")
    private boolean canReview = false;

    @ApiModelProperty("是否可以加办")
    private boolean canAdd = false;

    @ApiModelProperty("是否可实施")
    private boolean canImpl = false;

    @ApiModelProperty("是否可删除")
    private boolean canDelete = false;

    @ApiModelProperty("是否可修改")
    private boolean canEdit = false;

    @ApiModelProperty("是否可转发")
    private boolean canTrans = false;

    @ApiModelProperty("是否可回退")
    private boolean canFall = false;

    @ApiModelProperty("是否可")
    private boolean canAdviser;

    @ApiModelProperty("当前环节处理人")
    private String processingPerson;

    @ApiModelProperty("数据来源. 1.页面填写 2.美亚数据")
    private String whereFrom;

    @ApiModelProperty("建设方式 0:自建 1:第三方建设")
    private String buildMode;

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

    @ApiModelProperty("承建单位输入类型 0:选择输入 1:手动输入")
    private String cjInputType;

    @ApiModelProperty("更新周期")
    private String updateCycle;

    @ApiModelProperty("数据资源")
    private String dataResource;

    @ApiModelProperty("数据来源")
    private String dataFrom;

    @ApiModelProperty("资源来源系统")
    private String fromSystem;

    @ApiModelProperty("来源网域")
    private String fromNet;

    @ApiModelProperty("采集单位")
    private String collectionUnit;

    @ApiModelProperty("实例id")
    private String instanceId;


}
