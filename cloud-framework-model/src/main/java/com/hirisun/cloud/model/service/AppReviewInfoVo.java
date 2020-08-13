package com.hirisun.cloud.model.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("应用审核信息vo")
public class AppReviewInfoVo implements Serializable {

	private static final long serialVersionUID = 1212392047917512430L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("审批人")
    private String creator;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;

	@ApiModelProperty("结果 0:驳回 1:通过")
    private String result;

	@ApiModelProperty("备注")
    private String remark;

	@ApiModelProperty("环节名")
    private String stepName;

	@ApiModelProperty("服务信息id")
    private String appInfoId;

	@ApiModelProperty("类型 1：审核 2：实施;4 回退，5参与人,6加办")
    private String rType;

	@ApiModelProperty("环节 id")
    private String flowStepId;

	@ApiModelProperty("用户")
    private UserVO user;

	@ApiModelProperty("文件")
    private List<FilesVo> fileList;

	@ApiModelProperty("是否关系型数据库新增账号流程")
    private int  rdbAddAccount;

	@ApiModelProperty("是否关系型数据库新增账号流程")
    private int resourceRecoveredAgree;

}
