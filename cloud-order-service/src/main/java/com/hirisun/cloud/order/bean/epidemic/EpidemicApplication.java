package com.hirisun.cloud.order.bean.epidemic;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.vo.WorkOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * SaaS资源申请原始信息表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-03
 */
@Data
@TableName("TB_EPIDEMIC_APPLICATION")
@ApiModel(value="EpidemicApplication对象", description="SaaS资源申请原始信息表")
public class EpidemicApplication extends WorkOrder implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField(exist = false)
    private final String businessName = BusinessName.EPIDEMIC;

    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "申请人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "申请人姓名")
    @TableField("CREATOR_NAME")
    private String creatorName;

    @ApiModelProperty(value = "单位id")
    @TableField("ORG_ID")
    private String orgId;

    @ApiModelProperty(value = "单位名称")
    @TableField("ORG_NAME")
    private String orgName;

    @ApiModelProperty(value = "职务")
    @TableField("POST_TYPE")
    private String postType;

    @ApiModelProperty(value = "手机号")
    @TableField("MOBILE_WORK")
    private String mobileWork;

    @ApiModelProperty(value = "申请时间")
    @TableField("APPLICATION_TIME")
    private LocalDateTime applicationTime;

    @ApiModelProperty(value = "上报方式--按地区 0；按警种 1")
    @TableField("APPLY_TYPE")
    private String applyType;

    @ApiModelProperty(value = "地区")
    @TableField("AREAS")
    private String areas;

    @ApiModelProperty(value = "系统所属警种")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @ApiModelProperty(value = "申请说明")
    @TableField("EXPLANATION")
    private String explanation;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("MODIFIED_TIME")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "申请单号")
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    @ApiModelProperty(value = "状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 ")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "终端IP地址")
    @TableField("IP")
    private String ip;

    @ApiModelProperty(value = "权限回收标志  0:未回收  -1:已回收  99:回收中")
    @TableField("RECOVER_FLAG")
    private String recoverFlag;

    @TableField("RECOVER_ID")
    private String recoverId;

    @ApiModelProperty(value = "流程ID")
    @TableField("WORK_FLOW_ID")
    private String workFlowId;

    @ApiModelProperty(value = "版本")
    @TableField("WORK_FLOW_VERSION")
    private Integer workFlowVersion;

    @TableField(exist = false)
    private List<FilesVo> fileList;


    @TableField(exist = false)
    private List<EpidemicApplicationExt> serviceList;

    /**
     * 审核记录(包含实施记录)
     */
    @TableField(exist = false)
    private List<ApplyReviewRecord> reviewList;
    /**
     * 创建人
     */
    @TableField(exist = false)
    private UserVO user;

    @TableField(exist = false)
    private boolean canDelete = false;

    /**
     * 实施信息
     */
    @TableField(exist = false)
    private ApplyReviewRecord impl;

    /**
     * 当前环节处理人
     */
    @TableField(exist = false)
    private String processingPerson;

    @TableField(exist = false)
    private String instanceId;


    @Override
    public String toString() {
        return "EpidemicApplication{" +
        "id=" + id +
        ", creator=" + creator +
        ", creatorName=" + creatorName +
        ", orgId=" + orgId +
        ", orgName=" + orgName +
        ", postType=" + postType +
        ", mobileWork=" + mobileWork +
        ", applicationTime=" + applicationTime +
        ", applyType=" + applyType +
        ", areas=" + areas +
        ", policeCategory=" + policeCategory +
        ", explanation=" + explanation +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", orderNumber=" + orderNumber +
        ", status=" + status +
        ", ip=" + ip +
        ", recoverFlag=" + recoverFlag +
        ", recoverId=" + recoverId +
        ", workFlowId=" + workFlowId +
        ", workFlowVersion=" + workFlowVersion +
        "}";
    }
}
