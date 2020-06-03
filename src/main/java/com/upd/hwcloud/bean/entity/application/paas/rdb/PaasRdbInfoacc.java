package com.upd.hwcloud.bean.entity.application.paas.rdb;

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
 * 关系型数据库账号关联
 * </p>
 *
 * @author yyc
 * @since 2020-04-19
 */
@TableName("TB_PAAS_RDB_INFOACC")
public class PaasRdbInfoacc extends Model<PaasRdbInfoacc> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
        @TableId(value = "ID", type = IdType.INPUT)
    private String id;

        /**
     * 数据库ID
     */
         @TableField("DB_ID")
    private String dbId;

        /**
     * 账号ID
     */
         @TableField("ACCOUNT_ID")
    private String accountId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public PaasRdbInfoacc setId(String id) {
        this.id = id;
        return this;
    }

    public String getDbId() {
        return dbId;
    }

    public PaasRdbInfoacc setDbId(String dbId) {
        this.dbId = dbId;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public PaasRdbInfoacc setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasRdbInfoacc{" +
        "id=" + id +
        ", dbId=" + dbId +
        ", accountId=" + accountId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
