package com.hirisun.cloud.user.mapper;

import com.hirisun.cloud.user.bean.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * permission 权限表 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> listRoleMenu(@Param("param") Map map);

    List<Permission> listUserMenu(@Param("param") Map map);

}
