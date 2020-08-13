package com.hirisun.cloud.system.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;


@TableName("T_FUN_CHA")
public class FunCha implements Serializable {

	private static final long serialVersionUID = -1237900938170616753L;

	@TableId(value = "ID",type = IdType.UUID)
    private String id;

    @TableField("MASTER_ID")
    private String masterId;

        /**
     * 图标
     */ @ApiModelProperty(value = "图标")
         @TableField("IMAGE")
    private String image;

        /**
     * 标题
     */ @ApiModelProperty(value = "标题")
         @TableField("TITLE")
    private String title;

        /**
     * 描述
     */ @ApiModelProperty(value = "描述")
         @TableField("DESCRIPTION")
    private String description;

    @TableField("STATUS")
    private String status;

    @TableField("REMARK")
    private String remark;


    public String getId() {
        return id;
    }

    public FunCha setId(String id) {
        this.id = id;
        return this;
    }

    public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public String getImage() {
        return image;
    }

    public FunCha setImage(String image) {
        this.image = image;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FunCha setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FunCha setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public FunCha setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public FunCha setRemark(String remark) {
        this.remark = remark;
        return this;
    }

}
