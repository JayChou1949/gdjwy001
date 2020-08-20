package com.hirisun.cloud.workflow.bean;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 流程流转表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Data
@TableName("T_WORKFLOW_ACTIVITY")
@ApiModel(value="WorkflowActivity对象", description="流程流转表")
public class WorkflowActivity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 环节状态 0待办
     */
    public static final Integer STATUS_WAITING=0;
    /**
     * 环节状态 1已提交
     */
    public static final Integer STATUS_SUBMIT=1;

    /**
     * 环节状态  2已回退
     */
    public static final Integer STATUS_REJECT=2;

    /**
     * 环节状态 3已呈批
     */
    public static final Integer STATUS_AUDIT=3;

    /**
     * 环节状态 4已抢占
     */
    public static final Integer STATUS_PREEMPT=4;

    /**
     * 环节状态 5已终止
     */
    public static final Integer STATUS_TERMINATE=5;

    /**
     * 环节状态 6已转发
     */
    public static final Integer STATUS_FORWARD=6;


    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "流程实例id")
    @TableField("INSTANCE_ID")
    private String instanceId;

    @ApiModelProperty(value = "流程环节id")
    @TableField("NODE_ID")
    private String nodeId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建人id")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "接收时间")
    @TableField("RECIVE_TIME")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date reciveTime;

    @ApiModelProperty(value = "处理时间")
    @TableField("HANDLE_TIME")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date handleTime;

    @ApiModelProperty(value = "处理人")
    @TableField("HANDLE_PERSONS")
    private String handlePersons;

    @ApiModelProperty(value = " 0:是  1:不是")
    @TableField("IS_START")
    private Integer isStart;

    @ApiModelProperty(value = "直接上级环节")
    @TableField("PRE_ACTIVITY_ID")
    private String preActivityId;

    @ApiModelProperty(value = "流转类型 adviser：参与人")
    @TableField("ACTIVITY_TYPE")
    private String activityType;

    @ApiModelProperty(value = "处理人名字")
    @TableField("HANDLE_PERSON_NAME")
    private String handlePersonName;

    @ApiModelProperty(value = "环节状态 0待办 1已提交 2已回退 3已呈批 4已抢占 5已终止 6已转发")
    @TableField("ACTIVITY_STATUS")
    private Integer activityStatus;

    @ApiModelProperty(value = "创建人机构id")
    @TableField("CREATOR_ORG_ID")
    private String creatorOrgId;


    @Override
    public String toString() {
        return "WorkflowActivity{" +
        "id=" + id +
        ", instanceId=" + instanceId +
        ", nodeId=" + nodeId +
        ", createTime=" + createTime +
        ", creator=" + creator +
        ", reciveTime=" + reciveTime +
        ", handleTime=" + handleTime +
        ", handlePersons=" + handlePersons +
        ", isStart=" + isStart +
        ", preActivityId=" + preActivityId +
        ", activityType=" + activityType +
        ", handlePersonName=" + handlePersonName +
        ", activityStatus=" + activityStatus +
        "}";
    }
}
