package com.upd.hwcloud.bean.entity.iaasConfig;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 虚拟设备
 * </p>
 *
 * @author huru
 * @since 2018-11-17
 */
@TableName("TB_VM_DEVICE")
public class VmDevice extends Model<VmDevice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 云服务器
     */
         @TableField("CLOUD_SERVER")
    private String cloudServer;

        /**
     * 裸金属服务器
     */
         @TableField("METAL_SERVER")
    private String metalServer;

        /**
     * 镜像
     */
         @TableField("IMAGE")
    private String image;

        /**
     * 云硬盘
     */
         @TableField("CLOUD_DISK")
    private String cloudDisk;

        /**
     * 虚拟私有云
     */
         @TableField("PRI_CLOUD")
    private String priCloud;

        /**
     * 弹性ip
     */
         @TableField("ELA_IP")
    private String elaIp;

        /**
     * 弹性负载均衡
     */
         @TableField("ELA_LOAD")
    private String elaLoad;

        /**
     * 虚拟防火墙
     */
         @TableField("VM_FIREWELL")
    private String vmFirewell;

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

    public VmDevice setId(String id) {
        this.id = id;
        return this;
    }

    public String getCloudServer() {
        return cloudServer;
    }

    public VmDevice setCloudServer(String cloudServer) {
        this.cloudServer = cloudServer;
        return this;
    }

    public String getMetalServer() {
        return metalServer;
    }

    public VmDevice setMetalServer(String metalServer) {
        this.metalServer = metalServer;
        return this;
    }

    public String getImage() {
        return image;
    }

    public VmDevice setImage(String image) {
        this.image = image;
        return this;
    }

    public String getCloudDisk() {
        return cloudDisk;
    }

    public VmDevice setCloudDisk(String cloudDisk) {
        this.cloudDisk = cloudDisk;
        return this;
    }

    public String getPriCloud() {
        return priCloud;
    }

    public VmDevice setPriCloud(String priCloud) {
        this.priCloud = priCloud;
        return this;
    }

    public String getElaIp() {
        return elaIp;
    }

    public VmDevice setElaIp(String elaIp) {
        this.elaIp = elaIp;
        return this;
    }

    public String getElaLoad() {
        return elaLoad;
    }

    public VmDevice setElaLoad(String elaLoad) {
        this.elaLoad = elaLoad;
        return this;
    }

    public String getVmFirewell() {
        return vmFirewell;
    }

    public VmDevice setVmFirewell(String vmFirewell) {
        this.vmFirewell = vmFirewell;
        return this;
    }

    public String getType() {
        return type;
    }

    public VmDevice setType(String type) {
        this.type = type;
        return this;
    }

    public String getArea() {
        return area;
    }

    public VmDevice setArea(String area) {
        this.area = area;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VmDevice{" +
        "id=" + id +
        ", cloudServer=" + cloudServer +
        ", metalServer=" + metalServer +
        ", image=" + image +
        ", cloudDisk=" + cloudDisk +
        ", priCloud=" + priCloud +
        ", elaIp=" + elaIp +
        ", elaLoad=" + elaLoad +
        ", vmFirewell=" + vmFirewell +
        ", type=" + type +
        ", area=" + area +
        "}";
    }
}
