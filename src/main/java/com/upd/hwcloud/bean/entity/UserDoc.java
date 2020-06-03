package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户操作文档管理
 * </p>
 *
 * @author wuc
 * @since 2019-09-25
 */
@TableName("TB_USER_DOC")
public class UserDoc extends Model<UserDoc> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField("NAME")
    private String name;

        /**
     * 适用业务
     */
         @TableField("DOMAIN")
    private String domain;

        /**
     * 排序
     */
         @TableField("SORT")
    private Long sort;

        /**
     * 描述
     */
         @TableField("DESCRIPTION")
    private String description;

        /**
     * 状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除
     */
         @TableField("STATUS")
    private Long status;

        /**
     * 创建人
     */
         @TableField("CREATOR")
    private String creator;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private String fileUrl;

    @TableField(exist = false)
    private Files file;

    public String getId() {
        return id;
    }

    public UserDoc setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDoc setName(String name) {
        this.name = name;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public UserDoc setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public Long getSort() {
        return sort;
    }

    public UserDoc setSort(Long sort) {
        this.sort = sort;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UserDoc setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getStatus() {
        return status;
    }

    public UserDoc setStatus(Long status) {
        this.status = status;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public UserDoc setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public UserDoc setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public UserDoc setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Files getFile() {
        return file;
    }

    public void setFile(Files file) {
        this.file = file;
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }

    @Override
    public String toString() {
        return "UserDoc{" +
        "id=" + id +
        ", name=" + name +
        ", domain=" + domain +
        ", sort=" + sort +
        ", description=" + description +
        ", status=" + status +
        ", creator=" + creator +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
