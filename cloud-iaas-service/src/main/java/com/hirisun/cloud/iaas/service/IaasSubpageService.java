package com.hirisun.cloud.iaas.service;

import com.hirisun.cloud.iaas.bean.IaasSubpage;
import com.hirisun.cloud.model.user.UserVO;

public interface IaasSubpageService {

	public void saveIaasPage(UserVO user, IaasSubpage iaas);

	public void updateIaasPage(UserVO user, IaasSubpage iaas);
	
	public IaasSubpage getDetail(String iaasId);

}
