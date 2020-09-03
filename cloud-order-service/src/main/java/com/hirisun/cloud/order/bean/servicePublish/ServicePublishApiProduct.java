package com.hirisun.cloud.order.bean.servicePublish;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 服务发布-api产品
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@TableName("TB_SERVICE_PUBLISH_API_PRODUCT")
@ApiModel(value="ServicePublishApiProduct对象", description="服务发布-api产品")
public class ServicePublishApiProduct implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFIED_TIME")
    private Date modifiedTime;

    @ApiModelProperty(value = "服务发布ID")
    @TableField("PUBLISH_ID")
    private String publishId;

    @ApiModelProperty(value = "API名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "版本")
    @TableField("VERSION")
    private String version;

    @ApiModelProperty(value = "微网关")
    @TableField("MICROGW")
    private String microgw;

    @ApiModelProperty(value = "微网关域名")
    @TableField("MICROGW_DOMAIN_NAME")
    private String microgwDomainName;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMicrogw() {
        return microgw;
    }

    public void setMicrogw(String microgw) {
        this.microgw = microgw;
    }

    public String getMicrogwDomainName() {
        return microgwDomainName;
    }

    public void setMicrogwDomainName(String microgwDomainName) {
        this.microgwDomainName = microgwDomainName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ServicePublishApiProduct{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", publishId=" + publishId +
        ", name=" + name +
        ", version=" + version +
        ", microgw=" + microgw +
        ", microgwDomainName=" + microgwDomainName +
        ", remark=" + remark +
        "}";
    }
}
