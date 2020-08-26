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
 * 基于虚拟机的DCS分布式缓存实施表
 *
 * DMS ELB公用改实施表
 * </p>
 *
 * @author yyc
 * @since 2020-05-07
 */
@TableName("TB_PAAS_DCS_IMPL")
public class PaasDcsImpl extends Model<PaasDcsImpl> {

    private static final long serialVersionUID = 1L;

        /**
     * 主键 UUID
     */
         @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 账号
     */
         @TableField("ACCOUNT")
    private String account;

        /**
     * 密码
     */
         @TableField("PASS_WORD")
    private String passWord;

        /**
     * IP
     */
         @TableField("IP")
    private String ip;

        /**
     * 端口
     */
         @TableField("PORT")
    private String port;

        /**
     * DCS_ID
     */
         @TableField("MASTER_ID")
    private String masterId;

        /**
     * 订单ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 创建时间
     */
         @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

        /**
     * 修改时间
     */
         @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public PaasDcsImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public PaasDcsImpl setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public PaasDcsImpl setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public PaasDcsImpl setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getPort() {
        return port;
    }

    public PaasDcsImpl setPort(String port) {
        this.port = port;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public PaasDcsImpl setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasDcsImpl setAppInfoId(String appInfoId) {
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasDcsImpl{" +
        "id=" + id +
        ", account=" + account +
        ", passWord=" + passWord +
        ", ip=" + ip +
        ", port=" + port +
        ", masterId=" + masterId +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
