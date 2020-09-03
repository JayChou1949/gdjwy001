package com.hirisun.cloud.saas.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * SaaS申请原始信息扩展表——可视化建模平台资源信息
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-24
 */
@Data
@TableName("T_SAAS_APPLY_EXT_RESOURCE")
@ApiModel(value="SaasApplyExtResource对象", description="SaaS申请原始信息扩展表——可视化建模平台资源信息")
public class SaasApplyExtResource implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "申请信息 id")
    @TableField("MASTER_ID")
    private String masterId;

    @ApiModelProperty(value = "目录中文名称")
    @TableField("FOLDER_NAME")
    private String folderName;

    @ApiModelProperty(value = "数据资源中文名称")
    @TableField("RESOURCE_NAME")
    private String resourceName;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @ApiModelProperty(value = "数据资源id")
    @TableField("RESOURCE_ID")
    private String resourceId;


    @Override
    public String toString() {
        return "SaasApplyExtResource{" +
        "id=" + id +
        ", masterId=" + masterId +
        ", folderName=" + folderName +
        ", resourceName=" + resourceName +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", resourceId=" + resourceId +
        "}";
    }
}
