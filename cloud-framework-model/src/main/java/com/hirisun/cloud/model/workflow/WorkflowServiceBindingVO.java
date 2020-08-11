package com.hirisun.cloud.model.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * IAAS\PAAS流程配置绑定表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Data
@ApiModel(value="WorkflowServiceBinding对象", description="IAAS/PAAS流程配置绑定表")
public class WorkflowServiceBindingVO implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "服务ID")
    private String serviceId;

    @ApiModelProperty(value = "流程定义ID")
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
