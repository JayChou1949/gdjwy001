package com.upd.hwcloud.bean.dto.cov;

/**
 * @author junglefisher
 * @date 2020/3/2 18:47
 */
public class CloudDeskNumByAreaOrPolice {

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
