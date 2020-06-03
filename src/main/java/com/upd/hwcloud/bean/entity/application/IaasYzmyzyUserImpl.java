package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 云桌面云资源人员信息
 * </p>
 *
 * @author zwb
 * @since 2019-05-09
 */
@TableName("TB_IAAS_YZMYZY_USER_IMPL")
public class IaasYzmyzyUserImpl extends Model<IaasYzmyzyUserImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;
        /**
     * 姓名
     */
         @TableField("NAME")
    private String name;

        /**
     * 身份证号
     */
         @TableId("IDCARD")
    private String idcard;

        /**
     * 联系电话
     */
         @TableField("PHONE")
    private String phone;

        /**
     * 公司名称
     */
         @TableField("COMPANY")
    private String company;

        /**
     * 应用名称
     */
         @TableField("PROJECT_NAME")
    private String projectName;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFIED_TIME")
    private Date modifiedTime;

        /**
     * 用户类型
     */
         @TableField("USER_TYPE")
    private String userType;

        /**
     * 性别,引用表码3.5
     */
         @TableField("SEX")
    private String sex;

        /**
     * 申请负责人
     */
         @TableField("JS_PRINCIPAL")
    private String jsPrincipal;

        /**
     * 申请负责人电话
     */
         @TableField("JS_PRINCIPAL_PHONE")
    private String jsPrincipalPhone;

        /**
     * 负责人
     */
         @TableField("CJ_PRINCIPAL")
    private String cjPrincipal;

        /**
     * 负责人电话
     */
         @TableField("CJ_PRINCIPAL_PHONE")
    private String cjPrincipalPhone;

    @TableField("YZY_IP")
    private String yzyIp;

    @TableField("ACCOUNT")
    private String account;

    @TableField("PASSWORD")
    private String password;

    @TableField("USER_STATUS")
    private String userStatus;

    /**
     * 申请类型 0:新增 1：扩容
     */
    @TableField("APPLY_TYPE")
    private Integer applyType;

    /**
     * 变更前存储大小
     */
    @TableField("OLD_DISK")
    private Double oldDisk;

    /**
     * 变更后存储大小
     */
    @TableField("NEW_DISK")
    private Double newDisk;
    public IaasYzmyzyUserImpl(){

    }
    public IaasYzmyzyUserImpl(IaasYzmyzyUser user) {
        this.name=user.getName();
        this.idcard=user.getIdcard();
        this.phone=user.getPhone();
        this.company=user.getCompany();
        this.projectName=user.getProjectName();
        this.createTime=user.getCreateTime();
        this.modifiedTime=user.getModifiedTime();
        this.userType=user.getUserType();
        this.sex=user.getSex();
        this.jsPrincipal=user.getJsPrincipal();
        this.jsPrincipalPhone=user.getJsPrincipalPhone();
        this.cjPrincipal=user.getCjPrincipal();
        this.cjPrincipalPhone=user.getCjPrincipalPhone();
        this.yzyIp=user.getYzyIp();
        this.account=user.getAccount();
        this.password=user.getPassword();
        this.userStatus= user.getUserStatus();
        this.applyType = user.getApplyType();
        this.oldDisk = user.getOldDisk();
        this.newDisk = user.getNewDisk();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public IaasYzmyzyUserImpl setName(String name) {
        this.name = name;
        return this;
    }

    public String getIdcard() {
        return idcard;
    }

    public IaasYzmyzyUserImpl setIdcard(String idcard) {
        this.idcard = idcard;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public IaasYzmyzyUserImpl setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public IaasYzmyzyUserImpl setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public IaasYzmyzyUserImpl setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasYzmyzyUserImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasYzmyzyUserImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public IaasYzmyzyUserImpl setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public IaasYzmyzyUserImpl setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getJsPrincipal() {
        return jsPrincipal;
    }

    public IaasYzmyzyUserImpl setJsPrincipal(String jsPrincipal) {
        this.jsPrincipal = jsPrincipal;
        return this;
    }

    public String getJsPrincipalPhone() {
        return jsPrincipalPhone;
    }

    public IaasYzmyzyUserImpl setJsPrincipalPhone(String jsPrincipalPhone) {
        this.jsPrincipalPhone = jsPrincipalPhone;
        return this;
    }

    public String getCjPrincipal() {
        return cjPrincipal;
    }

    public IaasYzmyzyUserImpl setCjPrincipal(String cjPrincipal) {
        this.cjPrincipal = cjPrincipal;
        return this;
    }

    public String getCjPrincipalPhone() {
        return cjPrincipalPhone;
    }

    public IaasYzmyzyUserImpl setCjPrincipalPhone(String cjPrincipalPhone) {
        this.cjPrincipalPhone = cjPrincipalPhone;
        return this;
    }

    public String getYzyIp() {
        return yzyIp;
    }

    public IaasYzmyzyUserImpl setYzyIp(String yzyIp) {
        this.yzyIp = yzyIp;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public IaasYzmyzyUserImpl setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IaasYzmyzyUserImpl setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public IaasYzmyzyUserImpl setUserStatus(String userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Double getOldDisk() {
        return oldDisk;
    }

    public void setOldDisk(Double oldDisk) {
        this.oldDisk = oldDisk;
    }

    public Double getNewDisk() {
        return newDisk;
    }

    public void setNewDisk(Double newDisk) {
        this.newDisk = newDisk;
    }

    @Override
    protected Serializable pkVal() {
        return this.idcard;
    }

    @Override
    public String toString() {
        return "IaasYzmyzyUserImpl{" +
        "name=" + name +
        ", idcard=" + idcard +
        ", phone=" + phone +
        ", company=" + company +
        ", projectName=" + projectName +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", userType=" + userType +
        ", sex=" + sex +
        ", jsPrincipal=" + jsPrincipal +
        ", jsPrincipalPhone=" + jsPrincipalPhone +
        ", cjPrincipal=" + cjPrincipal +
        ", cjPrincipalPhone=" + cjPrincipalPhone +
        ", yzyIp=" + yzyIp +
        ", account=" + account +
        ", password=" + password +
        ", userStatus=" + userStatus +
        "}";
    }
}
