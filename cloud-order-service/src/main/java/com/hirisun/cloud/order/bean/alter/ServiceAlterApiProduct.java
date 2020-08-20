package com.hirisun.cloud.order.bean.alter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@TableName("T_SERVICE_ALTER_API_PRODUCT")
@ApiModel("服务变更-api产品")
public class ServiceAlterApiProduct implements Serializable {

	private static final long serialVersionUID = -3041320407293493763L;

	@TableId(value = "ID", type = IdType.UUID)
    @ApiModelProperty("id")
    private String id;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField("ALTER_ID")
    private String alterId;

    @TableField("NAME")
    @ApiModelProperty("API名称")
    private String name;

    @TableField("VERSION")
    @ApiModelProperty("版本")
    private String version;

    @TableField("MICROGW")
    @ApiModelProperty("微网关")
    private String microgw;

    @TableField("MICROGW_DOMAIN_NAME")
    @ApiModelProperty("微网关域名")
    private String microgwDomainName;

    @TableField("REMARK")
    @ApiModelProperty("备注")
    private String remark;
    
    @TableField(exist = false)
    @ApiModelProperty(value="审核状态 ")
	private String status;
	
    @TableField(exist = false)
	@ApiModelProperty(value="api产品类型(服务类型)")
	private String serviceType;
	
    @TableField(exist = false)
	@ApiModelProperty(value="发布人")
	private String publisher;
	
    @TableField(exist = false)
	@ApiModelProperty(value="发布人时间")
	private String publishDate;

    @TableField(exist = false)
    @ApiModelProperty(value="api list")
    private List<ServiceAlterApi> apiList;
    
    @TableField(exist = false)
    @ApiModelProperty(value="backend list")
    private List<ServiceAlterBackend> backendList;
    
    public String getId() {
        return id;
    }

    public ServiceAlterApiProduct setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ServiceAlterApiProduct setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ServiceAlterApiProduct setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getAlterId() {
		return alterId;
	}

	public void setAlterId(String alterId) {
		this.alterId = alterId;
	}

	public String getName() {
        return name;
    }

    public ServiceAlterApiProduct setName(String name) {
        this.name = name;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ServiceAlterApiProduct setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getMicrogw() {
        return microgw;
    }

    public ServiceAlterApiProduct setMicrogw(String microgw) {
        this.microgw = microgw;
        return this;
    }

    public String getMicrogwDomainName() {
        return microgwDomainName;
    }

    public ServiceAlterApiProduct setMicrogwDomainName(String microgwDomainName) {
        this.microgwDomainName = microgwDomainName;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ServiceAlterApiProduct setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public List<ServiceAlterApi> getApiList() {
		return apiList;
	}

	public void setApiList(List<ServiceAlterApi> apiList) {
		this.apiList = apiList;
	}

	public List<ServiceAlterBackend> getBackendList() {
		return backendList;
	}

	public void setBackendList(List<ServiceAlterBackend> backendList) {
		this.backendList = backendList;
	}

}
