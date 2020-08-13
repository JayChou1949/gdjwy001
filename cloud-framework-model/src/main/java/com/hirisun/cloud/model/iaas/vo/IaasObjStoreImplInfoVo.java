package com.hirisun.cloud.model.iaas.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 对象存储实施信息扩展表
 */
@Data
@ApiModel("对象存储实施信息扩展表")
public class IaasObjStoreImplInfoVo implements Serializable {

	private static final long serialVersionUID = -27811703178192028L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("应用组件")
    private String component;

	@ApiModelProperty("申请容量")
    private String applyStorage;

	@ApiModelProperty("组件描述")
    private String description;

	@ApiModelProperty("容量")
    private String storage;

	@ApiModelProperty("桶名称")
    private String bucketName;

	@ApiModelProperty("申请信息 id")
    private String appInfoId;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;

}
