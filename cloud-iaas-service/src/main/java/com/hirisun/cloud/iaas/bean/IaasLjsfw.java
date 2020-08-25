package com.hirisun.cloud.iaas.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hirisun.cloud.common.annotation.ExcelExplain;

/**
 * <p>
 * 裸金属服务器申请信息
 * </p>
 *
 * @author xqp
 * @since 2020-08-12
 */
@TableName("TB_IAAS_LJSFW")
public class IaasLjsfw extends Model<IaasLjsfw> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 应用组件
     */
        @ExcelExplain(head = "应用组件")
         @TableField("COMPONENT")
    private String component;

        /**
     * 规格
     */
        @ExcelExplain(head = "规格名称")
         @TableField("SPECIFICATION")
    private String specification;

        /**
     * 操作系统
     */
        @ExcelExplain(head = "操作系统")
         @TableField("OS")
    private String os;

        /**
     * 网络
     */
        @ExcelExplain(head = "网络")
         @TableField("NET")
    private String net;

        /**
     * 申请数量
     */
        @ExcelExplain(head = "数量")
         @TableField("NUM")
    private Long num;

        /**
     * 部署应用
     */
        @ExcelExplain(head = "部署应用")
         @TableField("DEPLOY_APP")
    private String deployApp;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 应用类型
     */
        @ExcelExplain(head = "应用类型")
         @TableField("APP_TYPE")
    private String appType;

        /**
     * 组件描述
     */
        @ExcelExplain(head = "组件描述")
         @TableField("COMPONENT_DESC")
    private String componentDesc;

        /**
     * 购物车ID
     */
         @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;


    public String getId() {
        return id;
    }

    public IaasLjsfw setId(String id) {
        this.id = id;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public IaasLjsfw setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getSpecification() {
        return specification;
    }

    public IaasLjsfw setSpecification(String specification) {
        this.specification = specification;
        return this;
    }

    public String getOs() {
        return os;
    }

    public IaasLjsfw setOs(String os) {
        this.os = os;
        return this;
    }

    public String getNet() {
        return net;
    }

    public IaasLjsfw setNet(String net) {
        this.net = net;
        return this;
    }

    public Long getNum() {
        return num;
    }

    public IaasLjsfw setNum(Long num) {
        this.num = num;
        return this;
    }

    public String getDeployApp() {
        return deployApp;
    }

    public IaasLjsfw setDeployApp(String deployApp) {
        this.deployApp = deployApp;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasLjsfw setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasLjsfw setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasLjsfw setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getAppType() {
        return appType;
    }

    public IaasLjsfw setAppType(String appType) {
        this.appType = appType;
        return this;
    }

    public String getComponentDesc() {
        return componentDesc;
    }

    public IaasLjsfw setComponentDesc(String componentDesc) {
        this.componentDesc = componentDesc;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public IaasLjsfw setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasLjsfw{" +
        "id=" + id +
        ", component=" + component +
        ", specification=" + specification +
        ", os=" + os +
        ", net=" + net +
        ", num=" + num +
        ", deployApp=" + deployApp +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", appType=" + appType +
        ", componentDesc=" + componentDesc +
        ", shoppingCartId=" + shoppingCartId +
        "}";
    }
}
