package com.hirisun.cloud.order.bean.reported;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.vo.WorkOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 申请信息表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-04
 */
@Data
@TableName("TB_IAAS_ZYSB")
public class IaasZysb extends WorkOrder implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private final String businessName = BusinessName.IAAS_ZYSB;

    @TableId(value = "ID",type = IdType.ASSIGN_UUID)
    private String id;

        /**
     * 申请人
     */
         @TableField("CREATOR")
    private String creator;

        /**
     * 状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 
     */
         @TableField("STATUS")
    private String status;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;
    /**
     * 上报方式
     */ @ApiModelProperty(value = "上报方式 0-地区 1-警种")
    @TableField("APPLY_TYPE")
    private String applyType;
        /**
     * 流程id
     */
         @TableField("WORK_FLOW_ID")
    private String workFlowId;

    /**
     * 流程版本
     */
    @TableField("WORK_FLOW_VERSION")
    private Integer workFlowVersion;

    /**
     * 国家专项
     */
    @TableField("NATIONAL_SPECIAL_PROJECT")
    private String nationalSpecialProject;

    /**
     * 申请单号
     */
    @Excel(name = "工单号")
    @TableField("ORDER_NUMBER")
    private String orderNumber;

        /**
     * 申请人姓名
     */
        @Excel(name = "申请人")
         @TableField("CREATOR_NAME")
    private String creatorName;

        /**
     * 申请说明
     */
         @TableField("EXPLANATION")
    private String explanation;

        /**
     * 系统所属警种
     */
         @TableField("POLICE_CATEGORY")
    private String policeCategory;

        /**
     * 申请人单位
     */
        @Excel(name = "申请单位")
         @TableField("CREATOR_UNIT")
    private String creatorUnit;

    /**
     * 申请时间
     */
    @Excel(name = "申请时间", exportFormat = "yyyy-MM-dd")
    @TableField("APPLICATION_TIME")
    private Date applicationTime;

        /**
     * 申请人电话
     */
         @TableField("CREATOR_PHONE")
    private String creatorPhone;

        /**
     * 地区
     */
         @TableField("AREAS")
    private String areas;

        /**
     * 开始时间
     */
        @Excel(name = "预计使用时间", exportFormat = "yyyy-MM-dd")
         @TableField("START_DATE")
    private Date startDate;

    @TableField(exist = false)
    private List<IaasZysbXmxx> projectList;

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


    
    /**
     * 是否可删除
     */
    @TableField(exist = false)
    private boolean canDelete = false;

    /**
     * 当前环节处理人
     */
    @TableField(exist = false)
    private String processingPerson;
    @TableField(exist = false)
    private boolean canAdviser;

    @TableField(exist = false)
    private String instanceId;

    @Override
    public String toString() {
        return "IaasZysb{" +
        "id=" + id +
        ", creator=" + creator +
        ", status=" + status +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", workFlowId=" + workFlowId +
        ", creatorName=" + creatorName +
        ", orderNumber=" + orderNumber +
        ", explanation=" + explanation +
        ", policeCategory=" + policeCategory +
        ", applicationTime=" + applicationTime +
        ", creatorUnit=" + creatorUnit +
        ", creatorPhone=" + creatorPhone +
        ", areas=" + areas +
        ", startDate=" + startDate +
        "}";
    }
}
