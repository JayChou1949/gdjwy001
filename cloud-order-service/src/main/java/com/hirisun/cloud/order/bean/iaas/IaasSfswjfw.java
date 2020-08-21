package com.hirisun.cloud.order.bean.iaas;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * sfs弹性文件服务申请信息
 * </p>
 *
 * @author zwb
 * @since 2019-05-20
 */
@TableName("TB_IAAS_SFSWJFW")
public class IaasSfswjfw extends Model<IaasSfswjfw> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 容量
     */
         @TableField("CAPACITY")
    private String capacity;
    @TableField("IP")
    private String ip;
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

    public IaasSfswjfw setId(String id) {
        this.id = id;
        return this;
    }

    public String getCapacity() {
        return capacity;
    }

    public IaasSfswjfw setCapacity(String capacity) {
        this.capacity = capacity;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasSfswjfw setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasSfswjfw setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasSfswjfw setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
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

    public IaasSfswjfw setIp(String ip) {
        this.ip = ip;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasSfswjfw{" +
        "id=" + id +
        ", capacity=" + capacity +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", ip=" + ip +
        "}";
    }
}
