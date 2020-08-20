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
@TableName("TB_PAAS_LIBRA_INFO")
public class PaasLibraInfo extends Model<PaasLibraInfo> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
        @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * CPU(核)
     */
         @TableField("CPU")
    private Double cpu;

        /**
     * 内存(GB)
     */
         @TableField("MEMORY")
    private Double memory;

        /**
     * 存储(TB)
     */
         @TableField("STORAGE")
    private Double storage;

        /**
     * 申请原因
     */
         @TableField("APPLY_REASON")
    private String applyReason;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

        /**
     * 订单ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;


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
     * 购物车ID
     */
         @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;


    public String getId() {
        return id;
    }

    public PaasLibraInfo setId(String id) {
        this.id = id;
        return this;
    }

    public Double getCpu() {
        return cpu;
    }

    public PaasLibraInfo setCpu(Double cpu) {
        this.cpu = cpu;
        return this;
    }

    public Double getMemory() {
        return memory;
    }

    public PaasLibraInfo setMemory(Double memory) {
        this.memory = memory;
        return this;
    }

    public Double getStorage() {
        return storage;
    }

    public PaasLibraInfo setStorage(Double storage) {
        this.storage = storage;
        return this;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public PaasLibraInfo setApplyReason(String applyReason) {
        this.applyReason = applyReason;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public PaasLibraInfo setRemark(String remark) {
        this.remark = remark;
        return this;
    }


    public String getAppInfoId() {
        return appInfoId;
    }

    public void setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
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

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public PaasLibraInfo setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasLibraInfo{" +
        "id=" + id +
        ", cpu=" + cpu +
        ", memory=" + memory +
        ", storage=" + storage +
        ", applyReason=" + applyReason +
        ", remark=" + remark +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", shoppingCartId=" + shoppingCartId +
        "}";
    }
}
