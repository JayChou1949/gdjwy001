package com.hirisun.cloud.order.bean.recover;

import com.baomidou.mybatisplus.annotation.*;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.vo.WorkOrder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 资源回收申请信息
 */
@Data
@TableName("TB_RESOURCE_RECOVER_APP_INFO")
public class ResourceRecoverAppInfo extends WorkOrder implements Serializable {

	private static final long serialVersionUID = 3768746595846131924L;

    @TableField(exist = false)
    private final String businessName = "RECOVER";

		/**
     * UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 发起回收人
     */
         @TableField("CREATOR")
    private String creator;

        /**
     * 发起回收人申请号
     */
         @TableField("CREATOR_ID_CARD")
    private String creatorIdCard;

        /**
     * 发起回收人电话
     */
         @TableField("CREATOR_PHONE")
    private String creatorPhone;

        /**
     * 被回收人
     */
         @TableField("RECOVERED_PERSON")
    private String recoveredPerson;

        /**
     * 被回收人电话
     */
         @TableField("RECOVERED_PERSON_PHONE")
    private String recoveredPersonPhone;

    /**
     * 被回收人身份证
     */
    @TableField("RECOVERED_PERSON_ID_CARD")
    private String recoveredPersonIdCard;

        /**
     * 订单号
     */
         @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * 拟缩虚拟机数目
     */
    @TableField(exist = false)
     private String num;

        /**
     * 订单状态
     */
         @TableField("STATUS")
    private String status;

        /**
     * 创建时间
     */
        @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

        /**
     * 修改时间
     */
        @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 被回收人同意回收 0 不同意 1同意
     */
    @TableField("RECOVERED_AGREE")
    private Integer recoveredAgree;


    @TableField(exist = false)
    private List<FilesVo> fileList;

    /**
     * 流程ID
     */
    @TableField(exist = false)
         private String workFlowId;
    /**
     * 流程版本号
     */
    @TableField(exist = false)
         private Integer workFlowVersion;

    @TableField(exist = false)
    private List<ResourceRecover> serverList;

    @TableField(exist = false)
    private List<ResourceRecoverImpl> implServerList;

    @TableField(exist = false)
    private List<ApplyReviewRecord> reviewList;

    /**
     * 实施结果(最后一条)
     */
    @TableField(exist = false)
    private ApplyReviewRecord impl;

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
    private String instanceId;


}
