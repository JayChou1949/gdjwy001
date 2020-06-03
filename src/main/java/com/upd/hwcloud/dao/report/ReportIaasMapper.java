package com.upd.hwcloud.dao.report;

import com.upd.hwcloud.bean.entity.report.ReportIaas;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yyc
 * @since 2020-05-19
 */
public interface ReportIaasMapper extends BaseMapper<ReportIaas> {

    List<ReportIaas> getStatisticsData(String appInfoId);

}
