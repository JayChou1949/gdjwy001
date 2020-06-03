package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 统一消息申请信息
 * </p>
 *
 * @author huru
 * @since 2018-12-24
 */
@TableName("TB_PAAS_TYXX")
public class PaasTyxx extends Model<PaasTyxx> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 短信功能
     */
         @TableField("SMS_FUNC")
    private String smsFunc;

        /**
     * 政务微信功能
     */
         @TableField("WX_FUNC")
    private String wxFunc;

        /**
     * 邮件功能
     */
         @TableField("EMAIL_FUNC")
    private String emailFunc;

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

    public PaasTyxx setId(String id) {
        this.id = id;
        return this;
    }



    public String getSmsFunc() {
        return smsFunc;
    }

    public PaasTyxx setSmsFunc(String smsFunc) {
        this.smsFunc = smsFunc;
        return this;
    }

    public String getWxFunc() {
        return wxFunc;
    }

    public PaasTyxx setWxFunc(String wxFunc) {
        this.wxFunc = wxFunc;
        return this;
    }

    public String getEmailFunc() {
        return emailFunc;
    }

    public PaasTyxx setEmailFunc(String emailFunc) {
        this.emailFunc = emailFunc;
        return this;
    }


    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasTyxx setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }


    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public PaasTyxx setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasTyxx setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasTyxx{" +
                "id='" + id + '\'' +
                ", smsFunc='" + smsFunc + '\'' +
                ", wxFunc='" + wxFunc + '\'' +
                ", emailFunc='" + emailFunc + '\'' +
                ", appInfoId='" + appInfoId + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", errorMsg='" + errorMsg + '\'' +
                ", errorJson='" + errorJson + '\'' +
                ", instanceGuid='" + instanceGuid + '\'' +
                ", userData='" + userData + '\'' +
                '}';
    }
}
