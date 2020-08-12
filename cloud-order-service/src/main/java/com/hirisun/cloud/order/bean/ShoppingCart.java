package com.hirisun.cloud.order.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hirisun.cloud.model.file.FileSystemVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-08
 */
@TableName("T_SHOPPING_CART")
@ApiModel(value="ShoppingCart对象", description="")
public class ShoppingCart<S> implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 购物车状态 0 待提交
     */
    public static final Integer STATUS_WAIT_SUBMIT = 0;

    /**
     * 购物车状态 1 草稿
     */
    public static final Integer STATUS_DRAFT= 1;


    @ApiModelProperty(value = "UUID")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "资源ID")
    @TableField("SERVICE_TYPE_ID")
    private String serviceTypeId;

    @ApiModelProperty(value = "资源名")
    @TableField("SERVICE_TYPE_NAME")
    private String serviceTypeName;

    @ApiModelProperty(value = "资源类别(1:IAAS 2:DAAS 3:PAAS 4:SAAS应用 5:SAAS服务)")
    @TableField("RESOURCE_TYPE")
    private Integer resourceType;

    @ApiModelProperty(value = "状态 0 :草稿 1:待提交")
    @TableField("STATUS")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date modifiedTime;

    @ApiModelProperty(value = "创建者身份证号")
    @TableField("CREATOR_ID_CARD")
    private String creatorIdCard;

    @ApiModelProperty(value = "创建者名字")
    @TableField("CREATOR_NAME")
    private String creatorName;

    @ApiModelProperty(value = "表单编码，与流程配置相关")
    @TableField("FORM_NUM")
    private String formNum;

    @ApiModelProperty(value = "申请说明")
    @TableField("EXPLANATION")
    private String explanation;

    @ApiModelProperty(value = "服务列表")
    @TableField(exist = false)
    private List<S> serverList;


    @ApiModelProperty(value = "文件列表")
    @TableField(exist = false)
    private List<FileSystemVO> fileList;

    @ApiModelProperty(value = "组件总数")
    @TableField(exist = false)
    private Integer totalNum;

    public List<S> getServerList() {
        return serverList;
    }

    public void setServerList(List<S> serverList) {
        this.serverList = serverList;
    }

    public List<FileSystemVO> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileSystemVO> fileList) {
        this.fileList = fileList;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getCreatorIdCard() {
        return creatorIdCard;
    }

    public void setCreatorIdCard(String creatorIdCard) {
        this.creatorIdCard = creatorIdCard;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getFormNum() {
        return formNum;
    }

    public void setFormNum(String formNum) {
        this.formNum = formNum;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
        "id=" + id +
        ", serviceTypeId=" + serviceTypeId +
        ", serviceTypeName=" + serviceTypeName +
        ", resourceType=" + resourceType +
        ", status=" + status +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", creatorIdCard=" + creatorIdCard +
        ", creatorName=" + creatorName +
        ", formNum=" + formNum +
        ", explanation=" + explanation +
        "}";
    }
}
