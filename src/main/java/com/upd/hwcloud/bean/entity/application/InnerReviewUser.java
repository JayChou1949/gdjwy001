package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 部门内审核关联表
 * </p>
 *
 * @author wuc
 * @since 2018-12-19
 */
@TableName("TB_INNER_REVIEW_USER")
public class InnerReviewUser extends Model<InnerReviewUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 用户 id（身份证）
     */
         @TableField("USER_ID")
    private String userId;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;


    public String getId() {
        return id;
    }

    public InnerReviewUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public InnerReviewUser setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public InnerReviewUser setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "InnerReviewUser{" +
        "id=" + id +
        ", userId=" + userId +
        ", appInfoId=" + appInfoId +
        "}";
    }
}
