package com.upd.hwcloud.bean.entity.application.manage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("TB_APPLICATION_RECORDS")
public class ApplicationRecords extends Model<ApplicationRecords> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 地市或警种
     */
         @TableField("AREA_OR_POLICE")
    private String areaOrPolice;

        /**
     * 用户配额数
     */
         @TableField("USER_QUOTA")
    private Integer userQuota;

        /**
     * 修改后的用户配额数
     */
         @TableField("NEW_USER_QUOTA")
    private Integer newUserQuota;

        /**
     * 修改人
     */
         @TableField("USER_NAME")
    private String userName;

        /**
     * 修改时间
     */
         @TableField("MODFIY_TIME")
    private LocalDateTime modfiyTime;

    /**
     * 关联表TB_APPLICATION_MANAGE的主键
     */
    @TableField("REF_APPLICATION_MANAGE_ID")
    private String refId;
}
