package com.hirisun.cloud.order.bean.instance;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

@TableName("TB_WFM_INSTANCE")
public class Instance extends Model<Instance> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.INPUT)
    private String id;

        /**
     * 流程ID
     */
         @TableField("WORKFLOWID")
    private String workflowid;

        /**
     * 创建时间
     */
         @TableField("CREATETIME")
    private Date createtime;

        /**
     * 创建人
     */
         @TableField("CREATEPERSON")
    private String createperson;

        /**
     * 完成时间
     */
         @TableField("COMPLETETIME")
    private Date completetime;

        /**
     * 实例状态
     */
         @TableField("INSTANCESTATUS")
    private String instancestatus;

        /**
     * 流程版本号
     */
         @TableField("WORKFLOWVERSION")
    private Integer workflowversion;

        /**
     * 业务ID
     */
         @TableField("BUSINESSID")
    private String businessid;

        /**
     * 优先级
     */
         @TableField("PRIORITY")
    private Integer priority;


    public String getId() {
        return id;
    }

    public Instance setId(String id) {
        this.id = id;
        return this;
    }

    public String getWorkflowid() {
        return workflowid;
    }

    public Instance setWorkflowid(String workflowid) {
        this.workflowid = workflowid;
        return this;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public Instance setCreatetime(Date createtime) {
        this.createtime = createtime;
        return this;
    }

    public String getCreateperson() {
        return createperson;
    }

    public Instance setCreateperson(String createperson) {
        this.createperson = createperson;
        return this;
    }

    public Date getCompletetime() {
        return completetime;
    }

    public Instance setCompletetime(Date completetime) {
        this.completetime = completetime;
        return this;
    }

    public String getInstancestatus() {
        return instancestatus;
    }

    public Instance setInstancestatus(String instancestatus) {
        this.instancestatus = instancestatus;
        return this;
    }

    public Integer getWorkflowversion() {
        return workflowversion;
    }

    public Instance setWorkflowversion(Integer workflowversion) {
        this.workflowversion = workflowversion;
        return this;
    }

    public String getBusinessid() {
        return businessid;
    }

    public Instance setBusinessid(String businessid) {
        this.businessid = businessid;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public Instance setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Instance{" +
        "id=" + id +
        ", workflowid=" + workflowid +
        ", createtime=" + createtime +
        ", createperson=" + createperson +
        ", completetime=" + completetime +
        ", instancestatus=" + instancestatus +
        ", workflowversion=" + workflowversion +
        ", businessid=" + businessid +
        ", priority=" + priority +
        "}";
    }
}
