package com.hirisun.cloud.order.bean.paas;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @author yyc
 * @since 2020-06-11
 */
@TableName("TB_PAAS_LIBRA_WHITELIST_IMPL")
public class PaasLibraWhitelistImpl extends Model<PaasLibraWhitelistImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 使用人/维护人姓名
     */
         @TableField("USER_NAME")
    private String userName;

        /**
     * ip地址
     */
         @TableField("IP_ADDRESS")
    private String ipAddress;

        /**
     * 物理地址
     */
         @TableField("MAC_ADDRESS")
    private String macAddress;

        /**
     * ip地址类型
     */
         @TableField("IP_TYPE")
    private String ipType;

        /**
     * 申请原因
     */
         @TableField("APPLY_REASON")
    private String applyReason;

        /**
     * 数据库
     */
         @TableField("DB")
    private String db;

        /**
     * 数据库浮动ip
     */
         @TableField("DB_IP_ADDRESS")
    private String dbIpAddress;

        /**
     * 申请时间
     */
         @TableField("APPLY_TIME")
    private LocalDateTime applyTime;

        /**
     * 数据库账号
     */
         @TableField("DB_ACCOUNTS")
    private String dbAccounts;

        /**
     * 申请表ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 购物车ID
     */
         @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public PaasLibraWhitelistImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public PaasLibraWhitelistImpl setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public PaasLibraWhitelistImpl setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public PaasLibraWhitelistImpl setMacAddress(String macAddress) {
        this.macAddress = macAddress;
        return this;
    }

    public String getIpType() {
        return ipType;
    }

    public PaasLibraWhitelistImpl setIpType(String ipType) {
        this.ipType = ipType;
        return this;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public PaasLibraWhitelistImpl setApplyReason(String applyReason) {
        this.applyReason = applyReason;
        return this;
    }

    public String getDb() {
        return db;
    }

    public PaasLibraWhitelistImpl setDb(String db) {
        this.db = db;
        return this;
    }

    public String getDbIpAddress() {
        return dbIpAddress;
    }

    public PaasLibraWhitelistImpl setDbIpAddress(String dbIpAddress) {
        this.dbIpAddress = dbIpAddress;
        return this;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public PaasLibraWhitelistImpl setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
        return this;
    }

    public String getDbAccounts() {
        return dbAccounts;
    }

    public PaasLibraWhitelistImpl setDbAccounts(String dbAccounts) {
        this.dbAccounts = dbAccounts;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasLibraWhitelistImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public PaasLibraWhitelistImpl setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
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
        return "PaasLibraWhitelistImpl{" +
        "id=" + id +
        ", userName=" + userName +
        ", ipAddress=" + ipAddress +
        ", macAddress=" + macAddress +
        ", ipType=" + ipType +
        ", applyReason=" + applyReason +
        ", db=" + db +
        ", dbIpAddress=" + dbIpAddress +
        ", applyTime=" + applyTime +
        ", dbAccounts=" + dbAccounts +
        ", appInfoId=" + appInfoId +
        ", shoppingCartId=" + shoppingCartId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
