package com.hirisun.cloud.file.controller;

import com.alibaba.fastjson.JSON;
import com.hirisun.cloud.api.file.FileApi;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.file.bean.FileSystem;
import com.hirisun.cloud.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

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

    @ApiIgnore
    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/upload", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public String upload(@RequestPart("file") MultipartFile file, @RequestParam("businessKey") String businessKey, @RequestParam("businessTag") String businessTag) {
        return fileService.fdfs_upload(file, businessKey, businessTag);
    }

    @ApiIgnore
    @ApiOperation(value = "文件删除")
    @DeleteMapping("/delete/{id}")
    @Override
    public boolean delete(@PathVariable("id") String id) {
        Integer num = fileService.fdfs_delete(id);
        log.debug("<== num:{}", num);
        return num == 0;
    }

    @ApiIgnore
    @ApiOperation(value = "文件下载")
    @GetMapping("/download/{id}")
    @Override
    public byte[] download(@PathVariable("id") String id) {
        return fileService.fdfs_download(id);
    }

    @ApiIgnore
    @ApiOperation(value = "根据文件ID获得文件系统信息")
    @GetMapping("/file/{id}")
    @Override
    public String getFileSystemInfo(@PathVariable("id") String id) {
        FileSystem fileSystem = fileService.getFileSystemById(id);
        return JSON.toJSONString(fileSystem);
    }
    @ApiIgnore
    @ApiOperation(value = "根据文件ID串获得文件系统信息")
    @GetMapping("/file/getFileByIds")
    @Override
    public String getFileByIds(@RequestParam("ids") List<String> ids) {
        List<FileSystem> fileSystem = fileService.getFileByIds(ids);
        return JSON.toJSONString(fileSystem);
    }

    @ApiOperation(value = "前端文件上传",notes = "前端文件上传")
    @PostMapping(value = "/uploadFile")
    public QueryResponseResult uploadFile(@RequestPart("file") MultipartFile file,
                                          @ApiParam(value = "所属一级菜单名",required = true) @RequestParam("businessKey") String businessKey,
                                          @ApiParam(value = "所属二级菜单名",required = true) @RequestParam("businessTag") String businessTag) {
        return QueryResponseResult.success(fileService.fdfs_upload(file, businessKey, businessTag));
    }
    
    @ApiOperation(value = "前端文件上传,返回FileSystem",notes = "前端文件上传,返回FileSystem")
    @PostMapping(value = "/v2/uploadFile")
    public QueryResponseResult uploadFile2(@RequestPart("file") MultipartFile file,
                                          @ApiParam(value = "所属一级菜单名",required = true) @RequestParam("businessKey") String businessKey,
                                          @ApiParam(value = "所属二级菜单名",required = true) @RequestParam("businessTag") String businessTag) {
        return QueryResponseResult.success(fileService.fdfs_uploadV2(file, businessKey, businessTag));
    }

    @ApiOperation(value = "根据文件ID获得文件信息",notes = "前端根据文件ID获得文件信息")
    @GetMapping("/getFileById")
    public QueryResponseResult<FileSystem> getFileById(@ApiParam(value = "文件id",required = true) @RequestParam(value = "id",required = true) String id) {
        FileSystem fileSystem = fileService.getFileSystemById(id);
        return QueryResponseResult.success(fileSystem);
    }

}
