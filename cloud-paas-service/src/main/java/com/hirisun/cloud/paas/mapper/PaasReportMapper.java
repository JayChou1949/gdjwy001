package com.hirisun.cloud.paas.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.paas.bean.PaasReport;

public interface PaasReportMapper extends BaseMapper<PaasReport> {

    List<PaasReport> getStatisticsData(String appInfoId);

}
