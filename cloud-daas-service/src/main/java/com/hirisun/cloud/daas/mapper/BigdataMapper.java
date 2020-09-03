package com.hirisun.cloud.daas.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.daas.bean.Bigdata;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 大数据库服务目录 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-02
 */
public interface BigdataMapper extends BaseMapper<Bigdata> {
    Page<Bigdata> getPage(Page<Bigdata> page, @Param("name") String name, @Param("dataFrom") String dataFrom,
                          @Param("collectionUnit") String collectionUnit, @Param("category") String category);
}
