package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * role_permission 角色权限表
 * </p>
 *
 * @author wuc
 * @since 2018-11-19
 */
@TableName("S_ROLE_PERMISSION")
public class RolePermission extends Model<RolePermission> {

    private static final long serialVersionUID = 1L;

        /**
     * 角色ID
     */
         @TableField("RID")
    private String rid;

        /**
     * 权限ID
     */
         @TableField("PID")
    private String pid;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getRid() {
        return rid;
    }

    public RolePermission setRid(String rid) {
        this.rid = rid;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public RolePermission setPid(String pid) {
        this.pid = pid;
        return this;
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
    protected Serializable pkVal() {
        return null;
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
