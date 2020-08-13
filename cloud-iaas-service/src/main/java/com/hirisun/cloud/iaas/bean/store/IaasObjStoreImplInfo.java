package com.hirisun.cloud.iaas.bean.store;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 对象存储实施信息扩展表
 */
@TableName("T_IAAS_OBJ_STORE_IMPL_INFO")
@Data
public class IaasObjStoreImplInfo implements Serializable {

	private static final long serialVersionUID = -27811703178192028L;

	@TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 应用组件
     */
    @TableField("COMPONENT")
    private String component;

        /**
     * 申请容量
     */
    @TableField("APPLY_STORAGE")
    private String applyStorage;

        /**
     * 组件描述
     */
    @TableField("DESCRIPTION")
    private String description;

        /**
     * 容量
     */
    @TableField("STORAGE")
    private String storage;

        /**
     * 桶名称
     */
    @TableField("BUCKET_NAME")
    private String bucketName;

        /**
     * 申请信息 id
     */
    @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

}
