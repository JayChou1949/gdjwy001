package com.hirisun.cloud.system.service;

import com.hirisun.cloud.system.bean.MonitorLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 第三方接口调用监控表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-04
 */
public interface MonitorLogService extends IService<MonitorLog> {
    void saveLog();
}
