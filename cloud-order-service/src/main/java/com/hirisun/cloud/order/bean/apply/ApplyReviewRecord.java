package com.hirisun.cloud.order.bean.apply;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hirisun.cloud.model.file.FilesVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 服务申请审核信息表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-12
 */
@Data
@TableName("T_APPLY_REVIEW_RECORD")
@ApiModel(value="ApplyReviewRecord对象", description="服务申请审核信息表")
public class ApplyReviewRecord implements Serializable {

    /**
     * 类型 1审核
     */
    public static final String TYPE_AUDIT="1";
    /**
     * 类型 2实施
     */
    public static final String TYPE_IMPL="2";

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "审批人")
    @TableField("CREATOR")
    private String creator;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date modifiedTime;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "环节名")
    @TableField("STEP_NAME")
    private String stepName;

    @ApiModelProperty(value = "服务信息id")
    @TableField("APPLY_ID")
    private String applyId;

    @ApiModelProperty(value = "类型 1：审核 2：实施")
    @TableField("TYPE")
    private String type;

    @ApiModelProperty(value = "环节 id")
    @TableField("WORKFLOW_NODE_ID")
    private String workflowNodeId;

    @ApiModelProperty(value = "审批结果 0:驳回 1:通过")
    @TableField("RESULT")
    private Integer result;

    @TableField(exist = false)
    private List<FilesVo> fileList;

    /**
     * 是否关系型数据库新增账号流程
     */
    @ApiModelProperty(value = "是否关系型数据库新增账号流程")
    @TableField(exist = false)
    private int  rdbAddAccount;

    @ApiModelProperty(value = "资源是否同意回收")
    @TableField(exist = false)
    private int resourceRecoveredAgree;


    @Override
    public String toString() {
        return "ApplyReviewRecord{" +
        "id=" + id +
        ", creator=" + creator +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", remark=" + remark +
        ", stepName=" + stepName +
        ", applyId=" + applyId +
        ", type=" + type +
        ", workflowNodeId=" + workflowNodeId +
        ", result=" + result +
        "}";
    }
}
