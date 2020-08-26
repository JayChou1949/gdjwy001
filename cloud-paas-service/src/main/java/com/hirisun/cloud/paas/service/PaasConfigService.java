package com.hirisun.cloud.paas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.paas.bean.PaasConfig;

public interface PaasConfigService {

	public String create(PaasConfig paas);
	
	public void serviceSort(String id,String ope);
	
	public void publish(UserVO user, String id, Integer result, String remark);

	public IPage<PaasConfig> getPage(IPage<PaasConfig> page, UserVO user, 
			Integer status, String name, String subType);

	public void delete(UserVO user,String id);
	
	public void edit(PaasConfig paas);
	
	public PaasConfig getDetail(UserVO user, String id);
	
	public PaasConfig setWorkflow(String id,String flowId);

}
