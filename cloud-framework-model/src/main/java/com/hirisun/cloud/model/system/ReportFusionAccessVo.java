package com.hirisun.cloud.model.system;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("桌面云资源")
public class ReportFusionAccessVo implements Serializable {


	private static final long serialVersionUID = -3634014708988770167L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("民警访问 台")
    @Excel(name = "民警访问(台)")
    private Integer policeAccess;

	@ApiModelProperty("开发人员访问 台")
    @Excel(name = "开发人员访问(台)")
    private Integer developerAccess;

	@ApiModelProperty("备注")
    private String remark;

	@ApiModelProperty(" 应用（原项目）ID")
    private String projectId;

	@ApiModelProperty("申请ID")
    private String appInfoId;

	@ApiModelProperty("创建时间")
    private Date createTime;

	@ApiModelProperty("修改时间")
    private Date modifiedTime;


}
