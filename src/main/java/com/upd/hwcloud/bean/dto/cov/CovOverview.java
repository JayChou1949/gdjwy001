package com.upd.hwcloud.bean.dto.cov;

/**
 * @author junglefisher
 * @date 2020/2/27 13:56
 */
public class CovOverview {

    /**
     * 省厅/地市总数
     */
    private Integer count;
    /**
     * 订购数
     */
    private Integer orderCount;
    /**
     * 调用总数
     */
    private Integer dyCount;
    /**
     * 昨日新增
     */
    private Integer ydCount;

    public Integer getYdCount() {
        return ydCount;
    }

    public void setYdCount(Integer ydCount) {
        this.ydCount = ydCount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
        return "Overview{" +
                "count=" + count +
                ", orderCount=" + orderCount +
                ", dyCount=" + dyCount +
                '}';
    }
}
