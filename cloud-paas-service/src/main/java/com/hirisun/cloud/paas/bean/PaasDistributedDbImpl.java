package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yyc
 * @since 2020-05-11
 */
@TableName("TB_PAAS_DISTRIBUTED_DB_IMPL")
public class PaasDistributedDbImpl extends Model<PaasDistributedDbImpl> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 数据库类型
     */
         @TableField("DB_TYPE")
    private String dbType;

        /**
     * 数据库
     */
         @TableField("DB")
    private String db;

        /**
     * 主机/IP
     */
         @TableField("HOST")
    private String host;

        /**
     * 账号
     */
         @TableField("ACCOUNT")
    private String account;

        /**
     * 账号-创建权限 0:无 1:有
     */
         @TableField("CAN_CREATED")
    private Integer canCreated;

        /**
     * 账号-修改权限 0:无 1:有
     */
         @TableField("CAN_MODIFIED")
    private Integer canModified;

        /**
     * 账号-查询权限 0:无 1:有
     */
         @TableField("CAN_QUERY")
    private Integer canQuery;

        /**
     * 账号-删除权限 0:无 1:有
     */
         @TableField("CAN_DELETE")
    private Integer canDelete;

        /**
     * 模式名
     */
         @TableField("SCHEMA")
    private String schema;

        /**
     * CPU（核）
     */
         @TableField("CPU")
    private Integer cpu;

        /**
     * 内存（GB）
     */
         @TableField("MEMORY")
    private Double memory;

        /**
     * 存储（TB）
     */
         @TableField("STORAGE")
    private Double storage;

        /**
     * 创建时间
     */
        @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

        /**
     * 修改时间
     */
        @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 申请信息id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 密码
     */
         @TableField("PASSWORD")
    private String password;


    public String getId() {
        return id;
    }

    public PaasDistributedDbImpl setId(String id) {
        this.id = id;
        return this;
    }


    public String getDbType() {
        return dbType;
    }

    public PaasDistributedDbImpl setDbType(String dbType) {
        this.dbType = dbType;
        return this;
    }

    public String getDb() {
        return db;
    }

    public PaasDistributedDbImpl setDb(String db) {
        this.db = db;
        return this;
    }

    public String getHost() {
        return host;
    }

    public PaasDistributedDbImpl setHost(String host) {
        this.host = host;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public PaasDistributedDbImpl setAccount(String account) {
        this.account = account;
        return this;
    }

    public Integer getCanCreated() {
        return canCreated;
    }

    public PaasDistributedDbImpl setCanCreated(Integer canCreated) {
        this.canCreated = canCreated;
        return this;
    }

    public Integer getCanModified() {
        return canModified;
    }

    public PaasDistributedDbImpl setCanModified(Integer canModified) {
        this.canModified = canModified;
        return this;
    }

    public Integer getCanQuery() {
        return canQuery;
    }

    public PaasDistributedDbImpl setCanQuery(Integer canQuery) {
        this.canQuery = canQuery;
        return this;
    }

    public Integer getCanDelete() {
        return canDelete;
    }

    public PaasDistributedDbImpl setCanDelete(Integer canDelete) {
        this.canDelete = canDelete;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public PaasDistributedDbImpl setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public Integer getCpu() {
        return cpu;
    }

    public PaasDistributedDbImpl setCpu(Integer cpu) {
        this.cpu = cpu;
        return this;
    }

    public Double getMemory() {
        return memory;
    }

    public PaasDistributedDbImpl setMemory(Double memory) {
        this.memory = memory;
        return this;
    }

    public Double getStorage() {
        return storage;
    }

    public PaasDistributedDbImpl setStorage(Double storage) {
        this.storage = storage;
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

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasDistributedDbImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PaasDistributedDbImpl setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasDistributedDbImpl{" +
        "id=" + id +
        ", dbType=" + dbType +
        ", db=" + db +
        ", host=" + host +
        ", account=" + account +
        ", canCreated=" + canCreated +
        ", canModified=" + canModified +
        ", canQuery=" + canQuery +
        ", canDelete=" + canDelete +
        ", schema=" + schema +
        ", cpu=" + cpu +
        ", memory=" + memory +
        ", storage=" + storage +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", appInfoId=" + appInfoId +
        ", password=" + password +
        "}";
    }
}
