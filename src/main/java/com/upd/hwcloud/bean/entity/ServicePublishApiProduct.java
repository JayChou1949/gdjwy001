package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 服务发布-api产品
 * </p>
 *
 * @author wuc
 * @since 2019-10-17
 */
@TableName("TB_SERVICE_PUBLISH_API_PRODUCT")
public class ServicePublishApiProduct extends Model<ServicePublishApiProduct> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 服务发布ID
     */
         @TableField("PUBLISH_ID")
    private String publishId;

        /**
     * API名称
     */
         @TableField("NAME")
    private String name;

        /**
     * 版本
     */
         @TableField("VERSION")
    private String version;

        /**
     * 微网关
     */
         @TableField("MICROGW")
    private String microgw;

        /**
     * 微网关域名
     */
         @TableField("MICROGW_DOMAIN_NAME")
    private String microgwDomainName;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;


    public String getId() {
        return id;
    }

    public ServicePublishApiProduct setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ServicePublishApiProduct setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ServicePublishApiProduct setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getPublishId() {
        return publishId;
    }

    public ServicePublishApiProduct setPublishId(String publishId) {
        this.publishId = publishId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ServicePublishApiProduct setName(String name) {
        this.name = name;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ServicePublishApiProduct setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getMicrogw() {
        return microgw;
    }

    public ServicePublishApiProduct setMicrogw(String microgw) {
        this.microgw = microgw;
        return this;
    }

    public String getMicrogwDomainName() {
        return microgwDomainName;
    }

    public ServicePublishApiProduct setMicrogwDomainName(String microgwDomainName) {
        this.microgwDomainName = microgwDomainName;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ServicePublishApiProduct setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ServicePublishApiProduct{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", publishId=" + publishId +
        ", name=" + name +
        ", version=" + version +
        ", microgw=" + microgw +
        ", microgwDomainName=" + microgwDomainName +
        ", remark=" + remark +
        "}";
    }
}
