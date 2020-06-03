package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资产
 * </p>
 *
 * @author huru
 * @since 2018-11-27
 */
@TableName("TB_ASSET")
public class Asset extends Model<Asset> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 创建人
     */
    @TableField("CREATOR")
    private String creator;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_NO")
    private String contractNo;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("TIME")
    private Date time;

    /**
     * 厂家
     */
    @TableField("VENDER")
    private String vender;

    /**
     * 产品名称
     */
    @TableField("DEVICE_NAME")
    private String deviceName;

    /**
     * 数量
     */
    @TableField("AMOUNT")
    private Long amount;

    /**
     * 设备型号
     */
    @TableField("MODEL_NUMBER")
    private String modelNumber;

    /**
     * 产品配置名称
     */
    @TableField("CONFIG")
    private String config;

    /**
     * 安装位置
     */
    @TableField("LOCATION")
    private String location;

    /**
     * 机房
     */
    @TableField("COMPUTER_ROOM")
    private String computerRoom;

    /**
     * 机柜
     */
    @TableField("CABINET")
    private String cabinet;

    /**
     * 机架
     */
    @TableField("FRAME")
    private String frame;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 产品线
     */
    @TableField("PRODUCT_LINE")
    private String productLine;

    /**
     * 设备类型
     */
    @TableField("DEVICE_TYPE")
    private String deviceType;

    /**
     * 产品族
     */
    @TableField("PRODUCT_TYPE")
    private String productType;

    @TableField("BOQ_NO")
    private String boqNo;

    /**
     * 总数量
     */
    @TableField("TOTAL")
    private Long total;

    /**
     * 站点
     */
    @TableField("SITE")
    private String site;

    /**
     * 退税软件名称
     */
    @TableField("SOFTWARE_NAME")
    private String softwareName;

    public String getId() {
        return id;
    }

    public Asset setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Asset setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public Asset setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Asset setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getContractNo() {
        return contractNo;
    }

    public Asset setContractNo(String contractNo) {
        this.contractNo = contractNo;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Asset setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getVender() {
        return vender;
    }

    public Asset setVender(String vender) {
        this.vender = vender;
        return this;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Asset setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public Asset setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public Asset setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
        return this;
    }

    public String getConfig() {
        return config;
    }

    public Asset setConfig(String config) {
        this.config = config;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Asset setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getComputerRoom() {
        return computerRoom;
    }

    public Asset setComputerRoom(String computerRoom) {
        this.computerRoom = computerRoom;
        return this;
    }

    public String getCabinet() {
        return cabinet;
    }

    public Asset setCabinet(String cabinet) {
        this.cabinet = cabinet;
        return this;
    }

    public String getFrame() {
        return frame;
    }

    public Asset setFrame(String frame) {
        this.frame = frame;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Asset setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getBoqNo() {
        return boqNo;
    }

    public void setBoqNo(String boqNo) {
        this.boqNo = boqNo;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", creator=" + creator +
                ", contractNo=" + contractNo +
                ", time=" + time +
                ", vender=" + vender +
                ", deviceName=" + deviceName +
                ", amount=" + amount +
                ", modelNumber=" + modelNumber +
                ", config=" + config +
                ", location=" + location +
                ", computerRoom=" + computerRoom +
                ", cabinet=" + cabinet +
                ", frame=" + frame +
                ", remark=" + remark +
                "}";
    }
}
