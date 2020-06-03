package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.Dict;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 字典 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-10-18
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 查询同一字典层级内最大排序号
     */
    Integer getMaxSort(@Param("pid") String pid);

    /**
     * 修改子集值
     */
    void updateSubDictValue(@Param("pValue") String pValue, @Param("pid") String pid);
}
