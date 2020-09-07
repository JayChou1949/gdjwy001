package com.hirisun.cloud.order.bean.reported;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hirisun.cloud.model.file.FilesVo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * IAAS资源上报项目信息
 */
@Data
@TableName("TB_IAAS_ZYSB_XMXX")
public class IaasZysbXmxx extends Model<IaasZysbXmxx> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 项目名称
     */
         @TableField("PROJECT_NAME")
    private String projectName;

        /**
     * 项目说明
     */
         @TableField("PROJECT_DESC")
    private String projectDesc;

        /**
     * 申请信息 id
     */
         @TableField("MASTER_ID")
    private String masterId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * IaaS资源
     */
    @TableField(exist = false)
    private List<ReportIaas> iaasList;

    /**
     * PaaS资源
     */
    @TableField(exist = false)
    private List<ReportPaas> paasList;

    /**
     * 桌面云资源
     */
    @TableField(exist = false)
    private List<ReportFusionAccess> fusionAccessList;


    /**
     * 特殊需求
     */
    @TableField(exist = false)
    private List<ReportSpecial> specialList;

    @TableField(exist = false)
    private List<FilesVo> fileList;


}
