package com.hirisun.cloud.system.bean;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;

@TableName("TB_IAAS_FUN_DETAIL")
public class FunDetail implements Serializable {

	private static final long serialVersionUID = 3099177564260369299L;

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
    private List<FunDetailExp> detailExps;
    
    public String getId() {
        return id;
    }

    public FunDetail setId(String id) {
        this.id = id;
        return this;
    }

    public List<FunDetailExp> getDetailExps() {
        return detailExps;
    }

    public void setDetailExps(List<FunDetailExp> detailExps) {
        this.detailExps = detailExps;
    }

	public String getIaasId() {
		return iaasId;
	}

	public void setIaasId(String iaasId) {
		this.iaasId = iaasId;
	}

	public String getImage() {
        return image;
    }

    public FunDetail setImage(String image) {
        this.image = image;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FunDetail setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FunDetail setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public FunDetail setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public FunDetail setRemark(String remark) {
        this.remark = remark;
        return this;
    }

}
