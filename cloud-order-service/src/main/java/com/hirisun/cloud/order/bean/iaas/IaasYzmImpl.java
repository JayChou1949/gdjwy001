package com.hirisun.cloud.order.bean.iaas;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * IaaS 云桌面实施信息
 * </p>
 *
 * @author wuc
 * @since 2019-01-07
 */
@TableName("TB_IAAS_YZM_IMPL")
public class IaasYzmImpl extends Model<IaasYzmImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    /**
     * 账号
     */
    @TableField("ACCOUNT")
    private String account;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public IaasYzmImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasYzmImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasYzmImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasYzmImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasYzmImpl{" +
        "id=" + id +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
