package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 服务用户评价回复
 * </p>
 *
 * @author zwb
 * @since 2019-06-05
 */
@TableName("TB_SERVICE_FEEDBACK_REPLY")
public class ServiceFeedbackReply extends Model<ServiceFeedbackReply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 评价ID
     */
         @TableField("FEEDBACK_ID")
    private String feedbackId;

        /**
     * 回复内容
     */
         @TableField("CONTENT")
    private String content;

        /**
     * 回复人IDCARD
     */
         @TableField("CREATOR")
    private String creator;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    /**
     * 回复人姓名
     */
         @TableField("CREATOR_NAME")
    private String creatorName;

        /**
     * 回复类型：1-回复评价；2-回复回复
     */
         @TableField("REPLY_TYPE")
    private String replyType;

    @TableField(exist = false)
    private User user;
    public String getId() {
        return id;
    }

    public ServiceFeedbackReply setId(String id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public ServiceFeedbackReply setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ServiceFeedbackReply setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public ServiceFeedbackReply setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ServiceFeedbackReply setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ServiceFeedbackReply setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public ServiceFeedbackReply setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public String getReplyType() {
        return replyType;
    }

    public ServiceFeedbackReply setReplyType(String replyType) {
        this.replyType = replyType;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ServiceFeedbackReply{" +
        "id=" + id +
        ", feedbackId=" + feedbackId +
        ", content=" + content +
        ", creator=" + creator +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", creatorName=" + creatorName +
        ", replyType=" + replyType +
        "}";
    }
}
