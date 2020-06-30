package com.hirisun.cloud.model.ncov.vo.realtime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("疫情实时数据")
public class NcovRealtimeVo {

	@ApiModelProperty(value="id")
	private String id;

	@ApiModelProperty(value="区域名称")
    private String regionName; 

	@ApiModelProperty(value="确诊")
    private int diagnosis; 

	@ApiModelProperty(value="疑似")
    private int suspected; 

	@ApiModelProperty(value="死亡")
    private int death; 

	@ApiModelProperty(value="治愈")
    private int cure; 

	@ApiModelProperty(value="创建日期")
    private String createDate; 

	@ApiModelProperty(value="创建人")
    private String createUser; 

	@ApiModelProperty(value="省份号码 广东=44")
    private Integer provinceCode; 

	@ApiModelProperty(value="1=省，2=市")
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
