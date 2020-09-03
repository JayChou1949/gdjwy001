package com.hirisun.cloud.order.bean.servicePublish;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.ncov.vo.file.FileVo;
import com.hirisun.cloud.model.user.UserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Data
@TableName("TB_SERVICE_ALTER")
@ApiModel(value="ServiceAlter对象", description="")
public class ServiceAlter implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFIED_TIME")
    private Date modifiedTime;

    @TableField("CREATOR")
    private String creator;

    @TableField("STATUS")
    private String status;

    @TableField("CREATOR_NAME")
    private String creatorName;

    @TableField("ORDER_NUMBER")
    private String orderNumber;

    @TableField("SERVICE_NAME")
    private String serviceName;

    @TableField("SERVICE_TYPE")
    private String serviceType;

    @TableField("IS_PUBLISH_APIG")
    private String isPublishApig;

    @TableField("IS_FROM_APP")
    private String isFromApp;

    @TableField("FROM_APP_NAME")
    private String fromAppName;

    @TableField("FROM_APP_ID")
    private String fromAppId;

    @TableField("AREA_NAME")
    private String areaName;

    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @TableField("CATEGORY")
    private String category;

    @TableField("PRIORITY_NAME")
    private String priorityName;

    @TableField("VENDOR")
    private String vendor;

    @TableField("REMARK")
    private String remark;

    @TableField("LOGO_URL")
    private String logoUrl;

    @TableField("WORK_FLOW_ID")
    private String workFlowId;

    @TableField("TAG")
    private String tag;

    @TableField("WHERE_FROM")
    private String whereFrom;

    @TableField("JS_UNIT")
    private String jsUnit;

    @TableField("JS_PRINCIPAL")
    private String jsPrincipal;

    @TableField("JS_PRINCIPAL_PHONE")
    private String jsPrincipalPhone;

    @TableField("JS_MANAGER")
    private String jsManager;

    @TableField("JS_MANAGER_PHONE")
    private String jsManagerPhone;

    @TableField("CJ_UNIT")
    private String cjUnit;

    @TableField("CJ_PRINCIPAL")
    private String cjPrincipal;

    @TableField("CJ_PRINCIPAL_PHONE")
    private String cjPrincipalPhone;

    @TableField("CJ_HANDLER")
    private String cjHandler;

    @TableField("CJ_HANDLER_PHONE")
    private String cjHandlerPhone;

    @TableField("CJ_PRINCIPAL_IDCARD")
    private String cjPrincipalIdcard;

    @TableField("CJ_ORG_CODE")
    private String cjOrgCode;

    @TableField("CJ_INPUT_TYPE")
    private String cjInputType;

    @TableField("BUILD_MODE")
    private String buildMode;

    @TableField("UPDATE_CYCLE")
    private String updateCycle;

    @TableField("DATA_RESOURCE")
    private String dataResource;

    @TableField("DATA_FROM")
    private String dataFrom;

    @TableField("FROM_SYSTEM")
    private String fromSystem;

    @TableField("FROM_NET")
    private String fromNet;

    @TableField("COLLECTION_UNIT")
    private String collectionUnit;

    @TableField("WORK_FLOW_VERSION")
    private Integer workFlowVersion;

    @TableField("REGION_ID")
    private String regionId;

    @TableField("API_PRODUCT_ID")
    private String apiProductId;

    /**
     * 创建人
     */
    @TableField(exist = false)
    private UserVO user;

    /**
     * 审核附件
     */
    @TableField(exist = false)
    private List<FilesVo> fileList;

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
    private ServiceAlterApiProduct apiProduct;

    /**
     * api
     */
    @TableField(exist = false)
    @ApiModelProperty(value="api list")
    private List<ServiceAlterApi> apiList;

    /**
     * 后端服务
     */
    @TableField(exist = false)
    @ApiModelProperty(value="backend list")
    private List<ServiceAlterBackend> backendList;

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

    @TableField(exist = false)
    private String instanceId;

    @TableField(exist = false)
    @ApiModelProperty(value="发布人")
    private String publisher;

    @TableField(exist = false)
    @ApiModelProperty(value="发布人时间")
    private String publishDate;

    @TableField(exist = false)
    @ApiModelProperty(value="流程版本")
    private String version;

    @Override
    public String toString() {
        return "ServiceAlter{" +
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
        ", apiProductId=" + apiProductId +
        "}";
    }
}
