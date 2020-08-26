package com.hirisun.cloud.paas.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 统一消息申请信息
 */
@TableName("TB_PAAS_TYYH")
public class PaasTyyh extends Model<PaasTyyh> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    /**
     * 统一用户系统
     */
    @TableField("USER_SYS")
    private String userSys;

    /**
     * 单点登录系统
     */
    @TableField("LOGIN_SYS")
    private String loginSys;

    /**
     * 租户功能
     */
    @TableField("TENANT_FUNC")
    private String tenantFunc;

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

    @TableField(exist = false)
    private List<PassTyxxUser> userList;

    /**
     * 业务类型
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_NO")
    private String contractNo;

    /**
     * 应用 URL
     */
    @TableField("APP_URL")
    private String appUrl;

    @TableField("ERROR_MSG")
    private String errorMsg;

    @TableField("ERROR_JSON")
    private String errorJson;

    /**
     * 实例id
     */
    @TableField("INSTANCE_GUID")
    private String instanceGuid;

    /**
     * api列表
     */
    @TableField("USER_DATA")
    private String userData;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorJson() {
        return errorJson;
    }

    public void setErrorJson(String errorJson) {
        this.errorJson = errorJson;
    }

    public String getInstanceGuid() {
        return instanceGuid;
    }

    public void setInstanceGuid(String instanceGuid) {
        this.instanceGuid = instanceGuid;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getId() {
        return id;
    }

    public PaasTyyh setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserSys() {
        return userSys;
    }

    public PaasTyyh setUserSys(String userSys) {
        this.userSys = userSys;
        return this;
    }

    public String getLoginSys() {
        return loginSys;
    }

    public PaasTyyh setLoginSys(String loginSys) {
        this.loginSys = loginSys;
        return this;
    }

    public String getTenantFunc() {
        return tenantFunc;
    }

    public PaasTyyh setTenantFunc(String tenantFunc) {
        this.tenantFunc = tenantFunc;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasTyyh setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PaasTyyh setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasTyyh setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public List<PassTyxxUser> getUserList() {
        return userList;
    }

    public void setUserList(List<PassTyxxUser> userList) {
        this.userList = userList;
    }

    public String getBusinessType() {
        return businessType;
    }

    public PaasTyyh setBusinessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    public String getContractNo() {
        return contractNo;
    }

    public PaasTyyh setContractNo(String contractNo) {
        this.contractNo = contractNo;
        return this;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public PaasTyyh setAppUrl(String appUrl) {
        this.appUrl = appUrl;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasTyyh{" +
                "id='" + id + '\'' +
                ", userSys='" + userSys + '\'' +
                ", loginSys='" + loginSys + '\'' +
                ", tenantFunc='" + tenantFunc + '\'' +
                ", appInfoId='" + appInfoId + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", userList=" + userList +
                ", businessType='" + businessType + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", appUrl='" + appUrl + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", errorJson='" + errorJson + '\'' +
                ", instanceGuid='" + instanceGuid + '\'' +
                ", userData='" + userData + '\'' +
                '}';
    }
}
