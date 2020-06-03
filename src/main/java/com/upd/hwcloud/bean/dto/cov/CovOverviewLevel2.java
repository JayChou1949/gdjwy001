package com.upd.hwcloud.bean.dto.cov;

/**
 * @author junglefisher
 * @date 2020/2/27 14:10
 */
public class CovOverviewLevel2 {

    /**
     * 省厅/地市名称
     */
    private String name;
    /**
     * 订购数
     */
    private Integer orderCount;
    /**
     * 调用总数
     */
    private Integer dyCount;

    /**
     * 昨日新增调用次数
     *
     * @return
     */
    private Integer ydCount;

    public Integer getYdCount() {
        return ydCount;
    }

    public void setYdCount(Integer ydCount) {
        this.ydCount = ydCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getDyCount() {
        return dyCount;
    }

    public void setDyCount(Integer dyCount) {
        this.dyCount = dyCount;
    }

    @Override
    public String toString() {
        return "CovOverviewLevel2{" +
                "name='" + name + '\'' +
                ", orderCount=" + orderCount +
                ", dyCount=" + dyCount +
                '}';
    }
}
