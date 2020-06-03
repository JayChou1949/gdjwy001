package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuc
 * @since 2020-01-03
 */
@TableName("TB_DRAFT")
public class Draft extends Model<Draft> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 身份证号
     */
         @TableField("IDCARD")
    private String idcard;

        /**
     * 0:服务发布
     */
         @TableField("TYPE")
    private Integer type;

        /**
     * 草稿数据
     */
         @TableField("DATA")
    private String data;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFIED_TIME")
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public Draft setId(String id) {
        this.id = id;
        return this;
    }

    public String getIdcard() {
        return idcard;
    }

    public Draft setIdcard(String idcard) {
        this.idcard = idcard;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public Draft setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getData() {
        return data;
    }

    public Draft setData(String data) {
        this.data = data;
        return this;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Draft{" +
        "id=" + id +
        ", idcard=" + idcard +
        ", type=" + type +
        ", data=" + data +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
