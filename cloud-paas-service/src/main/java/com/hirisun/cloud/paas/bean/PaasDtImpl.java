package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 地图服务实施信息
 * </p>
 *
 * @author zwb
 * @since 2019-05-14
 */
@TableName("TB_PAAS_DT_IMPL")
public class PaasDtImpl extends Model<PaasDtImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    @TableField("APP_KEY")
    private String appKey;

    @TableField("APP_SECRET")
    private String appSecret;

    @TableField("ORDER_RESULT")
    private String orderResult;
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

    public PaasDtImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public PaasDtImpl setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public String getOrderResult() {
        return orderResult;
    }

    public void setOrderResult(String orderResult) {
        this.orderResult = orderResult;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public PaasDtImpl setAppSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasDtImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PaasDtImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasDtImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasDtImpl{" +
        "id=" + id +
        ", appKey=" + appKey +
        ", appSecret=" + appSecret +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
