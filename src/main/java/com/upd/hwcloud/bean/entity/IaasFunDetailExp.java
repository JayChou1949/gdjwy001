package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * IAAS功能详情展开解释
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
@TableName("TB_IAAS_FUN_DETAIL_EXP")
public class IaasFunDetailExp extends Model<IaasFunDetailExp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    @TableField("IAAS_ID")
    private String iaasId;


         @TableField("APP_ID")
    private String appId;

        /**
     * 标题
     */
         @TableField("TITLE")
    private String title;

        /**
     * 描述
     */
         @TableField("DESCRIPTION")
    private String description;

    @TableField("STATUS")
    private String status;

    @TableField("REMARK")
    private String remark;


    public String getId() {
        return id;
    }

    public IaasFunDetailExp setId(String id) {
        this.id = id;
        return this;
    }

    public String getIaasId() {
        return iaasId;
    }

    public IaasFunDetailExp setIaasId(String iaasId) {
        this.iaasId = iaasId;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public IaasFunDetailExp setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public IaasFunDetailExp setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IaasFunDetailExp setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public IaasFunDetailExp setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public IaasFunDetailExp setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasFunDetailExp{" +
        "id=" + id +
        ", iaasId=" + iaasId +
        ", appId=" + appId +
        ", title=" + title +
        ", description=" + description +
        ", status=" + status +
        ", remark=" + remark +
        "}";
    }
}
