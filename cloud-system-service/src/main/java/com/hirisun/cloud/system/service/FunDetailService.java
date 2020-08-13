package com.hirisun.cloud.system.service;

import java.util.List;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.app.vo.FunDetailVo;

public interface FunDetailService {

	void save(SubpageParam param);

    List<FunDetailVo> getByMasterId(SubpageParam param);
    
    void remove(SubpageParam param);
}
