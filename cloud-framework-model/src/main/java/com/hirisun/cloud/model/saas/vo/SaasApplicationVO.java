package com.hirisun.cloud.model.saas.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * SaaS资源申请原始信息表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-31
 */
@Data
public class SaasApplicationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "序号")
    private String id;

    /**
     * 申请人姓名
     */
    @Excel(name = "姓名")
    private String creatorName;

        /**
     * 申请人
     */
        @Excel(name = "身份证号码")
    private String creator;

        /**
     * 单位id
     */
    private String orgId;

        /**
     * 单位名称
     */
        @Excel(name = "工作单位")
    private String orgName;

        /**
     * 职务
     */
        @Excel(name = "职务")
    private String postType;

        /**
     * 手机号
     */
        @Excel(name = "手机号码")
    private String mobileWork;

    /**
     * 终端IP地址
     */
    @Excel(name = "终端IP地址")
    private String ip;

    /**
     * 申请服务数量
     */
    @Excel(name = "申请服务数量")
    private int num = 0;

    /**
     * 服务列表
     */
    private List<Map<String,Object>> serviceList;

        /**
     * 申请时间
     */
    private Date applicationTime;

        /**
     * 上报方式--按地区 0；按警种 1
     */
    private String applyType;

        /**
     * 地区
     */
    private String areas;

        /**
     * 系统所属警种
     */
    private String policeCategory;

        /**
     * 申请说明
     */
    private String explanation;

    private Date createTime;

    private Date modifiedTime;

        /**
     * 申请单号
     */
    private String orderNumber;

        /**
     * 状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回
     */
    private String status;

    private String mergeId;

    /**
     * 权限回收标志  0:未回收  -1:已回收  99:回收中
     */
    private String recoverFlag;

    /**
     * 是否为导入的数据 0否 1是
     */
    private String isImport;

    /**
     * 回收申请单id
     */
    private String recoverId;

    /**
     * 是否为特殊应用申请 0:否 1:可视化建模平台 2:广东公安数据接入平台
     */
    private String visible;

    /**
     * 统一用户id
     */
    private String userId;

    private List<FilesVo> fileList;

    /**
     * 是否可删除
     */
    private boolean canDelete = false;

    /**
     * 是否可修改
     */
    private boolean canEdit = false;



    /**
     * 是否可提交
     */
    private boolean canSubmit = false;

    /**
     * 审核记录(包含实施记录)
     */
    private List<ApplyReviewRecordVO> reviewList;

    /**
     * 实施信息
     */
    private ApplyReviewRecordVO impl;

    /**
     * saas申请用用统计
     */
    private String total;
    /**
     * 服务ID
     */
    private String serviceId;
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     *服务应用名称（地市应用 警种应用 通用应用）
     * @return
     */
    private String applicationName;

    /**
     * 可视化资源列表
     */
    private List<SaasAppExtResourceVO> resourceList;



    @Override
    public String toString() {
        return "SaasApplication{" +
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
        ", mergeId=" + mergeId +
        ", ip=" + ip +
                ", userId=" + userId +
        "}";
    }
}
