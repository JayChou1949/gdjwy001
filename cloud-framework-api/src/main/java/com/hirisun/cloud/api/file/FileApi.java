package com.hirisun.cloud.api.file;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhoufeng
 * @version 1.0
 * @className FileApi
 * @data 2020/7/9 14:30
 * @description
 */
@FeignClient(name = "cloud-file-service", fallback = FileApiFallback.class)
public interface FileApi {

    /**
     * 上传文件
     * @param file 文件
     * @param businessKey 业务key
     * @param businessTag 业务标签
     * @return 文件Id
     */
    @PostMapping("file/upload")
    String upload(MultipartFile file, @RequestParam("businessKey") String businessKey, @RequestParam("businessTag") String businessTag);

    /**
     * 根据文件Id删除文件
     * @param id 文件Id
     * @return 是否成功
     */
    @DeleteMapping("file/delete/{id}")
    boolean delete(@PathVariable("id")String id);

    /**
     * 根据文件ID下载文件
     * @param id 文件Id
     * @return
     */
    @GetMapping("file/download/{id}")
    byte[] download(@PathVariable("id")String id);

    /**
     * 根据文件ID获得文件系统具体信息
     * @param id 文件ID
     * @return
     */
    @GetMapping("file/{id}")
    String getFileSystemInfo(@PathVariable("id")String id);
}
