package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.bean.entity.User;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 服务器申请反馈
 * </p>
 *
 * @author huru
 * @since 2019-02-22
 */
@TableName("TB_APPLICATION_FEEDBACK")
public class ApplicationFeedback extends Model<ApplicationFeedback> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 审批人
     */
         @TableField("CREATOR")
    private String creator;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 结果 0:未完成 1:完成
     */
         @TableField("RESULT")
    private String result;

    @TableField("REMARK")
    private String remark;

        /**
     * 服务信息id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 满意度
     */
         @TableField("SCORE")
    private String score;

    @TableField(exist = false)
    private User user;

    public String getId() {
        return id;
    }

    public ApplicationFeedback setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public ApplicationFeedback setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ApplicationFeedback setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ApplicationFeedback setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getResult() {
        return result;
    }

    public ApplicationFeedback setResult(String result) {
        this.result = result;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ApplicationFeedback setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public ApplicationFeedback setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getScore() {
        return score;
    }

    public ApplicationFeedback setScore(String score) {
        this.score = score;
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
        return "ApplicationFeedback{" +
        "id=" + id +
        ", creator=" + creator +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", result=" + result +
        ", remark=" + remark +
        ", appInfoId=" + appInfoId +
        ", score=" + score +
        "}";
    }
}
