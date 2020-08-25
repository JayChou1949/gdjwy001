package com.hirisun.cloud.saas.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * SaaS申请原始信息扩展表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-24
 */
@Data
@TableName("T_SAAS_APPLY_EXT")
@ApiModel(value="SaasApplyExt对象", description="SaaS申请原始信息扩展表")
public class SaasApplyExt implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "服务名称")
    @TableField("SERVICE_NAME")
    private String serviceName;

    @ApiModelProperty(value = "服务ID")
    @TableField("SERVICE_ID")
    private String serviceId;

    @ApiModelProperty(value = "申请信息 id")
    @TableField("MASTER_ID")
    private String masterId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 访问总数
     */
    @TableField(value = "VIEW_COUNT",exist = false)
    private Integer viewCount;

    /**
     * 应用开通次数
     */
    @TableField(value = "OPENING_NUMBER",exist = false)
    private Integer openingNumber;

    /**
     * 应用开通时间
     */
    @TableField(value = "OPENING_TIME",exist = false)
    private Date openingTime;

    /**
     * 权限被回收次数
     */
    @TableField(value = "RECYCLING_NUMBER",exist = false)
    private Integer recyclingNumber;

    /**
     * 最后回收时间
     */
    @TableField(value = "RECYCLING_TIME",exist = false)
    private Date recyclingTime;



    @Override
    public String toString() {
        return "SaasApplyExt{" +
        "id=" + id +
        ", serviceName=" + serviceName +
        ", serviceId=" + serviceId +
        ", masterId=" + masterId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
