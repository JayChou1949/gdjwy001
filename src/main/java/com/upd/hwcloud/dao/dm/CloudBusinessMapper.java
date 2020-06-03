package com.upd.hwcloud.dao.dm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.dm.CloudBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yyc
 * @since 2019-08-13
 */
public interface CloudBusinessMapper extends BaseMapper<CloudBusiness> {


    Integer latest(String name);

    List<CloudBusiness> allLatest();

    Page<CloudBusiness> page(Page<CloudBusiness> page, @Param("name")String name, @Param("order") String order, @Param("orderType") String orderType, @Param("cloud") String cloud);

}
