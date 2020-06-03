package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 处理环节审核人关联表
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
@TableName("TB_FLOW_STEP_USER")
public class FlowStepUser extends Model<FlowStepUser> {

    private static final long serialVersionUID = 1L;

        /**
     * 环节 ID
     */
         @TableField("STEP_ID")
    private String stepId;

        /**
     * 处理人身份证
     */
         @TableField("USER_ID")
    private String userId;


    public String getStepId() {
        return stepId;
    }

    public FlowStepUser setStepId(String stepId) {
        this.stepId = stepId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public FlowStepUser setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "FlowStepUser{" +
        "stepId=" + stepId +
        ", userId=" + userId +
        "}";
    }
}
