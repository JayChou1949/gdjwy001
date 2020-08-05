package com.hirisun.cloud.api.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.hirisun.cloud.model.ncov.vo.file.FileVo;

/**
 * @author zhoufeng
 * @version 1.0
 * @className FileApiFallback
 * @data 2020/7/9 14:30
 * @description
 */
@Component
@Slf4j
public class FileApiFallback implements FileApi {
    @Override
    public String upload(MultipartFile file, String businessKey, String businessTag) {
        log.error("<== 上传{}文件失败！",file.getOriginalFilename());
        return "文件上传失败";
    }

    @Override
    public boolean delete(String id) {
        log.error("删除文件失败！");
        return false;
    }

    @Override
    public byte[] download(String id) {
        log.error("下载文件失败！");
        return new byte[0];
    }

    @Override
    public String getFileSystemInfo(String id) {
        log.error("获取文件系统信息失败！");
        return "";
    }

}
