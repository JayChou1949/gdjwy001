package com.hirisun.cloud.model.iaas.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("IaaS 对象存储申请信息")
public class IaasObjStoreInfoVo implements Serializable {

	private static final long serialVersionUID = 4079379919901593669L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("统一用户账号")
    private String tyyhAccount;

	@ApiModelProperty("统一用户密码")
    private String tyyhPassword;

	@ApiModelProperty("申请信息 id")
    private String appInfoId;

	@ApiModelProperty("购物车id")
    private String shoppingCartId;

	@ApiModelProperty("创建日期")
    private Date createTime;
	
	@ApiModelProperty("修改日期")
    private Date modifiedTime;

	@ApiModelProperty("存储信息")
    private List<IaasObjStoreVo> ccxx;

}
