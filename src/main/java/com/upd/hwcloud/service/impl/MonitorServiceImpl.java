package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Monitor;
import com.upd.hwcloud.dao.MonitorMapper;
import com.upd.hwcloud.request.MonitorPageRequest;
import com.upd.hwcloud.service.IMonitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huru
 * @since 2019-04-03
 */
@Service
public class MonitorServiceImpl extends ServiceImpl<MonitorMapper, Monitor> implements IMonitorService {

    @Autowired
    private MonitorMapper monitorMapper;

    @Override
    public Page<Monitor> getPage(MonitorPageRequest monitorPageRequest) {
        Page<Monitor> page = new Page<>();
        String keyWords = monitorPageRequest.getKeyWords();
        String status = monitorPageRequest.getStatus();
        if(monitorPageRequest.getPageNum() == null || monitorPageRequest.getPageNum() <= 0) {
            monitorPageRequest.setPageNum(1);
        }
        if(monitorPageRequest.getPageSize() == null || monitorPageRequest.getPageSize() <= 0) {
            monitorPageRequest.setPageSize(20);
        }
        page.setCurrent(monitorPageRequest.getPageNum());
        page.setSize(monitorPageRequest.getPageSize());
        return monitorMapper.getPage(page,status,keyWords);
    }
}
