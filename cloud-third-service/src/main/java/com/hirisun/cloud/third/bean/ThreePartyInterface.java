package com.hirisun.cloud.third.bean;

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
 * @since 2020-06-28
 */
@TableName("TB_THREE_PARTY_INTERFACE")
@ApiModel(value="ThreePartyInterface对象", description="第三方接口表")
public class ThreePartyInterface implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "标签")
    @TableField("LABEL")
    private String label;

    @ApiModelProperty(value = "json串")
    @TableField("DATA")
    private String data;

    @ApiModelProperty(value = "1代表华为接口")
    @TableField("TYPE")
    private String type;

    @TableField("CREATE_TIME")
    private Date createTime;


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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ThreePartyInterface{" +
        "id=" + id +
        ", label=" + label +
        ", data=" + data +
        ", type=" + type +
        ", createTime=" + createTime +
        "}";
    }
}
