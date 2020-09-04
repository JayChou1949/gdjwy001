package com.hirisun.cloud.model.apply;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hirisun.cloud.model.file.FilesVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务申请审核信息表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-12
 */
@Data
@ApiModel(value="ApplyReviewRecord对象", description="服务申请审核信息表")
public class ApplyReviewRecordVO implements Serializable {

    /**
     * 类型 1审核
     */
    public static final String TYPE_AUDIT="1";
    /**
     * 类型 2实施
     */
    public static final String TYPE_IMPL="2";

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "审批人")
    private String creator;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date modifiedTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "环节名")
    private String stepName;

    @ApiModelProperty(value = "服务信息id")
    private String applyId;

    @ApiModelProperty(value = "类型 1：审核 2：实施")
    private String type;

    @ApiModelProperty(value = "环节 id")
    private String workflowNodeId;

    @ApiModelProperty(value = "审批结果 0:驳回 1:通过")
    private Integer result;

    private List<FilesVo> fileList;

    /**
     * 是否关系型数据库新增账号流程
     */
    @ApiModelProperty(value = "是否关系型数据库新增账号流程")
    private int  rdbAddAccount;

    @ApiModelProperty(value = "资源是否同意回收")
    private int resourceRecoveredAgree;



    @Override
    public String toString() {
        return "ApplyReviewRecord{" +
        "id=" + id +
        ", creator=" + creator +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", remark=" + remark +
        ", stepName=" + stepName +
        ", applyId=" + applyId +
        ", type=" + type +
        ", workflowNodeId=" + workflowNodeId +
        ", result=" + result +
        "}";
    }
}
