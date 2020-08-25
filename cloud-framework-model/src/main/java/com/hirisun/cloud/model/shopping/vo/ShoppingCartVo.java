package com.hirisun.cloud.model.shopping.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hirisun.cloud.model.file.FilesVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("购物车VO")
@Data
public class ShoppingCartVo implements Serializable {

	private static final long serialVersionUID = 8043816034270271770L;

	@ApiModelProperty("购物车id")
    private String id;

	@ApiModelProperty("资源ID")
    private String serviceTypeId;

	@ApiModelProperty("资源名")
    private String serviceTypeName;

	@ApiModelProperty("资源类别")
    private Integer resourceType;

	@ApiModelProperty("状态 0 :草稿 1:待提交")
    private Integer status;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;

	@ApiModelProperty("购物车创建人身份证号")
    private String creatorIdCard;

	@ApiModelProperty("购物车创建人名")
    private String creatorName;

	@ApiModelProperty("formNum")
    private String formNum;

	@ApiModelProperty("文档信息")
    private List<FilesVo> fileList;

	@ApiModelProperty("组件总数")
    private Integer totalNum;

	@ApiModelProperty("申请说明")
    private String explanation;

	@ApiModelProperty("DaaS api_guid,SaaS  id")
    private String dsId;

	@ApiModelProperty("DaaS、SaaS服务名")
    private String dsName;
	
	@ApiModelProperty("appInfoId")
    private String appInfoId;
	
	@ApiModelProperty("业务表单信息")
	private List<Map<String,Object>> serverList;

	public ShoppingCartVo(String id, String formNum) {
		this.id = id;
		this.formNum = formNum;
	}
	
	public ShoppingCartVo() {}

	public ShoppingCartVo(String id, String formNum, String appInfoId) {
		this.id = id;
		this.formNum = formNum;
		this.appInfoId = appInfoId;
	}
	
}
