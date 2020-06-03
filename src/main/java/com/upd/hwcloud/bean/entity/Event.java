package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 大事记
 * </p>
 *
 * @author huru
 * @since 2018-12-14
 */
@TableName("TB_EVENT")
public class Event extends Model<Event> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    /**
     * 事件名称
     */
    @TableField("NAME")
    private String name;

    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 创建人
     */
    @TableField("CREATOR")
    private String creator;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("HAPPEN_TIME")
    private Date happenTime;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    //首页显示
    @TableField(exist = false)
    private String showDate;

    @TableField(exist = false)
    private User user;

    public String getId() {
        return id;
    }

    public Event setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        this.name = name;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Event setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public Event setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Event setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Date getHappenTime() {
        return happenTime;
    }

    public Event setHappenTime(Date happenTime) {
        this.happenTime = happenTime;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Event setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
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
        return "Event{" +
                "id=" + id +
                ", name=" + name +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", creator=" + creator +
                ", happenTime=" + happenTime +
                ", remark=" + remark +
                "}";
    }
}
