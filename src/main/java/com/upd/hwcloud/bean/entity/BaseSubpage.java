package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 二级页面配置
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */

public class BaseSubpage<T extends BaseSubpage> extends Model<T> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 服务id
     */
         @TableField("MASTER_ID")
    private String masterId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 建设单位
     */ @ApiModelProperty(value = "建设单位")
         @TableField("JS_UNIT")
    private String jsUnit;
    /**
     * 建设进展说明
     */ @ApiModelProperty(value = "建设进展说明")
    @TableField("BUILD_DESC")
    private String buildDesc;
        /**
     * 建设联系人
     */ @ApiModelProperty(value = "建设联系人")
         @TableField("JS_PRINCIPAL")
    private String jsPrincipal;

        /**
     * 建设联系人电话
     */ @ApiModelProperty(value = "建设联系人电话")
         @TableField("JS_PRINCIPAL_PHONE")
    private String jsPrincipalPhone;

        /**
     * 承建单位
     */ @ApiModelProperty(value = "承建单位")
         @TableField("CJ_UNIT")
    private String cjUnit;

        /**
     * 承建联系人
     */ @ApiModelProperty(value = "承建联系人")
         @TableField("CJ_PRINCIPAL")
    private String cjPrincipal;

        /**
     * 承建联系人电话
     */ @ApiModelProperty(value = "承建联系人电话")
         @TableField("CJ_PRINCIPAL_PHONE")
    private String cjPrincipalPhone;
    @ApiModelProperty(value = "功能特点")
    @TableField(exist = false)
    private List<IaasFunCha> funChas;
    @ApiModelProperty(value = "功能详情")
    @TableField(exist = false)
    private List<IaasFunDetail> funDetails;
    @ApiModelProperty(value = "应用场景")
    @TableField(exist = false)
    private List<IaasAppScene> appScenes;

    @ApiModelProperty(value = "相关文档")
    @TableField(exist = false)
    private List<Files> filesList;

    public String getId() {
        return id;
    }

    public BaseSubpage setId(String id) {
        this.id = id;
        return this;
    }

    public List<IaasFunCha> getFunChas() {
        return funChas;
    }

    public String getBuildDesc() {
        return buildDesc;
    }

    public void setBuildDesc(String buildDesc) {
        this.buildDesc = buildDesc;
    }

    public void setFunChas(List<IaasFunCha> funChas) {
        this.funChas = funChas;
    }

    public List<IaasFunDetail> getFunDetails() {
        return funDetails;
    }

    public void setFunDetails(List<IaasFunDetail> funDetails) {
        this.funDetails = funDetails;
    }

    public List<IaasAppScene> getAppScenes() {
        return appScenes;
    }

    public void setAppScenes(List<IaasAppScene> appScenes) {
        this.appScenes = appScenes;
    }

    public List<Files> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<Files> filesList) {
        this.filesList = filesList;
    }

    public String getMasterId() {
        return masterId;
    }

    public BaseSubpage setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public BaseSubpage setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public BaseSubpage setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getJsUnit() {
        return jsUnit;
    }

    public BaseSubpage setJsUnit(String jsUnit) {
        this.jsUnit = jsUnit;
        return this;
    }

    public String getJsPrincipal() {
        return jsPrincipal;
    }

    public BaseSubpage setJsPrincipal(String jsPrincipal) {
        this.jsPrincipal = jsPrincipal;
        return this;
    }

    public String getJsPrincipalPhone() {
        return jsPrincipalPhone;
    }

    public BaseSubpage setJsPrincipalPhone(String jsPrincipalPhone) {
        this.jsPrincipalPhone = jsPrincipalPhone;
        return this;
    }

    public String getCjUnit() {
        return cjUnit;
    }

    public BaseSubpage setCjUnit(String cjUnit) {
        this.cjUnit = cjUnit;
        return this;
    }

    public String getCjPrincipal() {
        return cjPrincipal;
    }

    public BaseSubpage setCjPrincipal(String cjPrincipal) {
        this.cjPrincipal = cjPrincipal;
        return this;
    }

    public String getCjPrincipalPhone() {
        return cjPrincipalPhone;
    }

    public BaseSubpage setCjPrincipalPhone(String cjPrincipalPhone) {
        this.cjPrincipalPhone = cjPrincipalPhone;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasSubpage{" +
        "id=" + id +
        ", masterId=" + masterId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", jsUnit=" + jsUnit +
        ", jsPrincipal=" + jsPrincipal +
        ", jsPrincipalPhone=" + jsPrincipalPhone +
        ", cjUnit=" + cjUnit +
        ", cjPrincipal=" + cjPrincipal +
        ", cjPrincipalPhone=" + cjPrincipalPhone +
        "}";
    }
}
