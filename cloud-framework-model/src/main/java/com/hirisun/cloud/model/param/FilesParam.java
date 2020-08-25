package com.hirisun.cloud.model.param;

import java.io.Serializable;
import java.util.List;

import com.hirisun.cloud.model.file.FilesVo;

import lombok.Data;

@Data
public class FilesParam implements Serializable{

	private static final long serialVersionUID = 2215297000290475866L;
	
	private List<FilesVo> files;
	private String refId;
	//更新关联关系用到
	private String newRefId;
	private List<String> filesIdList;
	
	
}
