package com.hirisun.cloud.model.application;

/**
 * @author junglefisher
 * @date 2020/3/2 18:47
 */
public class CloudDeskNumByAreaOrPoliceVo {

    /**
     * 地市/警种
     */
    private String name;

    /**
     * 总数
     */
    private Integer count;

    public String getArea() {
        return name;
    }

    public void setArea(String area) {
        this.name = area;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CloudDeskByArea{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
