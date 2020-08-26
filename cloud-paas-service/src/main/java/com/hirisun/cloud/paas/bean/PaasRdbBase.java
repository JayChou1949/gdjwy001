package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 关系型数据库组申请信息
 * </p>
 *
 * @author yyc
 * @since 2020-04-19
 */
@TableName("TB_PAAS_RDB_BASE")
public class PaasRdbBase extends Model<PaasRdbBase> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
        @TableId(value = "ID", type = IdType.INPUT)
    private String id;

        /**
     * 申请类型  数据库申请：0   账号新增 ： 1
     */
         @TableField("APPLY_TYPE")
    private Integer applyType;

        /**
     * 数据库类型  Mysql : 0  Oracle : 1
     */
         @TableField("DATABASE_TYPE")
    private Integer databaseType;

        /**
     * 数据库版本
     */
         @TableField("DATABASE_VERSION")
    private String databaseVersion;

        /**
     * 字符集
     */
         @TableField("CHARACTER_SET")
    private String characterSet;

        /**
     * 最大并发事务估算
     */
         @TableField("MAX_CONCURRENT")
    private Integer maxConcurrent;

        /**
     * 申请理由
     */
         @TableField("APPLY_EXPLAIN")
    private String applyExplain;

        /**
     * 申请ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;


    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

        /**
     * IP
     */
         @TableField("IP")
    private String ip;

        /**
     * 端口
     */
         @TableField("PORT")
    private String port;


    /**
     * 申请单号
     */
    @TableField("ORDER_NUMBER")
         private String orderNumber;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 订单数据库信息
     */
    @TableField(exist = false)
    private List<PaasRdbInfo> rdbInfoList;

    /**
     * 订单数据库账号信息
     */
    @TableField(exist = false)
    private List<PaasRdbAcc> rdbAccList;

    /**
     * 订单数据库实施信息
     */
    @TableField(exist = false)
    private List<PaasRdbInfoImpl> rdbInfoImplList;

    /**
     * 订单数据库账号实施信息
     */
    @TableField(exist = false)
    private List<PaasRdbAccImpl> rdbAccImplList;

    public String getId() {
        return id;
    }

    public PaasRdbBase setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public PaasRdbBase setApplyType(Integer applyType) {
        this.applyType = applyType;
        return this;
    }

    public Integer getDatabaseType() {
        return databaseType;
    }

    public PaasRdbBase setDatabaseType(Integer databaseType) {
        this.databaseType = databaseType;
        return this;
    }

    public String getDatabaseVersion() {
        return databaseVersion;
    }

    public PaasRdbBase setDatabaseVersion(String databaseVersion) {
        this.databaseVersion = databaseVersion;
        return this;
    }

    public String getCharacterSet() {
        return characterSet;
    }

    public PaasRdbBase setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
        return this;
    }

    public Integer getMaxConcurrent() {
        return maxConcurrent;
    }

    public PaasRdbBase setMaxConcurrent(Integer maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
        return this;
    }

    public String getApplyExplain() {
        return applyExplain;
    }

    public PaasRdbBase setApplyExplain(String applyExplain) {
        this.applyExplain = applyExplain;
        return this;
    }


    public List<PaasRdbInfoImpl> getRdbInfoImplList() {
        return rdbInfoImplList;
    }

    public void setRdbInfoImplList(List<PaasRdbInfoImpl> rdbInfoImplList) {
        this.rdbInfoImplList = rdbInfoImplList;
    }

    public List<PaasRdbAccImpl> getRdbAccImplList() {
        return rdbAccImplList;
    }

    public void setRdbAccImplList(List<PaasRdbAccImpl> rdbAccImplList) {
        this.rdbAccImplList = rdbAccImplList;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasRdbBase setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getIp() {
        return ip;
    }

    public PaasRdbBase setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public List<PaasRdbInfo> getRdbInfoList() {
        return rdbInfoList;
    }

    public void setRdbInfoList(List<PaasRdbInfo> rdbInfoList) {
        this.rdbInfoList = rdbInfoList;
    }

    public List<PaasRdbAcc> getRdbAccList() {
        return rdbAccList;
    }

    public void setRdbAccList(List<PaasRdbAcc> rdbAccList) {
        this.rdbAccList = rdbAccList;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasRdbBase{" +
        "id=" + id +
        ", applyType=" + applyType +
        ", databaseType=" + databaseType +
        ", databaseVersion=" + databaseVersion +
        ", characterSet=" + characterSet +
        ", maxConcurrent=" + maxConcurrent +
        ", applyExplain=" + applyExplain +
        ", appInfoId=" + appInfoId +
        ", ip=" + ip +
        ", port=" + port +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }


}
