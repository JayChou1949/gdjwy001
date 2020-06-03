package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.ApplyFlow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 资源申请流程配置 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
public interface ApplyFlowMapper extends BaseMapper<ApplyFlow> {

    IPage<ApplyFlow> getPage(IPage<ApplyFlow> page, @Param("name") String name);

    ApplyFlow getByResourceType(@Param("resourceType") Long resourceType, @Param("secretLevel") String secretLevel);

}
