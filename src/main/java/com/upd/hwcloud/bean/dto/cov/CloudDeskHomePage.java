package com.upd.hwcloud.bean.dto.cov;

/**
 * @author junglefisher
 * @date 2020/3/2 18:03
 */
public class CloudDeskHomePage {
    /**
     * 桌面云总数
     */
    private Integer total;
    /**
     * 支撑地市数
     */
    private Integer areaNum;
    /**
     *  支撑警种数
     */
    private Integer policeNum;
    /**
     *  CPU
     */
    private Integer cpu;
    /**
     *  内存
     */
    private Integer memory;
    /**
     *  存储
     */
    private Double disk;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(Integer areaNum) {
        this.areaNum = areaNum;
    }

    public Integer getPoliceNum() {
        return policeNum;
    }

    public void setPoliceNum(Integer policeNum) {
        this.policeNum = policeNum;
    }

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Double getDisk() {
        return disk;
    }

    public void setDisk(Double disk) {
        this.disk = disk;
    }

    @Override
    public String toString() {
        return "CloudDeskHomePage{" +
                "total=" + total +
                ", areaNum=" + areaNum +
                ", policeNum=" + policeNum +
                ", cpu=" + cpu +
                ", memory=" + memory +
                ", disk=" + disk +
                '}';
    }
}
