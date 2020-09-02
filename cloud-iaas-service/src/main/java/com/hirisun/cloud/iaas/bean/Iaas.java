package com.hirisun.cloud.iaas.bean;

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


/**
 * iaas 流程配置
 * @author cjh
 *
 */
@TableName("TB_IAAS")
public class Iaas implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId(value = "ID", type = IdType.UUID)
    private String id;

    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称")
    @TableField("NAME")
    private String name;
    @TableField(exist = false)
    private String subTypeName;
    /**
     * 子类
     */
    @ApiModelProperty(value = "子类")
    @TableField("SUB_TYPE")
    private String subType;

    /**
     * 排序
     */
    @TableField(value = "SORT", fill = FieldFill.INSERT)
    private Long sort;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    @TableField("VERSION_CODE")
    private String versionCode;

    /**
     * 外链
     */
    @ApiModelProperty(value = "外链")
    @TableField("URL")
    private String url;

    /**
     * 展示图
     */
    @ApiModelProperty(value = "展示图")
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
    @ApiModelProperty(value = "描述")
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 标签描述
     */
    @ApiModelProperty(value = "标签描述")
    @TableField("TAG_DESC")
    private String tagDesc;

    /**
     * 接入系统数量
     */
    @ApiModelProperty(value = "接入系统数量")
    @TableField("AP_NUM")
    private String apNum;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 状态,  1: 待上线 2: 上线 4:删除
     */
    @TableField("STATUS")
    private Long status;

    /**
     * 创建人
     */
    @TableField("CREATOR")
    private String creator;

    /**
     * 是否大图 0:否 1:是
     */
    @TableField("TOP")
    private Integer top;

    /**
     * 二级页面 URL
     */
    @ApiModelProperty(value = "二级页面")
    @TableField("SUB_PAGE_URL")
    private String subPageUrl;

    /**
     * 0.已完成 1.建设中
     */
    @ApiModelProperty(value = "0.已完成 1.建设中")
    @TableField("BUILD_STATUS")
    private Integer buildStatus;

    /**
     * 二级页面查看权限 0.完全开放 1登录可看
     */
    @ApiModelProperty(value = "二级页面查看权限 0.完全开放 1登录可看")
    @TableField("SUB_PAGE_PERMISSION")
    private Integer subPagePermission;

    /**
     * 是否首页展示 0:否 1:是
     */
    @TableField("HOME")
    private String home;

    /**
     * 是否能申请 0:否 1:是
     */
    @ApiModelProperty(value = "是否能申请0:否 1:是")
    @TableField("CAN_APPLICATION")
    private String canApplication;

    /**
     * 是否有开发文档 0:否 1:是
     */
    @TableField("HAS_DOC")
    private String hasDoc;

    /**
     * 申请表单编码
     */
    @ApiModelProperty(value = "申请表单编码")
    @TableField("FORM_NUM")
    private String formNum;

    /**
     * 服务简称
     */
    @ApiModelProperty(value = "服务简称")
    @TableField("SHORT_NAME")
    private String shortName;

    /**
     * 申请说明
     */
    @ApiModelProperty(value = "申请说明")
    @TableField("INSTRUCTIONS")
    private String instructions;

    @TableField("WORK_FLOW_ID")
    private String workFlowId;

    @TableField(exist = false)
    private UserVO user;

    @TableField(exist = false)
    private List<FilesVo> fileList;

    /**
     * 变更表单编码
     */
    @TableField("FORM_NUM_MODIFY")
    private String formNumModify;

    public String getFormNumModify() {
        return formNumModify;
    }

    public void setFormNumModify(String formNumModify) {
        this.formNumModify = formNumModify;
    }

    public List<FilesVo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FilesVo> fileList) {
        this.fileList = fileList;
    }

    public String getId() {
        return id;
    }

    public Iaas setId(String id) {
        this.id = id;
        return this;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public Iaas setName(String name) {
        this.name = name;
        return this;
    }

    public String getSubType() {
        return subType;
    }

    public Iaas setSubType(String subType) {
        this.subType = subType;
        return this;
    }

    public Long getSort() {
        return sort;
    }

    public Iaas setSort(Long sort) {
        this.sort = sort;
        return this;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public Iaas setVersionCode(String versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Iaas setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Iaas setImage(String image) {
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

    public Iaas setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public Iaas setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
        return this;
    }

    public String getApNum() {
        return apNum;
    }

    public Iaas setApNum(String apNum) {
        this.apNum = apNum;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Iaas setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public Iaas setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public Long getStatus() {
        return status;
    }

    public Iaas setStatus(Long status) {
        this.status = status;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Iaas setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Integer getTop() {
        return top;
    }

    public Iaas setTop(Integer top) {
        this.top = top;
        return this;
    }

    public String getSubPageUrl() {
        return subPageUrl;
    }

    public Iaas setSubPageUrl(String subPageUrl) {
        this.subPageUrl = subPageUrl;
        return this;
    }

    public Integer getBuildStatus() {
        return buildStatus;
    }

    public Iaas setBuildStatus(Integer buildStatus) {
        this.buildStatus = buildStatus;
        return this;
    }

    public Integer getSubPagePermission() {
        return subPagePermission;
    }

    public Iaas setSubPagePermission(Integer subPagePermission) {
        this.subPagePermission = subPagePermission;
        return this;
    }

    public String getHome() {
        return home;
    }

    public Iaas setHome(String home) {
        this.home = home;
        return this;
    }

    public String getCanApplication() {
        return canApplication;
    }

    public Iaas setCanApplication(String canApplication) {
        this.canApplication = canApplication;
        return this;
    }

    public String getHasDoc() {
        return hasDoc;
    }

    public Iaas setHasDoc(String hasDoc) {
        this.hasDoc = hasDoc;
        return this;
    }

    public String getFormNum() {
        return formNum;
    }

    public Iaas setFormNum(String formNum) {
        this.formNum = formNum;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public Iaas setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public Iaas setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public Iaas setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
        return this;
    }

	@Override
	public String toString() {
		return "IaasConfig [id=" + id + ", name=" + name + ", subTypeName=" + subTypeName + ", subType=" + subType
				+ ", sort=" + sort + ", versionCode=" + versionCode + ", url=" + url + ", image=" + image
				+ ", realImage=" + realImage + ", description=" + description + ", tagDesc=" + tagDesc + ", apNum="
				+ apNum + ", createTime=" + createTime + ", modifiedTime=" + modifiedTime + ", status=" + status
				+ ", creator=" + creator + ", top=" + top + ", subPageUrl=" + subPageUrl + ", buildStatus="
				+ buildStatus + ", subPagePermission=" + subPagePermission + ", home=" + home + ", canApplication="
				+ canApplication + ", hasDoc=" + hasDoc + ", formNum=" + formNum + ", shortName=" + shortName
				+ ", instructions=" + instructions + ", workFlowId=" + workFlowId + ", user=" + user + ", fileList="
				+ fileList + ", formNumModify=" + formNumModify + "]";
	}

    
}
