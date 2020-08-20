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
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author yyc
 * @since 2020-05-11
 */
@TableName("TB_PAAS_COMPONENT_IMPL")
public class PaasComponentImpl extends Model<PaasComponentImpl> {

    private static final long serialVersionUID = 1L;

        /**
     * 主键UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * IP:端口
     */
         @TableField("IP_PORT")
    private String ipPort;

        /**
     * 账号名
     */
         @TableField("ACCOUNT")
    private String account;

        /**
     * 密码
     */
         @TableField("PASSWORD")
    private String password;

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

    /**
     * 具体组件信息
     */
    @TableField(exist = false)
        private List<PaasComponentDetailImpl> detailImplList;


    public String getId() {
        return id;
    }

    public PaasComponentImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getIpPort() {
        return ipPort;
    }

    public PaasComponentImpl setIpPort(String ipPort) {
        this.ipPort = ipPort;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public PaasComponentImpl setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PaasComponentImpl setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasComponentImpl setAppInfoId(String appInfoId) {
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

    public List<PaasComponentDetailImpl> getDetailImplList() {
        return detailImplList;
    }

    public void setDetailImplList(List<PaasComponentDetailImpl> detailImplList) {
        this.detailImplList = detailImplList;
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
        return "PaasComponentImpl{" +
        "id=" + id +
        ", ipPort=" + ipPort +
        ", account=" + account +
        ", password=" + password +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
