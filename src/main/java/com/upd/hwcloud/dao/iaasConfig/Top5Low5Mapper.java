package com.upd.hwcloud.dao.iaasConfig;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.iaasConfig.Top5Low5;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 全网 警种 地区 top5 low5 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-11-17
 */
public interface Top5Low5Mapper extends BaseMapper<Top5Low5> {

    List<Top5Low5> getByType(@Param("type") String type, @Param("column") String column);
}
