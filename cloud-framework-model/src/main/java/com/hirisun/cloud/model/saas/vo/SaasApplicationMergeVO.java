package com.hirisun.cloud.model.saas.vo;

import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * SaaS资源申请合并信息表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-31
 */
@Data
public class SaasApplicationMergeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

        /**
     * 申请人
     */
    private String creator;

        /**
     * 申请人姓名
     */
    private String creatorName;

        /**
     * 单位id
     */
    private String orgId;

        /**
     * 单位名称
     */
    private String orgName;

        /**
     * 职务
     */
    private String postType;

        /**
     * 手机号
     */
    private String mobileWork;

        /**
     * 申请时间
     */
    private Date applicationTime;

    private Date createTime;

    private Date modifiedTime;

        /**
     * 状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 
     */
    private String status;

    /**
     * 流程id
     */
    private String workFlowId;


    /**
     * 流程版本
     */
    private Integer workFlowVersion;

    /**
     * 申请单号
     */
    private String orderNumber;

    /**
     * 申请说明
     */
    private String explanation;

    /**
     * 地区
     */
    private String areas;

    /**
     * 系统所属警种
     */
    private String policeCategory;

    private Date recheckTime;

    private Date bigDataTime;

    private Date carryTime;

    /**
     * 是否为导入的数据 0否 1是
     */
    private String isImport;

    private List<FilesVo> fileList;

    private List<SaasApplicationVO> applicationList;

    /**
     * 审核记录(包含实施记录)
     */
    private List<AppReviewInfoVo> reviewList;
    /**
     * 创建人
     */
    private UserVO user;

    /**
     * 是否可审核
     */
    private boolean canReview = false;

    /**
     * 是否可以加办
     */
    private boolean canAdd = false;

    /**
     * 是否可删除
     */
    private boolean canDelete = false;

    /**
     * 是否可修改
     */
    private boolean canEdit = false;

    /**
     * 是否可转发
     */
    private boolean canTrans = false;

    /**
     * 是否可回退
     */
    private boolean canFall = false;

    private boolean canAdviser;

    /**
     * 是否可实施
     */
    private boolean canImpl = false;

    /**
     * 是否可撤销合并
     */
    private boolean canUnmerge = false;

    /**
     * 申请单数量
     */
    private int num = 0;

    /**
     * 实施信息
     */
    private AppReviewInfoVo impl;

    /**
     * 当前环节处理人
     */
    private String processingPerson;

    private String instanceId;

    @Override
    public String toString() {
        return "SaasApplicationMerge{" +
        "id=" + id +
        ", creator=" + creator +
        ", creatorName=" + creatorName +
        ", orgId=" + orgId +
        ", orgName=" + orgName +
        ", postType=" + postType +
        ", mobileWork=" + mobileWork +
        ", applicationTime=" + applicationTime +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", status=" + status +
        "}";
    }
}
