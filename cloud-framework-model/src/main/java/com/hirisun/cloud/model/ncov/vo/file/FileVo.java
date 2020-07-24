package com.hirisun.cloud.model.ncov.vo.file;

import java.io.Serializable;

public class FileVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1979004104629668309L;
	
	private String fileName;
	private byte[] fileByte;
	
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
	
}
