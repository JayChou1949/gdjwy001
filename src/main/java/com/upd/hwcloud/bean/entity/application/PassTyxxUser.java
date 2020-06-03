package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.common.utils.annotation.ExcelExplain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 统一用户人员信息
 * </p>
 *
 * @author huru
 * @since 2019-02-22
 */
@TableName("TB_PASS_TYYH_USER")
public class PassTyxxUser extends Model<PassTyxxUser> {

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
    @ExcelExplain(head = "公司名称")
    @TableField("COMPANY")
    private String company;

    /**
     * 建设单位名称
     */
    @ExcelExplain(head = "建设单位名称（科室）")
    @TableField("JS_UNIT")
    private String jsUnit;

    /**
     * 项目名称
     */
    @ExcelExplain(head = "项目名称")
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 申请信息 id
     */
    @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

    /**
     * 职务
     */
    @TableField("POSITION")
    private String position;

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

    /**
     * 人员类型：0-服务商人员，1-政府单位人员
     */
    @TableField("TYPE")
    private Integer type;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    public String getGovCode() {
        return govCode;
    }

    public void setGovCode(String govCode) {
        this.govCode = govCode;
    }

    public String getGovUnit() {
        return govUnit;
    }

    public void setGovUnit(String govUnit) {
        this.govUnit = govUnit;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public PassTyxxUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PassTyxxUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getIdcard() {
        return idcard;
    }

    public PassTyxxUser setIdcard(String idcard) {
        this.idcard = idcard;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public PassTyxxUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public PassTyxxUser setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getJsUnit() {
        return jsUnit;
    }

    public PassTyxxUser setJsUnit(String jsUnit) {
        this.jsUnit = jsUnit;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public PassTyxxUser setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PassTyxxUser setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PassTyxxUser setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PassTyxxUser setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PassTyxxUser{" +
                "id=" + id +
                ", name=" + name +
                ", idcard=" + idcard +
                ", phone=" + phone +
                ", company=" + company +
                ", jsUnit=" + jsUnit +
                ", projectName=" + projectName +
                ", appInfoId=" + appInfoId +
                ", position=" + position +
                ", govUnit=" + govUnit +
                ", govCode=" + govCode +
                ", type=" + type +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                "}";
    }
}
