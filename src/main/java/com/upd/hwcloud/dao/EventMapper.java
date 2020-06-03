package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Event;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 大事记 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-12-14
 */
public interface EventMapper extends BaseMapper<Event> {

    Page<Event> getPage(Page<Event> page,@Param("name") String name);
}
