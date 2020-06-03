package com.upd.hwcloud.bean.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @author wuc
 * @date 2019/7/25
 */
public class InstanceExport {

    @Excel(name = "应用名")
    private String alias;

    @Excel(name = "API网关应用名")
    private String appName;

    @Excel(name = "实例名")
    private String name;

    @Excel(name = "服务名")
    private String serviceAlias;

    @Excel(name = "订购时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Excel(name = "警种")
    private String policeCategory;

    @Excel(name = "地市")
    private String areaName;

    @Excel(name = "服务厂商")
    private String provider;

    public String getAlias() {
        if (StringUtils.isNotEmpty(alias)) {
            alias = alias.substring(0, alias.contains("（") ? alias.indexOf("（") : alias.length());
            return alias;
        }
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceAlias() {
        return serviceAlias;
    }

    public void setServiceAlias(String serviceAlias) {
        this.serviceAlias = serviceAlias;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public String getAreaName() {
        if ("省厅".equals(areaName)) {
            return null;
        }
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

}
