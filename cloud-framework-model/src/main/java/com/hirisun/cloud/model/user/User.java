package com.hirisun.cloud.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhoufeng
 * @version 1.0
 * @className User
 * @data 2020/6/19 10:07
 * @description
 */
@Data
public class User {

    /**
     * 用户类型-0 普通用户
     */
    public static final Long USER_TYPE_NORMAL=0L;
    /**
     * 用户类型-1 服务厂商
     */
    public static final Long USER_TYPE_COMPANY=1L;
    /**
     * 用户类型-20 租户管理员
     */
    public static final Long USER_TYPE_TENANT_MANAGER=20L;
    /**
     * 用户类型-30 省厅管理员
     */
    public static final Long USER_TYPE_PROVINCIAL_MANAGER=30L;
    /**
     * 用户类型-100 管理用户
     */
    public static final Long USER_TYPE_MANAGER=100L;

    /**
     * 通知类型-0 短信
     */
    public static final String NOTIFY_TYPE_SMS="0";
    /**
     * 通知类型-1 邮箱
     */
    public static final String NOTIFY_TYPE_EMAIL="1";
    /**
     * 通知类型-2 微信
     */
    public static final String NOTIFY_TYPE_WX="2";

    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "用户类型：10 民警  20 辅警    30外部人员")
    private String userType;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "民族,引用表码3.4")
    private String nation;

    @ApiModelProperty(value = "性别,引用表码3.5")
    private String sex;

    @ApiModelProperty(value = "出生日期")
    private LocalDateTime birth;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "照片id")
    private String photo;

    @ApiModelProperty(value = "机构id")
    private String orgId;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "联系地址")
    private String address;

    @ApiModelProperty(value = "排序号")
    private Long sortNo;

    @ApiModelProperty(value = "人员身份类型，引用表码3.6")
    private String manType;

    @ApiModelProperty(value = "警衔,引用表码3.7")
    private String rank;

    @ApiModelProperty(value = "职级,引用表码3.8")
    private String title;

    @ApiModelProperty(value = "责任民警ID")
    private String policeId;

    @ApiModelProperty(value = "责任民警姓名")
    private String policeName;

    @ApiModelProperty(value = "所属公司")
    private String company;

    @ApiModelProperty(value = "所属公司负责人")
    private String companyPerson;

    @ApiModelProperty(value = "所属公司负责人电话")
    private String personMobile;

    @ApiModelProperty(value = "施工项目")
    private String project;

    @ApiModelProperty(value = "0：删除；1：有效")
    private String deleted;

    @ApiModelProperty(value = "警种，引用表码3.9")
    private String policeCategory;

    @ApiModelProperty(value = "警号")
    private String policeNumber;

    private String mobilePrivte;

    @ApiModelProperty(value = "手机号码")
    private String mobileWork;

    @ApiModelProperty(value = "座机")
    private String phone;

    @ApiModelProperty(value = "QQ号")
    private String qqAccount;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "微信号")
    private String wxAccount;

    private String birthStr;

    private String createTimeStr;

    @ApiModelProperty(value = "用户类型  0：普通用户 1: 服务厂商  100：管理用户 20：租户管理员 30：省厅管理员")
    private Long type;

    @ApiModelProperty(value = "通知类型  0：短信 1:邮箱 2:微信")
    private String notifyType;

    @ApiModelProperty(value = "辅警类型")
    private String auxiliaryType;

    @ApiModelProperty(value = "在职类型")
    private String jobType;

    @ApiModelProperty(value = "职务")
    private String postType;

    @ApiModelProperty(value = "是否主机构 1：是、 0 ：否(警员专用")
    private String isParentOrg;

    @ApiModelProperty(value = "租户地区")
    private String tenantArea;

    @ApiModelProperty(value = "租户警种")
    private String tenantPoliceCategory;

    @ApiModelProperty(value = "是否第一租户管理员 1-是")
    private String defaultTenant;

    @ApiModelProperty(value = "所属地区")
    private String belongArea;

    @ApiModelProperty(value = "所属警种")
    private String belongPoliceType;

    @ApiModelProperty(value = "政务人员编号")
    private String userNumber;

    @ApiModelProperty(value = "政务机构code")
    private String govCode;

    @ApiModelProperty(value = "删除时间")
    private String deleteTime;

    @ApiModelProperty(value = "政务人员信息字段：职务")
    private String post;

    @ApiModelProperty(value = "政务人员信息字段：政务机构id")
    private String govId;

    @ApiModelProperty(value = "政务人员信息字段：政务机构名称")
    private String govName;

    @ApiModelProperty(value = "登录人员类型 01警务 02政务")
    private String personNeltype;

    @ApiModelProperty(value = "租户所属国家专项")
    private String nationalProject;
}
