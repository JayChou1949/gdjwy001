package com.hirisun.cloud.order.bean.epidemic;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * SaaS申请原始信息扩展表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-03
 */
@Data
@TableName("TB_EPIDEMIC_APPLICATION_EXT")
@ApiModel(value="EpidemicApplicationExt对象", description="SaaS申请原始信息扩展表")
public class EpidemicApplicationExt implements Serializable {

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

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("MODIFIED_TIME")
    private LocalDateTime modifiedTime;

    @Override
    public String toString() {
        return "EpidemicApplicationExt{" +
        "id=" + id +
        ", serviceName=" + serviceName +
        ", serviceId=" + serviceId +
        ", masterId=" + masterId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
