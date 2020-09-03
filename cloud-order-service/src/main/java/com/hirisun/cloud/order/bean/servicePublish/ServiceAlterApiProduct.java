package com.hirisun.cloud.order.bean.servicePublish;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

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
@TableName("TB_SERVICE_ALTER_API_PRODUCT")
@ApiModel(value="ServiceAlterApiProduct对象", description="")
public class ServiceAlterApiProduct implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("ID")
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

    @TableField("MICROGW")
    private String microgw;

    @TableField("MICROGW_DOMAIN_NAME")
    private String microgwDomainName;

    @TableField("REMARK")
    private String remark;

    @TableField("STATUS")
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


    @Override
    public String toString() {
        return "ServiceAlterApiProduct{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", alterId=" + alterId +
        ", name=" + name +
        ", version=" + version +
        ", microgw=" + microgw +
        ", microgwDomainName=" + microgwDomainName +
        ", remark=" + remark +
        ", status=" + status +
        "}";
    }
}
