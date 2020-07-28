package com.hirisun.cloud.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.user.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
public interface UserService extends IService<User> {

    /**
     * 分页查询
     */
    IPage<User> getPage(IPage<User> page, Map map);

    /**
     * 修改用户类型
     * @param userId 身份证
     * @param type 用户类型
     * @param areas  地市
     * @param policeCategory  警种
     * @param nationalProject 国家专项
     * @param defaultTenant  是否第一管理员 1是 0否
     */
    void editUserType(String userId, Long type, String areas, String policeCategory, String nationalProject,String defaultTenant);
}
