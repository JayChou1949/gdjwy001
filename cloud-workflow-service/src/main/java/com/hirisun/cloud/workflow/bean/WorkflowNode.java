package com.hirisun.cloud.workflow.bean;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 流程模型
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Data
@TableName("T_WORKFLOW_NODE")
@ApiModel(value="WorkflowNode对象", description="流程模型")
public class WorkflowNode implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 环节功能 3 可实施
     */
    public static final String NODE_ABILITY_IMPL = "3";

    /**
     * 环节顺序，从1开始
     */
    public static Integer NODE_SORT=1;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "流程定义表id")
    @TableField("WORKFLOW_ID")
    private String workflowId;

    @ApiModelProperty(value = "环节名称",required = true)
    @TableField("NODE_NAME")
    private String nodeName;

    @ApiModelProperty(value = "版本",required = true)
    @TableField("VERSION")
    private Integer version;

    @ApiModelProperty(value = "默认处理人")
    @TableField("DEFAULT_HANDLER")
    private String defaultHandler;

    @ApiModelProperty(value = "通知人")
    @TableField("NOTICE_PERSION")
    private String noticePersion;

    @ApiModelProperty(value = "参与人")
    @TableField("ADVISER_PERSON")
    private String adviserPerson;

    @ApiModelProperty(value = "环节拥有的功能，多个功能使用逗号(,)隔开。（1可审核 2可加办 3可实施 4可删除 5可修改 6可反馈 7可转发 8可回退 9可申请）",required = true)
    @TableField("NODE_FEATURE")
    private String nodeFeature;

    @ApiModelProperty(value = "环节顺序，从1开始")
    @TableField("NODE_SORT")
    private Integer nodeSort;

    @ApiModelProperty(value = "驳回位置顺序，从1开始")
    @TableField("REJECT_NUM")
    private Integer rejectNum;

    @TableField("NEXTMODELIDS")
    @ApiModelProperty("下一环节id")
    private String nextmodelids;

    @ApiModelProperty(value = "环节状态名")
    @TableField(exist = false)
    private Integer modeltype;

    @ApiModelProperty(value = "环节状态名")
    @TableField(exist = false)
    private Integer nodeStatus;

    @ApiModelProperty(value = "环节状态")
    @TableField(exist = false)
    private String nodeStatusCode;

    @ApiModelProperty(value = "默认处理人名称")
    @TableField(exist = false)
    private String defaultHandlerName;

    @ApiModelProperty(value = "通知人名称")
    @TableField(exist = false)
    private String noticePersionName;

    @ApiModelProperty(value = "参与人名称")
    @TableField(exist = false)
    private String adviserPersonName;

    @ApiModelProperty(value = "流转id")
    @TableField(exist = false)
    private String activityId;

    @Override
    public String toString() {
        return "WorkflowNode{" +
        "id=" + id +
        ", workflowId=" + workflowId +
        ", nodeName=" + nodeName +
        ", version=" + version +
        ", defaultHandler=" + defaultHandler +
        ", noticePersion=" + noticePersion +
        ", adviserPerson=" + adviserPerson +
        "}";
    }
}
