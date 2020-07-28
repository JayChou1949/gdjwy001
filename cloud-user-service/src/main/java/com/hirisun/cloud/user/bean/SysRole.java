package com.hirisun.cloud.user.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
@TableName("SYS_ROLE")
@ApiModel(value="SysRole对象", description="角色")
public class SysRole implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "序号")
    @TableField("NUM")
    private Integer num;

    @ApiModelProperty(value = "父角色ID")
    @TableField("PID")
    private String pid;

    @ApiModelProperty(value = "角色名称")
    @TableField("NAME")
    private String name;

    @TableField(value="CREATE_TIME", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(value="MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date modifiedTime;

    @ApiModelProperty(value = "描述")
    @TableField("DESCRIPTION")
    private String description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SysRole{" +
        "id=" + id +
        ", num=" + num +
        ", pid=" + pid +
        ", name=" + name +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", description=" + description +
        "}";
    }
}
