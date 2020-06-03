package com.upd.hwcloud.bean.entity.webManage;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.bean.entity.User;

import java.time.LocalDateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 试点应用
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
@TableName("TB_APPLY")
public class Apply extends Model<Apply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.INPUT)
    private String id;

    /**
     * 应用名称
     */
    @TableField("NAME")
    private String name;

    /**
     * url链接
     */
    @TableField("URL")
    private String url;

    /**
     * 二级页面url
     */
    @TableField("SUB_PAGE_URL")
    private String subPageUrl;

    /**
     * 图片id
     */
    @TableField("IMAGE_ID")
    private String imageId;

    /**
     * 排序
     */
    @TableField("SORT_NUM")
    private Long sortNum;

    /**
     * 应用描述
     */
    @TableField("CONTENT")
    private String content;

    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

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
    /**
     * 二级页面查看权限 0.完全开放 1登录可看
     */
    @TableField("SUB_PAGE_PERMISSION")
    private Long subPagePermission;

    /**
     * 是否有开发文档 0:否 1:是
     */
    @TableField("HAS_DOC")
    private String hasDoc;

    //前台需要
    @TableField(exist = false)
    private String imageUrl;

    @TableField(exist = false)
    private User user;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSubPageUrl() {
        return subPageUrl;
    }

    public void setSubPageUrl(String subPageUrl) {
        this.subPageUrl = subPageUrl;
    }

    public Long getSubPagePermission() {
        return subPagePermission;
    }

    public void setSubPagePermission(Long subPagePermission) {
        this.subPagePermission = subPagePermission;
    }

    public String getHasDoc() {
        return hasDoc;
    }

    public void setHasDoc(String hasDoc) {
        this.hasDoc = hasDoc;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Apply{" +
                "id=" + id +
                ", name=" + name +
                ", url=" + url +
                ", imageId=" + imageId +
                ", sortNum=" + sortNum +
                ", content=" + content +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", status=" + status +
                ", creator=" + creator +
                "}";
    }
}
