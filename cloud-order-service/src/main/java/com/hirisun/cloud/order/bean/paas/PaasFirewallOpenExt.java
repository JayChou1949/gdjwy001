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
 * @since 2020-05-19
 */
@TableName("TB_PAAS_FIREWALL_OPEN_EXT")
public class PaasFirewallOpenExt extends Model<PaasFirewallOpenExt> {

    private static final long serialVersionUID = 1L;

        /**
     * uuid
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 源地址
     */
         @TableField("SOURCE_ADDRESS")
    private String sourceAddress;



        /**
     * 目的地址
     */
         @TableField("DESTINATION_ADDRESS")
    private String destinationAddress;

        /**
     * 0:TCP  1:UDP 2:ICMP ','拼接 开通协议
     */
         @TableField("PROTOCOL")
    private String protocol;

        /**
     * 目的地址种类
     */
         @TableField("DESTINATION_TYPE")
    private String destinationType;



        /**
     * 目的地址端口
     */
         @TableField("DESTINATION_PORT")
    private String destinationPort;

        /**
     * 开通描述
     */
         @TableField("OPEN_DESCRIPTION")
    private String openDescription;

        /**
     * 基本信息ID
     */
         @TableField("MASTER_ID")
    private String masterId;

        /**
     * 申请ID
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
         @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

        /**
     * 修改时间
     */
         @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public PaasFirewallOpenExt setId(String id) {
        this.id = id;
        return this;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public PaasFirewallOpenExt setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
        return this;
    }






    public String getDestinationAddress() {
        return destinationAddress;
    }

    public PaasFirewallOpenExt setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
        return this;
    }


    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public PaasFirewallOpenExt setDestinationType(String destinationType) {
        this.destinationType = destinationType;
        return this;
    }



    public String getDestinationPort() {
        return destinationPort;
    }

    public PaasFirewallOpenExt setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
        return this;
    }

    public String getOpenDescription() {
        return openDescription;
    }

    public PaasFirewallOpenExt setOpenDescription(String openDescription) {
        this.openDescription = openDescription;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public PaasFirewallOpenExt setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasFirewallOpenExt setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public PaasFirewallOpenExt setShoppingCartId(String shoppingCartId) {
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
        return "PaasFirewallOpenExt{" +
        "id=" + id +
        ", sourceAddress=" + sourceAddress +
        ", destinationAddress=" + destinationAddress +
        ", destinationType=" + destinationType +
        ", destinationPort=" + destinationPort +
        ", openDescription=" + openDescription +
        ", masterId=" + masterId +
        ", appInfoId=" + appInfoId +
        ", shoppingCartId=" + shoppingCartId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
