package com.hirisun.cloud.model.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 流程模型
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Data
@ApiModel(value="WorkflowNode对象", description="流程模型")
public class WorkflowNodeVO implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "流程定义表id")
    private String workflowId;

    @ApiModelProperty(value = "环节名称",required = true)
    private String nodeName;

    @ApiModelProperty(value = "版本",required = true)
    private Integer version;

    @ApiModelProperty(value = "默认处理人",required = true)
    private String defaultHandler;

    @ApiModelProperty(value = "通知人")
    private String noticePersion;

    @ApiModelProperty(value = "参与人")
    private String adviserPerson;


    @ApiModelProperty(value = "环节拥有的功能，多个功能使用逗号(,)隔开。（1可审核 2可加办 3可实施 4可删除 5可修改 6可反馈 7可转发 8可回退 9可申请）",required = true)
    private String nodeFeature;

    @ApiModelProperty(value = "环节顺序")
    private Integer nodeSort;

    @ApiModelProperty(value = "驳回位置顺序，从1开始")
    private Integer rejectNum;

    @ApiModelProperty(value = "环节状态名")
    private Integer nodeStatus;

    @ApiModelProperty(value = "环节状态")
    private String nodeStatusCode;

    @ApiModelProperty(value = "默认处理人名称")
    private String defaultHandlerName;

    @ApiModelProperty(value = "通知人名称")
    private String noticePersionName;

    @ApiModelProperty(value = "参与人名称")
    private String adviserPersonName;

    @ApiModelProperty(value = "流转id")
    private String activityId;

    @Override
    public String toString() {
        return "WorkflowNodeVO{" +
                "id='" + id + '\'' +
                ", workflowId='" + workflowId + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", version=" + version +
                ", defaultHandler='" + defaultHandler + '\'' +
                ", noticePersion='" + noticePersion + '\'' +
                ", adviserPerson='" + adviserPerson + '\'' +
                ", nodeFeature='" + nodeFeature + '\'' +
                ", nodeSort=" + nodeSort +
                ", rejectNum=" + rejectNum +
                ", nodeStatus=" + nodeStatus +
                ", nodeStatusCode='" + nodeStatusCode + '\'' +
                ", defaultHandlerName='" + defaultHandlerName + '\'' +
                ", noticePersionName='" + noticePersionName + '\'' +
                ", adviserPersonName='" + adviserPersonName + '\'' +
                '}';
    }
}
