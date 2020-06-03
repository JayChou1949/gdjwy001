package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * SaaS申请原始信息扩展表
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
 */
@TableName("TB_SAAS_APPLICATION_EXT")
public class SaasApplicationExt extends Model<SaasApplicationExt> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 服务名称
     */
         @TableField("SERVICE_NAME")
    private String serviceName;

        /**
     * 服务ID
     */
         @TableField("SERVICE_ID")
    private String serviceId;

        /**
     * 申请信息 id
     */
         @TableField("MASTER_ID")
    private String masterId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public SaasApplicationExt setId(String id) {
        this.id = id;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public SaasApplicationExt setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public SaasApplicationExt setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public SaasApplicationExt setMasterId(String masterId) {
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SaasApplicationExt{" +
                "id='" + id + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", masterId='" + masterId + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                '}';
    }

}
