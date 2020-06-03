package com.upd.hwcloud.dao.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.application.ResourceRecover;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.vo.resourceRecover.ResourceRecoverResponse;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 资源回收列表 Mapper 接口
 * </p>
 *
 * @author yyc
 * @since 2020-05-14
 */
public interface ResourceRecoverMapper extends BaseMapper<ResourceRecover> {

    IPage<ResourceRecoverResponse> groupPage(IPage<ResourceRecoverResponse> page, @Param("p") Map<String,String> params);

}
