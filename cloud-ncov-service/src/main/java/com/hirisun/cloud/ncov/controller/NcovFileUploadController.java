package com.hirisun.cloud.ncov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.ncov.service.NcovFileUploadService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("疫情excel文件数据上传接口")
@RestController
@RequestMapping("/ncov/file")
public class NcovFileUploadController {

	@Autowired
	private NcovFileUploadService ncovFileUploadService;
	
	@ApiOperation(value = "文件上传")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "serviceType", value = "服务类型", required = true, paramType = "query"),
        @ApiImplicitParam(name = "dataType", value = "数据类型", required = true, paramType = "query"),
        @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "query")
	})
	@PostMapping (value="/upload")
	public ResponseResult upload(@RequestParam("serviceType") String serviceType,
			@RequestParam("dataType") String dataType,@RequestParam("file") MultipartFile file) throws Exception {
		
		ncovFileUploadService.fileUpload(serviceType, dataType,file);
		return ResponseResult.success();
	}
	
	@ApiOperation(value = "获取疫情excel文件下载地址")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "serviceType", value = "服务类型", required = true, paramType = "query")
	})
	@PostMapping (value="/url")
	public QueryResponseResult getUrl(@RequestParam("serviceType") String serviceType) throws Exception {
		return QueryResponseResult.success(ncovFileUploadService.getFileUrlByServiceType(serviceType));
	}
	
	
	
}
