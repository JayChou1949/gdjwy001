package com.hirisun.cloud.model.workflow;

import com.hirisun.cloud.model.service.AppReviewInfoVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("流程回退时的封装类,用以接口调用传递参数")
public class FallBackVO {
	@ApiModelProperty("回退时所处的环节ID")
	private String currentActivityId;
	@ApiModelProperty("选择的回退环节模型ID串,多个环节之间用','隔开")
	private String fallBackModelIds;
	@ApiModelProperty("审批复核等信息接收")
	private AppReviewInfoVo approve;
	@ApiModelProperty("反馈信息接收")
	private ApplicationFeedbackVo feedBack;
	
}
