package com.hirisun.cloud.paas.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * PaaS 数据库实施信息
 */
@TableName("TB_PAAS_DATABASE_IMPL")
public class PaasDatabaseImpl extends Model<PaasDatabaseImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 账号
     */
         @TableField("ACCOUNT")
    private String account;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 密码
     */
         @TableField("PASSWORD")
    private String password;


    public String getId() {
        return id;
    }

    public PaasDatabaseImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public PaasDatabaseImpl setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasDatabaseImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PaasDatabaseImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasDatabaseImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PaasDatabaseImpl setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasDatabaseImpl{" +
        "id=" + id +
        ", account=" + account +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", password=" + password +
        "}";
    }
}
