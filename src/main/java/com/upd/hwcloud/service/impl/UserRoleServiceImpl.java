package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.UserRole;
import com.upd.hwcloud.dao.UserRoleMapper;
import com.upd.hwcloud.service.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Transactional
    @Override
    public void userRole(String user, String roles) {
        String[] userRoles=roles.split(",");
        QueryWrapper entityWrapper=new QueryWrapper();
        entityWrapper.eq("user_id",user);
        this.remove(entityWrapper);//删除该角色的 所有权限
        List<UserRole> ls=new ArrayList<>();
        for(String role:userRoles){
            UserRole userRole=new UserRole();
            userRole.setRoleId(role);
            userRole.setUserId(user);
            ls.add(userRole);
        }
        this.saveBatch(ls);
    }
}
