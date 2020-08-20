package com.hirisun.cloud.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.common.exception.ExceptionCast;
import com.hirisun.cloud.file.bean.FileSystem;
import com.hirisun.cloud.file.mapper.FileSystemMapper;
import com.hirisun.cloud.file.service.FileService;
import com.hirisun.cloud.file.vo.FileCode;
import com.hirisun.cloud.model.ncov.vo.file.FileVo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

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
public class FileServiceImpl extends ServiceImpl<FileSystemMapper, FileSystem> implements FileService {

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

    @Autowired
    private FileSystemMapper fileSystemMapper;

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
    public String fdfs_upload(MultipartFile file, String businessKey, String businessTag) {
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
            //上传文件并获得文件目录及Id
            String fileId = storageClient.upload_file1(bytes, extName, null);
            //文件Id起始位置
            int beginIndex = fileId.lastIndexOf("/") + 1;
            //文件Id结束位置
            int endIndex = fileId.lastIndexOf(".");
            //文件id
            String id = fileId.substring(beginIndex, endIndex);
            //创建文件系统对象
            FileSystem fileSystem = new FileSystem();
            //设置文件系统属性
            fileSystem.setId(id)
                    .setFilePath(fileId)
                    .setBusinessKey(businessKey)
                    .setBusinessTag(businessTag)
                    .setFileName(file.getOriginalFilename())
                    .setFileSize(file.getSize())
                    .setFileType(file.getContentType())
                    .setCreateDate(new Date())
                    .setUpdateDate(new Date());
            fileSystemMapper.insert(fileSystem);
            return id;
        } catch (Exception e) {
            log.error("上传文件失败,具体信息为:{}", e.getMessage());
            ExceptionCast.cast(FileCode.FDFS_UPLOAD_FAULT);
        }
        return null;
    }

    @Override
    public Integer fdfs_delete(String id) {
        //初始化Fdfs配置信息
        initFdfsConfig();
        try {
            //根据文件id获得文件路径信息
            String filePath = fileSystemMapper.getFilePathById(id);
            if (StringUtils.isBlank(filePath)) {
                ExceptionCast.cast(FileCode.FILE_NO_EXISTS);
            }
            //删除文件信息
            fileSystemMapper.deleteById(id);
            //创建tracker client
            TrackerClient client = new TrackerClient();
            //获取tracker server
            TrackerServer server = client.getConnection();
            //获取storage
            StorageServer storage = client.getStoreStorage(server);
            //创建 storage client
            StorageClient1 storageClient = new StorageClient1(server, storage);
            //删除文件
            int count = storageClient.delete_file1(filePath);
            return count;
        } catch (Exception e) {
            log.error("删除文件失败,具体信息为:{}", e.getMessage());
            ExceptionCast.cast(FileCode.FDFS_DELETE_FAULT);
        }
        return 0;

    }

    @Override
    public byte[] fdfs_download(String id) {
        //初始化Fdfs配置信息
        initFdfsConfig();
        try {
            //根据文件id获得文件路径信息
            String filePath = fileSystemMapper.getFilePathById(id);
            if (StringUtils.isBlank(filePath)) {
                ExceptionCast.cast(FileCode.FILE_NO_EXISTS);
            }
            //创建tracker client
            TrackerClient client = new TrackerClient();
            //获取tracker server
            TrackerServer server = client.getConnection();
            //获取storage
            StorageServer storage = client.getStoreStorage(server);
            //创建 storage client
            StorageClient1 storageClient = new StorageClient1(server, storage);
            //上传文件并获得文件Id
            return storageClient.download_file1(filePath);
        } catch (Exception e) {
            log.error("下载文件失败,具体信息为:{}", e.getMessage());
            ExceptionCast.cast(FileCode.FDFS_DOWNLOAD_FAULT);
        }
        return null;
    }

    @Override
    public FileSystem getFileSystemById(String fileId) {
        return fileSystemMapper.selectById(fileId);
    }

    @Override
    public List<FileSystem> getFileByIds(List<String> fileIds) {
        return this.list(new QueryWrapper<FileSystem>().lambda()
                .in(FileSystem::getId, fileIds));
    }

	@Override
	public String uploadByte(FileVo fileVo) {
		
		byte[] fileByte = fileVo.getFileByte();
		
        if (fileByte == null) {
            ExceptionCast.cast(FileCode.FILE_IS_NULL);
        }
        
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
            //文件字节
            //获取文件原始名称
            String originalFilename = fileVo.getFileName();
            //获取文件扩展名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //上传文件并获得文件目录及Id
            String fileId = storageClient.upload_file1(fileByte, extName, null);
            //文件Id起始位置
            int beginIndex = fileId.lastIndexOf("/") + 1;
            //文件Id结束位置
            int endIndex = fileId.lastIndexOf(".");
            //文件id
            String id = fileId.substring(beginIndex, endIndex);
            //创建文件系统对象
            FileSystem fileSystem = new FileSystem();
            //设置文件系统属性
            fileSystem.setId(id)
                    .setFilePath(fileId)
                    .setBusinessKey(fileVo.getBusinessKey())
                    .setBusinessTag(fileVo.getBusinessTag())
                    .setFileName(originalFilename)
                    .setFileSize(fileVo.getFileSize())
                    .setFileType(fileVo.getFileType())
                    .setCreateDate(new Date())
                    .setUpdateDate(new Date());
            fileSystemMapper.insert(fileSystem);
            return id;
        } catch (Exception e) {
            log.error("上传文件失败,具体信息为:{}", e.getMessage());
            ExceptionCast.cast(FileCode.FDFS_UPLOAD_FAULT);
        }
        return null;
    
	}

}
