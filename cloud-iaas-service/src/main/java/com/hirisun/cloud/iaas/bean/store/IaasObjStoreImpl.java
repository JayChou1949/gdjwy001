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
 * IaaS对象存储实施信息表
 */
@TableName("T_IAAS_OBJ_STORE_IMPL")
@Data
public class IaasObjStoreImpl implements Serializable{

	private static final long serialVersionUID = 825317242434287311L;

	@TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField("ACCESS_KEY")
    private String accessKey;

    @TableField("ACCESS_SECRET")
    private String accessSecret;

        /**
     * 账号
     */
    @TableField("ACCOUNT")
    private String account;

        /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

        /**
     * 容量大小GB
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

    @TableField("DOMAIN_NAME")
    private String domainName;

    @TableField("IP")
    private String ip;

    @TableField(exist = false)
    private List<IaasObjStoreImplInfo> ccxx;

}
