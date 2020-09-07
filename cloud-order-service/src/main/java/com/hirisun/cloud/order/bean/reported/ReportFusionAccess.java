package com.hirisun.cloud.order.bean.reported;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
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
 * @since 2020-09-04
 */
@TableName("TB_REPORT_FUSION_ACCESS")
@ApiModel(value="ReportFusionAccess对象", description="")
public class ReportFusionAccess implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "UUID")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "民警访问 台")
    @TableField("POLICE_ACCESS")
    private Integer policeAccess;

    @ApiModelProperty(value = "开发人员访问 台")
    @TableField("DEVELOPER_ACCESS")
    private Integer developerAccess;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "应用（原项目）ID")
    @TableField("PROJECT_ID")
    private String projectId;

    @ApiModelProperty(value = "申请ID")
    @TableField("APP_INFO_ID")
    private String appInfoId;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFIED_TIME")
    private LocalDateTime modifiedTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPoliceAccess() {
        return policeAccess;
    }

    public void setPoliceAccess(Integer policeAccess) {
        this.policeAccess = policeAccess;
    }

    public Integer getDeveloperAccess() {
        return developerAccess;
    }

    public void setDeveloperAccess(Integer developerAccess) {
        this.developerAccess = developerAccess;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public void setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
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
