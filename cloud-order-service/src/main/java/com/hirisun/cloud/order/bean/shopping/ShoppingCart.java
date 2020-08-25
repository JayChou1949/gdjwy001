package com.hirisun.cloud.order.bean.shopping;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hirisun.cloud.model.file.FilesVo;

/**
 * 购物车
 */
@TableName("TB_SHOPPING_CART")
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1L;

		/**
     * UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 资源ID
     */
         @TableField("SERVICE_TYPE_ID")
    private String serviceTypeId;

        /**
     * 资源名
     */
         @TableField("SERVICE_TYPE_NAME")
    private String serviceTypeName;

        /**
     * 资源类别
     */
         @TableField("RESOURCE_TYPE")
    private Long resourceType;

        /**
     * 状态 0 :草稿 1:待提交
     */
         @TableField("STATUS")
    private Integer status;

    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 购物车创建人身份证号
     */
    @TableField("CREATOR_ID_CARD")
    private String creatorIdCard;

    /**
     * 购物车创建人名
     */
    @TableField("CREATOR_NAME")
    private String creatorName;

    @TableField("FORM_NUM")
    private String formNum;

    @TableField(exist = false)
    private List<Map<String,Object>> serverList;


    @TableField(exist = false)
    private List<FilesVo> fileList;

    /**
     * 组件总数
     */
    @TableField(exist = false)
    private Integer totalNum;

    /**
     * 申请说明
     */
    @TableField("EXPLANATION")
    private String explanation;

    /**
     * DaaS api_guid
     * SaaS  id
     */
    @TableField("DS_ID")
    private String dsId;


    /**
     * DaaS、SaaS服务名
     */
    @TableField("DS_NAME")
    private String dsName;


    public String getId() {
        return id;
    }

    public ShoppingCart setId(String id) {
        this.id = id;
        return this;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public ShoppingCart setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
        return this;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public ShoppingCart setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
        return this;
    }

    public Long getResourceType() {
        return resourceType;
    }

    public void setResourceType(Long resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getStatus() {
        return status;
    }

    public ShoppingCart setStatus(Integer status) {
        this.status = status;
        return this;
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


    public List<Map<String,Object>> getServerList() {
        return serverList;
    }

    public void setServerList(List<Map<String,Object>> serverList) {
        this.serverList = serverList;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<FilesVo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FilesVo> fileList) {
        this.fileList = fileList;
    }

    public String getDsId() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId = dsId;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

}
