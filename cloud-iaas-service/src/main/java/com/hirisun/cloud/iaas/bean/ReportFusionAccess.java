package com.hirisun.cloud.iaas.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * <p>
 * 
 * </p>
 *
 * @author yyc
 * @since 2020-05-19
 */
@TableName("TB_REPORT_FUSION_ACCESS")
public class ReportFusionAccess extends Model<ReportFusionAccess> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 民警访问 台
     */
        @Excel(name = "民警访问(台)")
         @TableField("POLICE_ACCESS")
    private Integer policeAccess;

        /**
     * 开发人员访问 台
     */
        @Excel(name = "开发人员访问(台)")
         @TableField("DEVELOPER_ACCESS")
    private Integer developerAccess;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

        /**
     * 应用（原项目）ID
     */
         @TableField("PROJECT_ID")
    private String projectId;

        /**
     * 申请ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 创建时间
     */
        @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

        /**
     * 修改时间
     */
         @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public ReportFusionAccess setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getPoliceAccess() {
        return policeAccess;
    }

    public ReportFusionAccess setPoliceAccess(Integer policeAccess) {
        this.policeAccess = policeAccess;
        return this;
    }

    public Integer getDeveloperAccess() {
        return developerAccess;
    }

    public ReportFusionAccess setDeveloperAccess(Integer developerAccess) {
        this.developerAccess = developerAccess;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ReportFusionAccess setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public ReportFusionAccess setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public ReportFusionAccess setAppInfoId(String appInfoId) {
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
        return "ReportFusionAccess{" +
        "id=" + id +
        ", policeAccess=" + policeAccess +
        ", developerAccess=" + developerAccess +
        ", remark=" + remark +
        ", projectId=" + projectId +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
