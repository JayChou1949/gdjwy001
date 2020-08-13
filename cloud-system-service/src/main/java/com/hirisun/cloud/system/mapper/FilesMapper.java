package com.hirisun.cloud.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.system.bean.Files;

public interface FilesMapper extends BaseMapper<Files> {

    Files getFileById(@Param("id") String id);

    List<Files> getFileListById(@Param("id") String id);
}
