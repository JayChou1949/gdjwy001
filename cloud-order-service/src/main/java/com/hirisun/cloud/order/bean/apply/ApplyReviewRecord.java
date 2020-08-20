package com.hirisun.cloud.order.bean.apply;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 服务申请审核信息表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-12
 */
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWorkflowNodeId() {
        return workflowNodeId;
    }

    public void setWorkflowNodeId(String workflowNodeId) {
        this.workflowNodeId = workflowNodeId;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

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
