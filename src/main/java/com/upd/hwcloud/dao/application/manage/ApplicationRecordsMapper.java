package com.upd.hwcloud.dao.application.manage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
public interface ApplicationRecordsMapper extends BaseMapper<ApplicationRecords> {
    /**
     * 通过id查询当前限额的修改记录
     * @param id
     * @return
     */
    Page<ApplicationRecords> getApplicationRecords(Page<ApplicationRecords>  page,@Param("id") String id);
}
