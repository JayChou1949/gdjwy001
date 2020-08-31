package com.hirisun.cloud.model.system;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("服务限额Vo")
@Data
public class ServiceLimitVo implements Serializable {

	private static final long serialVersionUID = 2687350357861752762L;

	@ApiModelProperty(name = "id")
	private String id;

    @ApiModelProperty(name = "CPU限额")
    private Double cpu;

    @ApiModelProperty(name = "内存限额")
    private Double memory;

    @ApiModelProperty(name = "存储限额")
    private Double storage;

    @ApiModelProperty(name = "地区")
    private String area;

    @ApiModelProperty(name = "警种")
    private String policeCategory;

    @ApiModelProperty(name = "资源服务名")
    private String serviceName;

    @ApiModelProperty(name = "说明")
    private String description;

    @ApiModelProperty(name = "资源类型 1:IAAS 2:DAAS 3:PAAS 4:SAAS")
    private Long resourceType;

    @ApiModelProperty(name = "formNum")
    private String formNum;

    @ApiModelProperty(name = "创建日期")
    private Date createTime;

    @ApiModelProperty(name = "修改日期")
    private Date modifiedTime;

    @ApiModelProperty(name = "国家专项")
    private String nationalSpecialProject;

}
