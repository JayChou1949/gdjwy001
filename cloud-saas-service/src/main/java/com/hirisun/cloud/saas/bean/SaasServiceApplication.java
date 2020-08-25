package com.hirisun.cloud.saas.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * saas服务应用申请信息
 */
@TableName("TB_SAAS_SERVICE_APPLICATION")
public class SaasServiceApplication extends Model<SaasServiceApplication> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String ID;

        /**
     * 服务名
     */
         @TableField("NAME")
    private String name;

        /**
     * 本地服务ID
     */
         @TableField("SERVICE_ID")
    private String serviceId;

        /**
     * APIG服务ID
     */
         @TableField("API_GUID")
    private String apiGuid;

    /**
     * 版本
     */
    @TableField("VERSION")
     private String version;

        /**
     * 申请订单ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField("APP_KEY")
    private String appKey;

    @TableField("APP_SECRET")
    private String appSecret;

        /**
     * 错误信息
     */
         @TableField("ERROR_MSG")
    private String errorMsg;

        /**
     * 错误JSON
     */
         @TableField("ERROR_JSON")
    private String errorJson;

        /**
     * 实例id
     */
         @TableField("INSTANCE_GUID")
    private String instanceGuid;

    @TableField("USER_DATA")
    private String userData;


    public String getID() {
        return ID;
    }

    public SaasServiceApplication setID(String ID) {
        this.ID  = ID ;
        return this;
    }

    public String getName() {
        return name;
    }

    public SaasServiceApplication setName(String name) {
        this.name = name;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public SaasServiceApplication setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getApiGuid() {
        return apiGuid;
    }

    public SaasServiceApplication setApiGuid(String apiGuid) {
        this.apiGuid = apiGuid;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public SaasServiceApplication setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SaasServiceApplication setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public SaasServiceApplication setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public SaasServiceApplication setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public SaasServiceApplication setAppSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public SaasServiceApplication setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public String getErrorJson() {
        return errorJson;
    }

    public SaasServiceApplication setErrorJson(String errorJson) {
        this.errorJson = errorJson;
        return this;
    }

    public String getInstanceGuid() {
        return instanceGuid;
    }

    public SaasServiceApplication setInstanceGuid(String instanceGuid) {
        this.instanceGuid = instanceGuid;
        return this;
    }

    public String getUserData() {
        return userData;
    }

    public SaasServiceApplication setUserData(String userData) {
        this.userData = userData;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.ID ;
    }


    @Override
    public String toString() {
        return "SaasServiceApplication{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", apiGuid='" + apiGuid + '\'' +
                ", version='" + version + '\'' +
                ", appInfoId='" + appInfoId + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", appKey='" + appKey + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", errorJson='" + errorJson + '\'' +
                ", instanceGuid='" + instanceGuid + '\'' +
                ", userData='" + userData + '\'' +
                '}';
    }
}
