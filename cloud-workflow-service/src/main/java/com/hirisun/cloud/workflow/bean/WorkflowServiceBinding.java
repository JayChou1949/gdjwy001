package com.hirisun.cloud.workflow.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * IAAS\PAAS流程配置绑定表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Data
@TableName("T_WORKFLOW_SERVICE_BINDING")
@ApiModel(value="WorkflowServiceBinding对象", description="IAAS/PAAS流程配置绑定表")
public class WorkflowServiceBinding implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "服务ID")
    @TableField("SERVICE_ID")
    private String serviceId;

    @ApiModelProperty(value = "流程定义ID")
    @TableField("WORKFLOW_ID")
    private String workflowId;

    @Override
    public String toString() {
        return "WorkflowServiceBinding{" +
        "id=" + id +
        ", serviceId=" + serviceId +
        ", workflowId=" + workflowId +
        "}";
    }
}
