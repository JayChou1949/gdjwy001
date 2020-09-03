package com.hirisun.cloud.order.bean.servicePublish;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.common.WorkOrder;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.ncov.vo.file.FileVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 服务发布
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Data
@TableName("TB_SERVICE_PUBLISH")
@ApiModel(value="ServicePublish对象", description="服务发布")
public class ServicePublish extends WorkOrder implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("ID")
    private String id;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFIED_TIME")
    private Date modifiedTime;

    @ApiModelProperty(value = "申请人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "状态 0:购物车 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施未完成")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "申请人姓名")
    @TableField("CREATOR_NAME")
    private String creatorName;

    @TableField("ORDER_NUMBER")
    private String orderNumber;

    @ApiModelProperty(value = "服务名称")
    @TableField("SERVICE_NAME")
    private String serviceName;

    @ApiModelProperty(value = "服务类型")
    @TableField("SERVICE_TYPE")
    private String serviceType;

    @ApiModelProperty(value = "是否发布APIG,1:是 0:否")
    @TableField("IS_PUBLISH_APIG")
    private String isPublishApig;

    @ApiModelProperty(value = "是否来源应用,1:是 0:否")
    @TableField("IS_FROM_APP")
    private String isFromApp;

    @ApiModelProperty(value = "来源应用名称")
    @TableField("FROM_APP_NAME")
    private String fromAppName;

    @ApiModelProperty(value = "来源应用 ID")
    @TableField("FROM_APP_ID")
    private String fromAppId;

    @ApiModelProperty(value = "地区")
    @TableField("AREA_NAME")
    private String areaName;

    @ApiModelProperty(value = "警种")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @ApiModelProperty(value = "分类")
    @TableField("CATEGORY")
    private String category;

    @ApiModelProperty(value = "优先显示名称")
    @TableField("PRIORITY_NAME")
    private String priorityName;

    @ApiModelProperty(value = "服务商")
    @TableField("VENDOR")
    private String vendor;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "服务logo")
    @TableField("LOGO_URL")
    private String logoUrl;

    @ApiModelProperty(value = "流程id")
    @TableField("WORK_FLOW_ID")
    private String workFlowId;

    @ApiModelProperty(value = "标签")
    @TableField("TAG")
    private String tag;

    @ApiModelProperty(value = "数据来源. 1.页面填写;2.来自美亚")
    @TableField("WHERE_FROM")
    private String whereFrom;

    @ApiModelProperty(value = "建设单位")
    @TableField("JS_UNIT")
    private String jsUnit;

    @ApiModelProperty(value = "建设单位负责人")
    @TableField("JS_PRINCIPAL")
    private String jsPrincipal;

    @ApiModelProperty(value = "建设单位负责人电话")
    @TableField("JS_PRINCIPAL_PHONE")
    private String jsPrincipalPhone;

    @ApiModelProperty(value = "建设单位经办人")
    @TableField("JS_MANAGER")
    private String jsManager;

    @ApiModelProperty(value = "建设单位经办人电话")
    @TableField("JS_MANAGER_PHONE")
    private String jsManagerPhone;

    @ApiModelProperty(value = "承建单位")
    @TableField("CJ_UNIT")
    private String cjUnit;

    @ApiModelProperty(value = "承建单位负责人")
    @TableField("CJ_PRINCIPAL")
    private String cjPrincipal;

    @ApiModelProperty(value = "承建单位负责人电话")
    @TableField("CJ_PRINCIPAL_PHONE")
    private String cjPrincipalPhone;

    @ApiModelProperty(value = "承建单位办理人")
    @TableField("CJ_HANDLER")
    private String cjHandler;

    @ApiModelProperty(value = "承建单位办理人电话")
    @TableField("CJ_HANDLER_PHONE")
    private String cjHandlerPhone;

    @ApiModelProperty(value = "承建单位负责人身份证")
    @TableField("CJ_PRINCIPAL_IDCARD")
    private String cjPrincipalIdcard;

    @ApiModelProperty(value = "统一社会信用代码")
    @TableField("CJ_ORG_CODE")
    private String cjOrgCode;

    @ApiModelProperty(value = "承建单位输入类型 0:选择输入 1:手动输入")
    @TableField("CJ_INPUT_TYPE")
    private String cjInputType;

    @ApiModelProperty(value = "建设方式 0:自建 1:第三方建设")
    @TableField("BUILD_MODE")
    private String buildMode;

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

    @ApiModelProperty(value = "版本")
    @TableField("WORK_FLOW_VERSION")
    private Integer workFlowVersion;

    @TableField("REGION_ID")
    private Integer regionId;

    @ApiModelProperty(value = "服务编码")
    @TableField("SERVICE_CODE")
    private String serviceCode;

    @ApiModelProperty(value = "流程实例id")
    @TableField(exist = false)
    private String instanceId;

    @ApiModelProperty(value = "创建人")
    @TableField(exist = false)
    private UserVO user;

    @ApiModelProperty(value = "审核附件")
    @TableField(exist = false)
    private List<FilesVo> fileList;

    @ApiModelProperty(value = "是否能删除")
    @TableField(exist = false)
    private boolean canDelete = false;

    /**
     * 接口文档附件
     */
    @TableField(exist = false)
    private List<FilesVo> interfaceFileList;

    /**
     * 开发工具包附件
     */
    @TableField(exist = false)
    private List<FilesVo> developmentFileList;

    /**
     * api产品
     */
    @TableField(exist = false)
    private ServicePublishApiProduct apiProduct;

    /**
     * api
     */
    @TableField(exist = false)
    private List<ServicePublishApi> apiList;

    /**
     * 后端服务
     */
    @TableField(exist = false)
    private List<ServicePublishBackend> backendList;

    /**
     * 审核记录(包含实施记录)
     */
    @TableField(exist = false)
    private List<ApplyReviewRecordVO> reviewList;

    /**
     * 实施结果(最后一条)
     */
    @TableField(exist = false)
    private ApplyReviewRecordVO impl;

    /**
     * 当前环节处理人
     */
    @TableField(exist = false)
    private String processingPerson;


    @Override
    public String toString() {
        return "ServicePublish{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", creator=" + creator +
        ", status=" + status +
        ", creatorName=" + creatorName +
        ", orderNumber=" + orderNumber +
        ", serviceName=" + serviceName +
        ", serviceType=" + serviceType +
        ", isPublishApig=" + isPublishApig +
        ", isFromApp=" + isFromApp +
        ", fromAppName=" + fromAppName +
        ", fromAppId=" + fromAppId +
        ", areaName=" + areaName +
        ", policeCategory=" + policeCategory +
        ", category=" + category +
        ", priorityName=" + priorityName +
        ", vendor=" + vendor +
        ", remark=" + remark +
        ", logoUrl=" + logoUrl +
        ", workFlowId=" + workFlowId +
        ", tag=" + tag +
        ", whereFrom=" + whereFrom +
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
        ", cjOrgCode=" + cjOrgCode +
        ", cjInputType=" + cjInputType +
        ", buildMode=" + buildMode +
        ", updateCycle=" + updateCycle +
        ", dataResource=" + dataResource +
        ", dataFrom=" + dataFrom +
        ", fromSystem=" + fromSystem +
        ", fromNet=" + fromNet +
        ", collectionUnit=" + collectionUnit +
        ", workFlowVersion=" + workFlowVersion +
        ", regionId=" + regionId +
        ", serviceCode=" + serviceCode +
        "}";
    }
}
