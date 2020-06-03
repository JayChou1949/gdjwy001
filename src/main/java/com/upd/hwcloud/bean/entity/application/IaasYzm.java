package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * IaaS 云桌面申请信息
 * </p>
 *
 * @author wuc
 * @since 2019-01-07
 */
@TableName("TB_IAAS_YZM")
public class IaasYzm extends Model<IaasYzm> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 申请数量
     */
         @TableField("NUM")
    private Long num;

        /**
     * 用途
     */
         @TableField("PURPOSE")
    private String purpose;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

         @TableField("SHOPPING_CART_ID")
         private String shoppingCartId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public IaasYzm setId(String id) {
        this.id = id;
        return this;
    }

    public Long getNum() {
        return num;
    }

    public IaasYzm setNum(Long num) {
        this.num = num;
        return this;
    }

    public String getPurpose() {
        return purpose;
    }

    public IaasYzm setPurpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasYzm setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }


    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasYzm setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasYzm setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasYzm{" +
        "id=" + id +
        ", num=" + num +
        ", purpose=" + purpose +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
