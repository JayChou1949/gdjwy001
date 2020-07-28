package com.hirisun.cloud.user.service.impl;

import com.hirisun.cloud.user.bean.UserRole;
import com.hirisun.cloud.user.mapper.UserRoleMapper;
import com.hirisun.cloud.user.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色关联表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
