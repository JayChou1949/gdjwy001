package com.hirisun.cloud.ncov.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface NcovFileUploadService {

	/**
	 * 文件上传
	 * @param serviceType
	 * @param dataType
	 * @param multipartFile
	 * @throws Exception
	 */
	public String fileUpload(String serviceType,String dataType, MultipartFile multipartFile) throws Exception;
	
	/**
	 * 根据服务类型获取对应所有的文件(拼接访问地址)
	 * @param serviceType
	 * @param dataType
	 * @param multipartFile
	 * @throws Exception
	 */
	public Map<String,String> getFileUrlByServiceType(String serviceType,String dataType);
	
	/**
	 * 根据服务类型获取对应所有的文件(不拼接访问地址)
	 * @param serviceType
	 * @param dataType
	 * @param multipartFile
	 * @throws Exception
	 */
	public Map<String,String> getFileUriByServiceType(String serviceType,String dataType);
}
