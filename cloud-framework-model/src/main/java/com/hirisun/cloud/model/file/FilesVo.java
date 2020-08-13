package com.hirisun.cloud.model.file;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("文件vo")
public class FilesVo implements Serializable {

	private static final long serialVersionUID = 5899434363137526627L;

	@ApiModelProperty("id")
	private String id;

	@ApiModelProperty("文件名称")
    private String name;

	@ApiModelProperty("原始名称")
    private String originaName;

	@ApiModelProperty("存储路径")
    private String path;

	@ApiModelProperty("后缀名")
    private String suffix;

	@ApiModelProperty("下载路径")
    private String url;

	@ApiModelProperty("refId")
    private String refId;
    
	@ApiModelProperty("标题")
    private String title;

	@ApiModelProperty("真实路径 供Nginx服务器使用")
    private String realURL;

}
