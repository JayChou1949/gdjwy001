package com.hirisun.cloud.iaas.bean.store;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * IaaS 对象存储申请信息
 */
@TableName("T_IAAS_OBJ_STORE_INFO")
@Data
public class IaasObjStoreInfo implements Serializable {

	private static final long serialVersionUID = 4079379919901593669L;

	@TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 统一用户账号
     */
    @TableField("TYYH_ACCOUNT")
    private String tyyhAccount;

        /**
     * 统一用户密码
     */
    @TableField("TYYH_PASSWORD")
    private String tyyhPassword;

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

    /**
     * 存储信息
     */
    @TableField(exist = false)
    private List<IaasObjStore> ccxx;

}
