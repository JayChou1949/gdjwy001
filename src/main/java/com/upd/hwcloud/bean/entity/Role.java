package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author wuc
 * @since 2018-10-18
 */
@TableName("SYS_ROLE")
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 序号
     */
         @TableField("NUM")
    private Integer num;

        /**
     * 父角色ID
     */
         @TableField("PID")
    private String pid;

        /**
     * 角色名称
     */
         @TableField("NAME")
    private String name;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField("DESCRIPTION")
    private String description;


    public String getId() {
        return id;
    }

    public Role setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getNum() {
        return num;
    }

    public Role setNum(Integer num) {
        this.num = num;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public Role setPid(String pid) {
        this.pid = pid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Role setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public Role setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Role{" +
        "id=" + id +
        ", num=" + num +
        ", pid=" + pid +
        ", name=" + name +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", description=" + description +
        "}";
    }
}
