package com.hirisun.cloud.ncov.service;

import com.hirisun.cloud.model.ncov.vo.iaas.NcovHomePageIaasVo;

public interface NcovIaasService {

	public NcovHomePageIaasVo getIaasVmData();
	
	public NcovHomePageIaasVo epidemicDesktopNum() throws Exception;
}
