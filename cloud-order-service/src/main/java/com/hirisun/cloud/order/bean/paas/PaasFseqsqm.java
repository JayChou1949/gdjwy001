package com.hirisun.cloud.order.bean.paas;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 飞识二期授权码申请信息
 * </p>
 *
 * @author huru
 * @since 2019-03-22
 */
@TableName("TB_PAAS_FSEQSQM")
public class PaasFseqsqm extends Model<PaasFseqsqm> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 有效期
     */
         @TableField("VALIDITY_PERIOD_START")
    private Date validityPeriodStart;

    @TableField("VALIDITY_PERIOD_END")
    private Date validityPeriodEnd;

        /**
     * 频次
     */
         @TableField("FREQUENCY")
    private String frequency;
    //技术负责人
    @TableField("RES_PERSON")
    private String resPerson;
    // 联系电话
    @TableField("TEL")
    private String tel;
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

    public String getResPerson() {
        return resPerson;
    }

    public void setResPerson(String resPerson) {
        this.resPerson = resPerson;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getId() {
        return id;
    }

    public PaasFseqsqm setId(String id) {
        this.id = id;
        return this;
    }

    public Date getValidityPeriodStart() {
        return validityPeriodStart;
    }

    public void setValidityPeriodStart(Date validityPeriodStart) {
        this.validityPeriodStart = validityPeriodStart;
    }

    public Date getValidityPeriodEnd() {
        return validityPeriodEnd;
    }

    public void setValidityPeriodEnd(Date validityPeriodEnd) {
        this.validityPeriodEnd = validityPeriodEnd;
    }

    public String getFrequency() {
        return frequency;
    }

    public PaasFseqsqm setFrequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasFseqsqm setAppInfoId(String appInfoId) {
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

    public PaasFseqsqm setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasFseqsqm setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasFseqsqm{" +
        "id=" + id +
        ", frequency=" + frequency +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
