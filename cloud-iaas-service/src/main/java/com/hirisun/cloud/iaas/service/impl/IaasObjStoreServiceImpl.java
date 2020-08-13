package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.store.IaasObjStore;
import com.hirisun.cloud.iaas.mapper.store.IaasObjStoreMapper;
import com.hirisun.cloud.iaas.service.IaasObjStoreService;
import com.hirisun.cloud.model.iaas.vo.IaasObjStoreVo;

/**
 * IaaS 对象存储申请信息 服务实现类
 */
@Service
public class IaasObjStoreServiceImpl extends 
	ServiceImpl<IaasObjStoreMapper,IaasObjStore> implements IaasObjStoreService {

	/**
	 * 根据字段排序
	 */
	public List<IaasObjStoreVo> find(IaasObjStoreVo iaasObjStoreVo) {
		
		boolean orderByAsc = iaasObjStoreVo.isOrderByAsc();
		
		List<IaasObjStore> list = null;
		
		if(orderByAsc) {
			list = this.list(new QueryWrapper<IaasObjStore>().eq(iaasObjStoreVo.getOrderByEqField(), 
					iaasObjStoreVo.getOrderByEqFieldValue())
			.orderByAsc(iaasObjStoreVo.getOrderByField()));
		}else {
			list = this.list(new QueryWrapper<IaasObjStore>().eq(iaasObjStoreVo.getOrderByEqField(), 
					iaasObjStoreVo.getOrderByEqFieldValue())
			.orderByDesc(iaasObjStoreVo.getOrderByField()));
		}
		
		if(CollectionUtils.isNotEmpty(list)) {
			List<IaasObjStoreVo> voList = JSON.parseObject(JSON.toJSONString(list),
					new TypeReference<List<IaasObjStoreVo>>(){});
			return voList;
		}
		
		return null;
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void save(IaasObjStoreVo iaasObjStoreVo) {
		
		IaasObjStore iaasObjStore = new IaasObjStore();
		BeanUtils.copyProperties(iaasObjStoreVo, iaasObjStore);
		this.save(iaasObjStore);
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(IaasObjStoreVo iaasObjStoreVo) {
		IaasObjStore iaasObjStore = new IaasObjStore();
		BeanUtils.copyProperties(iaasObjStoreVo, iaasObjStore);
		this.save(iaasObjStore);
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void removeByAppInfo(String appInfoId) {
		this.remove(new QueryWrapper<IaasObjStore>().lambda()
				.eq(IaasObjStore::getAppInfoId, appInfoId));
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void removeByShoppingCart(String shoppingCartId) {
		this.remove(new QueryWrapper<IaasObjStore>().lambda()
				.eq(IaasObjStore::getShoppingCartId, shoppingCartId));
		
	}

}
