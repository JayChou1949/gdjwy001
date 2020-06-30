package com.upd.hwcloud.bean.entity.application.manage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 应用运营管理
 * </p>
 *
 * @author xqp
 * @since 2020-06-29
 */
@TableName("TB_APPLICATION_MANAGE")
public class ApplicationManage extends Model<ApplicationManage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 地市/警种
     */
         @TableField("AREA_OR_POLICE")
    private String areaOrPolice;

        /**
     * 用户配额数
     */
         @TableField("USER_QUOTA_NUM")
    private Integer userQuotaNum;

        /**
     * 已有用户总数
     */
         @TableField("USER_TOTAL")
    private Integer userTotal;

        /**
     * 已有网警用户数
     */
         @TableField("POLICE_USER_NUM")
    private Integer policeUserNum;

        /**
     * 已有非网警用户数
     */
         @TableField("NO_POLICE_USER_NUM")
    private Integer noPoliceUserNum;

        /**
     * 当前剩余配额数
     */
         @TableField("AVAILABLE_QUOTA_NUM")
    private Integer availableQuotaNum;

        /**
     * 违规次数
     */
         @TableField("VIOLATION_NUM")
    private Integer violationNum;

        /**
     * 严重违规次数
     */
         @TableField("SERIOUS_VIOLATION_NUM")
    private Integer seriousViolationNum;


    public String getId() {
        return id;
    }

    public ApplicationManage setId(String id) {
        this.id = id;
        return this;
    }

    public String getAreaOrPolice() {
        return areaOrPolice;
    }

    public ApplicationManage setAreaOrPolice(String areaOrPolice) {
        this.areaOrPolice = areaOrPolice;
        return this;
    }

    public Integer getUserQuotaNum() {
        return userQuotaNum;
    }

    public ApplicationManage setUserQuotaNum(Integer userQuotaNum) {
        this.userQuotaNum = userQuotaNum;
        return this;
    }

    public Integer getUserTotal() {
        return userTotal;
    }

    public ApplicationManage setUserTotal(Integer userTotal) {
        this.userTotal = userTotal;
        return this;
    }

    public Integer getPoliceUserNum() {
        return policeUserNum;
    }

    public ApplicationManage setPoliceUserNum(Integer policeUserNum) {
        this.policeUserNum = policeUserNum;
        return this;
    }

    public Integer getNoPoliceUserNum() {
        return noPoliceUserNum;
    }

    public ApplicationManage setNoPoliceUserNum(Integer noPoliceUserNum) {
        this.noPoliceUserNum = noPoliceUserNum;
        return this;
    }

    public Integer getAvailableQuotaNum() {
        return availableQuotaNum;
    }

    public ApplicationManage setAvailableQuotaNum(Integer availableQuotaNum) {
        this.availableQuotaNum = availableQuotaNum;
        return this;
    }

    public Integer getViolationNum() {
        return violationNum;
    }

    public ApplicationManage setViolationNum(Integer violationNum) {
        this.violationNum = violationNum;
        return this;
    }

    public Integer getSeriousViolationNum() {
        return seriousViolationNum;
    }

    public ApplicationManage setSeriousViolationNum(Integer seriousViolationNum) {
        this.seriousViolationNum = seriousViolationNum;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ApplicationManage{" +
        "id=" + id +
        ", areaOrPolice=" + areaOrPolice +
        ", userQuotaNum=" + userQuotaNum +
        ", userTotal=" + userTotal +
        ", policeUserNum=" + policeUserNum +
        ", noPoliceUserNum=" + noPoliceUserNum +
        ", availableQuotaNum=" + availableQuotaNum +
        ", violationNum=" + violationNum +
        ", seriousViolationNum=" + seriousViolationNum +
        "}";
    }
}
