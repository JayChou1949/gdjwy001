package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * IAAS功能特点
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
@TableName("TB_IAAS_FUN_CHA")
public class IaasFunCha extends Model<IaasFunCha> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    @TableField("IAAS_ID")
    private String iaasId;

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

    public IaasFunCha setId(String id) {
        this.id = id;
        return this;
    }

    public String getIaasId() {
        return iaasId;
    }

    public IaasFunCha setIaasId(String iaasId) {
        this.iaasId = iaasId;
        return this;
    }

    public String getImage() {
        return image;
    }

    public IaasFunCha setImage(String image) {
        this.image = image;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public IaasFunCha setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IaasFunCha setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public IaasFunCha setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public IaasFunCha setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasFunCha{" +
        "id=" + id +
        ", iaasId=" + iaasId +
        ", image=" + image +
        ", title=" + title +
        ", description=" + description +
        ", status=" + status +
        ", remark=" + remark +
        "}";
    }
}
