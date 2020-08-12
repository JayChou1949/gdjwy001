package com.hirisun.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.user.bean.User;
import com.hirisun.cloud.user.mapper.UserMapper;
import com.hirisun.cloud.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<User> getPage(IPage<User> page, Map map) {
        return userMapper.getPage(page, map);
    }


    /**
     * 修改用户类型
     *
     * @param userId          身份证
     * @param type            用户类型
     * @param areas           地市
     * @param policeCategory  警种
     * @param nationalProject 国家专项
     * @param defaultTenant   是否第一管理员 1是 0否
     */
    @Transactional
    @Override
    public void editUserType(String userId, Long type, String areas, String policeCategory, String nationalProject, String defaultTenant) {
        // 清空租户管理员对应的警种或地市
        if (!UserVO.USER_TYPE_TENANT_MANAGER.equals(type)) {
            this.update(new User(), new UpdateWrapper<User>().lambda()
                    .eq(User::getIdCard, userId)
                    .set(User::getType, type)
                    .set(User::getTenantArea, null)
                    .set(User::getTenantPoliceCategory, null)
                    .set(User::getDefaultTenant, null));
        } else {
            if ("省厅".equals(policeCategory)) {
                new Exception("地市不能设置为省厅");
            }
            if ("1".equals(defaultTenant)) {
                LambdaQueryWrapper<User> checkUser = new QueryWrapper<User>().lambda()
                        .eq(User::getType, UserVO.USER_TYPE_TENANT_MANAGER)
                        .eq(User::getDefaultTenant, "1")
                        .ne(User::getIdCard, userId);
                LambdaUpdateWrapper<User> updateUser = new UpdateWrapper<User>().lambda()
                        .eq(User::getIdCard, userId)
                        .set(User::getType, type)
                        .set(User::getDefaultTenant, "1");
                if (StringUtils.isNotEmpty(areas)) {
                    User u = this.getOne(checkUser.eq(User::getTenantArea, areas));
                    if (u != null) {
                        new Exception("该地市已经设置过第一租户管理员: " + u.getName());
                    }
                    this.update(new User(), updateUser
                            .set(User::getTenantArea, areas)
                            .set(User::getTenantPoliceCategory, null)
                            .set(User::getNationalProject, null));
                } else if (StringUtils.isNotEmpty(policeCategory)) {
                    User u = this.getOne(checkUser.eq(User::getPoliceCategory, policeCategory));
                    if (u != null) {
                        new Exception("该警种已经设置过第一租户管理员: " + u.getName());
                    }
                    this.update(new User(), updateUser
                            .set(User::getTenantArea, null)
                            .set(User::getTenantPoliceCategory, policeCategory)
                            .set(User::getNationalProject, null));
                } else if (StringUtils.isNotEmpty(nationalProject)) { //如果国家专项
                    User u = this.getOne(checkUser.eq(User::getNationalProject, nationalProject));
                    if (u != null) {
                        new Exception("该专项已经设置过第一租户管理员: " + u.getName());
                    }
                    this.update(new User(), updateUser
                            .set(User::getTenantArea, null)
                            .set(User::getTenantPoliceCategory, null)
                            .set(User::getNationalProject, nationalProject));
                }
            } else {
                LambdaUpdateWrapper<User> updateUser = new UpdateWrapper<User>().lambda()
                        .eq(User::getIdCard, userId)
                        .set(User::getType, type)
                        .set(User::getDefaultTenant, "0");
                if (StringUtils.isNotEmpty(areas)) {
                    this.update(new User(), updateUser
                            .set(User::getTenantArea, areas)
                            .set(User::getTenantPoliceCategory, null)
                            .set(User::getNationalProject, null));
                } else if (StringUtils.isNotEmpty(policeCategory)) {
                    this.update(new User(), updateUser
                            .set(User::getTenantArea, null)
                            .set(User::getTenantPoliceCategory, policeCategory)
                            .set(User::getNationalProject, null));
                } else if (StringUtils.isNotEmpty(nationalProject)) {
                    this.update(new User(), updateUser
                            .set(User::getTenantArea, null)
                            .set(User::getTenantPoliceCategory, null)
                            .set(User::getNationalProject, nationalProject));
                }
            }
        }
    }
}
