package com.upd.hwcloud.bean.dto.cov;

/**
 * @author junglefisher
 * @date 2020/3/2 18:56
 */
public class CloudDeskDetail {

    /**
     * 序号
     */
    private Integer num;
    /**
     * 归属单位
     */
    private String unit;
    /**
     * 桌面云总数
     */
    private Integer total;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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
        return "CloudDeskDetail{" +
                "num=" + num +
                ", unit='" + unit + '\'' +
                ", total=" + total +
                ", cpu=" + cpu +
                ", memory=" + memory +
                ", disk=" + disk +
                '}';
    }
}
