package com.hirisun.cloud.third.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.third.bean.Files;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huru
 * @since 2018-10-25
 */
public interface IFilesService extends IService<Files> {
//    /**
//     * 上传文件
//     * @return
//     */
//    Files upload(MultipartFile file, String folder) throws IOException;
//
//    Files uploadNcovDataFile(MultipartFile file) throws IOException;
//
//    Files uploadNcovReport(MultipartFile file, String title) throws  IOException;
//
//    Files uploadClusterDataFile(MultipartFile file) throws  IOException;
//
//    Files uploadEcsDataFile(MultipartFile file) throws  IOException;
//
//    /**
//     * 防疫大数据配置文件通用上传
//     * @param file 文件
//     * @param module 模块名
//     * @return files
//     * @throws IOException
//     */
//    Files ncovDataAreaUpload(MultipartFile file, String module) throws  IOException;
//
//    /**
//     * 下载文件
//     */
//    void download(String fileId, HttpServletResponse response) throws IOException;
//
//    Files update(MultipartFile file, String id, String folder) throws IOException;
//
//   void refFiles(List<Files> files, String refId);
//
//    List<Files> getFilesByRefId(String refId);
//
//    void hotfix();
}
