package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wuc
 * @since 2018-10-12
 */
@TableName("TB_USER")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "ID", type = IdType.INPUT)
    private String id;

    /**
     * 用户类型：10 民警  20 辅警    30施工人员
     * {@link com.upd.hwcloud.bean.contains.UnifledUserType}
     */
    @TableField("USER_TYPE")
    private String userType;

    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;

    /**
     * 身份证
     */
    @TableField("IDCARD")
    private String idcard;

    /**
     * 民族,引用表码3.4
     */
    @TableField("NATION")
    private String nation;

    /**
     * 性别,引用表码3.5
     */
    @TableField("SEX")
    private String sex;

    /**
     * 照片id
     */
    @TableField("PHOTO")
    private String photo;

    /**
     * 机构id
     */
    @TableField("ORG_ID")
    private String orgId;

    /**
     * 机构名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 联系地址
     */
    @TableField("ADDRESS")
    private String address;

    /**
     * 排序号
     */
    @TableField("SORT_NO")
    private Long sortNo;

    /**
     * 人员身份类型，引用表码3.6
     */
    @TableField("MAN_TYPE")
    private String manType;

    /**
     * 警衔,引用表码3.7
     */
    @TableField("RANK")
    private String rank;

    /**
     * 职级,引用表码3.8
     */
    @TableField("TITLE")
    private String title;

    /**
     * 责任民警ID
     */
    @TableField("POLICE_ID")
    private String policeId;

    /**
     * 责任民警姓名
     */
    @TableField("POLICE_NAME")
    private String policeName;

    /**
     * 所属公司
     */
    @TableField("COMPANY")
    private String company;

    /**
     * 所属公司负责人
     */
    @TableField("COMPANY_PERSON")
    private String companyPerson;

    /**
     * 所属公司负责人电话
     */
    @TableField("PERSON_MOBILE")
    private String personMobile;

    /**
     * 施工项目
     */
    @TableField("PROJECT")
    private String project;

    /**
     * 0：删除；1：有效
     */
    @TableField("DELETED")
    private String deleted;

    /**
     * 警种，引用表码3.9
     */
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    /**
     * 警号
     */
    @TableField("POLICE_NUMBER")
    private String policeNumber;

    @TableField("MOBILE_WORK")
    private String mobileWork;

    @TableField("PHONE")
    private String phone;

    @TableField("QQ_ACCOUNT")
    private String qqAccount;

    @TableField("EMAIL")
    private String email;

    @TableField("WX_ACCOUNT")
    private String wxAccount;

    @TableField("BIRTH_STR")
    private String birthStr;

    @TableField("CREATE_TIME_STR")
    private String createTimeStr;

    /**
     * 辅警类型 （10：合同工、20：聘用、30：其它）
     */
    @TableField("AUXILIARY_TYPE")
    private String auxiliaryType;

    /**
     * 在职类型（11：在职  12：兼职  13：挂职  14：返聘）
     */
    @TableField("JOB_TYPE")
    private String jobType;

    /**
     * 职务
     */
    @TableField("POST_TYPE")
    private String postType;

    /**
     * 判断返回的 orgName和 orgId是否是该用户的主机构， 1：是、 0 ：否(警员专用)
     */
    @TableField("IS_PARENT_ORG")
    private String isParentOrg;

    /**
     * 用户类型  0：普通用户 1: 服务厂商 20:租户管理员  100：管理用户
     * {@link com.upd.hwcloud.bean.contains.UserType}
     */
    @TableField("TYPE")
    private Long type;

    /**
     * 通知类型  0：短信 1:邮箱 2:微信
     * {@link com.upd.hwcloud.bean.contains.NotifyType}
     */
    @TableField("NOTIFY_TYPE")
    private String notifyType;

    /**
     * 租户地区
     */
    @TableField("TENANT_AREA")
    private String tenantArea;

    /**
     * 租户警种
     */
    @TableField("TENANT_POLICE_CATEGORY")
    private String tenantPoliceCategory;

    @TableField("NATIONAL_PROJECT")
    private String nationalProject;

    /**
     * 是否第一租户管理员 1-是
     */
    @TableField("DEFAULT_TENANT")
    private String defaultTenant;

    @TableField(exist = false)
    private String policeCategoryName;

    @TableField(exist = false)
    private String roleName;

    /**
     * 所属公司名称
     */
    @TableField(exist = false)
    private String companyName;

    /**
     * 所属应用名称
     * @return
     */
    @TableField(exist = false)
    private String belongName;
    /**
     * 总数
     * @return
     */
    @TableField(exist = false)
    private String total;
    /**
     * 所属地区
     * @return
     */
    @TableField(exist = false)
    private String belongArea;
    /**
     * 所属警种
     * @return
     */
    @TableField(exist = false)
    private String belongPoliceType;

    /**
     *  政务人员编号
     */
    @TableField("USER_NUMBER")
    private String userNumber;

    /**
     *  政务机构code
     */
    @TableField("GOV_CODE")
    private String govCode;

    /**
     *  删除时间
     */
    @TableField("DELETE_TIME")
    private String deleteTime;

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

    public String getNationalProject() {
        return nationalProject;
    }

    public void setNationalProject(String nationalProject) {
        this.nationalProject = nationalProject;
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public User setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getIdcard() {
        return idcard;
    }

    public User setIdcard(String idcard) {
        this.idcard = idcard;
        return this;
    }

    public String getNation() {
        return nation;
    }

    public User setNation(String nation) {
        this.nation = nation;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public User setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public User setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public User setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public Long getSortNo() {
        return sortNo;
    }

    public User setSortNo(Long sortNo) {
        this.sortNo = sortNo;
        return this;
    }

    public String getManType() {
        return manType;
    }

    public User setManType(String manType) {
        this.manType = manType;
        return this;
    }

    public String getRank() {
        return rank;
    }

    public User setRank(String rank) {
        this.rank = rank;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public User setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPoliceId() {
        return policeId;
    }

    public User setPoliceId(String policeId) {
        this.policeId = policeId;
        return this;
    }

    public String getPoliceName() {
        return policeName;
    }

    public User setPoliceName(String policeName) {
        this.policeName = policeName;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public User setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getCompanyPerson() {
        return companyPerson;
    }

    public User setCompanyPerson(String companyPerson) {
        this.companyPerson = companyPerson;
        return this;
    }

    public String getPersonMobile() {
        return personMobile;
    }

    public User setPersonMobile(String personMobile) {
        this.personMobile = personMobile;
        return this;
    }

    public String getProject() {
        return project;
    }

    public User setProject(String project) {
        this.project = project;
        return this;
    }

    public String getDeleted() {
        return deleted;
    }

    public User setDeleted(String deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public User setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
        return this;
    }

    public String getPoliceNumber() {
        return policeNumber;
    }

    public User setPoliceNumber(String policeNumber) {
        this.policeNumber = policeNumber;
        return this;
    }

    public String getMobileWork() {
        return mobileWork;
    }

    public User setMobileWork(String mobileWork) {
        this.mobileWork = mobileWork;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getQqAccount() {
        return qqAccount;
    }

    public User setQqAccount(String qqAccount) {
        this.qqAccount = qqAccount;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getWxAccount() {
        return wxAccount;
    }

    public User setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount;
        return this;
    }

    public String getBirthStr() {
        return birthStr;
    }

    public User setBirthStr(String birthStr) {
        this.birthStr = birthStr;
        return this;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public User setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
        return this;
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

    public User setPostType(String postType) {
        this.postType = postType;
        return this;
    }

    public String getIsParentOrg() {
        return isParentOrg;
    }

    public void setIsParentOrg(String isParentOrg) {
        this.isParentOrg = isParentOrg;
    }

    public Long getType() {
        return type;
    }

    public User setType(Long type) {
        this.type = type;
        return this;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public User setNotifyType(String notifyType) {
        this.notifyType = notifyType;
        return this;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userType='" + userType + '\'' +
                ", name='" + name + '\'' +
                ", idcard='" + idcard + '\'' +
                ", nation='" + nation + '\'' +
                ", sex='" + sex + '\'' +
                ", photo='" + photo + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", address='" + address + '\'' +
                ", sortNo=" + sortNo +
                ", manType='" + manType + '\'' +
                ", rank='" + rank + '\'' +
                ", title='" + title + '\'' +
                ", policeId='" + policeId + '\'' +
                ", policeName='" + policeName + '\'' +
                ", company='" + company + '\'' +
                ", companyPerson='" + companyPerson + '\'' +
                ", personMobile='" + personMobile + '\'' +
                ", project='" + project + '\'' +
                ", deleted='" + deleted + '\'' +
                ", policeCategory='" + policeCategory + '\'' +
                ", policeNumber='" + policeNumber + '\'' +
                ", mobileWork='" + mobileWork + '\'' +
                ", phone='" + phone + '\'' +
                ", qqAccount='" + qqAccount + '\'' +
                ", email='" + email + '\'' +
                ", wxAccount='" + wxAccount + '\'' +
                ", birthStr='" + birthStr + '\'' +
                ", createTimeStr='" + createTimeStr + '\'' +
                ", auxiliaryType='" + auxiliaryType + '\'' +
                ", jobType='" + jobType + '\'' +
                ", postType='" + postType + '\'' +
                ", isParentOrg='" + isParentOrg + '\'' +
                ", type=" + type +
                ", notifyType='" + notifyType + '\'' +
                ", tenantArea='" + tenantArea + '\'' +
                ", tenantPoliceCategory='" + tenantPoliceCategory + '\'' +
                ", defaultTenant='" + defaultTenant + '\'' +
                ", policeCategoryName='" + policeCategoryName + '\'' +
                ", roleName='" + roleName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", belongName='" + belongName + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
