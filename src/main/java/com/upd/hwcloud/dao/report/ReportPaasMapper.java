package com.upd.hwcloud.dao.report;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.report.ReportPaas;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yyc
 * @since 2020-05-19
 */
public interface ReportPaasMapper extends BaseMapper<ReportPaas> {

    List<ReportPaas> getStatisticsData(String appInfoId);

}
