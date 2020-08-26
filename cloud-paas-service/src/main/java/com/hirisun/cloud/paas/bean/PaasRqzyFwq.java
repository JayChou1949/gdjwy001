package com.hirisun.cloud.paas.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hirisun.cloud.common.annotation.ExcelExplain;

/**
 * 服务器信息
 */
@TableName("TB_PAAS_RQZY_FWQ")
public class PaasRqzyFwq extends Model<PaasRqzyFwq> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 节点配置
     */
        @ExcelExplain(head = "节点配置(cpu/内存/系统盘容量)")
         @TableField("NODE_CONFIG")
    private String nodeConfig;

    @ExcelExplain(head = "数据盘存储(GB)")
    @TableField("STORAGES")
    private String storages;

        /**
     * 节点总数
     */
        @ExcelExplain(head = "节点总数(虚拟机)")
         @TableField("NODE_NUM")
    private Integer nodeNum;

    /**
     * 实例总数
     */
    @ExcelExplain(head = "实例总数(可发放容器数量)")
    @TableField("INSTANCE_NUM")
    private Integer instanceNum;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


        /**
     * 主表ID
     */
         @TableField("MASTER_ID")
    private String masterId;

    @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;


    public String getId() {
        return id;
    }

    public PaasRqzyFwq setId(String id) {
        this.id = id;
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

    public PaasRqzyFwq setNodeConfig(String nodeConfig) {
        this.nodeConfig = nodeConfig;
        return this;
    }

    public Integer getNodeNum() {
        return nodeNum;
    }

    public PaasRqzyFwq setNodeNum(Integer nodeNum) {
        this.nodeNum = nodeNum;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PaasRqzyFwq setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasRqzyFwq setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public PaasRqzyFwq setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasRqzyFwq setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Integer getInstanceNum() {
        return instanceNum;
    }

    public PaasRqzyFwq setInstanceNum(Integer instanceNum) {
        this.instanceNum = instanceNum;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasRqzyFwq{" +
        "id=" + id +
        ", nodeConfig=" + nodeConfig +
        ", nodeNum=" + nodeNum +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", masterId=" + masterId +
        ", appInfoId=" + appInfoId +
        ", instanceNum=" + instanceNum +
        "}";
    }
}
