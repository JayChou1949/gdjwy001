package com.hirisun.cloud.iaas.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.iaas.bean.IaasReport;
import com.hirisun.cloud.model.iaas.vo.IaasReportVo;

public interface IIaasReportService extends IService<IaasReport> {

	List<IaasReportVo> findIaasReportByAppInfoId(String appInfoId);
	
	List<IaasReportVo> doStatistics(String appInfoId);

}
