package com.hirisun.cloud.iaas.bean;

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
 * IaaS对象存储实施信息表
 * </p>
 *
 * @author huru
 * @since 2019-03-29
 */
@TableName("TB_IAAS_DXCC_IMPL")
public class IaasDxccImpl extends Model<IaasDxccImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField("ACCESS_KEY")
    private String accessKey;

    @TableField("ACCESS_SECRET")
    private String accessSecret;

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
     * 容量大小GB
     */
         @TableField("STORAGE")
    private String storage;

        /**
     * 桶名称
     */
         @TableField("BUCKET_NAME")
    private String bucketName;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField("DOMAIN_NAME")
    private String domainName;

    @TableField("IP")
    private String ip;

    @TableField(exist = false)
    private List<IaasDxccImplExt> ccxx;

    public String getId() {
        return id;
    }

    public IaasDxccImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public IaasDxccImpl setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public IaasDxccImpl setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public IaasDxccImpl setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IaasDxccImpl setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public IaasDxccImpl setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public IaasDxccImpl setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasDxccImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasDxccImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasDxccImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<IaasDxccImplExt> getCcxx() {
        return ccxx;
    }

    public void setCcxx(List<IaasDxccImplExt> ccxx) {
        this.ccxx = ccxx;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasDxccImpl{" +
        "id=" + id +
        ", accessKey=" + accessKey +
        ", accessSecret=" + accessSecret +
        ", account=" + account +
        ", password=" + password +
        ", storage=" + storage +
        ", bucketName=" + bucketName +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
