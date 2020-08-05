package com.hirisun.cloud.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.system.bean.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 日志 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-29
 */
public interface SysLogService extends IService<SysLog> {

    Page<SysLog> getPage(Page<SysLog> page, @Param("param") Map paramMap);

}
