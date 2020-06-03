package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

@Data
public class EpidemicDesktop {
    private String unit;
    private String yunCount;
    private String cpuCount;
    private String ramCount;
    private String distCount;
    private String area;
    private String police;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getYunCount() {
        return yunCount;
    }

    public void setYunCount(String yunCount) {
        this.yunCount = yunCount;
    }

    public String getCpuCount() {
        return cpuCount;
    }

    public void setCpuCount(String cpuCount) {
        this.cpuCount = cpuCount;
    }

    public String getRamCount() {
        return ramCount;
    }

    public void setRamCount(String ramCount) {
        this.ramCount = ramCount;
    }

    public String getDistCount() {
        return distCount;
    }

    public void setDistCount(String distCount) {
        this.distCount = distCount;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }
}
