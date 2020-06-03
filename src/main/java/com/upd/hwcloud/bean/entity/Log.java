package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 日志
 * </p>
 *
 * @author wuc
 * @since 2018-10-17
 */
@TableName("SYS_LOG")
public class Log extends Model<Log> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 用户ID
     */
         @TableField("CREATOR_ID")
    private String creatorId;

        /**
     * 创建时间
     */
        @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

        /**
     * 操作位置
     */
         @TableField("PATH")
    private String path;

    @TableField("IP")
    private String ip;

    /**
     * 创建人姓名
     */
    @TableField(exist = false)
    private String creatorName;

    public Log() {
    }

    public Log(String creatorId, String remark, String path, String ip) {
        this.creatorId = creatorId;
        this.remark = remark;
        this.path = path;
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public Log setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public Log setCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Log setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Log setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Log setPath(String path) {
        this.path = path;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Log setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Log{" +
        "id=" + id +
        ", creatorId=" + creatorId +
        ", createTime=" + createTime +
        ", remark=" + remark +
        ", path=" + path +
        ", ip=" + ip +
        "}";
    }
}
