package com.hirisun.cloud.iaas.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.iaas.bean.ReportFusionAccess;
import com.hirisun.cloud.model.system.ReportFusionAccessVo;

public interface IReportFusionAccessService extends IService<ReportFusionAccess> {

	List<ReportFusionAccessVo> doStatistics(String appInfoId);
}
