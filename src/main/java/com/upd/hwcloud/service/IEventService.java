package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Event;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 大事记 服务类
 * </p>
 *
 * @author huru
 * @since 2018-12-14
 */
public interface IEventService extends IService<Event> {

    Page<Event> getPage(Page<Event> page,String name);
}
