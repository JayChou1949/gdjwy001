package com.hirisun.cloud.order.bean.iaas;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hirisun.cloud.order.annotation.ExcelExplain;

/**
 * <p>
 * 云桌面云资源用户人员信息
 * </p>
 *
 * @author zwb
 * @since 2019-05-09
 */
@TableName("TB_IAAS_YZMYZY_USER")
public class IaasYzmyzyUser extends Model<IaasYzmyzyUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;
        /**
     * 姓名
     */
        @ExcelExplain(head = "姓名")
         @TableField("NAME")
    private String name;
        /**
     * 身份证号
     */
        @ExcelExplain(head = "身份证号")
         @TableField("IDCARD")
    private String idcard;
        /**
     * 联系电话
     */
        @ExcelExplain(head = "联系电话")
         @TableField("PHONE")
    private String phone;
        /**
     * 公司名称
     */
        @ExcelExplain(head = "归属单位")
         @TableField("COMPANY")
    private String company;
        /**
     * 应用名称
     */
         @TableField("PROJECT_NAME")
    private String projectName;
        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;
        /**
     * 用户类型
     */
        @ExcelExplain(head = "身份")
         @TableField("USER_TYPE")
    private String userType;
        /**
     * 性别,引用表码3.5
     */
        @ExcelExplain(head = "性别")
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
    @ExcelExplain(head = "云桌面IP")
    @TableField("YZY_IP")
    private String yzyIp;
    @ExcelExplain(head = "账户名称")
    @TableField("ACCOUNT")
    private String account;
    @ExcelExplain(head = "账户密码")
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


    public String getId() {
        return id;
    }

    public IaasYzmyzyUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public IaasYzmyzyUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getIdcard() {
        return idcard;
    }

    public IaasYzmyzyUser setIdcard(String idcard) {
        this.idcard = idcard;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public IaasYzmyzyUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public IaasYzmyzyUser setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public IaasYzmyzyUser setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasYzmyzyUser setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasYzmyzyUser setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasYzmyzyUser setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public IaasYzmyzyUser setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public IaasYzmyzyUser setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getJsPrincipal() {
        return jsPrincipal;
    }

    public IaasYzmyzyUser setJsPrincipal(String jsPrincipal) {
        this.jsPrincipal = jsPrincipal;
        return this;
    }

    public String getJsPrincipalPhone() {
        return jsPrincipalPhone;
    }

    public IaasYzmyzyUser setJsPrincipalPhone(String jsPrincipalPhone) {
        this.jsPrincipalPhone = jsPrincipalPhone;
        return this;
    }

    public String getCjPrincipal() {
        return cjPrincipal;
    }

    public IaasYzmyzyUser setCjPrincipal(String cjPrincipal) {
        this.cjPrincipal = cjPrincipal;
        return this;
    }

    public String getCjPrincipalPhone() {
        return cjPrincipalPhone;
    }

    public IaasYzmyzyUser setCjPrincipalPhone(String cjPrincipalPhone) {
        this.cjPrincipalPhone = cjPrincipalPhone;
        return this;
    }

    public String getYzyIp() {
        return yzyIp;
    }

    public IaasYzmyzyUser setYzyIp(String yzyIp) {
        this.yzyIp = yzyIp;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public IaasYzmyzyUser setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IaasYzmyzyUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUserStatus() {
        return userStatus;
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

    public IaasYzmyzyUser setUserStatus(String userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasYzmyzyUser{" +
        "id=" + id +
        ", name=" + name +
        ", idcard=" + idcard +
        ", phone=" + phone +
        ", company=" + company +
        ", projectName=" + projectName +
        ", appInfoId=" + appInfoId +
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
