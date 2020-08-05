package com.hirisun.cloud.system.service.impl;

import com.hirisun.cloud.api.redis.RedisApi;
import com.hirisun.cloud.system.bean.MonitorLog;
import com.hirisun.cloud.system.mapper.MonitorLogMapper;
import com.hirisun.cloud.system.service.MonitorLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方接口调用监控表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-04
 */
@Service
public class MonitorLogServiceImpl extends ServiceImpl<MonitorLogMapper, MonitorLog> implements MonitorLogService {

    @Autowired
    private RedisApi redisApi;

    @Override
    public void saveLog() {

    }
}
