package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.Permission;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * permission 权限表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-11-19
 */
public interface IPermissionService extends IService<Permission> {

    List<Permission> selectRoleMenu(String rid, String pid, Integer type);
    List<Permission> selectUserMenu(String uid, String pid, Integer type);
}
