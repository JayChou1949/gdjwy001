package com.hirisun.cloud.iaas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.iaas.bean.store.IaasObjStoreInfo;

public interface IaasObjStoreInfoService extends IService<IaasObjStoreInfo>{

	IaasObjStoreInfo getOne(String appInfoId);
}
