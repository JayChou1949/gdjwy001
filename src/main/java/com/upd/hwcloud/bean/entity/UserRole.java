package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
@TableName("S_USER_ROLE")
public class UserRole extends Model<RolePermission> {
	@TableField("USER_ID")
	private String userId;
	@TableField("ROLE_ID")
	private String roleId;

	public UserRole(){

	}
	public UserRole(String userId, String roleId){
		this.userId=userId;
		this.roleId=roleId;
	}
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
}
