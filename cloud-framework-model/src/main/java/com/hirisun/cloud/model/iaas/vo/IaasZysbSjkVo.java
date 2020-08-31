package com.hirisun.cloud.model.iaas.vo;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("IAAS资源上报数据库")
@Data
public class IaasZysbSjkVo implements Serializable {

	private static final long serialVersionUID = -2246049913149914881L;

	@ApiModelProperty("ID")
    private String id;

    @Excel(name = "数据库版本")
    @ApiModelProperty("数据库版本")
    private String version;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("申请信息 id")
    private String masterId;

    @ApiModelProperty("创建日期")
    private Date createTime;

    @ApiModelProperty("修改日期")
    private Date modifiedTime;

    @ApiModelProperty("应用ID")
    private String projectId;

    @Excel(name = "硬盘(TB)", type = 10)
    @ApiModelProperty("存储硬盘(TB)")
    private Double storages;

}
