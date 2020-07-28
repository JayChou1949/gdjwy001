package com.hirisun.cloud.user.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * role_permission 角色权限表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
@TableName("S_ROLE_PERMISSION")
@ApiModel(value="RolePermission对象", description="role_permission 角色权限表")
public class RolePermission implements Serializable {

    private static final long serialVersionUID=1L;


    @ApiModelProperty(value = "角色ID")
    @TableField("RID")
    private String rid;

    @ApiModelProperty(value = "权限ID")
    @TableField("PID")
    private String pid;

    @ApiModelProperty(value = "创建时间")
    @TableField(value="CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value="MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String toString() {
        return "RolePermission{" +
        "rid=" + rid +
        ", pid=" + pid +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
