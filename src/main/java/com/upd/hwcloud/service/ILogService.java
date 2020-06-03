package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.Log;

import java.util.Date;

/**
 * <p>
 * 日志 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-10-17
 */
public interface ILogService extends IService<Log> {

    IPage<Log> getPage(int pageNum, int pageSize, String startDate, String endDate);
    IPage<Log> getPage(int pageNum, int pageSize, String startDate, String endDate,String userId);
}
