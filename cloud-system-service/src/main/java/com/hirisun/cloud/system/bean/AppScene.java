package com.hirisun.cloud.system.bean;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;

@TableName("T_APP_SCENE")
public class AppScene implements Serializable {

	private static final long serialVersionUID = 1385420744393643170L;

	@TableId(value = "ID",type = IdType.UUID)
    private String id;

    @TableField("MASTER_ID")
    private String masterId;

        /**
     * 图标
     */
         @TableField("IMAGE")
    private String image;

        /**
     * 标题
     */
         @TableField("TITLE")
    private String title;

        /**
     * 描述
     */
         @TableField("DESCRIPTION")
    private String description;

    @TableField("STATUS")
    private String status;

    @TableField("REMARK")
    private String remark;
    @ApiModelProperty(value = "应用场景优势")
    @TableField(exist = false)
    private List<AppSupre> supres;

    public String getId() {
        return id;
    }

    public AppScene setId(String id) {
        this.id = id;
        return this;
    }

    public List<AppSupre> getSupres() {
		return supres;
	}

	public void setSupres(List<AppSupre> supres) {
		this.supres = supres;
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

    public AppScene setImage(String image) {
        this.image = image;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AppScene setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AppScene setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public AppScene setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AppScene setRemark(String remark) {
        this.remark = remark;
        return this;
    }

}
