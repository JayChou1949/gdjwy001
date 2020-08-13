package com.hirisun.cloud.system.service;

import java.util.List;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.app.vo.FunChaVo;

public interface FunChaService {

	void save(SubpageParam param);
	
	void remove(SubpageParam param);
	
	List<FunChaVo> find(SubpageParam param);
}
