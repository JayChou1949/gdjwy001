package com.hirisun.cloud.order.bean.servicePublish;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 服务用户评价回复
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@TableName("TB_SERVICE_FEEDBACK_REPLY")
@ApiModel(value="ServiceFeedbackReply对象", description="服务用户评价回复")
public class ServiceFeedbackReply implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "评价ID")
    @TableField("FEEDBACK_ID")
    private String feedbackId;

    @ApiModelProperty(value = "回复内容")
    @TableField("CONTENT")
    private String content;

    @ApiModelProperty(value = "回复人IDCARD")
    @TableField("CREATOR")
    private String creator;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("MODIFIED_TIME")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "回复人姓名")
    @TableField("CREATOR_NAME")
    private String creatorName;

    @ApiModelProperty(value = "回复类型：1-回复评价；2-回复回复")
    @TableField("REPLY_TYPE")
    private String replyType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
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
