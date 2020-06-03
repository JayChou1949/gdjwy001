package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.RolePermission;

/**
 * <p>
 * role_permission 角色权限表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-11-19
 */
public interface IRolePermissionService extends IService<RolePermission> {
    /**
     * 保存角色权限
     * @param role
     * @param permission
     */
    public void rolePermission(String role, String permission);
}
