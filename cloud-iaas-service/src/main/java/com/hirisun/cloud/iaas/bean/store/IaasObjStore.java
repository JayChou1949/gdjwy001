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
 * IaaS 对象存储申请信息
 */
@TableName("T_IAAS_OBJ_STORE")
@Data
public class IaasObjStore implements Serializable {

	private static final long serialVersionUID = -8534825969582659377L;

	@TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 应用组件
     */
    @TableField("COMPONENT")
    private String component;

        /**
     * 容量大小GB
     */
    @TableField("STORAGE")
    private String storage;

    /**
     * 组件描述
     */
    @TableField("DESCRIPTION")
    private String description;

        /**
     * 申请信息 id
     */
    @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

}
