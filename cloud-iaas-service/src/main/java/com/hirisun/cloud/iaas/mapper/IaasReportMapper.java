package com.hirisun.cloud.iaas.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.iaas.bean.IaasReport;

public interface IaasReportMapper extends BaseMapper<IaasReport> {

    List<IaasReport> getStatisticsData(String appInfoId);

}
