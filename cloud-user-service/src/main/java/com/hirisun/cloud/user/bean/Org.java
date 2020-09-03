package com.hirisun.cloud.user.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 机构表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Data
@TableName("TB_ORG")
@ApiModel(value="Org对象", description="机构表")
public class Org implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "机构id")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "机构代码")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "机构全称")
    @TableField("FULL_NAME")
    private String fullName;

    @ApiModelProperty(value = "机构简称")
    @TableField("SHORT_NAME")
    private String shortName;

    @ApiModelProperty(value = "行政区划")
    @TableField("AREA_CODE")
    private String areaCode;

    @ApiModelProperty(value = "机构类别,引用表码3.1")
    @TableField("ORG_TYPE")
    private String orgType;

    @ApiModelProperty(value = "机构类型 1:正式 2：临时")
    @TableField("ORG_KIND")
    private String orgKind;

    @ApiModelProperty(value = "单位性质 1：机关单位 2：事业单位")
    @TableField("DEPT_KIND")
    private String deptKind;

    @ApiModelProperty(value = "标准代码,引用表码3.2")
    @TableField("BIZ_TYPE")
    private String bizType;

    @ApiModelProperty(value = "单位层级,引用表码3.3")
    @TableField("ORG_LEVEL")
    private String orgLevel;

    @ApiModelProperty(value = "上级行政机构 行政隶属于那一个公安局的单位")
    @TableField("UP_GOV_ID")
    private String upGovId;

    @ApiModelProperty(value = "上级行政机构名称")
    @TableField("UP_GOV_NAME")
    private String upGovName;

    @ApiModelProperty(value = "联系人")
    @TableField("LINK_MAN")
    private String linkMan;

    @ApiModelProperty(value = "电话")
    @TableField("TEL")
    private String tel;

    @ApiModelProperty(value = "E-mail")
    @TableField("EMAIL")
    private String email;

    @ApiModelProperty(value = "地址")
    @TableField("ADDRESS")
    private String address;

    @ApiModelProperty(value = "邮编")
    @TableField("POSTCODE")
    private String postcode;

    @ApiModelProperty(value = "详细介绍")
    @TableField("DETAIL")
    private String detail;

    @ApiModelProperty(value = "机构状态 0：有效 1:删除")
    @TableField("DELETED")
    private String deleted;

    @ApiModelProperty(value = "启用日期")
    @TableField("ORG_START_DATE")
    private Date orgStartDate;

    @ApiModelProperty(value = "停用日期")
    @TableField("ORG_END_DATE")
    private Date orgEndDate;

    @ApiModelProperty(value = "排序号")
    @TableField("SORT_NO")
    private Long sortNo;

    @ApiModelProperty(value = "单位级别")
    @TableField("ORG_RANK")
    private String orgRank;

    @ApiModelProperty(value = "租户管理员")
    @TableField("TENANT_MANAGER")
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
