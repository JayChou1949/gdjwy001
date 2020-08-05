package com.hirisun.cloud.model.ncov.vo.file;

import java.io.Serializable;

public class FileVo implements Serializable{

	private static final long serialVersionUID = 1979004104629668309L;
	
	private String fileName;
	private byte[] fileByte;
	private Long fileSize;
	private String fileType;
	private String businessKey;
	private String businessTag;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getFileByte() {
		return fileByte;
	}
	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getBusinessTag() {
		return businessTag;
	}
	public void setBusinessTag(String businessTag) {
		this.businessTag = businessTag;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
}
