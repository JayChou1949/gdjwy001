package com.hirisun.cloud.order.bean.servicePublish;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 服务发布-api-访问控制IP信息
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@TableName("TB_API_AC_IP")
@ApiModel(value="ApiAcIp对象", description="服务发布-api-访问控制IP信息")
public class ApiAcIp implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("MODIFIED_TIME")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "API ID")
    @TableField("MASTER_ID")
    private String masterId;

    @ApiModelProperty(value = "IP 地址")
    @TableField("IP")
    private String ip;

    @ApiModelProperty(value = "IP 地址范围")
    @TableField("IP_RANGE")
    private String ipRange;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpRange() {
        return ipRange;
    }

    public void setIpRange(String ipRange) {
        this.ipRange = ipRange;
    }

    @Override
    public String toString() {
        return "ApiAcIp{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", masterId=" + masterId +
        ", ip=" + ip +
        ", ipRange=" + ipRange +
        "}";
    }
}
