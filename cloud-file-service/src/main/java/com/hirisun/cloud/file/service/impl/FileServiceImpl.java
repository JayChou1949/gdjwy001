package com.hirisun.cloud.file.service.impl;

import com.hirisun.cloud.common.exception.ExceptionCast;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.file.service.FileService;
import com.hirisun.cloud.file.vo.FileCode;
import lombok.extern.slf4j.Slf4j;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhoufeng
 * @version 1.0
 * @className FileServiceImpl
 * @data 2020/7/2 12:58
 * @description
 */
@RefreshScope
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    /**
     * trackerServer 地址
     */
    @Value("${cloud.fdfs.tracker_servers}")
    String tracker_servers;
    /**
     * 连接超时时间
     */
    @Value("${cloud.fdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    /**
     * 网络超时时间
     */
    @Value("${cloud.fdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;


    /**
     * 初始化Fdfs配置信息
     */
    private void initFdfsConfig() {
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
        } catch (Exception e) {
            log.error("Fdfs初始化错误，错误信息为：{}", e.getMessage());
            ExceptionCast.cast(FileCode.FDFS_INIT_FAULT);
        }
    }

    /**
     * 上传文件到Fdfs
     *
     * @param file 文件
     * @return
     */
    @Override
    public String fdfs_upload(MultipartFile file) {
        //初始化Fdfs配置信息
        initFdfsConfig();
        if (file == null) {
            ExceptionCast.cast(FileCode.FILE_IS_NULL);
        }
        try {
            //创建tracker client
            TrackerClient client = new TrackerClient();
            //获取tracker server
            TrackerServer server = client.getConnection();
            //获取storage
            StorageServer storage = client.getStoreStorage(server);
            //创建 storage client
            StorageClient1 storageClient = new StorageClient1(server, storage);
            //文件字节
            byte[] bytes = file.getBytes();
            //获取文件原始名称
            String originalFilename = file.getOriginalFilename();
            //获取文件扩展名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //上传文件并获得文件Id
            String fileId = storageClient.upload_file1(bytes, extName, null);
            return fileId;
        } catch (Exception e) {
            log.error("上传文件失败,具体信息为:{}", e.getMessage());
            ExceptionCast.cast(FileCode.FDFS_UPLOAD_FAULT);
        }
        return null;
    }

	@Override
	public Integer deleteFileByFileId(String fileId) {
		

        //初始化Fdfs配置信息
        initFdfsConfig();
        try {
            //创建tracker client
            TrackerClient client = new TrackerClient();
            //获取tracker server
            TrackerServer server = client.getConnection();
            //获取storage
            StorageServer storage = client.getStoreStorage(server);
            //创建 storage client
            StorageClient1 storageClient = new StorageClient1(server, storage);
            //获取文件扩展名
            //上传文件并获得文件Id
            return storageClient.delete_file1(fileId);
        } catch (Exception e) {
            log.error("删除文件失败,具体信息为:{}", e.getMessage());
            ExceptionCast.cast(FileCode.FDFS_UPLOAD_FAULT);
        }
        return null;
    
		
	}

	@Override
	public byte[] downloadFileByFileId(String fileId) {
		
		//初始化Fdfs配置信息
        initFdfsConfig();
        try {
            //创建tracker client
            TrackerClient client = new TrackerClient();
            //获取tracker server
            TrackerServer server = client.getConnection();
            //获取storage
            StorageServer storage = client.getStoreStorage(server);
            //创建 storage client
            StorageClient1 storageClient = new StorageClient1(server, storage);
            //获取文件扩展名
            //上传文件并获得文件Id
            return storageClient.download_file1(fileId);
        } catch (Exception e) {
            log.error("下载文件失败,具体信息为:{}", e.getMessage());
            ExceptionCast.cast(FileCode.FDFS_UPLOAD_FAULT);
        }
        return null;
		
		
	}
}
