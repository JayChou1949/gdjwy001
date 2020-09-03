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
 * 
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Data
@TableName("TB_SERVICE_ALTER_API")
@ApiModel(value="ServiceAlterApi对象", description="")
public class ServiceAlterApi implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFIED_TIME")
    private Date modifiedTime;

    @TableField("ALTER_ID")
    private String alterId;

    @TableField("NAME")
    private String name;

    @TableField("VERSION")
    private String version;

    @TableField("PROTOCOL")
    private String protocol;

    @TableField("BASE_PATH")
    private String basePath;

    @TableField("AUTH_TYPE")
    private String authType;

    @TableField("REMARK")
    private String remark;

    @TableField("FC_NAME")
    private String fcName;

    @TableField("FC_THRESHOLD_TYPE")
    private String fcThresholdType;

    @TableField("FC_API_THRESHOLD")
    private BigDecimal fcApiThreshold;

    @TableField("FC_TIME_UNIT")
    private String fcTimeUnit;

    @TableField("FC_TIME_INTERVAL")
    private BigDecimal fcTimeInterval;

    @TableField("FC_REMARK")
    private String fcRemark;

    @TableField("AC_NAME")
    private String acName;

    @TableField("AC_TYPE")
    private String acType;

    @TableField("AC_ACTION")
    private String acAction;

    @TableField("IS_AC")
    private String isAc;

    @TableField("FC_API_TIME")
    private BigDecimal fcApiTime;

    @TableField("FC_APP_TIME")
    private BigDecimal fcAppTime;

    /**
     * ip信息
     */
    @TableField(exist = false)
    private List<ApiAcIpVo> ipList;

    /**
     * api操作
     */
    @TableField(exist = false)
    @ApiModelProperty("api操作")
    private List<ApiOperationVo> apiOperationList;


    @Override
    public String toString() {
        return "ServiceAlterApi{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", alterId=" + alterId +
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
