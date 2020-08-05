package com.hirisun.cloud.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.system.bean.SysLog;
import com.hirisun.cloud.system.mapper.SysLogMapper;
import com.hirisun.cloud.system.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 日志 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-29
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public Page<SysLog> getPage(Page<SysLog> page, Map paramMap) {
        return sysLogMapper.getPage(page,paramMap);
    }
}
