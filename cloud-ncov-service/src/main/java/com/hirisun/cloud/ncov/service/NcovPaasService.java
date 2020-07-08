package com.hirisun.cloud.ncov.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterAppVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterOverviewVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterResourceVo;

public interface NcovPaasService {

	public NcovClusterOverviewVo getOverview();
	
	public List<NcovClusterResourceVo> getResource();
	
	public Page<NcovClusterAppVo> getAppDetailList(long pageSize,long current);
}
