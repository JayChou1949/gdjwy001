package com.upd.hwcloud.bean.entity;

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
 * 疫情申请信息扩展表
 * </p>
 *
 * @author wuc
 * @since 2020-02-27
 */
@TableName("TB_EPIDEMIC_APPLICATION_EXT")
public class EpidemicApplicationExt extends Model<EpidemicApplicationExt> {

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

    public EpidemicApplicationExt setId(String id) {
        this.id = id;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public EpidemicApplicationExt setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public EpidemicApplicationExt setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public EpidemicApplicationExt setMasterId(String masterId) {
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
        return "EpidemicApplicationExt{" +
        "id=" + id +
        ", serviceName=" + serviceName +
        ", serviceId=" + serviceId +
        ", masterId=" + masterId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
