package com.hirisun.cloud.order.bean.paas;

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
 * Libra+分布式并行数据库申请表
 * </p>
 *
 * @author wuc
 * @since 2020-03-20
 */
@TableName("TB_PAAS_DISTRIBUTED_DB_INFO")
public class PaasDistributedDbInfo extends Model<PaasDistributedDbInfo> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 应用名
     */
         @TableField("APP_NAME")
    private String appName;

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
     * 申请原因
     */
         @TableField("APPLY_REASON")
    private String applyReason;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

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

         @TableField("SHOPPING_CART_ID")
         private String shoppingCartId;


    public String getId() {
        return id;
    }

    public PaasDistributedDbInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public PaasDistributedDbInfo setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getDbType() {
        return dbType;
    }

    public PaasDistributedDbInfo setDbType(String dbType) {
        this.dbType = dbType;
        return this;
    }

    public String getDb() {
        return db;
    }

    public PaasDistributedDbInfo setDb(String db) {
        this.db = db;
        return this;
    }

    public String getHost() {
        return host;
    }

    public PaasDistributedDbInfo setHost(String host) {
        this.host = host;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public PaasDistributedDbInfo setAccount(String account) {
        this.account = account;
        return this;
    }

    public Integer getCanCreated() {
        return canCreated;
    }

    public PaasDistributedDbInfo setCanCreated(Integer canCreated) {
        this.canCreated = canCreated;
        return this;
    }

    public Integer getCanModified() {
        return canModified;
    }

    public PaasDistributedDbInfo setCanModified(Integer canModified) {
        this.canModified = canModified;
        return this;
    }

    public Integer getCanQuery() {
        return canQuery;
    }

    public PaasDistributedDbInfo setCanQuery(Integer canQuery) {
        this.canQuery = canQuery;
        return this;
    }

    public Integer getCanDelete() {
        return canDelete;
    }

    public PaasDistributedDbInfo setCanDelete(Integer canDelete) {
        this.canDelete = canDelete;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public PaasDistributedDbInfo setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public Integer getCpu() {
        return cpu;
    }

    public PaasDistributedDbInfo setCpu(Integer cpu) {
        this.cpu = cpu;
        return this;
    }

    public Double getMemory() {
        return memory;
    }

    public PaasDistributedDbInfo setMemory(Double memory) {
        this.memory = memory;
        return this;
    }

    public Double getStorage() {
        return storage;
    }

    public PaasDistributedDbInfo setStorage(Double storage) {
        this.storage = storage;
        return this;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public PaasDistributedDbInfo setApplyReason(String applyReason) {
        this.applyReason = applyReason;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public PaasDistributedDbInfo setRemark(String remark) {
        this.remark = remark;
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

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public PaasDistributedDbInfo setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasDistributedDbInfo{" +
        "id=" + id +
        ", appName=" + appName +
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
        ", applyReason=" + applyReason +
        ", remark=" + remark +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", appInfoId=" + appInfoId +
        "}";
    }
}
