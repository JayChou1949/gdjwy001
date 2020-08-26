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
 * 关系型数据数据库信息
 * </p>
 *
 * @author yyc
 * @since 2020-04-19
 */
@TableName("TB_PAAS_RDB_INFO")
public class PaasRdbInfo extends Model<PaasRdbInfo> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
        @TableId(value = "ID", type = IdType.INPUT)
    private String id;

        /**
     * 数据库名/表空间名
     */
         @TableField("NAME")
    private String name;

        /**
     * 预期大小(GB) 0-10000 四位小数
     */
         @TableField("EXCEPT_SIZE")
    private Double exceptSize;

        /**
     * 用途说明
     */
         @TableField("USE_EXPLANATION")
    private String useExplanation;

        /**
     * 备注
     */
         @TableField("REMARKS")
    private String remarks;

        /**
     * 基本信息ID
     */
         @TableField("MASTER_ID")
    private String masterId;

        /**
     * 申请订单ID
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
     * 数据库类型  Mysql : 0  Oracle : 1
     */
         @TableField("DB_TYPE")
    private Integer dbType;

    /**
     * 数据库关联账号 ,分割
     */
    @TableField("ASSOCIATED_ACC")
    private String associatedAcc;

    /**
     * 数据库下的账号
     */
    @TableField(exist = false)
         private List<PaasRdbAcc> rdbAccList;


    public String getId() {
        return id;
    }

    public PaasRdbInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PaasRdbInfo setName(String name) {
        this.name = name;
        return this;
    }

    public Double getExceptSize() {
        return exceptSize;
    }

    public PaasRdbInfo setExceptSize(Double exceptSize) {
        this.exceptSize = exceptSize;
        return this;
    }


    public String getUseExplanation() {
        return useExplanation;
    }

    public void setUseExplanation(String useExplanation) {
        this.useExplanation = useExplanation;
    }

    public String getRemarks() {
        return remarks;
    }

    public PaasRdbInfo setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public PaasRdbInfo setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasRdbInfo setAppInfoId(String appInfoId) {
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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getDbType() {
        return dbType;
    }

    public PaasRdbInfo setDbType(Integer dbType) {
        this.dbType = dbType;
        return this;
    }

    public String getAssociatedAcc() {
        return associatedAcc;
    }

    public void setAssociatedAcc(String associatedAcc) {
        this.associatedAcc = associatedAcc;
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
        return "PaasRdbInfo{" +
        "id=" + id +
        ", name=" + name +
        ", exceptSize=" + exceptSize +
        ", useExplain=" + useExplanation +
        ", remarks=" + remarks +
        ", masterId=" + masterId +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", dbType=" + dbType +
        "}";
    }
}
