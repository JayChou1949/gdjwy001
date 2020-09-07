package com.hirisun.cloud.order.mapper.recover;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.system.ResourceRecoverResponseVo;
import com.hirisun.cloud.order.bean.recover.ResourceRecover;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 资源回收列表 Mapper 接口
 */
public interface ResourceRecoverMapper extends BaseMapper<ResourceRecover> {

    IPage<ResourceRecoverResponseVo> groupPage(IPage<ResourceRecoverResponseVo> page, @Param("p") Map<String,String> params);

}
