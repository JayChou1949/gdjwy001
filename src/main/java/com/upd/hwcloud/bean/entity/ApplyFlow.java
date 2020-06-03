package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资源申请流程配置
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
@TableName("TB_APPLY_FLOW")
public class ApplyFlow extends Model<ApplyFlow> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 流程名
     */
         @TableField("NAME")
    private String name;

        /**
     * 适用资源 1:IAAS 2:DAAS 3:PAAS 4:SAAS
     */
         @TableField("TARGET_RESOURCE")
    private Long targetResource;

        /**
     * 密级
     */
         @TableField("SECRET_LEVEL")
    private String secretLevel;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

        /**
     * 创建人
     */
         @TableField("CREATOR")
    private String creator;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 环节
     */
    @TableField(exist = false)
    private List<FlowStep> stepList;

    /**
     * 创建人,用于查询
     */
    @TableField(exist = false)
    private String creatorName;

    /**
     * 环节数,用于查询
     */
    @TableField(exist = false)
    private String stepNum;


    public String getId() {
        return id;
    }

    public ApplyFlow setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ApplyFlow setName(String name) {
        this.name = name;
        return this;
    }

    public Long getTargetResource() {
        return targetResource;
    }

    public void setTargetResource(Long targetResource) {
        this.targetResource = targetResource;
    }

    public String getSecretLevel() {
        return secretLevel;
    }

    public ApplyFlow setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ApplyFlow setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public ApplyFlow setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ApplyFlow setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ApplyFlow setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public List<FlowStep> getStepList() {
        return stepList;
    }

    public void setStepList(List<FlowStep> stepList) {
        this.stepList = stepList;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getStepNum() {
        return stepNum;
    }

    public void setStepNum(String stepNum) {
        this.stepNum = stepNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ApplyFlow{" +
        "id=" + id +
        ", name=" + name +
        ", targetResource=" + targetResource +
        ", secretLevel=" + secretLevel +
        ", remark=" + remark +
        ", creator=" + creator +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
