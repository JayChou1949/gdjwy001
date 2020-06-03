package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 服务发布-api-访问控制IP信息
 * </p>
 *
 * @author wuc
 * @since 2019-10-17
 */
@TableName("TB_API_AC_IP")
public class ApiAcIp extends Model<ApiAcIp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * API ID
     */
         @TableField("MASTER_ID")
    private String masterId;

        /**
     * IP 地址
     */
         @TableField("IP")
    private String ip;

        /**
     * IP 地址范围
     */
         @TableField("IP_RANGE")
    private String ipRange;


    public String getId() {
        return id;
    }

    public ApiAcIp setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ApiAcIp setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ApiAcIp setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public ApiAcIp setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public ApiAcIp setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getIpRange() {
        return ipRange;
    }

    public ApiAcIp setIpRange(String ipRange) {
        this.ipRange = ipRange;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ApiAcIp{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", masterId=" + masterId +
        ", ip=" + ip +
        ", ipRange=" + ipRange +
        "}";
    }
}
