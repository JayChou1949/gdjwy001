package com.hirisun.cloud.system.service;

import java.util.List;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.app.vo.AppSceneVo;

public interface AppSceneService {

    void save(SubpageParam param);

    List<AppSceneVo> getByMasterId(String masterId);
    
    void remove(String masterId);
}
