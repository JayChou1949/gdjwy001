package com.upd.hwcloud.bean.entity.IaasStatistic;

/**
 * @Description:
 * @author: yyc
 * @date: 2018-10-28 14:18
 **/
public class BigDataUint {
    private Long cpuTotal;
    private Long cpuUsed;
    private Float cpuRatio;
    private Long ramTotal;
    private Long ramUsed;
    private Long ramRatio;
    private Long diskTotal;
    private Long diskUsed;
    private Long diskRatio;

    public Long getCpuTotal() {
        return cpuTotal;
    }

    public void setCpuTotal(Long cpuTotal) {
        this.cpuTotal = cpuTotal;
    }

    public Long getCpuUsed() {
        return cpuUsed;
    }

    public void setCpuUsed(Long cpuUsed) {
        this.cpuUsed = cpuUsed;
    }

    public Float getCpuRatio() {
        return cpuRatio;
    }

    public void setCpuRatio(Float cpuRatio) {
        this.cpuRatio = cpuRatio;
    }

    public Long getRamTotal() {
        return ramTotal;
    }

    public void setRamTotal(Long ramTotal) {
        this.ramTotal = ramTotal;
    }

    public Long getRamUsed() {
        return ramUsed;
    }

    public void setRamUsed(Long ramUsed) {
        this.ramUsed = ramUsed;
    }

    public Long getRamRatio() {
        return ramRatio;
    }

    public void setRamRatio(Long ramRatio) {
        this.ramRatio = ramRatio;
    }

    public Long getDiskTotal() {
        return diskTotal;
    }

    public void setDiskTotal(Long diskTotal) {
        this.diskTotal = diskTotal;
    }

    public Long getDiskUsed() {
        return diskUsed;
    }

    public void setDiskUsed(Long diskUsed) {
        this.diskUsed = diskUsed;
    }

    public Long getDiskRatio() {
        return diskRatio;
    }

    public void setDiskRatio(Long diskRatio) {
        this.diskRatio = diskRatio;
    }

    @Override
    public String toString() {
        return "BigDataUint{" +
                "cpuTotal=" + cpuTotal +
                ", cpuUsed=" + cpuUsed +
                ", cpuRatio=" + cpuRatio +
                ", ramTotal=" + ramTotal +
                ", ramUsed=" + ramUsed +
                ", ramRatio=" + ramRatio +
                ", diskTotal=" + diskTotal +
                ", diskUsed=" + diskUsed +
                ", diskRatio=" + diskRatio +
                '}';
    }
}
