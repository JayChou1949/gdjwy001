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

/**
 * <p>
 * 
 * </p>
 *
 * @author yyc
 * @since 2020-06-11
 */
@TableName("TB_PAAS_LIBRA_ACCOUNT_IMPL")
public class PaasLibraAccountImpl extends Model<PaasLibraAccountImpl> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
        @TableId(value = "ID", type = IdType.UUID)
    private String id ;

        /**
     * 账号名
     */
         @TableField("ACCOUNT")
    private String account;

        /**
     * 账号-创建权限 0:无 1:有
     */
         @TableField("CAN_CREATED")
    private Integer canCreated;

        /**
     * 账号-修改权限 0:无 1:有
     */
         @TableField("CAN_MODIFIED")
    private Integer canModified;

        /**
     * 账号-查询权限 0:无 1:有
     */
         @TableField("CAN_QUERY")
    private Integer canQuery;

        /**
     * 账号-删除权限 0:无 1:有
     */
         @TableField("CAN_DELETE")
    private Integer canDelete;

    /**
     * 模式名
     */
    @TableField("SCHEMA")
    private String schema;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

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

        /**
     * 申请信息id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 购物车ID
     */
         @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public PaasLibraAccountImpl setAccount(String account) {
        this.account = account;
        return this;
    }

    public Integer getCanCreated() {
        return canCreated;
    }

    public PaasLibraAccountImpl setCanCreated(Integer canCreated) {
        this.canCreated = canCreated;
        return this;
    }

    public Integer getCanModified() {
        return canModified;
    }

    public PaasLibraAccountImpl setCanModified(Integer canModified) {
        this.canModified = canModified;
        return this;
    }

    public Integer getCanQuery() {
        return canQuery;
    }

    public PaasLibraAccountImpl setCanQuery(Integer canQuery) {
        this.canQuery = canQuery;
        return this;
    }

    public Integer getCanDelete() {
        return canDelete;
    }

    public PaasLibraAccountImpl setCanDelete(Integer canDelete) {
        this.canDelete = canDelete;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getRemark() {
        return remark;
    }

    public PaasLibraAccountImpl setRemark(String remark) {
        this.remark = remark;
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

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasLibraAccountImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public PaasLibraAccountImpl setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasLibraAccountImpl{" +
        "ID =" + id  +
        ", account=" + account +
        ", canCreated=" + canCreated +
        ", canModified=" + canModified +
        ", canQuery=" + canQuery +
        ", canDelete=" + canDelete +
        ", remark=" + remark +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", appInfoId=" + appInfoId +
        ", shoppingCartId=" + shoppingCartId +
        "}";
    }
}
