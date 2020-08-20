package com.hirisun.cloud.workflow.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 流程定义表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Data
@TableName("T_WORKFLOW")
@ApiModel(value="Workflow对象", description="流程定义表")
public class Workflow implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 流程状态 0 正常
     */
    public static Integer STATUS_NORMAL=0;
    /**
     * 流程状态 1 删除
     */
    public static Integer STATUS_DELETE=1;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "流程业务名称")
    @TableField("FLOW_NAME")
    private String flowName;

    @ApiModelProperty(value = "流程说明")
    @TableField("FLOW_REMARK")
    private String flowRemark;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "创建人身份证")
    @TableField("CREATOR_ID")
    private String creatorId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifiedTime;

    @ApiModelProperty(value = "默认流程标记")
    @TableField("DEFAULT_PROCESS")
    private String defaultProcess;

    @ApiModelProperty(value = "状态 0正常 1删除")
    @TableField("FLOW_STATUS")
    private Integer flowStatus;

    @ApiModelProperty(value = "流程所属地区")
    @TableField("AREA")
    private String area;

    @ApiModelProperty(value = "流程所属警种")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @ApiModelProperty(value = "流程所属国家专项")
    @TableField("NATIONAL_SPECIAL_PROJECT")
    private String nationalSpecialProject;

    @ApiModelProperty(value = "流程类型 0:IPDS 1资源上报 2应用申请 3资源回收")
    @TableField("FLOW_TYPE")
    private Integer flowType;

    @ApiModelProperty(value = "版本")
    @TableField("VERSION")
    private Integer version;

    @ApiModelProperty(value = "创建人机构id")
    @TableField("CREATOR_ORG_ID")
    private String creatorOrgId;


    @ApiModelProperty(value = "环节列表")
    @TableField(exist = false)
    private LinkedList<WorkflowNode> nodeList;

    @Override
    public String toString() {
        return "Workflow{" +
        "id=" + id +
        ", flowName=" + flowName +
        ", flowRemark=" + flowRemark +
        ", creator=" + creator +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", defaultProcess=" + defaultProcess +
        ", flowStatus=" + flowStatus +
        ", area=" + area +
        ", policeCategory=" + policeCategory +
        ", nationalSpecialProject=" + nationalSpecialProject +
        ", flowType=" + flowType +
        ", version=" + version +
        "}";
    }
}
