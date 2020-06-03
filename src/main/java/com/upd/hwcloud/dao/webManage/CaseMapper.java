package com.upd.hwcloud.dao.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Case;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 典型案例 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-10-25
 */
public interface CaseMapper extends BaseMapper<Case> {

    Page<Case> getPage(Page<Case> page, @Param("user") User user, @Param("status") Integer status, @Param("name") String name);

    Page<Case> getCaseByPage(Page<Case> page);

}
