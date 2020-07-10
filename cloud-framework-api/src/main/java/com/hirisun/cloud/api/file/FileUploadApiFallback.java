package com.hirisun.cloud.api.file;

import org.springframework.web.multipart.MultipartFile;

import com.hirisun.cloud.common.vo.QueryResponseResult;

public class FileUploadApiFallback implements FileUploadApi {

	@Override
	public QueryResponseResult upload(MultipartFile file) {
		return null;
	}

	@Override
	public QueryResponseResult deleteFileByFileId(String fileId) {
		return null;
	}

	@Override
	public QueryResponseResult downloadFileByFileId(String fileId) {
		return null;
	}

}
