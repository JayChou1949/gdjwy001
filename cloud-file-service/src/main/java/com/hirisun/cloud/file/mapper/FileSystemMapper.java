package com.hirisun.cloud.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.file.bean.FileSystem;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhoufeng
 * @version 1.0
 * @className FileSystemMapper
 * @data 2020/7/22 17:57
 * @description 文件系统持久化接口
 */
public interface FileSystemMapper extends BaseMapper<FileSystem> {

    /**
     * 根据文件Id 获得文件路径信息
     * @param id 文件Id
     * @return 文件路径
     */
    String getFilePathById(@Param("id") String id);
}
