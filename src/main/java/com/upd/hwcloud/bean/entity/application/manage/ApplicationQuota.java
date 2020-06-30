package com.upd.hwcloud.bean.entity.application.manage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.upd.hwcloud.bean.entity.Files;
import lombok.Data;


/**
 * <p>
 * 
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
@Data
@TableName("TB_APPLICATION_QUOTA")
public class ApplicationQuota extends Model<ApplicationQuota> {

    private static final long serialVersionUID = 1L;

        /**
     * 主键id
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 申请单位
     */
         @TableField("APPLY_UNIT")
    private String applyUnit;

        /**
     * 申请人
     */
         @TableField("APPLY_PERSON")
    private String applyPerson;

        /**
     * 申请职位
     */
         @TableField("APPLY_POSITION")
    private String applyPosition;

        /**
     * 申请电话
     */
         @TableField("APPLY_PHONE")
    private String applyPhone;

        /**
     * 申请时间
     */
         @TableField("APPLY_TIME")
    private Date applyTime;

        /**
     * 申请描述
     */
         @TableField("APPLY_DESCRIPTION")
    private String applyDescription;

        /**
     * 关联附件id
     */
         @TableField("REF_FILES_ID")
    private String refFilesId;

        /**
     * 申请单号
     */
         @TableField("APPLY_NUMBER")
    private String applyNumber;

    @TableField(exist = false)
    private List<Files> filesList;

}
