package com.hirisun.cloud.paas.service;

import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.paas.bean.PaasSubpage;

public interface PaasSubpageService {

	public void savePaasPage(UserVO user, PaasSubpage iaas);

	public void updateIaasPage(UserVO user, PaasSubpage iaas);
	
	public PaasSubpage getDetail(String iaasId);

}
