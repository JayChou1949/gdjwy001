package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Monitor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2019-04-03
 */
public interface MonitorMapper extends BaseMapper<Monitor> {

    Page<Monitor> getPage(Page<Monitor> page
            , @Param("status") String status,@Param("keywords") String keywords);
}
