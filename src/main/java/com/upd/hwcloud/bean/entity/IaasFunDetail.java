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
 * IAAS功能详情
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
@TableName("TB_IAAS_FUN_DETAIL")
public class IaasFunDetail extends Model<IaasFunDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    @TableField("IAAS_ID")
    private String iaasId;

        /**
     * 图标
     */@ApiModelProperty(value = "图标")
         @TableField("IMAGE")
    private String image;

        /**
     * 标题
     */@ApiModelProperty(value = "标题")
         @TableField("TITLE")
    private String title;

        /**
     * 描述
     */@ApiModelProperty(value = "描述")
         @TableField("DESCRIPTION")
    private String description;

    @TableField("STATUS")
    private String status;

    @TableField("REMARK")
    private String remark;
    @ApiModelProperty(value = "功能详情展开解释")
    @TableField(exist = false)
    private List<IaasFunDetailExp> detailExps;
    public String getId() {
        return id;
    }

    public IaasFunDetail setId(String id) {
        this.id = id;
        return this;
    }

    public List<IaasFunDetailExp> getDetailExps() {
        return detailExps;
    }

    public void setDetailExps(List<IaasFunDetailExp> detailExps) {
        this.detailExps = detailExps;
    }

    public String getIaasId() {
        return iaasId;
    }

    public IaasFunDetail setIaasId(String iaasId) {
        this.iaasId = iaasId;
        return this;
    }

    public String getImage() {
        return image;
    }

    public IaasFunDetail setImage(String image) {
        this.image = image;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public IaasFunDetail setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IaasFunDetail setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public IaasFunDetail setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public IaasFunDetail setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasFunDetail{" +
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
