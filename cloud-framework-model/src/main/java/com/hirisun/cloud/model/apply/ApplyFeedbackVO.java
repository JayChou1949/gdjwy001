package com.hirisun.cloud.model.apply;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 服务申请反馈
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-12
 */
@Data
@ApiModel(value="ApplyFeedback对象", description="服务申请反馈")
public class ApplyFeedbackVO implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "审批人身份证")
    private String creator;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date modifiedTime;

    @ApiModelProperty(value = "结果 0:未完成 1:完成")
    private String result;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "服务信息id")
    private String appInfoId;

    @ApiModelProperty(value = "满意度")
    private String score;

    @ApiModelProperty(value = "审批人所属机构")
    private String creatorOrgId;

    @Override
    public String toString() {
        return "ApplyFeedback{" +
        "id=" + id +
        ", creator=" + creator +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", result=" + result +
        ", remark=" + remark +
        ", appInfoId=" + appInfoId +
        ", score=" + score +
        ", creatorOrgId=" + creatorOrgId +
        "}";
    }
}
