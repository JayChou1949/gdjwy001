package com.hirisun.cloud.api.file;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.ncov.vo.file.FileVo;

//@FeignClient(name = "cloud-file-service", fallback = FileUploadApiFallback.class)
public interface FileUploadApi {

	/**
     * 上传文件
     *
     * @return
     */
    @PostMapping(value = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    QueryResponseResult upload(MultipartFile file);
    
    /**
     * 上传文件
     *
     * @return
     */
    @PostMapping(value = "/file/upload/byte")
    public QueryResponseResult uploadByte(@RequestBody FileVo fileVo);
    /**
     * 文件删除
     * @param fileId
     * @return
     */
    @PostMapping("/file/delete")
    public QueryResponseResult deleteFileByFileId(@RequestParam("fileId")String fileId);
	
    /**
     * 文件下载
     * @param fileId
     * @return
     */
    @PostMapping("/file/download")
    public QueryResponseResult downloadFileByFileId(@RequestParam("fileId")String fileId);
    
    
}
