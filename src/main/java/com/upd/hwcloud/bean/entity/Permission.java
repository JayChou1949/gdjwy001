package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * permission 权限表
 * </p>
 *
 * @author wuc
 * @since 2018-11-19
 */
@TableName("S_PERMISSION")
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

        /**
     * 主键
     */
         @TableId("ID")
    private String id;

        /**
     * 上级ID
     */
         @TableField("PID")
    private String pid;

        /**
     * 权限名
     */
         @TableField("NAME")
    private String name;

        /**
     * 类型 0、菜单 1、功能
     */
         @TableField("TYPE")
    private Integer type;

        /**
     * 排序
     */
         @TableField("SORT")
    private Integer sort;

        /**
     * 地址
     */
         @TableField("URL")
    private String url;

        /**
     * 权限编码
     */
         @TableField("PERM_CODE")
    private String permCode;

        /**
     * 图标
     */
         @TableField("ICON")
    private String icon;

        /**
     * 描述
     */
         @TableField("DESCRIPTION")
    private String description;

        /**
     * 状态 0、禁用 1、正常
     */
         @TableField("STATUS")
    private Integer status;


    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;
    @TableField(exist = false)
    private List<Permission> children;
    public String getId() {
        return id;
    }

    public Permission setId(String id) {
        this.id = id;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public Permission setPid(String pid) {
        this.pid = pid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Permission setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public Permission setType(Integer type) {
        this.type = type;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public Permission setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Permission setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPermCode() {
        return permCode;
    }

    public Permission setPermCode(String permCode) {
        this.permCode = permCode;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Permission setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public List<Permission> getChildren() {
        return children;
    }

    public void setChildren(List<Permission> children) {
        this.children = children;
    }

    public String getDescription() {
        return description;
    }

    public Permission setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Permission setStatus(Integer status) {
        this.status = status;
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
        return this.id;
    }

    @Override
    public String toString() {
        return "Permission{" +
        "id=" + id +
        ", pid=" + pid +
        ", name=" + name +
        ", type=" + type +
        ", sort=" + sort +
        ", url=" + url +
        ", permCode=" + permCode +
        ", icon=" + icon +
        ", description=" + description +
        ", status=" + status +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
    public void addChild(Permission permission){
        if (null==children){
            children = new ArrayList();
        }
        children.add(permission);
    }

}
