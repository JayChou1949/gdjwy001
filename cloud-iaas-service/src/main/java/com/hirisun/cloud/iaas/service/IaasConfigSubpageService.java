package com.hirisun.cloud.iaas.service;

import com.hirisun.cloud.iaas.bean.config.IaasSubpageConfig;
import com.hirisun.cloud.model.user.UserVO;

public interface IaasConfigSubpageService {

	public void saveIaasPage(UserVO user, IaasSubpageConfig iaas);

	public void updateIaasPage(UserVO user, IaasSubpageConfig iaas);
	
	public IaasSubpageConfig getDetail(String iaasId);

}
