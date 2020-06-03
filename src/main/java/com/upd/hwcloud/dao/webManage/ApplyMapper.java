package com.upd.hwcloud.dao.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Apply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.webManage.Case;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 试点应用 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
public interface ApplyMapper extends BaseMapper<Apply> {

    Page<Apply> getPage(Page<Apply> page, @Param("user") User user, @Param("status") Integer status, @Param("name") String name);

    Page<Apply> getApplyByPage(Page<Apply> page);

}
