package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.dao.LogMapper;
import com.upd.hwcloud.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-10-17
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public IPage<Log> getPage(int pageNum, int pageSize, String startDate, String endDate) {

        return getPage(pageNum, pageSize, startDate,endDate, null);
    }

    @Override
    public IPage<Log> getPage(int pageNum, int pageSize, String startDate, String endDate, String userId) {
        Page<Log> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = logMapper.getPage(page, startDate, endDate,userId);
        return page;
    }

}
