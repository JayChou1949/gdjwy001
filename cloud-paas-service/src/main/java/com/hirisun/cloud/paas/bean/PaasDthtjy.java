package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;

/**
 * 地图-航天精一申请信息
 */
@TableName("TB_PAAS_DTHTJY")
public class PaasDthtjy extends Model<PaasDthtjy> {

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

    @TableField("RES_PERSON")
    private String resPerson;

    @TableField("TEL")
    private String tel;

    @TableField("MAP_CHECK")
    private String mapCheck;


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

    public PaasDthtjy setId(String id) {
        this.id = id;
        return this;
    }

    public Date getValidityPeriodStart() {
        return validityPeriodStart;
    }

    public PaasDthtjy setValidityPeriodStart(Date validityPeriodStart) {
        this.validityPeriodStart = validityPeriodStart;
        return this;
    }

    public String getFrequency() {
        return frequency;
    }

    public PaasDthtjy setFrequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasDthtjy setAppInfoId(String appInfoId) {
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

    public PaasDthtjy setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasDthtjy setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public Date getValidityPeriodEnd() {
        return validityPeriodEnd;
    }

    public PaasDthtjy setValidityPeriodEnd(Date validityPeriodEnd) {
        this.validityPeriodEnd = validityPeriodEnd;
        return this;
    }

    public String getResPerson() {
        return resPerson;
    }

    public PaasDthtjy setResPerson(String resPerson) {
        this.resPerson = resPerson;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public PaasDthtjy setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getMapCheck() {
        return mapCheck;
    }

    public PaasDthtjy setMapCheck(String mapCheck) {
        this.mapCheck = mapCheck;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasDthtjy{" +
        "id=" + id +
        ", validityPeriodStart=" + validityPeriodStart +
        ", frequency=" + frequency +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", validityPeriodEnd=" + validityPeriodEnd +
        ", resPerson=" + resPerson +
        ", tel=" + tel +
        ", mapCheck=" + mapCheck +
        "}";
    }
}
