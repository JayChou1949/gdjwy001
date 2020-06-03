package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 飞识二期授权码实施信息
 * </p>
 *
 * @author huru
 * @since 2019-03-22
 */
@TableName("TB_PAAS_FSEQSQM_IMPL")
public class PaasFseqsqmImpl extends Model<PaasFseqsqmImpl> {

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

    public PaasFseqsqmImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public PaasFseqsqmImpl setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public PaasFseqsqmImpl setAppSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasFseqsqmImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PaasFseqsqmImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasFseqsqmImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasFseqsqmImpl{" +
        "id=" + id +
        ", appKey=" + appKey +
        ", appSecret=" + appSecret +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
