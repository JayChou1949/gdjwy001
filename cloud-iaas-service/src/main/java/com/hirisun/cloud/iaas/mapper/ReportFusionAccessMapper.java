package com.hirisun.cloud.iaas.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.iaas.bean.ReportFusionAccess;

/**
 */
public interface ReportFusionAccessMapper extends BaseMapper<ReportFusionAccess> {

    List<ReportFusionAccess> getStatisticsData(String appInfoId);

}
