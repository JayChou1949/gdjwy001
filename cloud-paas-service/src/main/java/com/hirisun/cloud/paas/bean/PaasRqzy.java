package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * PAAS容器资源申请信息
 */
@TableName("TB_PAAS_RQZY")
public class PaasRqzy extends Model<PaasRqzy> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 网络类型
     */
         @TableField("NET_TYPE")
    private String netType;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;
    @TableField(exist = false)
    private List<PaasRqzyFwq> rqzyFwqs;

    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

    public String getId() {
        return id;
    }

    public PaasRqzy setId(String id) {
        this.id = id;
        return this;
    }

    public List<PaasRqzyFwq> getRqzyFwqs() {
        return rqzyFwqs;
    }

    public void setRqzyFwqs(List<PaasRqzyFwq> rqzyFwqs) {
        this.rqzyFwqs = rqzyFwqs;
    }

    public String getNetType() {
        return netType;
    }

    public PaasRqzy setNetType(String netType) {
        this.netType = netType;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasRqzy setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PaasRqzy setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasRqzy setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasRqzy{" +
        "id=" + id +
        ", netType=" + netType +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
