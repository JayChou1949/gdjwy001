package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * IAAS应用场景
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
@TableName("TB_IAAS_APP_SCENE")
public class IaasAppScene extends Model<IaasAppScene> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    @TableField("IAAS_ID")
    private String iaasId;

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
    private List<IaasAppSupre> iaasSupres;

    public String getId() {
        return id;
    }

    public IaasAppScene setId(String id) {
        this.id = id;
        return this;
    }

    public List<IaasAppSupre> getIaasSupres() {
        return iaasSupres;
    }

    public void setIaasSupres(List<IaasAppSupre> iaasSupres) {
        this.iaasSupres = iaasSupres;
    }

    public String getIaasId() {
        return iaasId;
    }

    public IaasAppScene setIaasId(String iaasId) {
        this.iaasId = iaasId;
        return this;
    }

    public String getImage() {
        return image;
    }

    public IaasAppScene setImage(String image) {
        this.image = image;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public IaasAppScene setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IaasAppScene setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public IaasAppScene setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public IaasAppScene setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasAppScene{" +
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
