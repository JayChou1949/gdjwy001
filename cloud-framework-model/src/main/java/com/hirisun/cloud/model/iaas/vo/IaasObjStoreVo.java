package com.hirisun.cloud.model.iaas.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("IaaS 对象存储申请信息")
public class IaasObjStoreVo implements Serializable {

	private static final long serialVersionUID = -8534825969582659377L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("应用组件")
    private String component;

	@ApiModelProperty("容量大小GB")
    private String storage;

	@ApiModelProperty("组件描述")
    private String description;

	@ApiModelProperty("申请信息 id")
    private String appInfoId;

	@ApiModelProperty("购物车id")
    private String shoppingCartId;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;
	
	private String orderByField;
	
	private String orderByFieldValue;
	
	private String orderByEqField;
	
	private String orderByEqFieldValue;
	
	private boolean orderByAsc;

}
