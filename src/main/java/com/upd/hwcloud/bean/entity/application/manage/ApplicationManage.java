package com.upd.hwcloud.bean.entity.application.manage;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;


/**
 * <p>
 * 应用运营管理
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
@Data
@TableName("TB_APPLICATION_MANAGE")
public class ApplicationManage extends Model<ApplicationManage> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

        /**
     * 地市
     */
         @TableField("AREA")
    private String area;

        /**
     * 警种
     */
         @TableField("POLICE")
    private String police;

        /**
     * 用户配额数
     */
         @TableField("USER_QUOTA_NUM")
    private Long userQuotaNum;

        /**
     * 已有用户总数
     */
         @TableField("USER_TOTAL")
    private Long userTotal;

        /**
     * 已有网警用户数
     */
         @TableField("POLICE_USER_NUM")
    private Long policeUserNum;

        /**
     * 已有非网警用户数
     */
         @TableField("NO_POLICE_USER_NUM")
    private Long noPoliceUserNum;

        /**
     * 当前剩余配额数
     */
         @TableField("AVAILABLE_QUOTA_NUM")
    private Long availableQuotaNum;

        /**
     * 违规次数
     */
         @TableField("VIOLATION_NUM")
    private Long violationNum;

        /**
     * 严重违规次数
     */
         @TableField("SERIOUS_VIOLATION_NUM")
    private Long seriousViolationNum;


}
