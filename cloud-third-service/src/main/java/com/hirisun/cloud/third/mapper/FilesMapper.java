package com.hirisun.cloud.third.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.third.bean.Files;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-10-25
 */
public interface FilesMapper extends BaseMapper<Files> {

    Files getFileById(@Param("id") String id);

    List<Files> getFileListById(@Param("id") String id);
}
