package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 后端服务地址
 * </p>
 *
 * @author wuc
 * @since 2019-10-17
 */
@TableName("TB_BACKEND_HOST")
public class BackendHost extends Model<BackendHost> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 后端服务ID
     */
         @TableField("MASTER_ID")
    private String masterId;

    @TableField("HOST_VALUE")
    private String hostValue;

        /**
     * 权重
     */
         @TableField("WEIGHT")
    private Long weight;


    public String getId() {
        return id;
    }

    public BackendHost setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public BackendHost setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public BackendHost setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public BackendHost setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public String getHostValue() {
        return hostValue;
    }

    public BackendHost setHostValue(String hostValue) {
        this.hostValue = hostValue;
        return this;
    }

    public Long getWeight() {
        return weight;
    }

    public BackendHost setWeight(Long weight) {
        this.weight = weight;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BackendHost{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", masterId=" + masterId +
        ", hostValue=" + hostValue +
        ", weight=" + weight +
        "}";
    }
}
