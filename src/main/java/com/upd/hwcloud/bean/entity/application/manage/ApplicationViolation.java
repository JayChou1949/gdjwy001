package com.upd.hwcloud.bean.entity.application.manage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xqp
 * @since 2020-06-30
 */
@TableName("TB_APPLICATION_VIOLATION")
public class ApplicationViolation extends Model<ApplicationViolation> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 身份证号
     */
         @TableField("CREATOR")
    private String creator;

        /**
     * 创建人姓名
     */
         @TableField("CREATOR_NAME")
    private String creatorName;

        /**
     * 组织机构ID
     */
         @TableField("ORG_ID")
    private String orgId;

        /**
     * 组织机构名称
     */
         @TableField("ORG_NAME")
    private String orgName;

        /**
     * 违规时间
     */
         @TableField("VIOLATION_TIME")
    private LocalDateTime violationTime;

        /**
     * 违规原因
     */
         @TableField("VIOLATION_REASON")
    private String violationReason;

        /**
     * 地市/警种
     */
         @TableField("AREA_OR_POLICE")
    private String areaOrPolice;


    public String getId() {
        return id;
    }

    public ApplicationViolation setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public ApplicationViolation setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public ApplicationViolation setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public ApplicationViolation setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public ApplicationViolation setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public LocalDateTime getViolationTime() {
        return violationTime;
    }

    public ApplicationViolation setViolationTime(LocalDateTime violationTime) {
        this.violationTime = violationTime;
        return this;
    }

    public String getViolationReason() {
        return violationReason;
    }

    public ApplicationViolation setViolationReason(String violationReason) {
        this.violationReason = violationReason;
        return this;
    }

    public String getAreaOrPolice() {
        return areaOrPolice;
    }

    public ApplicationViolation setAreaOrPolice(String areaOrPolice) {
        this.areaOrPolice = areaOrPolice;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ApplicationViolation{" +
        "id=" + id +
        ", creator=" + creator +
        ", creatorName=" + creatorName +
        ", orgId=" + orgId +
        ", orgName=" + orgName +
        ", violationTime=" + violationTime +
        ", violationReason=" + violationReason +
        ", areaOrPolice=" + areaOrPolice +
        "}";
    }
}
