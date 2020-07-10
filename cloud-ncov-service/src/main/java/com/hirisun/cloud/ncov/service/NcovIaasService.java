package com.hirisun.cloud.ncov.service;

import java.util.Map;

import com.hirisun.cloud.model.ncov.vo.iaas.NcovHomePageIaasVo;

public interface NcovIaasService {

	public Map<String,NcovHomePageIaasVo> getIaasNcovData() throws Exception;
	
}
