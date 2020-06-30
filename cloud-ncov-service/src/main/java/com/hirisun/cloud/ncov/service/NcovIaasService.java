package com.hirisun.cloud.ncov.service;

import com.hirisun.cloud.model.ncov.vo.iaas.NcovEcsOverviewVo;

public interface NcovIaasService {

	public NcovEcsOverviewVo getOverview();
	
	public NcovEcsOverviewVo getSupport();
}
