package com.upd.hwcloud.dao;

import com.upd.hwcloud.bean.entity.Files;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-10-25
 */
public interface FilesMapper extends BaseMapper<Files> {

    Files getFileById(String id);
}
