package com.upd.hwcloud.bean.entity.webManage;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.bean.entity.User;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 典型案例
 * </p>
 *
 * @author huru
 * @since 2018-10-25
 */
@TableName("TB_CASE")
public class Case extends Model<Case> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.INPUT)
    private String id;

    /**
     * 案例名称
     */
    @TableField("NAME")
    private String name;

    /**
     * url链接
     */
    @TableField("URL")
    private String url;

    /**
     * 案例描述
     */
    @TableField("CONTENT")
    private String content;

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
     * 创建时间
     */
    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 状态
     */
    @TableField("STATUS")
    private Long status;

    /**
     * 创建人
     */
    @TableField("CREATOR")
    private String creator;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "Case{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", imageId='" + imageId + '\'' +
                ", sortNum=" + sortNum +
                ", createTime=" + createTime +
                ", modifiedTIme=" + modifiedTime +
                ", status=" + status +
                ", creator='" + creator + '\'' +
                '}';
    }
}
