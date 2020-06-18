package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 大数据组件申请信息
 * </p>
 *
 * @author junglefisher
 * @since 2020-05-08
 */
@TableName("TB_PAAS_BIGDATA_COMPONENT")
public class PaasBigdataComponent extends Model<PaasBigdataComponent> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 订单id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 集群类型
     */
         @TableField("CLUSTER_TYPE")
    private Integer clusterType;

        /**
     * 账号
     */
         @TableField("ACCOUNT")
    private String account;

        /**
     * 申请说明
     */
         @TableField("EXPLANATION")
    private String explanation;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 购物车ID
     */
         @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

        /**
     * 组件名称
     */
         @TableField("NAME")
    private String name;

     @TableField("STRING")
     private String string;

    /**
     * 服务类型 0-新增 1-扩容
     */
    @TableField("SERVICE_TYPE")
     private Integer serviceType;

    /**
     * 组件详情列表
     * @return
     */
    @TableField(exist = false)
    private List<PaasComponentDetail> componentDetails;

    public List<PaasComponentDetail> getComponentDetails() {
        return componentDetails;
    }

    public void setComponentDetails(List<PaasComponentDetail> componentDetails) {
        this.componentDetails = componentDetails;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getId() {
        return id;
    }

    public PaasBigdataComponent setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasBigdataComponent setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Integer getClusterType() {
        return clusterType;
    }

    public PaasBigdataComponent setClusterType(Integer clusterType) {
        this.clusterType = clusterType;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public PaasBigdataComponent setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getExplanation() {
        return explanation;
    }

    public PaasBigdataComponent setExplanation(String explanation) {
        this.explanation = explanation;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PaasBigdataComponent setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasBigdataComponent setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public PaasBigdataComponent setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    public String getName() {
        return name;
    }

    public PaasBigdataComponent setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasBigdataComponent{" +
        "id=" + id +
        ", appInfoId=" + appInfoId +
        ", clusterType=" + clusterType +
        ", account=" + account +
        ", explanation=" + explanation +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", shoppingCartId=" + shoppingCartId +
        ", name=" + name +
        "}";
    }
}
