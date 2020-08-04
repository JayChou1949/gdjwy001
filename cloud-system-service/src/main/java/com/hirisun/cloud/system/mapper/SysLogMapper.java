package com.hirisun.cloud.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.system.bean.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 日志 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-29
 */
public interface SysLogMapper extends BaseMapper<SysLog> {
    Page<SysLog> getPage(Page<SysLog> page, @Param("param") Map paramMap);
}
