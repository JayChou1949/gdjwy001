package com.hirisun.cloud.ncov.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("T_NCOV_REALTIME")
public class NcovRealtime implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6455619186632153210L;

	@TableId(value = "ID", type = IdType.UUID)
	private String id;

	@TableField("REGION_NAME")
    private String regionName; //区域名称

	@TableField("DIAGNOSIS")
    private Integer diagnosis; //确诊

	@TableField("SUSPECTED")
    private Integer suspected; //疑似

	@TableField("DEATH")
    private Integer death; //死亡

	@TableField("CURE")
    private Integer cure; //治愈

	@TableField("CREATE_DATE")
    private String createDate; //创建日期

	@TableField("CREATE_USER")
    private String createUser; //创建人

	@TableField("PROVINCE_CODE")
    private Integer provinceCode; //省份号码 广东=44

	@TableField("REGION_TYPE")
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
