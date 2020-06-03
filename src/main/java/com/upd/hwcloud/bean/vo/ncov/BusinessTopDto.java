package com.upd.hwcloud.bean.vo.ncov;

/**
 * @author wuc
 * @date 2020/2/27
 */
public class BusinessTopDto {

    private String name;

    private Double vcpuUsage;

    private Double memeoryUsage;

    private Double evsDiskUsage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getVcpuUsage() {
        return vcpuUsage;
    }

    public void setVcpuUsage(Double vcpuUsage) {
        this.vcpuUsage = vcpuUsage;
    }

    public Double getMemeoryUsage() {
        return memeoryUsage;
    }

    public void setMemeoryUsage(Double memeoryUsage) {
        this.memeoryUsage = memeoryUsage;
    }

    public Double getEvsDiskUsage() {
        return evsDiskUsage;
    }

    public void setEvsDiskUsage(Double evsDiskUsage) {
        this.evsDiskUsage = evsDiskUsage;
    }

    @Override
    public String toString() {
        return "BusinessTopDto{" +
                "name='" + name + '\'' +
                ", vcpuUsage=" + vcpuUsage +
                ", memeoryUsage=" + memeoryUsage +
                ", evsDiskUsage=" + evsDiskUsage +
                '}';
    }
}
