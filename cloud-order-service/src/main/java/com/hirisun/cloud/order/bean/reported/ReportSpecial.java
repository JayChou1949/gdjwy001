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
@TableName("TB_REPORT_SPECIAL")
@ApiModel(value="ReportSpecial对象", description="")
public class ReportSpecial implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "UUID")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "需求名字")
    @TableField("DEMAND_NAME")
    private String demandName;

    @ApiModelProperty(value = "说明")
    @TableField("EXPLANATION")
    private String explanation;

    @ApiModelProperty(value = "应用(项目)ID ")
    @TableField("PROJECT_ID")
    private String projectId;

    @ApiModelProperty(value = "申请信息ID")
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

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
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
