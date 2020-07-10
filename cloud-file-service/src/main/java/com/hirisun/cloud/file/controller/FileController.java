package com.hirisun.cloud.file.controller;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhoufeng
 * @version 1.0
 * @className FileController
 * @data 2020/7/1 11:30
 * @description 文件服务控制器
 */
@Api(tags = "文件服务处理类")
@RequestMapping("/file")
@RestController
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public QueryResponseResult upload(MultipartFile file) {
        String fileId = fileService.fdfs_upload(file);
        log.debug("<== fileId:{}", fileId);
        return QueryResponseResult.success(fileId);
    }
    
    @ApiOperation(value = "文件删除")
    @PostMapping("/delete")
    public QueryResponseResult deleteFileByFileId(String fileId) {
        Integer success = fileService.deleteFileByFileId(fileId);
        log.debug("<== 文件删除 fileId:{}", fileId);
        return QueryResponseResult.success(success);
    }
    
    @ApiOperation(value = "文件下载")
    @PostMapping("/download")
    public QueryResponseResult downloadFileByFileId(String fileId) {
        byte[] file = fileService.downloadFileByFileId(fileId);
        log.debug("<== 文件下载 fileId:{}", fileId);
        return QueryResponseResult.success(file);
    }
}
