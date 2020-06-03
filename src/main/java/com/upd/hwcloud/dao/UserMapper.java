package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-10-12
 */
public interface UserMapper extends BaseMapper<User> {

    Page<User> getPage(IPage<User> page, @Param("name") String name, @Param("type") Long type);

    /**
     * 通过身份证查询用户
     */
    User findUserByIdcard(@Param("idcard") String idcard);

    /**
     * 通过流程步骤 id获取审核人
     */
    List<User> getUserListByStepId(@Param("stepId") String stepId);

    /**
     * 通过身份证查询用户角色
     */
    List<String> getRoleIdsByUserId(@Param("idcard") String idcard);

    List<String> findRolesByUserId(String uid);
    List<String> findPermissionsByUserId(String uid);

    List<User> findUserByManufacturerId(@Param("manufacturerId") String manufacturerId);

    List<User> search(@Param("orgId") String orgId, @Param("name") String name);

}
