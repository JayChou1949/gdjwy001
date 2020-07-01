package com.hirisun.cloud.model.third.dto;


/**
 * @author junglefisher
 * @date 2020/2/28 15:25
 */
public class DirectUnitOrderDetail {
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
     * 调用总次数
     */
    Integer countAll;

    /**
     * 备注
     */
    String remark;

    public DirectUnitOrderDetail() {
    }

    public DirectUnitOrderDetail(Integer num, String area, String unitName, String messageName, String serviceName, Integer countAll, String remark) {
        this.num = num;
        this.area = area;
        this.unitName = unitName;
        this.messageName = messageName;
        this.serviceName = serviceName;
        this.countAll = countAll;
        this.remark = remark;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCountAll() {
        return countAll;
    }

    public void setCountAll(Integer countAll) {
        this.countAll = countAll;
    }

    @Override
    public String toString() {
        return "DirectUnit{" +
                "num=" + num +
                ", area='" + area + '\'' +
                ", unitName='" + unitName + '\'' +
                ", messageName='" + messageName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", countAll=" + countAll +
                ", remark='" + remark + '\'' +
                '}';
    }
}
