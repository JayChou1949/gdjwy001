package com.hirisun.cloud.system.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.system.ResourceRecoverResponseVo;
import com.hirisun.cloud.system.bean.ResourceRecover;

/**
 * 资源回收列表 Mapper 接口
 */
public interface ResourceRecoverMapper extends BaseMapper<ResourceRecover> {

    IPage<ResourceRecoverResponseVo> groupPage(IPage<ResourceRecoverResponseVo> page, @Param("p") Map<String,String> params);

}
