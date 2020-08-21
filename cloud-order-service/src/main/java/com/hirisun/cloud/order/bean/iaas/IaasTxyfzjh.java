package com.hirisun.cloud.order.bean.iaas;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 飞识二期授权码申请信息
 * </p>
 *
 * @author zwb
 * @since 2019-04-16
 */
@TableName("TB_IAAS_TXYFZJH")
public class IaasTxyfzjh extends Model<IaasTxyfzjh> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

         @TableField("SHOPPING_CART_ID")
         private String shoppingCartId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFIED_TIME")
    private Date modifiedTime;

        /**
     * 前端访问协议
     */
         @TableField("ACCESS_PROTOCOL")
    private String accessProtocol;

        /**
     * 前端访问端口
     */
         @TableField("ACCESS_PORT")
    private String accessPort;

        /**
     * 分配策略类型
     */
         @TableField("STRAGE_TYPE")
    private String strageType;

        /**
     * 会话保持类型
     */
         @TableField("SESSION_TYPE")
    private String sessionType;
    @TableField(exist = false)
    private List<IaasTxyfzjhExt> IaasTxyfzjhExts;//表格

    public String getId() {
        return id;
    }

    public IaasTxyfzjh setId(String id) {
        this.id = id;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasTxyfzjh setAppInfoId(String appInfoId) {
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

    public IaasTxyfzjh setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasTxyfzjh setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getAccessProtocol() {
        return accessProtocol;
    }

    public IaasTxyfzjh setAccessProtocol(String accessProtocol) {
        this.accessProtocol = accessProtocol;
        return this;
    }

    public List<IaasTxyfzjhExt> getIaasTxyfzjhExts() {
        return IaasTxyfzjhExts;
    }

    public void setIaasTxyfzjhExts(List<IaasTxyfzjhExt> iaasTxyfzjhExts) {
        IaasTxyfzjhExts = iaasTxyfzjhExts;
    }

    public String getAccessPort() {
        return accessPort;
    }

    public IaasTxyfzjh setAccessPort(String accessPort) {
        this.accessPort = accessPort;
        return this;
    }

    public String getStrageType() {
        return strageType;
    }

    public IaasTxyfzjh setStrageType(String strageType) {
        this.strageType = strageType;
        return this;
    }

    public String getSessionType() {
        return sessionType;
    }

    public IaasTxyfzjh setSessionType(String sessionType) {
        this.sessionType = sessionType;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasTxyfzjh{" +
        "id=" + id +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", accessProtocol=" + accessProtocol +
        ", accessPort=" + accessPort +
        ", strageType=" + strageType +
        ", sessionType=" + sessionType +
        "}";
    }
}
