package com.hirisun.cloud.model.ncov.vo;

import java.util.Date;

public class NcovRealtimeVo {

	private String id;

    private String regionName;

    private Integer diagnosis;

    private Integer suspected;

    private Integer death;

    private Integer cure;

    private Date createDate;

    private String createUser;

    private Integer provinceCode;

    private Integer regionType;

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

    public Integer getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Integer diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Integer getSuspected() {
        return suspected;
    }

    public void setSuspected(Integer suspected) {
        this.suspected = suspected;
    }

    public Integer getDeath() {
        return death;
    }

    public void setDeath(Integer death) {
        this.death = death;
    }

    public Integer getCure() {
        return cure;
    }

    public void setCure(Integer cure) {
        this.cure = cure;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
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
