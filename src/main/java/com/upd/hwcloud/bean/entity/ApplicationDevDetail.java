package com.upd.hwcloud.bean.entity;

/**
 * @Description: 申请设备详情
 * @author: yyc
 * @date: 2018-10-13 14:50
 **/
public class ApplicationDevDetail {
    /**CPU核数/内存*/
    private String dev;
    /**系统*/
    private String op;
    /**磁盘大小*/
    private Integer diskSize;
    /**部署应用*/
    private String deployApp;
    /**应用组件*/
    private String appComponent;
    /**组件描述*/
    private String componentInfo;
    /**虚拟机需求数*/
    private String vmNumber;
    /**网络*/
    private String network;


    public String getDeployApp() {
        return deployApp;
    }

    public void setDeployApp(String deployApp) {
        this.deployApp = deployApp;
    }

    public String getAppComponent() {
        return appComponent;
    }

    public void setAppComponent(String appComponent) {
        this.appComponent = appComponent;
    }

    public String getComponentInfo() {
        return componentInfo;
    }

    public void setComponentInfo(String componentInfo) {
        this.componentInfo = componentInfo;
    }

    public String getVmNumber() {
        return vmNumber;
    }

    public void setVmNumber(String vmNumber) {
        this.vmNumber = vmNumber;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Integer getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(Integer diskSize) {
        this.diskSize = diskSize;
    }

    @Override
    public String toString() {
        return "ApplicationDevDetail{" +
                "dev='" + dev + '\'' +
                ", op='" + op + '\'' +
                ", diskSize=" + diskSize +
                ", deployApp='" + deployApp + '\'' +
                ", appComponent='" + appComponent + '\'' +
                ", componentInfo='" + componentInfo + '\'' +
                ", vmNumber='" + vmNumber + '\'' +
                ", network='" + network + '\'' +
                '}';
    }
}
