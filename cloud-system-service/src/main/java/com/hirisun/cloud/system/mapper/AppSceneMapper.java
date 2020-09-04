package com.hirisun.cloud.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.system.bean.AppScene;

public interface AppSceneMapper extends BaseMapper<AppScene> {

    List<AppScene> getByMasterId(String iaasId);
}
