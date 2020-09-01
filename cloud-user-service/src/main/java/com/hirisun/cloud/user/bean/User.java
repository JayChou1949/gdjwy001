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
 * 用户表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
@Data
@TableName("TB_USER")
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
    @TableField("IDCARD")
    private String idcard;

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

//    @ApiModelProperty(value = "政务人员信息字段：职务")
//    @TableField("POST")
//    private String post;

//    @ApiModelProperty(value = "政务人员信息字段：政务机构id")
//    @TableField("GOV_ID")
//    private String govId;
//
//    @ApiModelProperty(value = "政务人员信息字段：政务机构名称")
//    @TableField("GOV_NAME")
//    private String govName;
//
//    @ApiModelProperty(value = "登录人员类型 01警务 02政务")
//    @TableField("PERSON_NELTYPE")
//    private String personNeltype;

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


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userType=" + userType +
                ", name=" + name +
                ", idcard=" + idcard +
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
                ", nationalProject=" + nationalProject +
                "}";
    }
}
