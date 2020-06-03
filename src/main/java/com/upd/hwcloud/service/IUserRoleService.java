package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.UserRole;


public interface IUserRoleService extends IService<UserRole> {

    public void userRole(String user, String roles);
}
