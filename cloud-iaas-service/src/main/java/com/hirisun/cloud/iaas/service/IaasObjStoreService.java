package com.hirisun.cloud.iaas.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.iaas.bean.store.IaasObjStore;
import com.hirisun.cloud.model.iaas.vo.IaasObjStoreVo;

/**
 * IaaS 对象存储申请信息 服务类
 */
public interface IaasObjStoreService extends IService<IaasObjStore> {

	public List<IaasObjStoreVo> find(IaasObjStoreVo iaasObjStoreVo);
	
	public void save(IaasObjStoreVo iaasObjStoreVo);

	public void update(IaasObjStoreVo iaasObjStoreVo);
	
	public void removeByAppInfo(String appInfoId);
	
	public void removeByShoppingCart(String shoppingCartId);
}
