package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Event;
import com.upd.hwcloud.dao.EventMapper;
import com.upd.hwcloud.service.IEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 大事记 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-12-14
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements IEventService {

    @Autowired
    private EventMapper eventMapper;

    @Override
    public Page<Event> getPage(Page<Event> page, String name) {
        return eventMapper.getPage(page,name);
    }
}
