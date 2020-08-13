package com.hirisun.cloud.saas.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hirisun.cloud.model.daas.vo.DaasServiceOverview;
import com.hirisun.cloud.model.daas.vo.InnerServiceOverview;
import com.hirisun.cloud.model.daas.vo.ServiceOverview;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.saas.bean.SaasSubpageConfig;

public interface SaasSubpageConfigService {

	public void saveSaasPage(UserVO user, SaasSubpageConfig iaas);

	public void updateIaasPage(UserVO user, SaasSubpageConfig iaas);
	
	public SaasSubpageConfig getDetail(String iaasId);
	
	List<DaasServiceOverview> daas(String name);

    List<ServiceOverview> paas(String name);

    List<InnerServiceOverview> paasOther(String name);

}
