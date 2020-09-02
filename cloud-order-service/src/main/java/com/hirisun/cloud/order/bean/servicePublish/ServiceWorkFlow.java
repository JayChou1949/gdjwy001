package com.hirisun.cloud.order.bean.servicePublish;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@TableName("TB_SERVICE_WORK_FLOW")
@ApiModel(value="ServiceWorkFlow对象", description="")
public class ServiceWorkFlow implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "服务ID")
    @TableField("SERVICE_ID")
    private String serviceId;

    @ApiModelProperty(value = "流程ID")
    @TableField("WORK_FLOW_ID")
    private String workFlowId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    @Override
    public String toString() {
        return "ServiceWorkFlow{" +
        "id=" + id +
        ", serviceId=" + serviceId +
        ", workFlowId=" + workFlowId +
        "}";
    }
}
