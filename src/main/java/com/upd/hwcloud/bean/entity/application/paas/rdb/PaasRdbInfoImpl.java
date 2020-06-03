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
import java.util.List;

/**
 * <p>
 * 关系型数据数据库信息实施
 * </p>
 *
 * @author yyc
 * @since 2020-04-19
 */
@TableName("TB_PAAS_RDB_INFO_IMPL")
public class PaasRdbInfoImpl extends Model<PaasRdbInfoImpl> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
        @TableId(value = "ID", type = IdType.INPUT)
    private String id;

        /**
     * 数据库名/表空间名
     */
         @TableField("NAME")
    private String name;

        /**
     * 预期大小(GB) 0-10000 四位小数
     */
         @TableField("EXCEPT_SIZE")
    private Double exceptSize;

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
     * 基本信息ID
     */
         @TableField("MASTER_ID")
    private String masterId;

        /**
     * 申请订单ID
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

        /**
     * 始发空间(GB)
     */
         @TableField("INIT_SIZE")
    private Double initSize;

        /**
     * 数据库类型  Mysql : 0  Oracle : 1
     */
         @TableField("DB_TYPE")
    private Integer dbType;

    /**
     * 数据库关联账号 ,分割
     */
    @TableField("ASSOCIATED_ACC")
    private String associatedAcc;


    /**
     * 数据库下的账号
     */
    @TableField(exist = false)
    private List<PaasRdbAccImpl> rdbAccImplList;


    public String getId() {
        return id;
    }

    public PaasRdbInfoImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PaasRdbInfoImpl setName(String name) {
        this.name = name;
        return this;
    }

    public Double getExceptSize() {
        return exceptSize;
    }

    public PaasRdbInfoImpl setExceptSize(Double exceptSize) {
        this.exceptSize = exceptSize;
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

    public PaasRdbInfoImpl setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public PaasRdbInfoImpl setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasRdbInfoImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
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

    public String getPassword() {
        return password;
    }

    public PaasRdbInfoImpl setPassword(String password) {
        this.password = password;
        return this;
    }

    public Double getInitSize() {
        return initSize;
    }

    public PaasRdbInfoImpl setInitSize(Double initSize) {
        this.initSize = initSize;
        return this;
    }

    public Integer getDbType() {
        return dbType;
    }

    public PaasRdbInfoImpl setDbType(Integer dbType) {
        this.dbType = dbType;
        return this;
    }


    public String getAssociatedAcc() {
        return associatedAcc;
    }

    public void setAssociatedAcc(String associatedAcc) {
        this.associatedAcc = associatedAcc;
    }

    public List<PaasRdbAccImpl> getRdbAccImplList() {
        return rdbAccImplList;
    }

    public void setRdbAccImplList(List<PaasRdbAccImpl> rdbAccImplList) {
        this.rdbAccImplList = rdbAccImplList;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasRdbInfoImpl{" +
        "id=" + id +
        ", name=" + name +
        ", exceptSize=" + exceptSize +
        ", useExplain=" + useExplanation +
        ", remarks=" + remarks +
        ", masterId=" + masterId +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", password=" + password +
        ", initSize=" + initSize +
        ", dbType=" + dbType +
        "}";
    }
}
