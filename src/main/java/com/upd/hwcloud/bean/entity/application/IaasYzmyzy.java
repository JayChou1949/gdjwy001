package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * IaaS 云桌面云资源申请信息
 * </p>
 *
 * @author zwb
 * @since 2019-05-09
 */
@TableName("TB_IAAS_YZMYZY")
public class IaasYzmyzy extends Model<IaasYzmyzy> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 用途
     */
         @TableField("USE_TYPE")
    private String useType;

        /**
     * 归属
     */
         @TableField("BELONG")
    private String belong;


    /**
     * 申请类型 0：新增 1:扩容
     */
    @TableField("APPLY_TYPE")
         private Integer applyType;
         /**使用人员列表*/
@TableField(exist = false)
    private List<IaasYzmyzyUser> yzyUsers;
    public String getId() {
        return id;
    }

    public IaasYzmyzy setId(String id) {
        this.id = id;
        return this;
    }

    public List<IaasYzmyzyUser> getYzyUsers() {
        return yzyUsers;
    }

    public void setYzyUsers(List<IaasYzmyzyUser> yzyUsers) {
        this.yzyUsers = yzyUsers;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasYzmyzy setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasYzmyzy setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasYzmyzy setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getUseType() {
        return useType;
    }

    public IaasYzmyzy setUseType(String useType) {
        this.useType = useType;
        return this;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getBelong() {
        return belong;
    }

    public IaasYzmyzy setBelong(String belong) {
        this.belong = belong;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasYzmyzy{" +
        "id=" + id +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", useType=" + useType +
        ", belong=" + belong +
        "}";
    }
}
