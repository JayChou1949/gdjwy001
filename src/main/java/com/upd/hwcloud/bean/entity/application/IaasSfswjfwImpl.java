package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * sfs弹性文件服务实施信息
 * </p>
 *
 * @author wuc
 * @since 2019-10-25
 */
@TableName("TB_IAAS_SFSWJFW_IMPL")
public class IaasSfswjfwImpl extends Model<IaasSfswjfwImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 容量
     */
         @TableField("CAPACITY")
    private String capacity;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField("IP")
    private String ip;

    @TableField("DOMAIN_NAME")
    private String domainName;

        /**
     * 存储
     */
         @TableField("STORAGE")
    private String storage;

    @TableField("DNS")
    private String dns;

        /**
     * 路径
     */
         @TableField("PATH")
    private String path;


    public String getId() {
        return id;
    }

    public IaasSfswjfwImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getCapacity() {
        return capacity;
    }

    public IaasSfswjfwImpl setCapacity(String capacity) {
        this.capacity = capacity;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasSfswjfwImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasSfswjfwImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasSfswjfwImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public IaasSfswjfwImpl setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getDomainName() {
        return domainName;
    }

    public IaasSfswjfwImpl setDomainName(String domainName) {
        this.domainName = domainName;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public IaasSfswjfwImpl setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getDns() {
        return dns;
    }

    public IaasSfswjfwImpl setDns(String dns) {
        this.dns = dns;
        return this;
    }

    public String getPath() {
        return path;
    }

    public IaasSfswjfwImpl setPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasSfswjfwImpl{" +
        "id=" + id +
        ", capacity=" + capacity +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", ip=" + ip +
        ", domainName=" + domainName +
        ", storage=" + storage +
        ", dns=" + dns +
        ", path=" + path +
        "}";
    }
}
