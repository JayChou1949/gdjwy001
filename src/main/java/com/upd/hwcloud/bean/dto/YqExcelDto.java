package com.upd.hwcloud.bean.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @author junglefisher
 * @date 2020/2/23 19:33
 */
public class YqExcelDto {
    @Excel(name = "省直/地市")
    String area;
    @Excel(name = "部门名称")
    String unitName;
    @Excel(name = "信息类名称")
    String messageName;
    @Excel(name = "数据服务名称")
    String serviceName;
    @Excel(name = "统计日期")
    String time;
    @Excel(name = "调用总次数")
    Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    @Override
    public String toString() {
        return "YqExcelDto{" +
                "area='" + area + '\'' +
                ", unitName='" + unitName + '\'' +
                ", messageName='" + messageName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", time='" + time + '\'' +
                ", count=" + count +
                '}';
    }
}
