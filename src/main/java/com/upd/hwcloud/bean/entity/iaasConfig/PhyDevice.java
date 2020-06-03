package com.upd.hwcloud.bean.entity.iaasConfig;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 物理设备
 * </p>
 *
 * @author huru
 * @since 2018-11-17
 */
@TableName("TB_PHY_DEVICE")
public class PhyDevice extends Model<PhyDevice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 服务器
     */
         @TableField("SERVER")
    private String server;

        /**
     * 路由器
     */
         @TableField("ROUTER")
    private String router;

        /**
     * 存储服务器
     */
         @TableField("STORAGE")
    private String storage;

        /**
     * 交换机
     */
         @TableField("CHANGE")
    private String change;

        /**
     * 防火墙
     */
         @TableField("FIREWALL")
    private String firewall;

        /**
     * 大数据
     */
         @TableField("BIGDATA")
    private String bigdata;

        /**
     * 类型
     */
         @TableField("TYPE")
    private String type;

        /**
     * 地区
     */
         @TableField("AREA")
    private String area;


    public String getId() {
        return id;
    }

    public PhyDevice setId(String id) {
        this.id = id;
        return this;
    }

    public String getServer() {
        return server;
    }

    public PhyDevice setServer(String server) {
        this.server = server;
        return this;
    }

    public String getRouter() {
        return router;
    }

    public PhyDevice setRouter(String router) {
        this.router = router;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public PhyDevice setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getChange() {
        return change;
    }

    public PhyDevice setChange(String change) {
        this.change = change;
        return this;
    }

    public String getFirewall() {
        return firewall;
    }

    public PhyDevice setFirewall(String firewall) {
        this.firewall = firewall;
        return this;
    }

    public String getBigdata() {
        return bigdata;
    }

    public PhyDevice setBigdata(String bigdata) {
        this.bigdata = bigdata;
        return this;
    }

    public String getType() {
        return type;
    }

    public PhyDevice setType(String type) {
        this.type = type;
        return this;
    }

    public String getArea() {
        return area;
    }

    public PhyDevice setArea(String area) {
        this.area = area;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PhyDevice{" +
        "id=" + id +
        ", server=" + server +
        ", router=" + router +
        ", storage=" + storage +
        ", change=" + change +
        ", firewall=" + firewall +
        ", bigdata=" + bigdata +
        ", type=" + type +
        ", area=" + area +
        "}";
    }
}
