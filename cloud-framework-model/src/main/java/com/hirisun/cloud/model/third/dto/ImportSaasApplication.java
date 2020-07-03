package com.hirisun.cloud.model.third.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

public class ImportSaasApplication {

    @Excel(name = "序号")
    private String id;

    @Excel(name = "姓名")
    private String name;

    @Excel(name = "身份证号码")
    private String idcard;

    @Excel(name = "工作单位")
    private String workOrgName;

    @Excel(name = "职务")
    private String postType;

    @Excel(name = "手机号码")
    private String phone;

    @Excel(name = "终端IP地址")
    private String ip;

    @Excel(name = "实际所在单位")
    private String realOrgName;

    @Excel(name = "二级管理单位（地市/警种）")
    private String policeCategory;

    @Excel(name = "二级单位提交大数据办审核时间", importFormat = "yyyy/M/dd")
    private Date submitTime;

    @Excel(name = "大数据办完成审批时间", importFormat = "yyyy/M/dd")
    private Date bigDataTime;

    @Excel(name = "账号开通时间", importFormat = "yyyy/M/dd")
    private Date carryTime;

    @Excel(name = "二级管理单位是否申请回收权限")
    private String lvl2Recycle;

    @Excel(name = "大数据办是否回收权限")
    private String bigDataRecycle;

    @Excel(name = "权限回收时间")
    private String recycleTime;

    @Excel(name = "广东公安全息画像")
    private String serviceQxhx;

    @Excel(name = "广东公安对象管控")
    private String serviceDxgk;

    @Excel(name = "广东公安可视化建模平台")
    private String serviceKshjm;

    @Excel(name = "广东公安模型超市")
    private String serviceMxcs;

    @Excel(name = "广东公安智慧新搜索平台")
    private String serviceZhxss;

    @Excel(name = "广东公安知识图谱系统")
    private String serviceZstp;

    @Excel(name = "广东公安全网追逃")
    private String serviceQwzt;

    @Excel(name = "广东公安批量落地")
    private String servicePlld;

    @Excel(name = "广东公安轨迹分析")
    private String serviceGjfx;

    @Excel(name = "广东公安姓名分析")
    private String serviceXmfx;

    @Excel(name = "广东公安网脉分析")
    private String serviceWmfx;

    @Excel(name = "广东公安IP落地")
    private String serviceIpld;

    @Excel(name = "广东公安数据积木")
    private String serviceSjjm;

    @Excel(name = "广东公安人车伴随")
    private String serviceRcbs;

    @Excel(name = "广东公安警情分布")
    private String serviceJqfb;

    @Excel(name = "技网建模平台")
    private String serviceJmpt;

    @Excel(name = "账号权限是否激活")
    private String isActive;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getWorkOrgName() {
        return workOrgName;
    }

    public void setWorkOrgName(String workOrgName) {
        this.workOrgName = workOrgName;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRealOrgName() {
        return realOrgName;
    }

    public void setRealOrgName(String realOrgName) {
        this.realOrgName = realOrgName;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getBigDataTime() {
        return bigDataTime;
    }

    public void setBigDataTime(Date bigDataTime) {
        this.bigDataTime = bigDataTime;
    }

    public Date getCarryTime() {
        return carryTime;
    }

    public void setCarryTime(Date carryTime) {
        this.carryTime = carryTime;
    }

    public String getLvl2Recycle() {
        return lvl2Recycle;
    }

    public void setLvl2Recycle(String lvl2Recycle) {
        this.lvl2Recycle = lvl2Recycle;
    }

    public String getBigDataRecycle() {
        return bigDataRecycle;
    }

    public void setBigDataRecycle(String bigDataRecycle) {
        this.bigDataRecycle = bigDataRecycle;
    }

    public String getRecycleTime() {
        return recycleTime;
    }

    public void setRecycleTime(String recycleTime) {
        this.recycleTime = recycleTime;
    }

    public String getServiceQxhx() {
        return serviceQxhx;
    }

    public void setServiceQxhx(String serviceQxhx) {
        this.serviceQxhx = serviceQxhx;
    }

    public String getServiceDxgk() {
        return serviceDxgk;
    }

    public void setServiceDxgk(String serviceDxgk) {
        this.serviceDxgk = serviceDxgk;
    }

    public String getServiceKshjm() {
        return serviceKshjm;
    }

    public void setServiceKshjm(String serviceKshjm) {
        this.serviceKshjm = serviceKshjm;
    }

    public String getServiceMxcs() {
        return serviceMxcs;
    }

    public void setServiceMxcs(String serviceMxcs) {
        this.serviceMxcs = serviceMxcs;
    }

    public String getServiceZhxss() {
        return serviceZhxss;
    }

    public void setServiceZhxss(String serviceZhxss) {
        this.serviceZhxss = serviceZhxss;
    }

    public String getServiceZstp() {
        return serviceZstp;
    }

    public void setServiceZstp(String serviceZstp) {
        this.serviceZstp = serviceZstp;
    }

    public String getServiceQwzt() {
        return serviceQwzt;
    }

    public void setServiceQwzt(String serviceQwzt) {
        this.serviceQwzt = serviceQwzt;
    }

    public String getServicePlld() {
        return servicePlld;
    }

    public void setServicePlld(String servicePlld) {
        this.servicePlld = servicePlld;
    }

    public String getServiceGjfx() {
        return serviceGjfx;
    }

    public void setServiceGjfx(String serviceGjfx) {
        this.serviceGjfx = serviceGjfx;
    }

    public String getServiceXmfx() {
        return serviceXmfx;
    }

    public void setServiceXmfx(String serviceXmfx) {
        this.serviceXmfx = serviceXmfx;
    }

    public String getServiceWmfx() {
        return serviceWmfx;
    }

    public void setServiceWmfx(String serviceWmfx) {
        this.serviceWmfx = serviceWmfx;
    }

    public String getServiceIpld() {
        return serviceIpld;
    }

    public void setServiceIpld(String serviceIpld) {
        this.serviceIpld = serviceIpld;
    }

    public String getServiceSjjm() {
        return serviceSjjm;
    }

    public void setServiceSjjm(String serviceSjjm) {
        this.serviceSjjm = serviceSjjm;
    }

    public String getServiceRcbs() {
        return serviceRcbs;
    }

    public void setServiceRcbs(String serviceRcbs) {
        this.serviceRcbs = serviceRcbs;
    }

    public String getServiceJqfb() {
        return serviceJqfb;
    }

    public void setServiceJqfb(String serviceJqfb) {
        this.serviceJqfb = serviceJqfb;
    }

    public String getServiceJmpt() {
        return serviceJmpt;
    }

    public void setServiceJmpt(String serviceJmpt) {
        this.serviceJmpt = serviceJmpt;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

}
