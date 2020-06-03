package com.upd.hwcloud.dao;

import com.upd.hwcloud.bean.entity.SubPortalView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 二级门户浏览量 Mapper 接口
 * </p>
 *
 * @author yyc
 * @since 2019-12-20
 */
public interface SubPortalViewMapper extends BaseMapper<SubPortalView> {

    /**
     * 浏览记录加1
     * @param name 警种/地市名
     * @param type 0-警种 1-地市
     * @return
     */
    int incrementCount(@Param("name") String name,@Param("type") Integer type);

}
