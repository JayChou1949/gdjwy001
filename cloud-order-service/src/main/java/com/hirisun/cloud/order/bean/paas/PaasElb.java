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
 * ELB弹性负载均衡
 * </p>
 *
 * @author yyc
 * @since 2020-05-07
 */
@TableName("TB_PAAS_ELB")
public class PaasElb extends Model<PaasElb> {

    private static final long serialVersionUID = 1L;

        /**
     * 主键UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 组件名
     */
         @TableField("COMPONENT")
    private String component;

        /**
     * 组件需求
     */
         @TableField("DEMAND")
    private String demand;

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
     * 订单ID
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

    public PaasElb setId(String id) {
        this.id = id;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public PaasElb setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getDemand() {
        return demand;
    }

    public PaasElb setDemand(String demand) {
        this.demand = demand;
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

    public PaasElb setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public PaasElb setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasElb{" +
        "id=" + id +
        ", component=" + component +
        ", demand=" + demand +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", appInfoId=" + appInfoId +
        ", shoppingCartId=" + shoppingCartId +
        "}";
    }
}
