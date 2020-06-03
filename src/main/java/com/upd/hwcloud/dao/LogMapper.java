package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Log;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 日志 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-10-17
 */
public interface LogMapper extends BaseMapper<Log> {

    Page<Log> getPage(Page<Log> page, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") String userId);
}
