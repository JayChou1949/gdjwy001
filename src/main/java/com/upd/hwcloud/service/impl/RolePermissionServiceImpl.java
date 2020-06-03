package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.RolePermission;
import com.upd.hwcloud.dao.RolePermissionMapper;
import com.upd.hwcloud.service.IRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * role_permission 角色权限表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-11-19
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {
    @Transactional
    @Override
    public void rolePermission(String role, String permission) {
        String[] rolePermissions=permission.split(",");
        QueryWrapper entityWrapper=new QueryWrapper();
        entityWrapper.eq("rid",role);
        this.remove(entityWrapper);//删除该角色的 所有权限
        List<RolePermission> ls=new ArrayList<>();
        for(String per:rolePermissions){
            RolePermission rolePermission=new RolePermission();
            rolePermission.setRid(role);
            rolePermission.setPid(per);
            ls.add(rolePermission);
        }
        this.saveBatch(ls);
    }
}
