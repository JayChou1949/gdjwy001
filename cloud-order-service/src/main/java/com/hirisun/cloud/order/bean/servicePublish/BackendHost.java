package com.hirisun.cloud.order.bean.servicePublish;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 后端服务地址
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Data
@TableName("TB_BACKEND_HOST")
@ApiModel(value="BackendHost对象", description="后端服务地址")
public class BackendHost implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFIED_TIME")
    private Date modifiedTime;

    @ApiModelProperty(value = "后端服务ID")
    @TableField("MASTER_ID")
    private String masterId;

    @TableField("HOST_VALUE")
    private String hostValue;

    @ApiModelProperty(value = "权重")
    @TableField("WEIGHT")
    private BigDecimal weight;





    @Override
    public String toString() {
        return "BackendHost{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", masterId=" + masterId +
        ", hostValue=" + hostValue +
        ", weight=" + weight +
        "}";
    }
}
