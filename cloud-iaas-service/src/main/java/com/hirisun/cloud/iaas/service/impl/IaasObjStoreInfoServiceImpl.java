package com.hirisun.cloud.iaas.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.iaas.bean.store.IaasObjStoreInfo;
import com.hirisun.cloud.iaas.service.IaasObjStoreInfoService;

@Service
public class IaasObjStoreInfoServiceImpl implements IaasObjStoreInfoService {

	@Override
	public boolean saveBatch(Collection<IaasObjStoreInfo> entityList, int batchSize) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveOrUpdateBatch(Collection<IaasObjStoreInfo> entityList, int batchSize) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBatchById(Collection<IaasObjStoreInfo> entityList, int batchSize) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveOrUpdate(IaasObjStoreInfo entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IaasObjStoreInfo getOne(Wrapper<IaasObjStoreInfo> queryWrapper, boolean throwEx) {
		return null;
	}

	@Override
	public Map<String, Object> getMap(Wrapper<IaasObjStoreInfo> queryWrapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V> V getObj(Wrapper<IaasObjStoreInfo> queryWrapper, Function<? super Object, V> mapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseMapper<IaasObjStoreInfo> getBaseMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IaasObjStoreInfo getOne(String appInfoId) {
		
		IaasObjStoreInfo iaasObjStoreInfo = this.getOne(new QueryWrapper<IaasObjStoreInfo>()
				.eq("APP_INFO_ID", appInfoId));
		
		return iaasObjStoreInfo;
		
	}

}
