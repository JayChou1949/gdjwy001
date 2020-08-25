package com.hirisun.cloud.system.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.param.FilesParam;
import com.hirisun.cloud.system.bean.Files;

public interface FilesService extends IService<Files> {
    /**
     * 上传文件
     * @return
     */
    Files upload(MultipartFile file,String folder) throws IOException;

    Files uploadNcovDataFile(MultipartFile file) throws IOException;

    Files uploadNcovReport(MultipartFile file,String title) throws  IOException;

    Files uploadClusterDataFile(MultipartFile file) throws  IOException;

    Files uploadEcsDataFile(MultipartFile file) throws  IOException;

    /**
     * 防疫大数据配置文件通用上传
     * @param file 文件
     * @param module 模块名
     * @return files
     * @throws IOException
     */
    Files ncovDataAreaUpload(MultipartFile file,String module) throws  IOException;

    /**
     * 下载文件
     */
    void download(String fileId, HttpServletResponse response) throws IOException;

    Files update(MultipartFile file,String id,String folder) throws IOException;

   void refFiles(SubpageParam param);

    List<Files> getFilesByRefId(String refId);

    void hotfix();
    
    void remove(String masterId);
    
    List<FilesVo> findByRefId(String refId);
    
    void saveBatch(FilesParam filesParam);

	void deleteBatch(FilesParam param);
	
	public void updateFileRef(@RequestBody FilesParam param);
}
