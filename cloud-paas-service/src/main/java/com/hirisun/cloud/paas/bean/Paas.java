package com.hirisun.cloud.paas.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;

import io.swagger.annotations.ApiModelProperty;

@TableName("TB_PAAS")
public class Paas implements Serializable {

	private static final long serialVersionUID = -3955494181702424476L;

	@TableId(value = "ID", type = IdType.INPUT)
    private String id;

        /**
     *  服务名称
     */
         @TableField("NAME")
    private String name;
    /**
     * 申请说明
     */
    @TableField("INSTRUCTIONS")
    private String instructions;
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    /**
     * 服务简称
     */
    @TableField("SHORT_NAME")
    private String shortName;

        /**
     * 子类
     */
         @TableField("SUB_TYPE")
    private String subType;

        /**
     * 排序
     */
        @TableField(value = "SORT",fill = FieldFill.INSERT)
    private Long sort;

        /**
     * 版本号
     */
         @TableField("VERSION_CODE")
    private String versionCode;

        /**
     * 链接
     */
         @TableField("URL")
    private String url;

    /**
     * 二级页面 URL
     */
    @TableField("SUB_PAGE_URL")
    private String subPageUrl;

        /**
     * 展示图
     */
         @TableField("IMAGE")
    private String image;

    /**
     * 通过nginx获取地址
     */
    @TableField("REAL_IMAGE")
    private String realImage;

        /**
     * 描述
     */
         @TableField("DESCRIPTION")
    private String description;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除
     */
         @TableField("STATUS")
    private Long status;

        /**
     * 创建人
     */
         @TableField("CREATOR")
    private String creator;

    /**
     * 是否大图  是否大图 0:否 1:是
     */
    @TableField("TOP")
    private String top;

    /**
     * 建设进展 0.已完成 1.建设中 2:公测
     */
    @TableField("BUILD_STATUS")
    private Long buildStatus;

    /**
     * 接入系统数量
     */@ApiModelProperty("接入系统数量")
    @TableField("IN_NUM")
    private Long inNum;

    /**
     * 二级页面查看权限 0.完全开放 1登录可看
     */@ApiModelProperty("二级页面查看权限 0.完全开放 1登录可看")
    @TableField("SUB_PAGE_PERMISSION")
    private Long subPagePermission;

    /**
     * 是否首页展示 0:否 1:是
     */
    @TableField("HOME")
    private String home;

    /**
     * 是否能申请 0:否 1:是
     */
    @TableField("CAN_APPLICATION")
    private String canApplication;

    /**
     * 是否有开发文档 0:否 1:是
     */
    @TableField("HAS_DOC")
    private String hasDoc;
    /**
     * 是否为API服务 0:否 1:是
     */@ApiModelProperty("是否为API服务 0:否 1:是")
    @TableField("API_ACCESS")
    private String apiAccess;
    /**
     * 服务GUID
     */
    @ApiModelProperty("服务GUID")
    @TableField("SERVICE_GUID")
    private String serviceGuid;
    /**
     * 申请表单编码
     */
    @TableField("FORM_NUM")
    private String formNum;

    /**
     * 是否一键申请页面展示 0:否 1:是
     */
    @TableField("APPLICATION_SHOW")
    private Integer applicationShow;

    @TableField(exist = false)
    private UserVO user;
    /**
     * 关联流程ID
     */
    @TableField("WORK_FLOW_ID")
    private String workFlowId;
    @TableField(exist = false)
    private String subTypeName;
    /**
     * 标签描述
     */@ApiModelProperty(value = "标签描述")
    @TableField("TAG_DESC")
    private String tagDesc;

    @TableField("REGIST_ID")
    private String registId;

    /**
     * 地区
     */
    @TableField("AREA_NAME")
    private String areaName;

    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    /**
     * 大数据组件标志 0:否 1:是
     */
    @TableField("BIG_DATA_COMPONENT_FLAG")
    private Integer bigDataComponentFlag;

    @TableField(exist = false)
    private List<FilesVo> fileList;

    public String getId() {
        return id;
    }

    public List<FilesVo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FilesVo> fileList) {
        this.fileList = fileList;
    }

    public Paas setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getApplicationShow() {
        return applicationShow;
    }

    public void setApplicationShow(Integer applicationShow) {
        this.applicationShow = applicationShow;
    }

    public String getApiAccess() {
        return apiAccess;
    }

    public void setApiAccess(String apiAccess) {
        this.apiAccess = apiAccess;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getName() {
        return name;
    }

    public Paas setName(String name) {
        this.name = name;
        return this;
    }

    public String getServiceGuid() {
        return serviceGuid;
    }

    public void setServiceGuid(String serviceGuid) {
        this.serviceGuid = serviceGuid;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSubType() {
        return subType;
    }

    public Paas setSubType(String subType) {
        this.subType = subType;
        return this;
    }

    public Long getSort() {
        return sort;
    }

    public Paas setSort(Long sort) {
        this.sort = sort;
        return this;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public Paas setVersionCode(String versionCode) {
        this.versionCode = versionCode;
        return this;
    }


    public String getUrl() {
        return url;
    }

    public Paas setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getSubPageUrl() {
        return subPageUrl;
    }

    public void setSubPageUrl(String subPageUrl) {
        this.subPageUrl = subPageUrl;
    }

    public String getImage() {
        return image;
    }

    public Paas setImage(String image) {
        this.image = image;
        return this;
    }

    public String getRealImage() {
        return realImage;
    }

    public void setRealImage(String realImage) {
        this.realImage = realImage;
    }

    public String getDescription() {
        return description;
    }

    public Paas setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Paas setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public Paas setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public Long getStatus() {
        return status;
    }

    public Paas setStatus(Long status) {
        this.status = status;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Paas setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getTop() {
        return top;
    }

    public Paas setTop(String top) {
        this.top = top;
        return this;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }



    public Long getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(Long buildStatus) {
        this.buildStatus = buildStatus;
    }

    public Long getInNum() {
        return inNum;
    }

    public void setInNum(Long inNum) {
        this.inNum = inNum;
    }

    public Long getSubPagePermission() {
        return subPagePermission;
    }

    public void setSubPagePermission(Long subPagePermission) {
        this.subPagePermission = subPagePermission;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getCanApplication() {
        return canApplication;
    }

    public void setCanApplication(String canApplication) {
        this.canApplication = canApplication;
    }

    public String getHasDoc() {
        return hasDoc;
    }

    public void setHasDoc(String hasDoc) {
        this.hasDoc = hasDoc;
    }

    public String getFormNum() {
        return formNum;
    }

    public void setFormNum(String formNum) {
        this.formNum = formNum;
    }


    public String getRegistId() {
        return registId;
    }

    public void setRegistId(String registId) {
        this.registId = registId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public Integer getBigDataComponentFlag() {
        return bigDataComponentFlag;
    }

    public void setBigDataComponentFlag(Integer bigDataComponentFlag) {
        this.bigDataComponentFlag = bigDataComponentFlag;
    }

    
}
