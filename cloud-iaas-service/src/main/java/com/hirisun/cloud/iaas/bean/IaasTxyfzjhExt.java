package com.hirisun.cloud.iaas.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * <p>
 * 
 * </p>
 *
 * @author zwb
 * @since 2019-04-16
 */
@TableName("TB_IAAS_TXYFZJH_EXT")
public class IaasTxyfzjhExt extends Model<IaasTxyfzjhExt> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;
    /**
     * 主表ID
     */
    @TableField("MASTER_ID")
    private String masterId;
    /**
     * 申请信息 id
     */
    @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;
        /**
     * 后端提供服务的服务IP
     */
         @TableField("SERVER_IP")
    private String serverIp;

        /**
     * 后端端口
     */
         @TableField("SERVER_PORT")
    private String serverPort;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFIED_TIME")
    private Date modifiedTime;
    //访问路径
    @TableField("SERVER_URL")
    private String serverUrl;


    public String getId() {
        return id;
    }

    public IaasTxyfzjhExt setId(String id) {
        this.id = id;
        return this;
    }

    public String getServerIp() {
        return serverIp;
    }

    public IaasTxyfzjhExt setServerIp(String serverIp) {
        this.serverIp = serverIp;
        return this;
    }

    public String getServerPort() {
        return serverPort;
    }

    public IaasTxyfzjhExt setServerPort(String serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasTxyfzjhExt setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasTxyfzjhExt setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public IaasTxyfzjhExt setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public void setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasTxyfzjhExt{" +
                "id='" + id + '\'' +
                ", masterId='" + masterId + '\'' +
                ", appInfoId='" + appInfoId + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", serverUrl='" + serverUrl + '\'' +
                '}';
    }


}
