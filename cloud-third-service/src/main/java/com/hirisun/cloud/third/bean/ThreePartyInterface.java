package com.hirisun.cloud.third.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 第三方接口表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-05
 */
@TableName("T_THREE_PARTY_INTERFACE")
@ApiModel(value="ThreePartyInterface对象", description="第三方接口表")
public class ThreePartyInterface implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "标签")
    @TableField("LABEL")
    private String label;

    @ApiModelProperty(value = "json串")
    @TableField("DATA")
    private String data;

    @ApiModelProperty(value = "接口类型（1 华为 2 美亚）")
    @TableField("TYPE")
    private String type;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UPDATE_TIME",fill = FieldFill.INSERT)
    private Date updateTime;

    @ApiModelProperty(value = "接口名")
    @TableField("NAME")
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ThreePartyInterface{" +
        "id=" + id +
        ", label=" + label +
        ", data=" + data +
        ", type=" + type +
        ", updateTime=" + updateTime +
        ", name=" + name +
        "}";
    }
}
