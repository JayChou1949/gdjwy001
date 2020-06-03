package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.sql.Clob;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 第三方接口表
 * </p>
 *
 * @author huru
 * @since 2018-12-03
 */
@TableName("TB_THREE_PARTY_INTERFACE")
public class ThreePartyInterface extends Model<ThreePartyInterface> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.INPUT)
    private String id;

    /**
     * 标签
     */
    @TableField("LABEL")
    private String label;

    /**
     * json串
     */
    @TableField("DATA")
    private String data;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;


    public String getId() {
        return id;
    }

    public ThreePartyInterface setId(String id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public ThreePartyInterface setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getData() {
        return data;
    }

    public ThreePartyInterface setData(String data) {
        this.data = data;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ThreePartyInterface{" +
                "id=" + id +
                ", label=" + label +
                ", data=" + data +
                "}";
    }
}
