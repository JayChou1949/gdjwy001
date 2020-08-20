package com.hirisun.cloud.order.bean.shopping;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hirisun.cloud.model.file.FilesVo;

/**
 * 购物车
 * <p>
 * 
 * </p>
 *
 * @author yyc
 * @since 2020-04-16
 */
@TableName("TB_SHOPPING_CART")
public class ShoppingCart<S> extends Model<ShoppingCart> {

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
    private List<S> serverList;


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


    public List<S> getServerList() {
        return serverList;
    }

    public void setServerList(List<S> serverList) {
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id='" + id + '\'' +
                ", serviceTypeId='" + serviceTypeId + '\'' +
                ", serviceTypeName='" + serviceTypeName + '\'' +
                ", resourceType=" + resourceType +
                ", status=" + status +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", creatorIdCard='" + creatorIdCard + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", formNum='" + formNum + '\'' +
                ", serverList=" + serverList +
                '}';
    }
}
