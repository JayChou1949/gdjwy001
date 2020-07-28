package com.hirisun.cloud.user.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户-角色关联表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
@TableName("S_USER_ROLE")
@ApiModel(value="UserRole对象", description="用户-角色关联表")
public class UserRole implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("USER_ID")
    private String userId;

    @TableField("ROLE_ID")
    private String roleId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
        "userId=" + userId +
        ", roleId=" + roleId +
        "}";
    }
}
