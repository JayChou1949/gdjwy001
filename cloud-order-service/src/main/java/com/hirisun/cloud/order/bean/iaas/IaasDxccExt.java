package com.hirisun.cloud.order.bean.iaas;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * IaaS 对象存储申请信息
 * </p>
 *
 * @author wuc
 * @since 2019-07-30
 */
@TableName("TB_IAAS_DXCC_EXT")
public class IaasDxccExt extends Model<IaasDxccExt> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 统一用户账号
     */
         @TableField("TYYH_ACCOUNT")
    private String tyyhAccount;

        /**
     * 统一用户密码
     */
         @TableField("TYYH_PASSWORD")
    private String tyyhPassword;

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

    /**
     * 存储信息
     */
    @TableField(exist = false)
    private List<IaasDxcc> ccxx;

    public List<IaasDxcc> getCcxx() {
        return ccxx;
    }

    public void setCcxx(List<IaasDxcc> ccxx) {
        this.ccxx = ccxx;
    }

    public String getId() {
        return id;
    }

    public IaasDxccExt setId(String id) {
        this.id = id;
        return this;
    }

    public String getTyyhAccount() {
        return tyyhAccount;
    }

    public IaasDxccExt setTyyhAccount(String tyyhAccount) {
        this.tyyhAccount = tyyhAccount;
        return this;
    }

    public String getTyyhPassword() {
        return tyyhPassword;
    }

    public IaasDxccExt setTyyhPassword(String tyyhPassword) {
        this.tyyhPassword = tyyhPassword;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasDxccExt setAppInfoId(String appInfoId) {
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

    public IaasDxccExt setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasDxccExt setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasDxccExt{" +
        "id=" + id +
        ", tyyhAccount=" + tyyhAccount +
        ", tyyhPassword=" + tyyhPassword +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
