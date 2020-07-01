package com.hirisun.cloud.model.third.dto;


public class OverviewDTO {

    /**
     * ci名称
     */
    private String name;


    /**
     * 物理主机数量
     */
    private Double physicalhostNumber;

    /**
     * 存储池数量
     */
    private Double storagepoolNumber;

    /**
     * 虚拟机数量
     */
    private Double vmNumber;


    /**
     * 内存总量
     */
    private Double memoryTotal;


    /**
     * 存储总量
     */
    private Double storageTotal;




    private Integer appNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public Double getPhysicalhostNumber() {
        return physicalhostNumber;
    }

    public void setPhysicalhostNumber(Double physicalhostNumber) {
        this.physicalhostNumber = physicalhostNumber;
    }

    public Double getStoragepoolNumber() {
        return storagepoolNumber;
    }

    public void setStoragepoolNumber(Double storagepoolNumber) {
        this.storagepoolNumber = storagepoolNumber;
    }

    public Double getVmNumber() {
        return vmNumber;
    }

    public void setVmNumber(Double vmNumber) {
        this.vmNumber = vmNumber;
    }



    public Double getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(Double memoryTotal) {
        this.memoryTotal = memoryTotal;
    }


    public Double getStorageTotal() {
        return storageTotal;
    }

    public void setStorageTotal(Double storageTotal) {
        this.storageTotal = storageTotal;
    }



    public Integer getAppNum() {
        return appNum;
    }

    public void setAppNum(Integer appNum) {
        this.appNum = appNum;
    }
}
