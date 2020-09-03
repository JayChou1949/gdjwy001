package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 大数据安全体系-网页防篡改
 * </p>
 *
 * @author wuc
 * @since 2020-04-07
 */
@TableName("TB_PAAS_SECURITY_TAMPER_PROOF")
public class PaasSecurityTamperProof extends Model<PaasSecurityTamperProof> {

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
     * 客户端数目
     */
         @TableField("CLIENT_NUM")
    private Integer clientNum;

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

    public PaasSecurityTamperProof setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PaasSecurityTamperProof setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getClientNum() {
        return clientNum;
    }

    public PaasSecurityTamperProof setClientNum(Integer clientNum) {
        this.clientNum = clientNum;
        return this;
    }

    public String getVpc() {
        return vpc;
    }

    public PaasSecurityTamperProof setVpc(String vpc) {
        this.vpc = vpc;
        return this;
    }

    public String getSecurityGroup() {
        return securityGroup;
    }

    public PaasSecurityTamperProof setSecurityGroup(String securityGroup) {
        this.securityGroup = securityGroup;
        return this;
    }

    public String getSubNet() {
        return subNet;
    }

    public PaasSecurityTamperProof setSubNet(String subNet) {
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

    public PaasSecurityTamperProof setAppInfoId(String appInfoId) {
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
        return "PaasSecurityTamperProof{" +
        "id=" + id +
        ", name=" + name +
        ", clientNum=" + clientNum +
        ", vpc=" + vpc +
        ", securityGroup=" + securityGroup +
        ", subNet=" + subNet +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", appInfoId=" + appInfoId +
        "}";
    }
}
