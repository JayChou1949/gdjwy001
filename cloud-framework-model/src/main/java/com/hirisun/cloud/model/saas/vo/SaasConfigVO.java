package com.hirisun.cloud.model.saas.vo;

import com.hirisun.cloud.model.user.UserVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SaasConfigVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4020631660531331116L;

    private String id;

        /**
     *  服务名称
     */
    private String name;
    /**
     * 关联流程ID
     */
    private String workFlowId;
    /**
     * 服务简称
     */
    private String shortName;
    /**
     * 申请说明
     */
    private String instructions;
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
        /**
     * 子类
     */
    private String subType;

        /**
     * 排序
     */
    private Long sort;

        /**
     * 版本号
     */
    private String versionCode;

        /**
     * 用户名
     */
    private String userName;

        /**
     * 密码
     */
    private String password;

        /**
     * 链接
     */
    private String url;

    /**
     * 二级页面 URL
     */
    private String subPageUrl;

        /**
     * 展示图
     */
    private String image;



    /**
     * 通过nginx获取地址
     */
    private String realImage;

        /**
     * 描述
     */
    private String description;

        /**
     * 生产环境资源申请方式
     */
    private String proApplication;

        /**
     * 生产环境资源使用方式
     */
    private String proUse;

        /**
     * 测试环境存在形式
     */
    private String testForm;

        /**
     * 测试环境资源申请方式
     */
    private String testApplication;

        /**
     * 测试环境资源使用方式
     */
    private String testUse;

        /**
     * 使用/开发指南
     */
    private String guide;

        /**
     * 公安网 使用/开发指南位置
     */
    private String guideIntranet;

        /**
     * 运维服务第一接口人
     */
    private String firstInterfacePerson;

        /**
     * 运维服务上升接口人
     */
    private String upInterfacePerson;

    private Date createTime;

    private Date modifiedTime;

        /**
     * 状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除
     */
    private Long status;

        /**
     * 创建人
     */
    private String creator;

    /**
     * 是否大图  是否大图 0:否 1:是
     */
    private String top;

    /**
     * 建设进展 0.已完成 1.建设中
     */
    private Long buildStatus;

    /**
     * 二级页面查看权限 0.完全开放 1登录可看
     */
    private Long subPagePermission;

    /**
     * 是否首页展示 0:否 1:是
     */
    private String home;

    /**
     * 是否能申请 0:否 1:是
     */
    private String canApplication;

    /**
     * 是否有开发文档 0:否 1:是
     */
    private String hasDoc;

    /**
     * 是否加密应用 0:否 1:是
     */
    private String secret;

    /**
     * 申请表单编码
     */
    private String formNum;

    private UserVO user;

    private String subTypeName;

    /**
     * 标签描述
     */
    private String tagDesc;

    /**
     * 接入系统数量
     */
    private String apNum;

    /**
     * 上报方式 0-地区 1-警种 2-政府机构
     */
    private String applyType;

    /**
     * 地区
     */
    private String areaName;

    private String policeCategory;

    private String registId;

    /**
     * 浏览量
     */
    private Long viewCount;


    /**
     * API网关GUID
     */
    private String serviceGuid;


    /**
     * 是否为saas服务 0应用 1为服务
     */
    private Integer serviceFlag;


    /**
     * 是否是试点应用：0-非试点,1-2019,2-2020,...(目前只有2019试点，以后扩展按该规则)
     */
    private Integer pilotApp;

    /**
     * 进入系统点击量
     */
    private Long sysView;


    /**
     * 测试应用 0：非测试应用 1：测试应用 类别为通用应用时生效
     */
    private Boolean test;

    /**
     * SAAS/应用所属专项
     */
    private String project;

    private boolean canAddShoppingCart = true;

}
