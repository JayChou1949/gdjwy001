package com.hirisun.cloud.iaas.bean;

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
 * @author yyc
 * @since 2020-05-19
 */
@TableName("TB_REPORT_SPECIAL")
public class ReportSpecial extends Model<ReportSpecial> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 需求名字
     */
         @TableField("DEMAND_NAME")
    private String demandName;

        /**
     * 说明
     */
         @TableField("EXPLANATION")
    private String explanation;

        /**
     * 应用(项目)ID 
     */
         @TableField("PROJECT_ID")
    private String projectId;

        /**
     * 申请信息ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 创建时间
     */
         @TableField("CREATE_TIME")
    private Date createTime;

        /**
     * 修改时间
     */
         @TableField("MODIFIED_TIME")
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public ReportSpecial setId(String id) {
        this.id = id;
        return this;
    }

    public String getDemandName() {
        return demandName;
    }

    public ReportSpecial setDemandName(String demandName) {
        this.demandName = demandName;
        return this;
    }

    public String getExplanation() {
        return explanation;
    }

    public ReportSpecial setExplanation(String explanation) {
        this.explanation = explanation;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public ReportSpecial setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public ReportSpecial setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ReportSpecial{" +
        "id=" + id +
        ", demandName=" + demandName +
        ", explanation=" + explanation +
        ", projectId=" + projectId +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
