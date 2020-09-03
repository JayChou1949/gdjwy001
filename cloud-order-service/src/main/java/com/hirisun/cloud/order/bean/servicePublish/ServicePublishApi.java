package com.hirisun.cloud.order.bean.servicePublish;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.hirisun.cloud.model.service.ApiAcIpVo;
import com.hirisun.cloud.model.service.ApiOperationVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 服务发布-API
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Data
@TableName("TB_SERVICE_PUBLISH_API")
@ApiModel(value="ServicePublishApi对象", description="服务发布-API")
public class ServicePublishApi implements Serializable {

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

    @ApiModelProperty(value = "访问协议,取值: HTTP/HTTPS/HTTP&HTTPS/WS/WSS")
    @TableField("PROTOCOL")
    private String protocol;

    @ApiModelProperty(value = "根路径")
    @TableField("BASE_PATH")
    private String basePath;

    @ApiModelProperty(value = "安全认证类型")
    @TableField("AUTH_TYPE")
    private String authType;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "流量控制策略名称")
    @TableField("FC_NAME")
    private String fcName;

    @ApiModelProperty(value = "流量控制策略类型,shared:共享额度, exclusive:专用额度")
    @TableField("FC_THRESHOLD_TYPE")
    private String fcThresholdType;

    @ApiModelProperty(value = "流量控制API流量限制次数")
    @TableField("FC_API_THRESHOLD")
    private BigDecimal fcApiThreshold;

    @ApiModelProperty(value = "流量控制流量限制单位,取值: SECOND/MINUTE/HOUR/DAY")
    @TableField("FC_TIME_UNIT")
    private String fcTimeUnit;

    @ApiModelProperty(value = "流量控制应用流量限制次数")
    @TableField("FC_TIME_INTERVAL")
    private BigDecimal fcTimeInterval;

    @ApiModelProperty(value = "备注")
    @TableField("FC_REMARK")
    private String fcRemark;

    @ApiModelProperty(value = "访问控制策略名称")
    @TableField("AC_NAME")
    private String acName;

    @ApiModelProperty(value = "访问控制策略限制类型")
    @TableField("AC_TYPE")
    private String acType;

    @ApiModelProperty(value = "访问控制策略动作，允许：ALLOW  拒绝：DENY")
    @TableField("AC_ACTION")
    private String acAction;

    @ApiModelProperty(value = "是否配置访问策略,1:是,0:否")
    @TableField("IS_AC")
    private String isAc;

    @ApiModelProperty(value = "流量控制API流量限制时间")
    @TableField("FC_API_TIME")
    private BigDecimal fcApiTime;

    @ApiModelProperty(value = "流量控制应用流量限制时间")
    @TableField("FC_APP_TIME")
    private BigDecimal fcAppTime;
    /**
     * ip信息
     */
    @ApiModelProperty(value = "ip信息")
    @TableField(exist = false)
    private List<ApiAcIpVo> ipList;

    /**
     * api操作
     */
    @ApiModelProperty(value = "api操作")
    @TableField(exist = false)
    private List<ApiOperationVo> apiOperationList;

    @Override
    public String toString() {
        return "ServicePublishApi{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", publishId=" + publishId +
        ", name=" + name +
        ", version=" + version +
        ", protocol=" + protocol +
        ", basePath=" + basePath +
        ", authType=" + authType +
        ", remark=" + remark +
        ", fcName=" + fcName +
        ", fcThresholdType=" + fcThresholdType +
        ", fcApiThreshold=" + fcApiThreshold +
        ", fcTimeUnit=" + fcTimeUnit +
        ", fcTimeInterval=" + fcTimeInterval +
        ", fcRemark=" + fcRemark +
        ", acName=" + acName +
        ", acType=" + acType +
        ", acAction=" + acAction +
        ", isAc=" + isAc +
        ", fcApiTime=" + fcApiTime +
        ", fcAppTime=" + fcAppTime +
        "}";
    }
}
