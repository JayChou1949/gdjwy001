package com.hirisun.cloud.model.service.publish.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hirisun.cloud.model.service.ApiAcIpVo;
import com.hirisun.cloud.model.service.ApiOperationVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("服务发布api")
public class ServicePublishApiVo implements Serializable {

	private static final long serialVersionUID = 550295847008165741L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;
	
	@ApiModelProperty("服务发布ID")
    private String publishId;

	@ApiModelProperty("API名称")
    private String name;

	@ApiModelProperty("版本")
    private String version;

	@ApiModelProperty("访问协议.取值: HTTP/HTTPS/HTTP&HTTPS/WS/WSS")
    private String protocol;

	@ApiModelProperty("根路径")
    private String basePath;

	@ApiModelProperty("安全认证类型")
    private String authType;

	@ApiModelProperty("备注")
    private String remark;

	@ApiModelProperty("流量控制策略名称")
    private String fcName;

	@ApiModelProperty("流量控制策略类型.shared:共享额度, exclusive:专用额度")
    private String fcThresholdType;

	@ApiModelProperty("流量控制API流量限制")
    private Long fcApiThreshold;

	@ApiModelProperty("流量控制API流量限制时间")
    private Long fcApiTime;

	@ApiModelProperty("流量控制API流量限制单位,取值: SECOND/MINUTE/HOUR/DAY")
    private String fcTimeUnit;

	@ApiModelProperty("流量控制应用流量限制")
    private Long fcTimeInterval;

	@ApiModelProperty("流量控制应用流量限制时间")
    private Long fcAppTime;

	@ApiModelProperty("备注")
    private String fcRemark;
    
	@ApiModelProperty("是否配置访问策略,1:是,0:否")
    private String isAc;

	@ApiModelProperty("访问控制策略名称")
    private String acName;

	@ApiModelProperty("访问控制策略限制类型")
    private String acType;

	@ApiModelProperty("访问控制策略动作，允许：ALLOW  拒绝：DENY")
    private String acAction;

	@ApiModelProperty("ip信息")
    private List<ApiAcIpVo> ipList;

	@ApiModelProperty("api操作")
    private List<ApiOperationVo> apiOperationList;

}
