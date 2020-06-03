package com.upd.hwcloud.bean.entity.wfm;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
 */
@TableName("TB_WFM_WORKFLOW")
public class Workflow extends Model<Workflow> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.INPUT)
    private String id;
    @TableField("DEFAULT_PROCESS")
    private String defaultProcess;
        /**
     * 流程业务名称
     */
         @TableField("WORKFLOWNAME")
    private String workflowname;

        /**
     * 流程业务类型
     */
         @TableField("WORKFLOWTYPE")
    private String workflowtype;

        /**
     * 访问连接
     */
         @TableField("ACESSURL")
    private String acessurl;

    @TableField("IMAGEURL")
    private String imageurl;

        /**
     * 说明
     */
         @TableField("ROUTERNAME")
    private String routername;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;
    //删除标记 0=正常，1=删除
    @TableField("FLOW_STATUS")
    private int flowStatus;
        /**
     * 创建人
     */
         @TableField("CREATOR")
    private String creator;

    /**
     * 流程所属地区
     */
    @TableField("AREA")
    private String area;

    /**
     * 版本
     */
    @TableField("VERSION")
    private Integer version;

    /**
     * 警种所属警种
     */
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    /**
     * 业务办理
     */
    @TableField(exist = false)
    private Workflowmodel carry;
    /**
     * 大数据办审批(原:科信审批)
     */
    @TableField(exist = false)
    private Workflowmodel approve;
    /**
     * 服务台复核
     */
    @TableField(exist = false)
    private Workflowmodel recheck;

    @TableField(exist = false)
    private Workflowmodel recovered;

    /**
     * 一级管理员审批
     */
    @TableField(exist = false)
    private Workflowmodel lvl1Manager;

    /**
     * 大数据办审批
     */
    @TableField(exist = false)
    private Workflowmodel bigData;

    /**
     * 服务上线
     */
    @TableField(exist = false)
    private Workflowmodel online;

    /**
     * 是否配置(用于Iaas PaaS服务流程配置) boolean基础类型默认为false
     */
    @TableField(exist = false)
    private boolean serviceFlowCheck;

    public String getId() {
        return id;
    }

    public Workflow setId(String id) {
        this.id = id;
        return this;
    }

    public int getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(int flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getWorkflowname() {
        return workflowname;
    }

    public Workflow setWorkflowname(String workflowname) {
        this.workflowname = workflowname;
        return this;
    }

    public boolean isServiceFlowCheck() {
        return serviceFlowCheck;
    }

    public void setServiceFlowCheck(boolean serviceFlowCheck) {
        this.serviceFlowCheck = serviceFlowCheck;
    }

    public String getDefaultProcess() {
        return defaultProcess;
    }

    public void setDefaultProcess(String defaultProcess) {
        this.defaultProcess = defaultProcess;
    }

    public String getWorkflowtype() {
        return workflowtype;
    }

    public Workflow setWorkflowtype(String workflowtype) {
        this.workflowtype = workflowtype;
        return this;
    }

    public Workflowmodel getRecovered() {
        return recovered;
    }

    public void setRecovered(Workflowmodel recovered) {
        this.recovered = recovered;
    }

    public String getAcessurl() {
        return acessurl;
    }

    public Workflow setAcessurl(String acessurl) {
        this.acessurl = acessurl;
        return this;
    }

    public String getImageurl() {
        return imageurl;
    }

    public Workflow setImageurl(String imageurl) {
        this.imageurl = imageurl;
        return this;
    }

    public String getRoutername() {
        return routername;
    }

    public Workflow setRoutername(String routername) {
        this.routername = routername;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Workflow setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public Workflow setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Workflow setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Workflowmodel getApprove() {
        return approve;
    }

    public void setApprove(Workflowmodel approve) {
        this.approve = approve;
    }

    public Workflowmodel getRecheck() {
        return recheck;
    }

    public void setRecheck(Workflowmodel recheck) {
        this.recheck = recheck;
    }

    public Workflowmodel getLvl1Manager() {
        return lvl1Manager;
    }

    public void setLvl1Manager(Workflowmodel lvl1Manager) {
        this.lvl1Manager = lvl1Manager;
    }

    public Workflowmodel getBigData() {
        return bigData;
    }

    public void setBigData(Workflowmodel bigData) {
        this.bigData = bigData;
    }

    public Workflowmodel getOnline() {
        return online;
    }

    public void setOnline(Workflowmodel online) {
        this.online = online;
    }

    public String getArea() {
        return area;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    @Override
    public String toString() {
        return "Workflow{" +
        "id=" + id +
        ", workflowname=" + workflowname +
        ", workflowtype=" + workflowtype +
        ", acessurl=" + acessurl +
        ", imageurl=" + imageurl +
        ", routername=" + routername +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", creator=" + creator +
        "}";
    }

    public Workflowmodel getCarry() {
        return carry;
    }

    public void setCarry(Workflowmodel carry) {
        this.carry = carry;
    }
}
