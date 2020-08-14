package com.hirisun.cloud.user.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
@TableName("T_USER")
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "用户类型：10 民警  20 辅警    30外部人员")
    @TableField("USER_TYPE")
    private String userType;

    @ApiModelProperty(value = "姓名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "身份证")
    @TableField("ID_CARD")
    private String idCard;

    @ApiModelProperty(value = "民族,引用表码3.4")
    @TableField("NATION")
    private String nation;

    @ApiModelProperty(value = "性别,引用表码3.5")
    @TableField("SEX")
    private String sex;

    @ApiModelProperty(value = "出生日期")
    @TableField("BIRTH")
    private Date birth;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "照片id")
    @TableField("PHOTO")
    private String photo;

    @ApiModelProperty(value = "机构id")
    @TableField("ORG_ID")
    private String orgId;

    @ApiModelProperty(value = "机构名称")
    @TableField("ORG_NAME")
    private String orgName;

    @ApiModelProperty(value = "联系地址")
    @TableField("ADDRESS")
    private String address;

    @ApiModelProperty(value = "排序号")
    @TableField("SORT_NO")
    private Long sortNo;

    @ApiModelProperty(value = "人员身份类型，引用表码3.6")
    @TableField("MAN_TYPE")
    private String manType;

    @ApiModelProperty(value = "警衔,引用表码3.7")
    @TableField("RANK")
    private String rank;

    @ApiModelProperty(value = "职级,引用表码3.8")
    @TableField("TITLE")
    private String title;

    @ApiModelProperty(value = "责任民警ID")
    @TableField("POLICE_ID")
    private String policeId;

    @ApiModelProperty(value = "责任民警姓名")
    @TableField("POLICE_NAME")
    private String policeName;

    @ApiModelProperty(value = "所属公司")
    @TableField("COMPANY")
    private String company;

    @ApiModelProperty(value = "所属公司负责人")
    @TableField("COMPANY_PERSON")
    private String companyPerson;

    @ApiModelProperty(value = "所属公司负责人电话")
    @TableField("PERSON_MOBILE")
    private String personMobile;

    @ApiModelProperty(value = "施工项目")
    @TableField("PROJECT")
    private String project;

    @ApiModelProperty(value = "0：删除；1：有效")
    @TableField("DELETED")
    private String deleted;

    @ApiModelProperty(value = "警种，引用表码3.9")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @ApiModelProperty(value = "警号")
    @TableField("POLICE_NUMBER")
    private String policeNumber;

    @TableField("MOBILE_PRIVTE")
    private String mobilePrivte;

    @ApiModelProperty(value = "手机号码")
    @TableField("MOBILE_WORK")
    private String mobileWork;

    @ApiModelProperty(value = "座机")
    @TableField("PHONE")
    private String phone;

    @ApiModelProperty(value = "QQ号")
    @TableField("QQ_ACCOUNT")
    private String qqAccount;

    @ApiModelProperty(value = "邮箱")
    @TableField("EMAIL")
    private String email;

    @ApiModelProperty(value = "微信号")
    @TableField("WX_ACCOUNT")
    private String wxAccount;

    @TableField("BIRTH_STR")
    private String birthStr;

    @TableField("CREATE_TIME_STR")
    private String createTimeStr;

    @ApiModelProperty(value = "用户类型  0：普通用户 1: 服务厂商  100：管理用户 20：租户管理员 30：省厅管理员")
    @TableField("TYPE")
    private Long type;

    @ApiModelProperty(value = "通知类型  0：短信 1:邮箱 2:微信")
    @TableField("NOTIFY_TYPE")
    private String notifyType;

    @ApiModelProperty(value = "辅警类型")
    @TableField("AUXILIARY_TYPE")
    private String auxiliaryType;

    @ApiModelProperty(value = "在职类型")
    @TableField("JOB_TYPE")
    private String jobType;

    @ApiModelProperty(value = "职务")
    @TableField("POST_TYPE")
    private String postType;

    @ApiModelProperty(value = "是否主机构 1：是、 0 ：否(警员专用")
    @TableField("IS_PARENT_ORG")
    private String isParentOrg;

    @ApiModelProperty(value = "租户地区")
    @TableField("TENANT_AREA")
    private String tenantArea;

    @ApiModelProperty(value = "租户警种")
    @TableField("TENANT_POLICE_CATEGORY")
    private String tenantPoliceCategory;

    @ApiModelProperty(value = "是否第一租户管理员 1-是")
    @TableField("DEFAULT_TENANT")
    private String defaultTenant;

    @ApiModelProperty(value = "所属地区")
    @TableField("BELONG_AREA")
    private String belongArea;

    @ApiModelProperty(value = "所属警种")
    @TableField("BELONG_POLICE_TYPE")
    private String belongPoliceType;

    @ApiModelProperty(value = "政务人员编号")
    @TableField("USER_NUMBER")
    private String userNumber;

    @ApiModelProperty(value = "政务机构code")
    @TableField("GOV_CODE")
    private String govCode;

    @ApiModelProperty(value = "删除时间")
    @TableField("DELETE_TIME")
    private String deleteTime;

    @ApiModelProperty(value = "政务人员信息字段：职务")
    @TableField("POST")
    private String post;

    @ApiModelProperty(value = "政务人员信息字段：政务机构id")
    @TableField("GOV_ID")
    private String govId;

    @ApiModelProperty(value = "政务人员信息字段：政务机构名称")
    @TableField("GOV_NAME")
    private String govName;

    @ApiModelProperty(value = "登录人员类型 01警务 02政务")
    @TableField("PERSON_NELTYPE")
    private String personNeltype;

    @ApiModelProperty(value = "租户所属国家专项")
    @TableField("NATIONAL_PROJECT")
    private String nationalProject;

    @ApiModelProperty(value = "所属公司")
    @TableField(exist = false)
    private String companyName;

    @ApiModelProperty(value = "警种")
    @TableField(exist = false)
    private String policeCategoryName;

    @ApiModelProperty(value = "角色名")
    @TableField(exist = false)
    private String roleName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getSortNo() {
        return sortNo;
    }

    public void setSortNo(Long sortNo) {
        this.sortNo = sortNo;
    }

    public String getManType() {
        return manType;
    }

    public void setManType(String manType) {
        this.manType = manType;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoliceId() {
        return policeId;
    }

    public void setPoliceId(String policeId) {
        this.policeId = policeId;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyPerson() {
        return companyPerson;
    }

    public void setCompanyPerson(String companyPerson) {
        this.companyPerson = companyPerson;
    }

    public String getPersonMobile() {
        return personMobile;
    }

    public void setPersonMobile(String personMobile) {
        this.personMobile = personMobile;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public String getPoliceNumber() {
        return policeNumber;
    }

    public void setPoliceNumber(String policeNumber) {
        this.policeNumber = policeNumber;
    }

    public String getMobilePrivte() {
        return mobilePrivte;
    }

    public void setMobilePrivte(String mobilePrivte) {
        this.mobilePrivte = mobilePrivte;
    }

    public String getMobileWork() {
        return mobileWork;
    }

    public void setMobileWork(String mobileWork) {
        this.mobileWork = mobileWork;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQqAccount() {
        return qqAccount;
    }

    public void setQqAccount(String qqAccount) {
        this.qqAccount = qqAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWxAccount() {
        return wxAccount;
    }

    public void setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount;
    }

    public String getBirthStr() {
        return birthStr;
    }

    public void setBirthStr(String birthStr) {
        this.birthStr = birthStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getAuxiliaryType() {
        return auxiliaryType;
    }

    public void setAuxiliaryType(String auxiliaryType) {
        this.auxiliaryType = auxiliaryType;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getIsParentOrg() {
        return isParentOrg;
    }

    public void setIsParentOrg(String isParentOrg) {
        this.isParentOrg = isParentOrg;
    }

    public String getTenantArea() {
        return tenantArea;
    }

    public void setTenantArea(String tenantArea) {
        this.tenantArea = tenantArea;
    }

    public String getTenantPoliceCategory() {
        return tenantPoliceCategory;
    }

    public void setTenantPoliceCategory(String tenantPoliceCategory) {
        this.tenantPoliceCategory = tenantPoliceCategory;
    }

    public String getDefaultTenant() {
        return defaultTenant;
    }

    public void setDefaultTenant(String defaultTenant) {
        this.defaultTenant = defaultTenant;
    }

    public String getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(String belongArea) {
        this.belongArea = belongArea;
    }

    public String getBelongPoliceType() {
        return belongPoliceType;
    }

    public void setBelongPoliceType(String belongPoliceType) {
        this.belongPoliceType = belongPoliceType;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getGovCode() {
        return govCode;
    }

    public void setGovCode(String govCode) {
        this.govCode = govCode;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getGovId() {
        return govId;
    }

    public void setGovId(String govId) {
        this.govId = govId;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getPersonNeltype() {
        return personNeltype;
    }

    public void setPersonNeltype(String personNeltype) {
        this.personNeltype = personNeltype;
    }

    public String getNationalProject() {
        return nationalProject;
    }

    public void setNationalProject(String nationalProject) {
        this.nationalProject = nationalProject;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPoliceCategoryName() {
        return policeCategoryName;
    }

    public void setPoliceCategoryName(String policeCategoryName) {
        this.policeCategoryName = policeCategoryName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userType=" + userType +
                ", name=" + name +
                ", idCard=" + idCard +
                ", nation=" + nation +
                ", sex=" + sex +
                ", birth=" + birth +
                ", createTime=" + createTime +
                ", photo=" + photo +
                ", orgId=" + orgId +
                ", orgName=" + orgName +
                ", address=" + address +
                ", sortNo=" + sortNo +
                ", manType=" + manType +
                ", rank=" + rank +
                ", title=" + title +
                ", policeId=" + policeId +
                ", policeName=" + policeName +
                ", company=" + company +
                ", companyPerson=" + companyPerson +
                ", personMobile=" + personMobile +
                ", project=" + project +
                ", deleted=" + deleted +
                ", policeCategory=" + policeCategory +
                ", policeNumber=" + policeNumber +
                ", mobilePrivte=" + mobilePrivte +
                ", mobileWork=" + mobileWork +
                ", phone=" + phone +
                ", qqAccount=" + qqAccount +
                ", email=" + email +
                ", wxAccount=" + wxAccount +
                ", birthStr=" + birthStr +
                ", createTimeStr=" + createTimeStr +
                ", type=" + type +
                ", notifyType=" + notifyType +
                ", auxiliaryType=" + auxiliaryType +
                ", jobType=" + jobType +
                ", postType=" + postType +
                ", isParentOrg=" + isParentOrg +
                ", tenantArea=" + tenantArea +
                ", tenantPoliceCategory=" + tenantPoliceCategory +
                ", defaultTenant=" + defaultTenant +
                ", belongArea=" + belongArea +
                ", belongPoliceType=" + belongPoliceType +
                ", userNumber=" + userNumber +
                ", govCode=" + govCode +
                ", deleteTime=" + deleteTime +
                ", post=" + post +
                ", govId=" + govId +
                ", govName=" + govName +
                ", personNeltype=" + personNeltype +
                ", nationalProject=" + nationalProject +
                "}";
    }
}
