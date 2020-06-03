package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.common.utils.annotation.ExcelExplain;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * <p>
 * 弹性云服务实施信息表
 * </p>
 *
 * @author wuc
 * @since 2018-12-04
 */
@TableName("TB_IAAS_TXYFW_IMPL")
public class IaasTxyfwImpl extends Model<IaasTxyfwImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 应用名称
     */
         @TableField("APP_NAME")
    private String appName;

        /**
     * 应用组件
     */
        @Excel(name = "应用组件" )
        @ExcelExplain(head = "应用组件")
         @TableField("COMPONENT")
    private String component;
    /**
     * 应用类型
     *
     */
    @Excel(name = "应用类型" )
    @ExcelExplain(head = "应用类型")
    @TableField("APP_TYPE")
    private String appType;
        /**
     * 规格
     */
        @Excel(name = "规格名称" )
        @ExcelExplain(head = "规格名称")
         @TableField("SPECIFICATION")
    private String specification;

        /**
     * 存储
     */
        @Excel(name = "数据盘存储(GB)" )
        @ExcelExplain(head = "数据盘存储(GB)")
        @TableField("STORAGE")
    private String storage;

        /**
     * 操作系统
     */
        @Excel(name = "系统" )
        @ExcelExplain(head = "系统")
         @TableField("OS")
    private String os;

        /**
     * 网络
     */
        @Excel(name = "网络" )
        @ExcelExplain(head = "网络")
         @TableField("NET")
    private String net;

        /**
     * 服务器 ID
     */
        @Excel(name = "实例ID" )
        @ExcelExplain(head = "实例ID")
         @TableField("SERVER_ID")
    private String serverId;

        /**
     * 虚拟 IP
     */
         @TableField("SERVER_IP")
    private String serverIp;
    /**
     * 公安网访问 IP
     */
    @Excel(name = "公安网访问IP" )
   @TableField("ACCESS_IP")
   @ExcelExplain(head = "公安网访问IP")
    private String accessIp;
        /**
     * 开放端口
     */
        @Excel(name = "开放端口" )
        @ExcelExplain(head = "开放端口")
         @TableField("SERVER_PORT")
    private String serverPort;

        /**
     * 用户名
     */
        @Excel(name = "用户名" )
        @ExcelExplain(head = "用户名")
         @TableField("USER_NAME")
    private String userName;

        /**
     * 密码
     */
        @Excel(name = "初始密码" )
        @ExcelExplain(head = "初始密码")
         @TableField("PASSWORD")
    private String password;

        /**
     * 申请服务信息id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;
    /**
     * IP
     */
    @Excel(name = "IP")
    @ExcelExplain(head = "IP")
    @TableField("SERVER_IP1")
    private String serverIp1;
    /**
     * 是否发放
     */
    @Excel(name = "是否发放")
    @ExcelExplain(head = "是否发放")
    @TableField("IS_FF")
    private String isFf;

    /**
     * GPU数目
     */
    @Excel(name = "GPU数量",type = 10)
    @ExcelExplain(head = "GPU数量")
    @TableField("GPU_NUM")
    private Integer gpuNum;

    @TableField(exist = false)
    private String createName;

    @TableField(exist = false)
    private String orderNumber;

    /**
     * CPU数
     */
    @TableField(exist = false)
    private  String cpuNum;

    /**
     * 内存数
     */
    @TableField(exist = false)
    private String memoryNum;

    public String getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    public String getMemoryNum() {
        return memoryNum;
    }

    public void setMemoryNum(String memoryNum) {
        this.memoryNum = memoryNum;
    }

    public Integer getGpuNum() {
        return gpuNum;
    }

    public void setGpuNum(Integer gpuNum) {
        this.gpuNum = gpuNum;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getIsFf() {
        return isFf;
    }

    public void setIsFf(String isFf) {
        this.isFf = isFf;
    }

    public String getId() {
        return id;
    }

    public IaasTxyfwImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public IaasTxyfwImpl setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }

    public IaasTxyfwImpl setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getSpecification() {
        return specification;
    }

    public IaasTxyfwImpl setSpecification(String specification) {
        this.specification = specification;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public IaasTxyfwImpl setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getOs() {
        return os;
    }

    public IaasTxyfwImpl setOs(String os) {
        this.os = os;
        return this;
    }

    public String getNet() {
        return net;
    }

    public IaasTxyfwImpl setNet(String net) {
        this.net = net;
        return this;
    }

    public String getServerId() {
        return serverId;
    }

    public IaasTxyfwImpl setServerId(String serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getServerIp() {
        return serverIp;
    }

    public IaasTxyfwImpl setServerIp(String serverIp) {
        this.serverIp = serverIp;
        return this;
    }

    public String getServerPort() {
        return serverPort;
    }

    public IaasTxyfwImpl setServerPort(String serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public IaasTxyfwImpl setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IaasTxyfwImpl setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public void setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasTxyfwImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasTxyfwImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getServerIp1() {
        return serverIp1;
    }

    public void setServerIp1(String serverIp1) {
        this.serverIp1 = serverIp1;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasTxyfwImpl{" +
                "id='" + id + '\'' +
                ", appName='" + appName + '\'' +
                ", component='" + component + '\'' +
                ", appType='" + appType + '\'' +
                ", specification='" + specification + '\'' +
                ", storage='" + storage + '\'' +
                ", os='" + os + '\'' +
                ", net='" + net + '\'' +
                ", serverId='" + serverId + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", accessIp='" + accessIp + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", appInfoId='" + appInfoId + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", serverIp1='" + serverIp1 + '\'' +
                '}';
    }


}
