package com.upd.hwcloud.dao.report;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.report.ReportFusionAccess;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yyc
 * @since 2020-05-19
 */
public interface ReportFusionAccessMapper extends BaseMapper<ReportFusionAccess> {

    List<ReportFusionAccess> getStatisticsData(String appInfoId);

}
