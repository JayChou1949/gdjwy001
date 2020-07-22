package com.hirisun.cloud.model.ncov.vo.file;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文件上传返回文件名称和访问路径Vo
 * @author Lenovo
 *
 */
@ApiModel("文件上传vo")
public class FileUploadVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5247770593369939060L;

	@ApiModelProperty(value="文件名")
	private String fileName;
	
	@ApiModelProperty(value="文件访问路径")
	private String filePath;
	
	@ApiModelProperty(value="文件数据类型")
	private String dataType;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
}
