package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 机构表
 * </p>
 *
 * @author wuc
 * @since 2018-10-12
 */
@TableName("TB_ORG")
public class Org extends Model<Org> {

    private static final long serialVersionUID = 1L;

        /**
     * 机构id
     */
         @TableId(value = "ID", type = IdType.INPUT)
    private String id;

        /**
     * 机构代码
     */
         @TableField("CODE")
    private String code;

        /**
     * 机构全称
     */
         @TableField("FULL_NAME")
    private String fullName;

        /**
     * 机构简称
     */
         @TableField("SHORT_NAME")
    private String shortName;

        /**
     * 行政区划
     */
         @TableField("AREA_CODE")
    private String areaCode;

        /**
     * 机构类别,引用表码3.1
     */
         @TableField("ORG_TYPE")
    private String orgType;

        /**
     * 机构类型 1:正式 2：临时
     */
         @TableField("ORG_KIND")
    private String orgKind;

        /**
     * 单位性质 1：机关单位 2：事业单位
     */
         @TableField("DEPT_KIND")
    private String deptKind;

        /**
     * 标准代码,引用表码3.2
     */
         @TableField("BIZ_TYPE")
    private String bizType;

        /**
     * 单位层级,引用表码3.3
     */
         @TableField("ORG_LEVEL")
    private String orgLevel;

    /**
     * 单位级别，引用表码4.9
     */
    @TableField("ORG_RANK")
    private String orgRank;

        /**
     * 上级行政机构 行政隶属于那一个公安局的单位
     */
         @TableField("UP_GOV_ID")
    private String upGovId;

        /**
     * 上级行政机构名称
     */
         @TableField("UP_GOV_NAME")
    private String upGovName;

        /**
     * 联系人
     */
         @TableField("LINK_MAN")
    private String linkMan;

        /**
     * 电话
     */
         @TableField("TEL")
    private String tel;

        /**
     * E-mail
     */
         @TableField("EMAIL")
    private String email;

        /**
     * 地址
     */
         @TableField("ADDRESS")
    private String address;

        /**
     * 邮编
     */
         @TableField("POSTCODE")
    private String postcode;

        /**
     * 详细介绍
     */
         @TableField("DETAIL")
    private String detail;

        /**
     * 机构状态 0：有效 1:删除
     */
         @TableField("DELETED")
    private String deleted;

        /**
     * 排序号
     */
         @TableField("SORT_NO")
    private Long sortNo;

    /**
     * 租户管理员
     */
    @TableField("TENANT_MANAGER")
    private String tenantManager;

    public String getTenantManager() {
        return tenantManager;
    }

    public void setTenantManager(String tenantManager) {
        this.tenantManager = tenantManager;
    }

    public String getId() {
        return id;
    }

    public Org setId(String id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Org setCode(String code) {
        this.code = code;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public Org setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public Org setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public Org setAreaCode(String areaCode) {
        this.areaCode = areaCode;
        return this;
    }

    public String getOrgType() {
        return orgType;
    }

    public Org setOrgType(String orgType) {
        this.orgType = orgType;
        return this;
    }

    public String getOrgKind() {
        return orgKind;
    }

    public Org setOrgKind(String orgKind) {
        this.orgKind = orgKind;
        return this;
    }

    public String getDeptKind() {
        return deptKind;
    }

    public Org setDeptKind(String deptKind) {
        this.deptKind = deptKind;
        return this;
    }

    public String getBizType() {
        return bizType;
    }

    public Org setBizType(String bizType) {
        this.bizType = bizType;
        return this;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public Org setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
        return this;
    }

    public String getOrgRank() {
        return orgRank;
    }

    public void setOrgRank(String orgRank) {
        this.orgRank = orgRank;
    }

    public String getUpGovId() {
        return upGovId;
    }

    public Org setUpGovId(String upGovId) {
        this.upGovId = upGovId;
        return this;
    }

    public String getUpGovName() {
        return upGovName;
    }

    public Org setUpGovName(String upGovName) {
        this.upGovName = upGovName;
        return this;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public Org setLinkMan(String linkMan) {
        this.linkMan = linkMan;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public Org setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Org setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Org setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPostcode() {
        return postcode;
    }

    public Org setPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public Org setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public String getDeleted() {
        return deleted;
    }

    public Org setDeleted(String deleted) {
        this.deleted = deleted;
        return this;
    }

    public Long getSortNo() {
        return sortNo;
    }

    public Org setSortNo(Long sortNo) {
        this.sortNo = sortNo;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Org{" +
        "id=" + id +
        ", code=" + code +
        ", fullName=" + fullName +
        ", shortName=" + shortName +
        ", areaCode=" + areaCode +
        ", orgType=" + orgType +
        ", orgKind=" + orgKind +
        ", deptKind=" + deptKind +
        ", bizType=" + bizType +
        ", orgLevel=" + orgLevel +
        ", upGovId=" + upGovId +
        ", upGovName=" + upGovName +
        ", linkMan=" + linkMan +
        ", tel=" + tel +
        ", email=" + email +
        ", address=" + address +
        ", postcode=" + postcode +
        ", detail=" + detail +
        ", deleted=" + deleted +
        ", sortNo=" + sortNo +
        "}";
    }
}
