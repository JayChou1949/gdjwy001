package com.hirisun.cloud.model.workflow;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 流程实例表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Data
@ApiModel(value="WorkflowInstance对象", description="流程实例表")
public class WorkflowInstanceVO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 实例状态 0办理中
     */
    public static Integer INSTANCE_STATUS_WORKING = 0;
    /**
     * 实例状态 1已完成
     */
    public static Integer INSTANCE_STATUS_COMPLETE = 1;
    /**
     * 实例状态  2终止
     */
    public static Integer INSTANCE_STATUS_TERMINATE = 2;



    private String id;

    @ApiModelProperty(value = "流程定义表ID")
    private String workflowId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "完成时间")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date completeTime;

    @ApiModelProperty(value = "流程版本号")
    private Integer version;

    @ApiModelProperty(value = "业务ID")
    private String businessId;

    @ApiModelProperty(value = "实例状态 0办理中 1已完成 2终止")
    private Integer instanceStatus;

    @ApiModelProperty(value = "创建人机构id")
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
