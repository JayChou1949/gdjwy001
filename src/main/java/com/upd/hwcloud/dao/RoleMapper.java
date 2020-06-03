package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Role;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-10-18
 */
public interface RoleMapper extends BaseMapper<Role> {

    IPage<Role> getPage(Page<Role> page);
}
