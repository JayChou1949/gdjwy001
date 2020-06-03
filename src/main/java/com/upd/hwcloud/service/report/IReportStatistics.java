package com.upd.hwcloud.service.report;

import java.util.List;

/**
 * @author yyc
 * @date 2020/5/21
 */
public interface IReportStatistics<T> {

    /**
     * 统计数据
     * @param appInfoId 申请单ID
     * @return 统计数据
     */
    List<T> doStatistics(String appInfoId);
}
