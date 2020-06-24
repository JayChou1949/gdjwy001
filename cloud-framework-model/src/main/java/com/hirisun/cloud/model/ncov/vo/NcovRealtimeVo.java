package com.hirisun.cloud.model.ncov.vo;

public class NcovRealtimeVo {

	private String id;

    private String regionName; //区域名称

    private int diagnosis; //确诊

    private int suspected; //疑似

    private int death; //死亡

    private int cure; //治愈

    private String createDate; //创建日期

    private String createUser; //创建人

    private Integer provinceCode; //省份号码 广东=44

    private Integer regionType; //1=省，2=市

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(int diagnosis) {
        this.diagnosis = diagnosis;
    }

    public int getSuspected() {
        return suspected;
    }

    public void setSuspected(int suspected) {
        this.suspected = suspected;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getCure() {
        return cure;
    }

    public void setCure(int cure) {
        this.cure = cure;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Integer getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Integer provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Integer getRegionType() {
        return regionType;
    }

    public void setRegionType(Integer regionType) {
        this.regionType = regionType;
    }

}
