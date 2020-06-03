package com.upd.hwcloud.bean.entity.wfm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("TB_WFM_ACTIVITY")
public class Activity extends Model<Activity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.INPUT)
    private String id;

        /**
     * 实例id
     */
         @TableField("INSTANCEID")
    private String instanceid;

        /**
     * 流程定义环节id
     */
         @TableField("MODELID")
    private String modelid;

        /**
     * 创建时间
     */
         @TableField("CREATETIME")
    private Date createtime;

        /**
     * 创建人id
     */
         @TableField("CREATEPERSONID")
    private String createpersonid;

        /**
     * 接收时间
     */
         @TableField("RECIVETIME")
    private Date recivetime;

        /**
     * 处理时间
     */
         @TableField("HANDLETIME")
    private Date handletime;

        /**
     * 处理人
     */
         @TableField("HANDLEPERSONIDS")
    private String handlepersonids;

        /**
     *  0:是  1:不是
     */
         @TableField("ISSTART")
    private Integer isstart;

        /**
     * 直接上级环节
     */
         @TableField("PREVIOUSACTIVITYIDS")
    private String previousactivityids;

        /**
     * 环节状态
     */
         @TableField("ACTIVITYSTATUS")
    private String activitystatus;

        /**
     * 处理部门
     */
         @TableField("HANDLEDEPARTMENT")
    private String handledepartment;

    @TableField("ACTIVITYTYPE")
    private String activitytype;

    @TableField("ISRECEVE")
    private String isreceve;


    public String getId() {
        return id;
    }

    public Activity setId(String id) {
        this.id = id;
        return this;
    }

    public String getInstanceid() {
        return instanceid;
    }

    public Activity setInstanceid(String instanceid) {
        this.instanceid = instanceid;
        return this;
    }

    public String getModelid() {
        return modelid;
    }

    public Activity setModelid(String modelid) {
        this.modelid = modelid;
        return this;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public Activity setCreatetime(Date createtime) {
        this.createtime = createtime;
        return this;
    }

    public String getCreatepersonid() {
        return createpersonid;
    }

    public Activity setCreatepersonid(String createpersonid) {
        this.createpersonid = createpersonid;
        return this;
    }

    public Date getRecivetime() {
        return recivetime;
    }

    public Activity setRecivetime(Date recivetime) {
        this.recivetime = recivetime;
        return this;
    }

    public Date getHandletime() {
        return handletime;
    }

    public Activity setHandletime(Date handletime) {
        this.handletime = handletime;
        return this;
    }

    public String getHandlepersonids() {
        return handlepersonids;
    }

    public Activity setHandlepersonids(String handlepersonids) {
        this.handlepersonids = handlepersonids;
        return this;
    }

    public Integer getIsstart() {
        return isstart;
    }

    public Activity setIsstart(Integer isstart) {
        this.isstart = isstart;
        return this;
    }

    public String getPreviousactivityids() {
        return previousactivityids;
    }

    public Activity setPreviousactivityids(String previousactivityids) {
        this.previousactivityids = previousactivityids;
        return this;
    }

    public String getActivitystatus() {
        return activitystatus;
    }

    public Activity setActivitystatus(String activitystatus) {
        this.activitystatus = activitystatus;
        return this;
    }

    public String getHandledepartment() {
        return handledepartment;
    }

    public Activity setHandledepartment(String handledepartment) {
        this.handledepartment = handledepartment;
        return this;
    }

    public String getActivitytype() {
        return activitytype;
    }

    public Activity setActivitytype(String activitytype) {
        this.activitytype = activitytype;
        return this;
    }

    public String getIsreceve() {
        return isreceve;
    }

    public Activity setIsreceve(String isreceve) {
        this.isreceve = isreceve;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Activity{" +
        "id=" + id +
        ", instanceid=" + instanceid +
        ", modelid=" + modelid +
        ", createtime=" + createtime +
        ", createpersonid=" + createpersonid +
        ", recivetime=" + recivetime +
        ", handletime=" + handletime +
        ", handlepersonids=" + handlepersonids +
        ", isstart=" + isstart +
        ", previousactivityids=" + previousactivityids +
        ", activitystatus=" + activitystatus +
        ", handledepartment=" + handledepartment +
        ", activitytype=" + activitytype +
        ", isreceve=" + isreceve +
        "}";
    }
}
