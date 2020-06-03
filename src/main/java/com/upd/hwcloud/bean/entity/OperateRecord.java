package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 操作记录
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
@TableName("TB_OPERATE_RECORD")
public class OperateRecord extends Model<OperateRecord> {

    private static final long serialVersionUID = 1L;

        /**
     *  
     */
         @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 记录目标 ID
     */
         @TableField("TARGET_ID")
    private String targetId;

        /**
     * 操作人
     */
         @TableField("OPERATOR")
    private String operator;

    /**
     * 操作
     */
    @TableField("OPERATE")
    private String operate;

        /**
     * 结果
     */
         @TableField("RESULT")
    private String result;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField(exist = false)
    private User user;

    /**
     * 是否第一次上线 1.是
     */
    @TableField("FIRST")
    private Long first;

    public String getId() {
        return id;
    }

    public OperateRecord setId(String id) {
        this.id = id;
        return this;
    }

    public String getTargetId() {
        return targetId;
    }

    public OperateRecord setTargetId(String targetId) {
        this.targetId = targetId;
        return this;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getOperator() {
        return operator;
    }

    public OperateRecord setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public String getResult() {
        return result;
    }

    public OperateRecord setResult(String result) {
        this.result = result;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public OperateRecord setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public OperateRecord setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public OperateRecord setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getFirst() {
        return first;
    }

    public void setFirst(Long first) {
        this.first = first;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OperateRecord{" +
        "id=" + id +
        ", targetId=" + targetId +
        ", operate=" + operate +
        ", operator=" + operator +
        ", result=" + result +
        ", remark=" + remark +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
