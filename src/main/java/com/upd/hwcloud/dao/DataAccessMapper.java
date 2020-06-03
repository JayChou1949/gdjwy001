package com.upd.hwcloud.dao;

import com.upd.hwcloud.bean.entity.DataAccess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 数据接入表 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2019-03-13
 */
public interface DataAccessMapper extends BaseMapper<DataAccess> {

    /**
     * 通过是否需要历史记录查询列表
     * @return
     */
    List<DataAccess> findListByHistory(String creator);

    /**
     * 详情
     * @return
     */
    DataAccess details(String id);
}
