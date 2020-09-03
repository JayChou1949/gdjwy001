package com.hirisun.cloud.order.bean.servicePublish;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 服务用户反馈
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@TableName("TB_SERVICE_FEEDBACK")
@ApiModel(value="ServiceFeedback对象", description="服务用户反馈")
public class ServiceFeedback implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @TableField("SERVICE_ID")
    private String serviceId;

    @ApiModelProperty(value = "反馈内容")
    @TableField("CONTENT")
    private String content;

    @ApiModelProperty(value = "功能评价")
    @TableField("GNPJ")
    private String gnpj;

    @ApiModelProperty(value = "用户体验")
    @TableField("YHTY")
    private String yhty;

    @ApiModelProperty(value = "是否便捷")
    @TableField("SFBJ")
    private String sfbj;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATOR")
    private String creator;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFIED_TIME")
    private Date modifiedTime;

    @ApiModelProperty(value = "回复数量")
    @TableField("REPLY_COUNT")
    private Long replyCount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGnpj() {
        return gnpj;
    }

    public void setGnpj(String gnpj) {
        this.gnpj = gnpj;
    }

    public String getYhty() {
        return yhty;
    }

    public void setYhty(String yhty) {
        this.yhty = yhty;
    }

    public String getSfbj() {
        return sfbj;
    }

    public void setSfbj(String sfbj) {
        this.sfbj = sfbj;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public Long getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Long replyCount) {
        this.replyCount = replyCount;
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
        ", replyCount=" + replyCount +
        "}";
    }
}
