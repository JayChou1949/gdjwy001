package com.hirisun.cloud.model.workflow;

import java.io.Serializable;
import java.util.Date;

import com.hirisun.cloud.model.user.UserVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("审批反馈的意见")
public class ApplicationFeedbackVo implements Serializable {

	private static final long serialVersionUID = 7771498164129366341L;

	@ApiModelProperty("id")
	private String id;

	@ApiModelProperty("审批人(身份证)")
    private String creator;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;

	@ApiModelProperty("结果 0:未完成 1:完成")
    private String result;

	@ApiModelProperty("备注")
    private String remark;

	@ApiModelProperty("服务信息 appInfoId")
    private String appInfoId;

	@ApiModelProperty("满意度")
    private String score;

	@ApiModelProperty("用户")
    private UserVO user;

}
