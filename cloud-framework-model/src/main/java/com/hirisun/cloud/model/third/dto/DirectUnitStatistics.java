package com.hirisun.cloud.model.third.dto;

/**
 * @author junglefisher
 * @date 2020/2/28 16:31
 */
public class DirectUnitStatistics {
    /**
     * 序号
     */
    Integer num;
    /**
     * 地区
     */
    String area;
    /**
     * 部门名称
     */
    String unitName;
    /**
     * 信息类名称
     */
    String messageName;
    /**
     * 数据服务名称
     */
    String serviceName;
    /**
     * 调用日期
     */
    String time;

    /**
     * 调用次数
     */
    Integer count;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DirectUnitStatistics{" +
                "num=" + num +
                ", area='" + area + '\'' +
                ", unitName='" + unitName + '\'' +
                ", messageName='" + messageName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", time=" + time +
                ", count='" + count + '\'' +
                '}';
    }
}
