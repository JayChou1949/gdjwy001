package com.hirisun.cloud.order.bean.register;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务注册表
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */
@Data
public class Register<T extends Register> extends Model<Register<T>> {

    @TableId(value = "ID",type = IdType.ASSIGN_UUID)
    protected String id;

        /**
     * 服务名称
     */
         @TableField("NAME")
         @ApiModelProperty(value = "服务名称")
    protected String name;

    /**
     * 项目名称
     */
    @TableField("PROJECT_NAME")
    @ApiModelProperty(value = "项目名称")
    protected String projectName;


        /**
     * 状态 ：1建设中；0 建设完成
     */
         @TableField("BUILD_STATUS")
         @ApiModelProperty(value = "服务状态")
    protected Long buildStatus;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    protected Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    protected Date modifiedTime;

        /**
     * 申请人
     */
         @TableField("CREATOR")
    protected String creator;
    /**
     * 流程id
     */
    @TableField("WORK_FLOW_ID")
    protected String workFlowId;

    /**
     * 流程版本
     */
    @TableField("WORK_FLOW_VERSION")
    protected Integer workFlowVersion;

    /**
     * 状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回
     */
    @TableField("STATUS")
    protected String status;
    /**
     * 申请人姓名
     */
    @TableField("CREATOR_NAME")
    protected String creatorName;

    /**
     * 申请单号
     */
    @TableField("ORDER_NUMBER")
    protected String orderNumber;
        /**
     * 所属分类
     */
         @TableField("SUB_TYPE")
         @ApiModelProperty(value = "所属分类")
    protected String subType;

        /**
     * 服务描述
     */ @ApiModelProperty(value = "服务描述")
         @TableField("DESCRIPTION")
    protected String description;

        /**
     * 建设单位
     */
         @TableField("JS_UNIT")
    protected String jsUnit;

        /**
     * 建设负责人
     */
         @TableField("JS_PRINCIPAL")
    protected String jsPrincipal;

        /**
     * 建设负责人电话
     */
         @TableField("JS_PRINCIPAL_PHONE")
    protected String jsPrincipalPhone;

        /**
     * 建设经办人
     */
         @TableField("JS_MANAGER")
    protected String jsManager;

        /**
     * 建设经办人电话
     */
         @TableField("JS_MANAGER_PHONE")
    protected String jsManagerPhone;

        /**
     * 承建单位
     */
         @TableField("CJ_UNIT")
    protected String cjUnit;

        /**
     * 承建负责人
     */
         @TableField("CJ_PRINCIPAL")
    protected String cjPrincipal;

        /**
     * 承建负责人电话
     */
         @TableField("CJ_PRINCIPAL_PHONE")
    protected String cjPrincipalPhone;

        /**
     * 承建办理人
     */
         @TableField("CJ_HANDLER")
    protected String cjHandler;

        /**
     * 承建办理人电话
     */
         @TableField("CJ_HANDLER_PHONE")
    protected String cjHandlerPhone;

        /**
     * 承建负责人身份证
     */
         @TableField("CJ_PRINCIPAL_IDCARD")
    protected String cjPrincipalIdcard;

        /**
     * 所属警种
     */ @ApiModelProperty(value = "所属警种")
         @TableField("POLICE_CATEGORY")
    protected String policeCategory;

        /**
     * 承建公司组织机构代码
     */
         @TableField("CJ_ORG_CODE")
    protected String cjOrgCode;

        /**
     * 承建单位输入类型 0:选择输入 1:手动输入
     */
         @TableField("CJ_INPUT_TYPE")
    protected String cjInputType;

        /**
     * 建设方式 0:自建 1:第三方建设
     */ @ApiModelProperty(value = "建设方式")
         @TableField("BUILD_MODE")
    protected String buildMode;

        /**
     * 所属地区
     */ @ApiModelProperty(value = "所属地区")
         @TableField("AREA")
    protected String area;

        /**
     * 系统地址
     */ @ApiModelProperty(value = "系统地址")
         @TableField("URL")
    protected String url;

        /**
     * 是否可申请
     */ @ApiModelProperty(value = "是否可申请")
         @TableField("CAN_APPLICATION")
    protected String canApplication;

        /**
     * 服务LOGO
     */ @ApiModelProperty(value = "服务LOGO")
         @TableField("IMAGE")
    protected String image;

        /**
     * 负责人
     */
         @TableField("RES_PERSON")
    protected String resPerson;

        /**
     * 负责人单位
     */
         @TableField("RES_ORG")
    protected String resOrg;

        /**
     * 权限说明
     */ @ApiModelProperty(value = "权限说明")
         @TableField("PERMISSION_INS")
    protected String permissionIns;

        /**
     * 建设周期
     */ @ApiModelProperty(value = "建设周期")
         @TableField("JS_CIRCLE")
    protected String jsCircle;

        /**
     * 生命周期
     */ @ApiModelProperty(value = "生命周期")
         @TableField("SM_CIRCLE")
    protected String smCircle;

        /**
     * 上线时间
     */ @ApiModelProperty(value = "上线时间")
         @TableField("ON_DATE")
    protected Date onDate;
    /**
     * 上报方式
     */ @ApiModelProperty(value = "上报方式 0-地区 1-警种 2-政府机构")
    @TableField("APPLY_TYPE")
     protected String applyType;
        /**
     * 建设负责人身份证
     */
         @TableField("JS_PRINCIPAL_IDCARD")
    protected String jsPrincipalIdcard;

    /**
     * 服务编码
     */
    @TableField("SERVICE_CODE")
    private String serviceCode;

    @TableField(exist = false)
    protected List<FilesVo> fileList;
    /**
     * 审核记录(包含实施记录)
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected List<ApplyReviewRecord> reviewList;
    /**
     * 创建人
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected UserVO user;

    /**
     * 是否可审核
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canReview = false;

    /**
     * 是否可以加办
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canAdd = false;

    /**
     * 是否可删除
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canDelete = false;

    /**
     * 是否可修改
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canEdit = false;

    /**
     * 是否可转发
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canTrans = false;

    /**
     * 是否可回退
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canFall = false;
    /**
     * 当前环节处理人
     */ @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected String processingPerson;
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    protected boolean canAdviser;

    @TableField(exist = false)
    protected String instanceId;


    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getId() {
        return id;
    }

    public Register setId(String id) {
        this.id = id;
        return this;
    }


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
        "}";
    }
}
