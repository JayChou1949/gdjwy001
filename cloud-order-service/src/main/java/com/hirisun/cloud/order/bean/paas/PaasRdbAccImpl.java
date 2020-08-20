package com.hirisun.cloud.order.bean.paas;

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
 * <p>
 * 关系型数据库账号实施
 * </p>
 *
 * @author yyc
 * @since 2020-04-19
 */
@TableName("TB_PAAS_RDB_ACC_IMPL")
public class PaasRdbAccImpl extends Model<PaasRdbAccImpl> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
        @TableId(value = "ID", type = IdType.INPUT)
    private String id;

        /**
     * 账号名
     */
         @TableField("ACCOUNT")
    private String account;

        /**
     * 责任单位
     */
         @TableField("RESPONSIBLE_UNIT")
    private String responsibleUnit;

        /**
     * 责任单位电话
     */
         @TableField("RESPONSIBLE_UNIT_PHONE")
    private String responsibleUnitPhone;

        /**
     * 责任单位负责人
     */
         @TableField("RESPONSIBLE_UNIT_PERSON")
    private String responsibleUnitPerson;

        /**
     * 用途说明
     */
         @TableField("USE_EXPLANATION")
    private String useExplanation;

    /**
     * 备注
     */
    @TableField("REMARKS")
    private String remarks;

        /**
     * 申请单ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 基本信息ID
     */
         @TableField("MASTER_ID")
    private String masterId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 权限(数字字符串 ‘，’分割)
     */
         @TableField("PERMISSION")
    private String permission;

        /**
     * 登录IP/范围
     */
         @TableField("LOGIN_IP")
    private String loginIp;

        /**
     * 数据库类型  Mysql : 0  Oracle : 1
     */
         @TableField("DB_TYPE")
    private Integer dbType;

        /**
     * 密码
     */
         @TableField("PASSWORD")
    private String password;


    /**
     * 账号关联数据库 ,分割
     */
    @TableField("ASSOCIATED_DB")
    private String associatedDb;

    /**
     * 账号对应数据库
     */
    @TableField(exist = false)
    private List<PaasRdbInfoImpl> infoImplList;



    public String getId() {
        return id;
    }

    public PaasRdbAccImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public PaasRdbAccImpl setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getResponsibleUnit() {
        return responsibleUnit;
    }

    public PaasRdbAccImpl setResponsibleUnit(String responsibleUnit) {
        this.responsibleUnit = responsibleUnit;
        return this;
    }

    public String getResponsibleUnitPhone() {
        return responsibleUnitPhone;
    }

    public PaasRdbAccImpl setResponsibleUnitPhone(String responsibleUnitPhone) {
        this.responsibleUnitPhone = responsibleUnitPhone;
        return this;
    }

    public String getResponsibleUnitPerson() {
        return responsibleUnitPerson;
    }

    public PaasRdbAccImpl setResponsibleUnitPerson(String responsibleUnitPerson) {
        this.responsibleUnitPerson = responsibleUnitPerson;
        return this;
    }

    public String getUseExplanation() {
        return useExplanation;
    }

    public void setUseExplanation(String useExplanation) {
        this.useExplanation = useExplanation;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasRdbAccImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public PaasRdbAccImpl setMasterId(String masterId) {
        this.masterId = masterId;
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

    public String getPermission() {
        return permission;
    }

    public PaasRdbAccImpl setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public PaasRdbAccImpl setLoginIp(String loginIp) {
        this.loginIp = loginIp;
        return this;
    }

    public Integer getDbType() {
        return dbType;
    }

    public PaasRdbAccImpl setDbType(Integer dbType) {
        this.dbType = dbType;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PaasRdbAccImpl setPassword(String password) {
        this.password = password;
        return this;
    }


    public String getAssociatedDb() {
        return associatedDb;
    }

    public void setAssociatedDb(String associatedDb) {
        this.associatedDb = associatedDb;
    }

    public List<PaasRdbInfoImpl> getInfoImplList() {
        return infoImplList;
    }

    public void setInfoImplList(List<PaasRdbInfoImpl> infoImplList) {
        this.infoImplList = infoImplList;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasRdbAccImpl{" +
        "id=" + id +
        ", account=" + account +
        ", responsibleUnit=" + responsibleUnit +
        ", responsibleUnitPhone=" + responsibleUnitPhone +
        ", responsibleUnitPerson=" + responsibleUnitPerson +
        ", useExplanation=" + useExplanation +
        ", appInfoId=" + appInfoId +
        ", masterId=" + masterId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", permission=" + permission +
        ", loginIp=" + loginIp +
        ", dbType=" + dbType +
        ", password=" + password +
        "}";
    }
}
