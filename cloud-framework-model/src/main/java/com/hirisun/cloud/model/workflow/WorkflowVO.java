package com.hirisun.cloud.model.workflow;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

/**
 * <p>
 * 流程定义表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-06
 */
@Data
@ApiModel(value="Workflow视图对象", description="流程定义表")
public class WorkflowVO implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "流程业务名称")
    private String flowName;

    @ApiModelProperty(value = "流程说明")
    private String flowRemark;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建人身份证")
    private String creatorId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date modifiedTime;

    @ApiModelProperty(value = "默认流程标记")
    private String defaultProcess;

    @ApiModelProperty(value = "状态 0正常 1删除")
    private Integer flowStatus;

    @ApiModelProperty(value = "流程所属地区")
    private String area;

    @ApiModelProperty(value = "流程所属警种")
    private String policeCategory;

    @ApiModelProperty(value = "流程所属国家专项")
    private String nationalSpecialProject;

    @ApiModelProperty(value = "流程类型 0:IPDS 1资源上报 2应用申请 3资源回收")
    private Integer flowType;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建人机构id")
    private String creatorOrgId;


    @ApiModelProperty(value = "环节列表")
    private LinkedList<WorkflowNodeVO> nodeList;

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
