package com.hirisun.cloud.user.service;

import com.hirisun.cloud.user.bean.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * permission 权限表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 将系统菜单数据组装成树形结构数据
     * @return
     */
    List<Permission> listWithTree(List<Permission> list);

    /**
     * 菜单、角色菜单管理表 连表查询，根据角色id查询权限
     * @param map
     * @return
     */
    List<Permission> listRoleMenu(Map map);


    /**
     * 菜单、角色、角色菜单管理表 连表查询，根据用户id查询菜单列表
     */
    List<Permission> listUserMenu(@Param("param") Map map);

}
