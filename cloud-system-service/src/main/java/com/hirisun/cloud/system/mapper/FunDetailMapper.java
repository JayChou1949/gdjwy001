package com.hirisun.cloud.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.system.bean.FunDetail;

public interface FunDetailMapper extends BaseMapper<FunDetail> {

    List<FunDetail> getByMasterId(String masterId);
}
