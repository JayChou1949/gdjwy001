package com.upd.hwcloud.dao.dm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.entity.dm.CloudBusinessTop;
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
public interface CloudBusinessTopMapper extends BaseMapper<CloudBusinessTop> {
    List<GeneralDTO> policeApplication();
    List<GeneralDTO> areaApplication();

    List<GeneralDTO> applicationCount();

    Page<CloudBusinessTop> page(Page<CloudBusinessTop> page, @Param("name")String name, @Param("order") String order, @Param("orderType") String orderType, @Param("cloud") String cloud);

}
