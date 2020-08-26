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
 * 大数据安全体系-漏洞扫描
 * </p>
 *
 * @author wuc
 * @since 2020-04-07
 */
@TableName("TB_PAAS_SECURITY_SCAN")
public class PaasSecurityScan extends Model<PaasSecurityScan> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
        @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 服务名称
     */
         @TableField("NAME")
    private String name;

        /**
     * 套餐类型 0:基础 1:标准 2:高级 3:旗舰
     */
         @TableField("SET_MEAL_TYPE")
    private Integer setMealType;

        /**
     * 虚拟私有云
     */
         @TableField("VPC")
    private String vpc;

        /**
     * 安全组
     */
         @TableField("SECURITY_GROUP")
    private String securityGroup;

        /**
     * 子网
     */
         @TableField("SUB_NET")
    private String subNet;

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

    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;


    public String getId() {
        return id;
    }

    public PaasSecurityScan setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PaasSecurityScan setName(String name) {
        this.name = name;
        return this;
    }


    public Integer getSetMealType() {
        return setMealType;
    }

    public void setSetMealType(Integer setMealType) {
        this.setMealType = setMealType;
    }

    public String getVpc() {
        return vpc;
    }

    public PaasSecurityScan setVpc(String vpc) {
        this.vpc = vpc;
        return this;
    }

    public String getSecurityGroup() {
        return securityGroup;
    }

    public PaasSecurityScan setSecurityGroup(String securityGroup) {
        this.securityGroup = securityGroup;
        return this;
    }

    public String getSubNet() {
        return subNet;
    }

    public PaasSecurityScan setSubNet(String subNet) {
        this.subNet = subNet;
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

    public PaasSecurityScan setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasSecurityScan{" +
        "id=" + id +
        ", name=" + name +
        ", setMealType=" + setMealType +
        ", vpc=" + vpc +
        ", securityGroup=" + securityGroup +
        ", subNet=" + subNet +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", appInfoId=" + appInfoId +
        "}";
    }
}
