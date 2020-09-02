package com.hirisun.cloud.paas.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hirisun.cloud.model.app.vo.AppSceneVo;
import com.hirisun.cloud.model.app.vo.FunChaVo;
import com.hirisun.cloud.model.app.vo.FunDetailVo;
import com.hirisun.cloud.model.file.FilesVo;

import io.swagger.annotations.ApiModelProperty;

@TableName("TB_PAAS_SUBPAGE")
public class PaasSubpage implements Serializable{
	
	private static final long serialVersionUID = 782323748379924226L;

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
    private List<FunChaVo> funChas;
    @ApiModelProperty(value = "功能详情")
    @TableField(exist = false)
    private List<FunDetailVo> funDetails;
    @ApiModelProperty(value = "应用场景")
    @TableField(exist = false)
    private List<AppSceneVo> appScenes;

    @ApiModelProperty(value = "相关文档")
    @TableField(exist = false)
    private List<FilesVo> filesList;

    public String getId() {
        return id;
    }

    public List<FunChaVo> getFunChas() {
        return funChas;
    }

    public String getBuildDesc() {
        return buildDesc;
    }

    public void setBuildDesc(String buildDesc) {
        this.buildDesc = buildDesc;
    }

    public void setFunChas(List<FunChaVo> funChas) {
        this.funChas = funChas;
    }

    public List<FunDetailVo> getFunDetails() {
        return funDetails;
    }

    public void setFunDetails(List<FunDetailVo> funDetails) {
        this.funDetails = funDetails;
    }

    public List<AppSceneVo> getAppScenes() {
        return appScenes;
    }

    public void setAppScenes(List<AppSceneVo> appScenes) {
        this.appScenes = appScenes;
    }

    public List<FilesVo> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<FilesVo> filesList) {
        this.filesList = filesList;
    }

    public String getMasterId() {
        return masterId;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public Date getModifiedTime() {
        return modifiedTime;
    }

    public String getJsUnit() {
        return jsUnit;
    }

    public String getJsPrincipal() {
        return jsPrincipal;
    }


    public String getJsPrincipalPhone() {
        return jsPrincipalPhone;
    }

    public String getCjUnit() {
        return cjUnit;
    }

    public String getCjPrincipal() {
        return cjPrincipal;
    }


    public String getCjPrincipalPhone() {
        return cjPrincipalPhone;
    }

	public void setId(String id) {
		this.id = id;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public void setJsUnit(String jsUnit) {
		this.jsUnit = jsUnit;
	}

	public void setJsPrincipal(String jsPrincipal) {
		this.jsPrincipal = jsPrincipal;
	}

	public void setJsPrincipalPhone(String jsPrincipalPhone) {
		this.jsPrincipalPhone = jsPrincipalPhone;
	}

	public void setCjUnit(String cjUnit) {
		this.cjUnit = cjUnit;
	}

	public void setCjPrincipal(String cjPrincipal) {
		this.cjPrincipal = cjPrincipal;
	}

	public void setCjPrincipalPhone(String cjPrincipalPhone) {
		this.cjPrincipalPhone = cjPrincipalPhone;
	}

}
