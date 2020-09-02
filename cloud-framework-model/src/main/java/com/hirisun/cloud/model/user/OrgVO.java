package com.hirisun.cloud.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 机构表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Data
@ApiModel(value="Org对象", description="机构表")
public class OrgVO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "机构id")
    private String id;

    @ApiModelProperty(value = "机构代码")
    private String code;

    @ApiModelProperty(value = "机构全称")
    private String fullName;

    @ApiModelProperty(value = "机构简称")
    private String shortName;

    @ApiModelProperty(value = "行政区划")
    private String areaCode;

    @ApiModelProperty(value = "机构类别,引用表码3.1")
    private String orgType;

    @ApiModelProperty(value = "机构类型 1:正式 2：临时")
    private String orgKind;

    @ApiModelProperty(value = "单位性质 1：机关单位 2：事业单位")
    private String deptKind;

    @ApiModelProperty(value = "标准代码,引用表码3.2")
    private String bizType;

    @ApiModelProperty(value = "单位层级,引用表码3.3")
    private String orgLevel;

    @ApiModelProperty(value = "上级行政机构 行政隶属于那一个公安局的单位")
    private String upGovId;

    @ApiModelProperty(value = "上级行政机构名称")
    private String upGovName;

    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "E-mail")
    private String email;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "邮编")
    private String postcode;

    @ApiModelProperty(value = "详细介绍")
    private String detail;

    @ApiModelProperty(value = "机构状态 0：有效 1:删除")
    private String deleted;

    @ApiModelProperty(value = "启用日期")
    private Date orgStartDate;

    @ApiModelProperty(value = "停用日期")
    private Date orgEndDate;

    @ApiModelProperty(value = "排序号")
    private Long sortNo;

    @ApiModelProperty(value = "单位级别")
    private String orgRank;

    @ApiModelProperty(value = "租户管理员")
    private String tenantManager;


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
        ", orgStartDate=" + orgStartDate +
        ", orgEndDate=" + orgEndDate +
        ", sortNo=" + sortNo +
        ", orgRank=" + orgRank +
        ", tenantManager=" + tenantManager +
        "}";
    }
}
