package com.hirisun.cloud.workflow.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 流程实例表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Data
@TableName("T_WORKFLOW_INSTANCE")
@ApiModel(value="WorkflowInstance对象", description="流程实例表")
public class WorkflowInstance implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "流程定义表ID")
    @TableField("WORKFLOW_ID")
    private String workflowId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "完成时间")
    @TableField("COMPLETE_TIME")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date completeTime;

    @ApiModelProperty(value = "流程版本号")
    @TableField("VERSION")
    private Integer version;

    @ApiModelProperty(value = "业务ID")
    @TableField("BUSINESS_ID")
    private String businessId;

    @ApiModelProperty(value = "实例状态 0办理中 1已完成 2终止")
    @TableField("INSTANCE_STATUS")
    private Integer instanceStatus;

    @ApiModelProperty(value = "创建人机构id")
    @TableField("CREATOR_ORG_ID")
    private String creatorOrgId;


    @Override
    public String toString() {
        return "WorkflowInstance{" +
        "id=" + id +
        ", workflowId=" + workflowId +
        ", createTime=" + createTime +
        ", creator=" + creator +
        ", completeTime=" + completeTime +
        ", version=" + version +
        ", businessId=" + businessId +
        ", instanceStatus=" + instanceStatus +
        "}";
    }
}
