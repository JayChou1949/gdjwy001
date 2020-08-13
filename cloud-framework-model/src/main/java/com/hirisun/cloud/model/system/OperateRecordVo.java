package com.hirisun.cloud.model.system;

import java.io.Serializable;
import java.util.Date;

import com.hirisun.cloud.model.user.UserVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("系统操作记录")
public class OperateRecordVo implements Serializable {

	private static final long serialVersionUID = -2382270534663369845L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("记录目标 ID")
    private String targetId;

	@ApiModelProperty("操作人")
    private String operator;

	@ApiModelProperty("操作")
    private String operate;

	@ApiModelProperty("结果")
    private String result;

	@ApiModelProperty("备注")
    private String remark;

	@ApiModelProperty("创建时间")
    private Date createTime;

	@ApiModelProperty("修改时间")
    private Date modifiedTime;

	@ApiModelProperty("当前用户")
    private UserVO user;

	@ApiModelProperty("是否第一次上线 1.是")
    private Long first;

}
