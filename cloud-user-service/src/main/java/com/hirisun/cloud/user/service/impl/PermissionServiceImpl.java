package com.hirisun.cloud.user.service.impl;

import com.hirisun.cloud.user.bean.Permission;
import com.hirisun.cloud.user.mapper.PermissionMapper;
import com.hirisun.cloud.user.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * permission 权限表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> listRoleMenu(Map map) {
        return permissionMapper.listRoleMenu(map);
    }

    @Override
    public List<Permission> listUserMenu(Map map) {
        return permissionMapper.listUserMenu(map);
    }


}
