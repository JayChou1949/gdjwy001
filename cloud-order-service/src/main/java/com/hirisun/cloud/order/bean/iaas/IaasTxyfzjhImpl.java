package com.hirisun.cloud.order.bean.iaas;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 飞识二期授权码实施信息
 * </p>
 *
 * @author zwb
 * @since 2019-04-16
 */
@TableName("TB_IAAS_TXYFZJH_IMPL")
public class IaasTxyfzjhImpl extends Model<IaasTxyfzjhImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField("IP")
    private String ip;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFIED_TIME")
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public IaasTxyfzjhImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public IaasTxyfzjhImpl setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasTxyfzjhImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasTxyfzjhImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasTxyfzjhImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasTxyfzjhImpl{" +
        "id=" + id +
        ", ip=" + ip +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
