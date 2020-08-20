package com.hirisun.cloud.order.bean.paas;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 容器资源申请实施信息
 */
@TableName("TB_PAAS_RQZY_IMPL")
public class PaasRqzyImpl extends Model<PaasRqzyImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    @TableField("NODE_IP")
    private String nodeIp;

    @TableField("NODE_CONFIG")
    private String nodeConfig;
    @TableField("STORAGES")
    private String storages;
        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 容器资源ID
     */
         @TableField("MASTER_ID")
    private String masterId;

        /**
     * ROOT用户名
     */
         @TableField("USER_NAME")
    private String userName;

        /**
     * 节点密码
     */
         @TableField("PASSWORD")
    private String password;
    /**
     * 开发端口
     */
    @TableField("OPEN_PORT")
    private String openPort;



    public String getId() {
        return id;
    }

    public PaasRqzyImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public PaasRqzyImpl setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
        return this;
    }

    public String getStorages() {
        return storages;
    }

    public void setStorages(String storages) {
        this.storages = storages;
    }

    public String getNodeConfig() {
        return nodeConfig;
    }

    public PaasRqzyImpl setNodeConfig(String nodeConfig) {
        this.nodeConfig = nodeConfig;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasRqzyImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PaasRqzyImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasRqzyImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public PaasRqzyImpl setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public PaasRqzyImpl setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PaasRqzyImpl setPassword(String password) {
        this.password = password;
        return this;
    }
    public String getOpenPort() {
        return openPort;
    }

    public void setOpenPort(String openPort) {
        this.openPort = openPort;
    }
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasRqzyImpl{" +
                "id='" + id + '\'' +
                ", nodeIp='" + nodeIp + '\'' +
                ", nodeConfig='" + nodeConfig + '\'' +
                ", storages='" + storages + '\'' +
                ", appInfoId='" + appInfoId + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", masterId='" + masterId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", openPort='" + openPort + '\'' +
                '}';
    }


}
