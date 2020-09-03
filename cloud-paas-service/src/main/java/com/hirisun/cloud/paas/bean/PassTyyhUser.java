package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 统一用户人员信息
 * </p>
 *
 * @author junglefisher
 * @since 2019-12-31
 */
@TableName("TB_PASS_TYYH_USER")
public class PassTyyhUser extends Model<PassTyyhUser> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

        /**
     * 姓名
     */
         @TableField("NAME")
    private String name;

        /**
     * 身份证号
     */
         @TableField("IDCARD")
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
     * 建设单位名称
     */
         @TableField("JS_UNIT")
    private String jsUnit;

        /**
     * 项目名称
     */
         @TableField("PROJECT_NAME")
    private String projectName;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFIED_TIME")
    private Date modifiedTime;

        /**
     * 职务
     */
         @TableField("POSITION")
    private String position;

        /**
     * 人员类型：0-服务商人员，1-政府单位人员
     */
         @TableField("TYPE")
    private Integer type;

        /**
     * 政府单位名称
     */
         @TableField("GOV_UNIT")
    private String govUnit;

        /**
     * 政府单位机构代码
     */
         @TableField("GOV_CODE")
    private String govCode;


    public String getId() {
        return id;
    }

    public PassTyyhUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PassTyyhUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getIdcard() {
        return idcard;
    }

    public PassTyyhUser setIdcard(String idcard) {
        this.idcard = idcard;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public PassTyyhUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public PassTyyhUser setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getJsUnit() {
        return jsUnit;
    }

    public PassTyyhUser setJsUnit(String jsUnit) {
        this.jsUnit = jsUnit;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public PassTyyhUser setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PassTyyhUser setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PassTyyhUser setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PassTyyhUser setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public PassTyyhUser setPosition(String position) {
        this.position = position;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public PassTyyhUser setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getGovUnit() {
        return govUnit;
    }

    public PassTyyhUser setGovUnit(String govUnit) {
        this.govUnit = govUnit;
        return this;
    }

    public String getGovCode() {
        return govCode;
    }

    public PassTyyhUser setGovCode(String govCode) {
        this.govCode = govCode;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PassTyyhUser{" +
        "id=" + id +
        ", name=" + name +
        ", idcard=" + idcard +
        ", phone=" + phone +
        ", company=" + company +
        ", jsUnit=" + jsUnit +
        ", projectName=" + projectName +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", position=" + position +
        ", type=" + type +
        ", govUnit=" + govUnit +
        ", govCode=" + govCode +
        "}";
    }
}
