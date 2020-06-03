package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Monitor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.request.MonitorPageRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huru
 * @since 2019-04-03
 */
public interface IMonitorService extends IService<Monitor> {

    Page<Monitor> getPage(MonitorPageRequest monitorPageRequest);
}
