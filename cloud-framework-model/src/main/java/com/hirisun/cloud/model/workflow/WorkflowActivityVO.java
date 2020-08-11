package com.hirisun.cloud.model.workflow;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 流程流转表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Data
@ApiModel(value="WorkflowActivity对象", description="流程流转表")
public class WorkflowActivityVO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 环节状态 0待办 1已提交 2已回退 3已呈批 4已抢占 5已终止 6已转发
     */
    public static final Integer STATUS_WAITING=0;
    /**
     * 环节状态 0待办 1已提交 2已回退 3已呈批 4已抢占 5已终止 6已转发
     */
    public static final Integer STATUS_SUBMIT=1;

    /**
     * 环节状态  4已抢占
     */
    public static final Integer STATUS_PREEMPT=4;

    private String id;

    @ApiModelProperty(value = "流程实例id")
    private String instanceId;

    @ApiModelProperty(value = "流程环节id")
    private String nodeId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建人id")
    private String creator;

    @ApiModelProperty(value = "接收时间")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date reciveTime;

    @ApiModelProperty(value = "处理时间")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date handleTime;

    @ApiModelProperty(value = "处理人")
    private String handlePersons;

    @ApiModelProperty(value = " 0:是  1:不是")
    private Integer isStart;

    @ApiModelProperty(value = "直接上级环节")
    private String preActivityId;

    @ApiModelProperty(value = "流转类型 adviser：参与人")
    private String activityType;

    @ApiModelProperty(value = "处理人名字")
    private String handlePersonName;

    @ApiModelProperty(value = "环节状态 0待办 1已提交 2已回退 3已呈批 4已抢占 5已终止 6已转发")
    private Integer activityStatus;

    @ApiModelProperty(value = "创建人机构id")
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
