package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 统一用户统一消息实施信息
 * </p>
 *
 * @author zwb
 * @since 2019-05-10
 */
@TableName("TB_PAAS_TYYH_IMPL")
public class PaasTyyhImpl extends Model<PaasTyyhImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField("APP_KEY")
    private String appKey;

    @TableField("APP_SECRET")
    private String appSecret;

    /**
     * 申请信息 id
     */
    @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public PaasTyyhImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public PaasTyyhImpl setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public PaasTyyhImpl setAppSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasTyyhImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PaasTyyhImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasTyyhImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasTyyhImpl{" +
                "id=" + id +
                ", appKey=" + appKey +
                ", appSecret=" + appSecret +
                ", appInfoId=" + appInfoId +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                "}";
    }
}
