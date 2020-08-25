package com.hirisun.cloud.iaas.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * iaas paas 二级页面内容
 * </p>
 *
 * @author huru
 * @since 2018-12-18
 */
@TableName("TB_SUBPAGE")
public class Subpage extends Model<Subpage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    /**
     * 厂商
     */
    @TableField("VENDOR")
    private String vendor;

    /**
     * 外链
     */
    @TableField("URL")
    private String url;

    /**
     * 功能描述1
     */
    @TableField("DESCRIPTION_ONE")
    private String descriptionOne;

    /**
     * 功能描述2
     */
    @TableField("DESCRIPTION_TWO")
    private String descriptionTwo;

    /**
     * 功能描述3
     */
    @TableField("DESCRIPTION_THREE")
    private String descriptionThree;

    /**
     * 应用场景1
     */
    @TableField("APPLICATION_ONE")
    private String applicationOne;

    /**
     * 应用场景2
     */
    @TableField("APPLICATION_TWO")
    private String applicationTwo;

    /**
     * 应用场景3
     */
    @TableField("APPLICATION_THREE")
    private String applicationThree;

    /**
     * 使用情况1
     */
    @TableField("USE_ONE")
    private String useOne;

    /**
     * 使用情况2
     */
    @TableField("USE_TWO")
    private String useTwo;

    /**
     * 使用情况3
     */
    @TableField("USE_THREE")
    private String useThree;

    /**
     * 使用情况4
     */
    @TableField("USE_FOUR")
    private String useFour;

    /**
     * 使用情况5
     */
    @TableField("USE_FIVE")
    private String useFive;

    /**
     * 申请流程1
     */
    @TableField("FLOW_ONE")
    private String flowOne;

    /**
     * 申请流程2
     */
    @TableField("FLOW_TWO")
    private String flowTwo;

    /**
     * 申请流程3
     */
    @TableField("FLOW_THREE")
    private String flowThree;

    /**
     * 申请流程4
     */
    @TableField("FLOW_FOUR")
    private String flowFour;

    /**
     * 反馈信息1
     */
    @TableField("FEEDBACK_ONE")
    private String feedbackOne;

    /**
     * 反馈人1
     */
    @TableField("FEEDBACK_MAN_ONE")
    private String feedbackManOne;

    /**
     * 反馈信息2
     */
    @TableField("FEEDBACK_TWO")
    private String feedbackTwo;

    /**
     * 反馈人2
     */
    @TableField("FEEDBACK_MAN_TWO")
    private String feedbackManTwo;

    /**
     * 反馈信息3
     */
    @TableField("FEEDBACK_THREE")
    private String feedbackThree;

    /**
     * 反馈人3
     */
    @TableField("FEEDBACK_MAN_THREE")
    private String feedbackManThree;

    /**
     * 反馈信息4
     */
    @TableField("FEEDBACK_FOUR")
    private String feedbackFour;

    /**
     * 反馈人4
     */
    @TableField("FEEDBACK_MAN_FOUR")
    private String feedbackManFour;

    /**
     * 关联服务id
     */
    @TableField("SERVICE_ID")
    private String serviceId;

    @TableField("SORT")
    private Long sort;

    /**
     * 标签 以 ; 分割
     */
    @TableField("USE")
    private String use;

    /**
     * 建设进展
     */
    @TableField("BUILD")
    private String build;

    /**
     * 建设单位
     */
    @TableField("BUILD_ORG")
    private String buildOrg;

    /**
     * 建设单位联系人
     */
    @TableField("BUILD_CONTACT")
    private String buildContact;

    /**
     * 建设单位联系电话
     */
    @TableField("BUILD_PHONE")
    private String buildPhone;

    /**
     * 研发单位
     */
    @TableField("DEVELOP_ORG")
    private String developOrg;

    /**
     * 研发单位联系人
     */
    @TableField("DEVELOP_CONTACT")
    private String developContact;

    /**
     * 研发单位联系电话
     */
    @TableField("DEVELOP_PHONE")
    private String developPhone;


    public String getId() {
        return id;
    }

    public Subpage setId(String id) {
        this.id = id;
        return this;
    }

    public String getVendor() {
        return vendor;
    }

    public Subpage setVendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescriptionOne() {
        return descriptionOne;
    }

    public Subpage setDescriptionOne(String descriptionOne) {
        this.descriptionOne = descriptionOne;
        return this;
    }

    public String getDescriptionTwo() {
        return descriptionTwo;
    }

    public Subpage setDescriptionTwo(String descriptionTwo) {
        this.descriptionTwo = descriptionTwo;
        return this;
    }

    public String getDescriptionThree() {
        return descriptionThree;
    }

    public Subpage setDescriptionThree(String descriptionThree) {
        this.descriptionThree = descriptionThree;
        return this;
    }

    public String getApplicationOne() {
        return applicationOne;
    }

    public Subpage setApplicationOne(String applicationOne) {
        this.applicationOne = applicationOne;
        return this;
    }

    public String getApplicationTwo() {
        return applicationTwo;
    }

    public Subpage setApplicationTwo(String applicationTwo) {
        this.applicationTwo = applicationTwo;
        return this;
    }

    public String getApplicationThree() {
        return applicationThree;
    }

    public Subpage setApplicationThree(String applicationThree) {
        this.applicationThree = applicationThree;
        return this;
    }

    public String getUseOne() {
        return useOne;
    }

    public Subpage setUseOne(String useOne) {
        this.useOne = useOne;
        return this;
    }

    public String getUseTwo() {
        return useTwo;
    }

    public Subpage setUseTwo(String useTwo) {
        this.useTwo = useTwo;
        return this;
    }

    public String getUseThree() {
        return useThree;
    }

    public Subpage setUseThree(String useThree) {
        this.useThree = useThree;
        return this;
    }

    public String getUseFour() {
        return useFour;
    }

    public Subpage setUseFour(String useFour) {
        this.useFour = useFour;
        return this;
    }

    public String getUseFive() {
        return useFive;
    }

    public Subpage setUseFive(String useFive) {
        this.useFive = useFive;
        return this;
    }

    public String getFlowOne() {
        return flowOne;
    }

    public Subpage setFlowOne(String flowOne) {
        this.flowOne = flowOne;
        return this;
    }

    public String getFlowTwo() {
        return flowTwo;
    }

    public Subpage setFlowTwo(String flowTwo) {
        this.flowTwo = flowTwo;
        return this;
    }

    public String getFlowThree() {
        return flowThree;
    }

    public Subpage setFlowThree(String flowThree) {
        this.flowThree = flowThree;
        return this;
    }

    public String getFlowFour() {
        return flowFour;
    }

    public Subpage setFlowFour(String flowFour) {
        this.flowFour = flowFour;
        return this;
    }

    public String getFeedbackOne() {
        return feedbackOne;
    }

    public Subpage setFeedbackOne(String feedbackOne) {
        this.feedbackOne = feedbackOne;
        return this;
    }

    public String getFeedbackManOne() {
        return feedbackManOne;
    }

    public Subpage setFeedbackManOne(String feedbackManOne) {
        this.feedbackManOne = feedbackManOne;
        return this;
    }

    public String getFeedbackTwo() {
        return feedbackTwo;
    }

    public Subpage setFeedbackTwo(String feedbackTwo) {
        this.feedbackTwo = feedbackTwo;
        return this;
    }

    public String getFeedbackManTwo() {
        return feedbackManTwo;
    }

    public Subpage setFeedbackManTwo(String feedbackManTwo) {
        this.feedbackManTwo = feedbackManTwo;
        return this;
    }

    public String getFeedbackThree() {
        return feedbackThree;
    }

    public Subpage setFeedbackThree(String feedbackThree) {
        this.feedbackThree = feedbackThree;
        return this;
    }

    public String getFeedbackManThree() {
        return feedbackManThree;
    }

    public Subpage setFeedbackManThree(String feedbackManThree) {
        this.feedbackManThree = feedbackManThree;
        return this;
    }

    public String getFeedbackFour() {
        return feedbackFour;
    }

    public Subpage setFeedbackFour(String feedbackFour) {
        this.feedbackFour = feedbackFour;
        return this;
    }

    public String getFeedbackManFour() {
        return feedbackManFour;
    }

    public Subpage setFeedbackManFour(String feedbackManFour) {
        this.feedbackManFour = feedbackManFour;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public Subpage setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getBuildOrg() {
        return buildOrg;
    }

    public void setBuildOrg(String buildOrg) {
        this.buildOrg = buildOrg;
    }

    public String getBuildContact() {
        return buildContact;
    }

    public void setBuildContact(String buildContact) {
        this.buildContact = buildContact;
    }

    public String getBuildPhone() {
        return buildPhone;
    }

    public void setBuildPhone(String buildPhone) {
        this.buildPhone = buildPhone;
    }

    public String getDevelopOrg() {
        return developOrg;
    }

    public void setDevelopOrg(String developOrg) {
        this.developOrg = developOrg;
    }

    public String getDevelopContact() {
        return developContact;
    }

    public void setDevelopContact(String developContact) {
        this.developContact = developContact;
    }

    public String getDevelopPhone() {
        return developPhone;
    }

    public void setDevelopPhone(String developPhone) {
        this.developPhone = developPhone;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Subpage{" +
                "id=" + id +
                ", vendor=" + vendor +
                ", descriptionOne=" + descriptionOne +
                ", descriptionTwo=" + descriptionTwo +
                ", descriptionThree=" + descriptionThree +
                ", applicationOne=" + applicationOne +
                ", applicationTwo=" + applicationTwo +
                ", applicationThree=" + applicationThree +
                ", useOne=" + useOne +
                ", useTwo=" + useTwo +
                ", useThree=" + useThree +
                ", useFour=" + useFour +
                ", useFive=" + useFive +
                ", flowOne=" + flowOne +
                ", flowTwo=" + flowTwo +
                ", flowThree=" + flowThree +
                ", flowFour=" + flowFour +
                ", feedbackOne=" + feedbackOne +
                ", feedbackManOne=" + feedbackManOne +
                ", feedbackTwo=" + feedbackTwo +
                ", feedbackManTwo=" + feedbackManTwo +
                ", feedbackThree=" + feedbackThree +
                ", feedbackManThree=" + feedbackManThree +
                ", feedbackFour=" + feedbackFour +
                ", feedbackManFour=" + feedbackManFour +
                ", serviceId=" + serviceId +
                "}";
    }
}
