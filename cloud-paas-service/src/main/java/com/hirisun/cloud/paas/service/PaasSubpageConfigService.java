package com.hirisun.cloud.paas.service;

import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.paas.bean.PaasSubpageConfig;

public interface PaasSubpageConfigService {

	public void savePaasPage(UserVO user, PaasSubpageConfig iaas);

	public void updateIaasPage(UserVO user, PaasSubpageConfig iaas);
	
	public PaasSubpageConfig getDetail(String iaasId);

}
