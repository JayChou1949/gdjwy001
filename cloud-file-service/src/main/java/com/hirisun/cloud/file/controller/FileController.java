package com.hirisun.cloud.file.controller;

import com.alibaba.fastjson.JSON;
import com.hirisun.cloud.api.file.FileApi;
import com.hirisun.cloud.file.bean.FileSystem;
import com.hirisun.cloud.file.service.FileService;
import com.hirisun.cloud.model.ncov.vo.file.FileVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
public class FileController implements FileApi {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    @Override
    public String upload(MultipartFile file, @RequestParam("businessKey") String businessKey, @RequestParam("businessTag") String businessTag) {
        return fileService.fdfs_upload(file,businessKey,businessTag);
    }

    @ApiOperation(value = "文件删除")
    @DeleteMapping("/delete/{id}")
    @Override
    public boolean delete(@PathVariable("id") String id) {
        Integer num = fileService.fdfs_delete(id);
        log.debug("<== num:{}",num);
        return num == 0;
    }

    @ApiOperation(value = "文件下载")
    @GetMapping("/download/{id}")
    @Override
    public byte[] download(@PathVariable("id") String id) {
        return fileService.fdfs_download(id);
    }

    @ApiOperation(value = "根据文件ID获得文件系统信息")
    @GetMapping("file/{id}")
    @Override
    public String getFileSystemInfo(@PathVariable("id")String id) {
        FileSystem fileSystem = fileService.getFileSystemById(id);
        return JSON.toJSONString(fileSystem);
    }
    
    @ApiOperation(value = "上传二进制格式的文件")
    @GetMapping("/upload/byte")
    @Override
	public String uploadByte(FileVo fileVo) {
		
    	String fileId = fileService.uploadByte(fileVo);
    	log.info("上传二进制格式的文件返回的fileId:{}",fileId);
		return fileId;
	}

}
