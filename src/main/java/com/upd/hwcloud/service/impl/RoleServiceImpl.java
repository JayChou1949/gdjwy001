package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.Role;
import com.upd.hwcloud.dao.RoleMapper;
import com.upd.hwcloud.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-10-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public IPage<Role> getPage(Integer pageNum, Integer pageSize) {
        Page<Role> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        return roleMapper.getPage(page);
    }

    @Override
    public List<Role> getList() {
        return this.list(new QueryWrapper<>());
    }

}
