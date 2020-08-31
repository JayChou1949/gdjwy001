package com.hirisun.cloud.paas.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.pass.vo.PaasReportVo;
import com.hirisun.cloud.paas.bean.PaasReport;

public interface IPaasReportService extends IService<PaasReport> {

	List<PaasReportVo> findPaasReportByAppInfoId(String appInfoId);
	
	void remove(String appInfoId);
	
	void saveBatch(List<PaasReportVo> voList);
	
	public List<PaasReportVo> doStatistics(String appInfoId);
}
