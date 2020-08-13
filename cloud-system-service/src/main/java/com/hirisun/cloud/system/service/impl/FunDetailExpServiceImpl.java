package com.hirisun.cloud.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.cloud.system.bean.FunDetailExp;
import com.hirisun.cloud.system.mapper.FunDetailExpMapper;
import com.hirisun.cloud.system.service.FunDetailExpService;

@Service
public class FunDetailExpServiceImpl implements FunDetailExpService {

	@Autowired
	private FunDetailExpMapper funDetailExpMapper;
	
	@Transactional(rollbackFor = Exception.class)
	public void save(FunDetailExp detailExp) {
		funDetailExpMapper.insert(detailExp);
	}

	

}
