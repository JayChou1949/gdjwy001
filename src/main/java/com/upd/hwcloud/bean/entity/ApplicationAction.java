package com.upd.hwcloud.bean.entity;

/**
 * @Description: 实施信息
 * @author: yyc
 * @date: 2018-10-16 10:05
 **/
public class ApplicationAction {
    /** id */
    private String id;

    /** 应用名称 */
    private String appName;

    /** 应用组件 */
    private String appComponent;

    /** 服务器ID */
    private String serverId;

    /** IP地址 */
    private String ip;

    /** 开放端口 */
    private String port;

    /** 规格 */
    private String dev;

    /** 数据盘大小 */
    private String diskSize;

    /** 网络 */
    private String netWork;

    /** 系统 */
    private String op;

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String password;
    public ApplicationAction(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppComponent() {
        return appComponent;
    }

    public void setAppComponent(String appComponent) {
        this.appComponent = appComponent;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(String diskSize) {
        this.diskSize = diskSize;
    }

    public String getNetWork() {
        return netWork;
    }

    public void setNetWork(String netWork) {
        this.netWork = netWork;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ApplicationAction(String appComponent) {
        this.appComponent = appComponent;
    }

    @Override
    public String toString() {
        return "ApplicationAction{" +
                "id='" + id + '\'' +
                ", appName='" + appName + '\'' +
                ", appComponent='" + appComponent + '\'' +
                ", serverId='" + serverId + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", dev='" + dev + '\'' +
                ", diskSize='" + diskSize + '\'' +
                ", netWork='" + netWork + '\'' +
                ", op='" + op + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
