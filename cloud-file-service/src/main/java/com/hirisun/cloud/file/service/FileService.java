package com.hirisun.cloud.file.service;

import com.hirisun.cloud.file.bean.FileSystem;
import org.springframework.web.multipart.MultipartFile;

import com.hirisun.cloud.model.ncov.vo.file.FileVo;

import java.util.List;

/**
 * @author zhoufeng
 * @version 1.0
 * @className FileService
 * @data 2020/7/2 11:30
 * @description 文件服务接口
 */
public interface FileService {

    /**
     * Fdfs上传文件
     * @param file
     * @return
     */
    String fdfs_upload(MultipartFile file,String businessKey,String businessTag);
    
    FileSystem fdfs_uploadV2(MultipartFile file, String businessKey, String businessTag);
    
    /**
     * 根据fileId删除文件
     * @param fileId
     * @return
     */
    Integer fdfs_delete(String fileId);
    
    /**
     * 根据fileId下载文件
     * @param fileId
     * @return
     */
    byte[] fdfs_download(String fileId);

    /**
     * 根据文件系统Id获得文件信息信息
     * @param fileId 文件系统ID
     * @return
     */
    FileSystem getFileSystemById(String fileId);
    
    /**
     * 上传二进制格式的文件
     * @param fileVo
     * @return
     */
    public String uploadByte(FileVo fileVo);

    /**
     * 通过文件id串查询多个文件
     * @param fileIds
     * @return
     */
    public List<FileSystem> getFileByIds(List<String> fileIds);
}
