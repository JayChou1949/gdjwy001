package com.hirisun.cloud.model.iaas.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("IaaS对象存储实施信息表")
public class IaasObjStoreImplVo implements Serializable{

	private static final long serialVersionUID = 825317242434287311L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("accessKey")
    private String accessKey;

	@ApiModelProperty("accessSecret")
    private String accessSecret;

	@ApiModelProperty("账号")
    private String account;

	@ApiModelProperty("密码")
    private String password;

	@ApiModelProperty("容量大小GB")
    private String storage;

	@ApiModelProperty("桶名称")
    private String bucketName;

	@ApiModelProperty("申请信息 id")
    private String appInfoId;

	@ApiModelProperty("创建时间")
    private Date createTime;

	@ApiModelProperty("修改时间")
    private Date modifiedTime;

	@ApiModelProperty("domainName")
    private String domainName;

	@ApiModelProperty("ip")
    private String ip;

	@ApiModelProperty("存储实施信息")
    private List<IaasObjStoreImplInfoVo> ccxx;

}
