package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 服务用户反馈
 * </p>
 *
 * @author huru
 * @since 2019-02-15
 */
@TableName("TB_SERVICE_FEEDBACK")
public class ServiceFeedback extends Model<ServiceFeedback> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField("SERVICE_ID")
    private String serviceId;

        /**
     * 反馈内容
     */
         @TableField("CONTENT")
    private String content;

        /**
     * 功能评价
     */
         @TableField("GNPJ")
    private String gnpj;

        /**
     * 用户体验
     */
         @TableField("YHTY")
    private String yhty;

        /**
     * 是否便捷
     */
         @TableField("SFBJ")
    private String sfbj;

        /**
     * 创建人
     */
         @TableField("CREATOR")
    private String creator;
    @TableField("REPLY_COUNT")
    private Integer replyCount;
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField(exist = false)
    private User user;

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public String getId() {
        return id;
    }

    public ServiceFeedback setId(String id) {
        this.id = id;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public ServiceFeedback setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ServiceFeedback setContent(String content) {
        this.content = content;
        return this;
    }

    public String getGnpj() {
        return gnpj;
    }

    public ServiceFeedback setGnpj(String gnpj) {
        this.gnpj = gnpj;
        return this;
    }

    public String getYhty() {
        return yhty;
    }

    public ServiceFeedback setYhty(String yhty) {
        this.yhty = yhty;
        return this;
    }

    public String getSfbj() {
        return sfbj;
    }

    public ServiceFeedback setSfbj(String sfbj) {
        this.sfbj = sfbj;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public ServiceFeedback setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ServiceFeedback setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ServiceFeedback setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
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
        return "ServiceFeedback{" +
        "id=" + id +
        ", serviceId=" + serviceId +
        ", content=" + content +
        ", gnpj=" + gnpj +
        ", yhty=" + yhty +
        ", sfbj=" + sfbj +
        ", creator=" + creator +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
