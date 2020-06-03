package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuc
 * @since 2020-03-03
 */
@TableName("TB_SERVICE_WORK_FLOW")
public class ServiceWorkFlow extends Model<ServiceWorkFlow> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 服务ID
     */
         @TableField("SERVICE_ID")
    private String serviceId;

        /**
     * 流程ID
     */
         @TableField("WORK_FLOW_ID")
    private String workFlowId;


    public String getId() {
        return id;
    }

    public ServiceWorkFlow setId(String id) {
        this.id = id;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public ServiceWorkFlow setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public ServiceWorkFlow setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
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
