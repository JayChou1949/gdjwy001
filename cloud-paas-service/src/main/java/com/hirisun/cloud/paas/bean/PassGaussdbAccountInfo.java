package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * GAUSSDB 数据库账户信息
 * </p>
 *
 * @author wangbao
 * @since 2019-10-22
 */
@TableName("TB_PASS_GAUSSDB_ACCOUNT_INFO")
public class PassGaussdbAccountInfo extends Model<PassGaussdbAccountInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;
    /**
     * 账号名
     */
    @TableField("ACCOUNT_NAME")
    private String accountName;
    /**
     * 账号密码
     */
    @TableField("ACCOUNT_PASSWORD")
    private String accountPassword;
    /**
     * 权限
     * 0 创建 1 修改 2 删除 3 查询
     */
    @TableField("PERMISSON")
    private String permisson;

    @TableField(value ="CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value="MODIFIED_TIME",fill=FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField("DB_ID")
    private String dbId;


    public String getId() {
        return id;
    }

    public PassGaussdbAccountInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public PassGaussdbAccountInfo setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public PassGaussdbAccountInfo setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
        return this;
    }

    public String getPermisson() {
        return permisson;
    }

    public PassGaussdbAccountInfo setPermisson(String permisson) {
        this.permisson = permisson;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PassGaussdbAccountInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PassGaussdbAccountInfo setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getDbId() {
        return dbId;
    }

    public PassGaussdbAccountInfo setDbId(String dbId) {
        this.dbId = dbId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PassGaussdbAccountInfo{" +
        "id=" + id +
        ", accountName=" + accountName +
        ", accountPassword=" + accountPassword +
        ", permisson=" + permisson +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", dbId=" + dbId +
        "}";
    }
}
