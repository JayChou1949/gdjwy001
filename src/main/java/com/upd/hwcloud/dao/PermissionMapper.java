package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * permission 权限表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-11-19
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 获取角色权限菜单
     * @param rid
     * @param pid
     * @param type
     * @return
     */
    List<Permission> selectRoleMenu(@Param("rid") String rid, @Param("pid") String pid, @Param("type") Integer type);

    /**
     * 获取用户权限菜单
     * @param uid
     * @param pid
     * @param type
     * @return
     */
    List<Permission> selectUserMenu(@Param("uid") String uid, @Param("pid") String pid, @Param("type") Integer type);


}
