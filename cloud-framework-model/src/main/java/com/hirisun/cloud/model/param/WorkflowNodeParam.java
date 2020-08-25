package com.hirisun.cloud.model.param;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("工作流程环节")
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowNodeParam implements Serializable {

	private static final long serialVersionUID = 5722609490393630631L;

	@ApiModelProperty(value = "流程定义表id")
    private String workflowId;

    @ApiModelProperty(value = "环节名称",required = true)
    private String nodeName;
    
    @ApiModelProperty(value = "版本",required = true)
    private Integer version;
    
    
}
