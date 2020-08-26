package com.hirisun.cloud.saas.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * SaaS资源申请原始信息表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-24
 */
@Data
@TableName("T_SAAS_APPLY")
@ApiModel(value="SaasApply对象", description="SaaS资源申请原始信息表")
public class SaasApply implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "申请人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "申请人姓名")
    @TableField("CREATOR_NAME")
    private String creatorName;

    @ApiModelProperty(value = "单位id")
    @TableField("ORG_ID")
    private String orgId;

    @ApiModelProperty(value = "单位名称")
    @TableField("ORG_NAME")
    private String orgName;

    @ApiModelProperty(value = "职务")
    @TableField("POST_TYPE")
    private String postType;

    @ApiModelProperty(value = "手机号")
    @TableField("MOBILE_WORK")
    private String mobileWork;

    @ApiModelProperty(value = "申请时间")
    @TableField("APPLICATION_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applicationTime;

    @ApiModelProperty(value = "上报方式--按地区 0；按警种 1")
    @TableField("APPLY_TYPE")
    private String applyType;

    @ApiModelProperty(value = "地区")
    @TableField("AREAS")
    private String areas;

    @ApiModelProperty(value = "系统所属警种")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @ApiModelProperty(value = "申请说明")
    @TableField("EXPLANATION")
    private String explanation;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @ApiModelProperty(value = "申请单号")
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    @ApiModelProperty(value = "状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 ")
    @TableField("STATUS")
    private String status;

    @TableField("MERGE_ID")
    private String mergeId;

    @ApiModelProperty(value = "终端IP地址")
    @TableField("IP")
    private String ip;

    @ApiModelProperty(value = "权限回收标志  0:未回收  -1:已回收  99:回收中")
    @TableField("RECOVER_FLAG")
    private String recoverFlag;

    @ApiModelProperty(value = "是否为导入的数据 0否 1是")
    @TableField("IS_IMPORT")
    private String isImport;

    @TableField("RECOVER_ID")
    private String recoverId;

    @ApiModelProperty(value = "是否为特殊应用申请 0:否 1:可视化建模平台 2:广东公安数据接入平台")
    @TableField("VISIBLE")
    private String visible;

    @ApiModelProperty(value = "统一用户id")
    @TableField("USERID")
    private String userid;

    /**
     * 申请服务数量
     */
    @Excel(name = "申请服务数量")
    @TableField(exist = false)
    private int num = 0;

    /**
     * 服务列表
     */
    @TableField(exist = false)
    private List<SaasApplyExt> serviceList;

    @ApiModelProperty(value = "文件id,多个使用逗号分割")
    @TableField(exist = false)
    private String fileIds;

    /**
     * 审核记录(包含实施记录)
     */
    @TableField(exist = false)
    private List<ApplyReviewRecordVO> reviewList;

    /**
     * 实施信息
     */
    @TableField(exist = false)
    private ApplyReviewRecordVO impl;

    /**
     * saas申请用用统计
     */
    @TableField(exist = false)
    private String total;
    /**
     * 服务ID
     */
    @TableField(exist = false)
    private String serviceId;
    /**
     * 服务名称
     */
    @TableField(exist = false)
    private String serviceName;

    /**
     *服务应用名称（地市应用 警种应用 通用应用）
     * @return
     */
    @TableField(exist = false)
    private String applicationName;

    /**
     * 可视化资源列表
     */
    @TableField(exist = false)
    private List<SaasApplyExtResource> resourceList;


    @Override
    public String toString() {
        return "SaasApply{" +
        "id=" + id +
        ", creator=" + creator +
        ", creatorName=" + creatorName +
        ", orgId=" + orgId +
        ", orgName=" + orgName +
        ", postType=" + postType +
        ", mobileWork=" + mobileWork +
        ", applicationTime=" + applicationTime +
        ", applyType=" + applyType +
        ", areas=" + areas +
        ", policeCategory=" + policeCategory +
        ", explanation=" + explanation +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", orderNumber=" + orderNumber +
        ", status=" + status +
        ", mergeId=" + mergeId +
        ", ip=" + ip +
        ", recoverFlag=" + recoverFlag +
        ", isImport=" + isImport +
        ", recoverId=" + recoverId +
        ", visible=" + visible +
        ", userid=" + userid +
        "}";
    }
}
