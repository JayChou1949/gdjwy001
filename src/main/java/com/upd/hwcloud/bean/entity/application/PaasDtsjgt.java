package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 飞识二期授权码申请信息
 * </p>
 *
 * @author zwb
 * @since 2019-05-14
 */
@TableName("TB_PAAS_DTSJGT")
public class PaasDtsjgt extends Model<PaasDtsjgt> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 有效期
     */
         @TableField("VALIDITY_PERIOD_START")
    private Date validityPeriodStart;

        /**
     * 频次
     */
         @TableField("FREQUENCY")
    private String frequency;

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

        /**
     * 有效期
     */
         @TableField("VALIDITY_PERIOD_END")
    private Date validityPeriodEnd;

        /**
     * 技术负责人
     */
         @TableField("RES_PERSON")
    private String resPerson;

    @TableField("TEL")
    private String tel;

        /**
     * 用户名
     */
         @TableField("USER_NAME")
    private String userName;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;


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

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getInstanceGuid() {
        return instanceGuid;
    }

    public void setInstanceGuid(String instanceGuid) {
        this.instanceGuid = instanceGuid;
    }

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

    public String getId() {
        return id;
    }

    public PaasDtsjgt setId(String id) {
        this.id = id;
        return this;
    }

    public Date getValidityPeriodStart() {
        return validityPeriodStart;
    }

    public PaasDtsjgt setValidityPeriodStart(Date validityPeriodStart) {
        this.validityPeriodStart = validityPeriodStart;
        return this;
    }

    public String getFrequency() {
        return frequency;
    }

    public PaasDtsjgt setFrequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasDtsjgt setAppInfoId(String appInfoId) {
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

    public PaasDtsjgt setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasDtsjgt setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public Date getValidityPeriodEnd() {
        return validityPeriodEnd;
    }

    public PaasDtsjgt setValidityPeriodEnd(Date validityPeriodEnd) {
        this.validityPeriodEnd = validityPeriodEnd;
        return this;
    }

    public String getResPerson() {
        return resPerson;
    }

    public PaasDtsjgt setResPerson(String resPerson) {
        this.resPerson = resPerson;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public PaasDtsjgt setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public PaasDtsjgt setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PaasDtsjgt setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasDtsjgt{" +
        "id=" + id +
        ", validityPeriodStart=" + validityPeriodStart +
        ", frequency=" + frequency +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", validityPeriodEnd=" + validityPeriodEnd +
        ", resPerson=" + resPerson +
        ", tel=" + tel +
        ", userName=" + userName +
        ", password=" + password +
        "}";
    }
}
